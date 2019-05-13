package com.example.todo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class FolderAdditionDialogFragment extends DialogFragment {
    private static final String TAG = "FolderAdditionDialog";

    private Toolbar localToolbar;
    private Button btnOk, btnAddMember;
    private TextInputEditText tietFolderName;
    private RecyclerView rvMembers;

    private MemberAdapter memberAdapter;

    private DialogDismissListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (DialogDismissListener) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "ClassCastException: " + context.toString() + " must implement DialogDismissListener!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fullscreen_dialog_create_folder, container, false);

        localToolbar = v.findViewById(R.id.localToolbar);
        btnOk = v.findViewById(R.id.btnOk);
        tietFolderName = v.findViewById(R.id.tietFolderName);
        btnAddMember = v.findViewById(R.id.btnAddMember);
        rvMembers = v.findViewById(R.id.rvMembers);

        localToolbar.setNavigationIcon(R.drawable.ic_close_white);
        localToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseFirestore.getInstance()
                        .collection(userId)
                        .document(tietFolderName.getText().toString())
                        .set(new HashMap<String, Object>());
                mListener.onDismiss();
                dismiss();
            }
        });

        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchMembersDialogFragment dialog = new SearchMembersDialogFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "SearchMembersDialogFragment");
            }
        });

        rvMembers.setLayoutManager(new LinearLayoutManager(getActivity()));
        memberAdapter = new MemberAdapter(getActivity());
        rvMembers.setAdapter(memberAdapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}
