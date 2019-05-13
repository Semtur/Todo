package com.example.todo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class TaskRenameDialogFragment extends DialogFragment {
    private static final String TAG = "TaskRenameDialog";
    public static final String ARG_FOLDER_NAME = "argument_folder_name";
    public static final String ARG_TASK_NAME = "argument_task_name";
    private DialogDismissListener mListener;

    private TextInputEditText tietFolderName;
    private Button btnOk;

    public static TaskRenameDialogFragment newInstance(String folderName, String taskName) {
        Bundle args = new Bundle();
        args.putString(ARG_FOLDER_NAME, folderName);
        args.putString(ARG_TASK_NAME, taskName);
        TaskRenameDialogFragment dialog = new TaskRenameDialogFragment();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (DialogDismissListener) getTargetFragment();
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
        final String taskName = getArguments().getString(ARG_TASK_NAME);
        tietFolderName.setText(taskName);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newTaskName = tietFolderName.getText().toString();
                if (newTaskName.equals(taskName)) {
                    dismiss();
                }
                final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                final CollectionReference colRef = FirebaseFirestore.getInstance()
                        .collection(userId)
                        .document(folderName)
                        .collection("tasks");
                colRef.document(taskName)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Map<String, Object> documentData = document.getData();
                                        colRef.document(newTaskName)
                                                .set(documentData);
                                        colRef.document(taskName)
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
