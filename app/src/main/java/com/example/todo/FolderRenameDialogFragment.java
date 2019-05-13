package com.example.todo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FolderRenameDialogFragment extends DialogFragment {
    private static final String TAG = "FolderRenameDialog";
    public static final String ARG_FOLDER_NAME = "argument_folder_name";
    private DialogDismissListener mListener;

    private TextInputEditText tietFolderName;
    private Button btnOk;

    public static FolderRenameDialogFragment newInstance(String folderName) {
        Bundle args = new Bundle();
        args.putString(ARG_FOLDER_NAME, folderName);
        FolderRenameDialogFragment dialog = new FolderRenameDialogFragment();
        dialog.setArguments(args);
        return dialog;
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
        View v = inflater.inflate(R.layout.folder_rename, container, false);

        tietFolderName = v.findViewById(R.id.tietFolderName);
        btnOk = v.findViewById(R.id.btnOk);

        final String folderName = getArguments().getString(ARG_FOLDER_NAME);
        tietFolderName.setText(folderName);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newFolderName = tietFolderName.getText().toString();
                if (newFolderName.equals(folderName)) {
                    dismiss();
                }
                final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection(userId).document(folderName);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String, Object> documentData = document.getData();
                                db.collection(userId)
                                        .document(newFolderName)
                                        .set(documentData);
                                db.collection(userId)
                                        .document(folderName)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                mListener.onDismiss();
                                                dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error deleting document", e);
                                            }
                                        });
                            }
                        }
                    }
                });
            }
        });
        return v;
    }
}
