package com.example.todo;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FolderActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatImageView acivMenu;
    private RecyclerView rvFolderList;
    private FloatingActionButton fabAdd;

    private FolderAdapter folderAdapter;

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
        popup.show();
    }

}
