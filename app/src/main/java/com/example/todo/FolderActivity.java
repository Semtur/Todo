package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FolderActivity extends AppCompatActivity implements View.OnClickListener, DialogDismissListener {
    private static final String TAG = "FolderActivity";

    private AppCompatImageView acivMenu;
    private RecyclerView rvFolderList;
    private FloatingActionButton fabAdd;

    private FolderAdapter folderAdapter;

    private String mUserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);

        acivMenu = findViewById(R.id.acivMenu);
        acivMenu.setOnClickListener(this);
        rvFolderList = findViewById(R.id.rvFolderList);
        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(this);


        rvFolderList.setLayoutManager(new LinearLayoutManager(this));
        folderAdapter = new FolderAdapter(this);
        rvFolderList.setAdapter(folderAdapter);
        isAdmin();
    }

    private void isAdmin() {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        mUserRole = document.get("role").toString();
                        showPopup(acivMenu);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acivMenu:
                showPopup(v);
                break;
            case R.id.fabAdd:
                DialogFragment dialog = new FolderAdditionDialogFragment();
                dialog.show(getSupportFragmentManager(), "FolderAdditionDialogFragment");
                break;
        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.folder_toolbar_menu, popup.getMenu());
        if (mUserRole != null && mUserRole.equals("admin")) {
            popup.getMenu().findItem(R.id.menu_item_registration_new_user).setVisible(true);
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_registration_new_user:
                        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                        return true;
                    case R.id.menu_item_exit:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        return true;
                    default:
                        return false;

                }
            }
        });
        popup.show();
    }

    @Override
    public void onDismiss() {
        folderAdapter.getData();
    }
}
