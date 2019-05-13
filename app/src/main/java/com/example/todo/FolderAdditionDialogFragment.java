package com.example.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;


public class FolderAdditionDialogFragment extends DialogFragment {

    Toolbar localToolbar;
    Button btnOk, btnAddMember;
    TextInputEditText tietFolderName;
    RecyclerView rvMembers;

    MemberAdapter memberAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
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
    public void onStart(){
        super.onStart();

    }


}
