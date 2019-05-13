package com.example.todo;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyViewHolder> {
    private static final String TAG = "FolderAdapter";

    private AppCompatActivity mActivity;
    private List<String> mData;
    private String mUserId;

    FolderAdapter(AppCompatActivity activity) {
        mActivity = activity;
        mData = new ArrayList<>();
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getData();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View v;
        TextView tvTitle;
        AppCompatImageView acivMenu;

        MyViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            acivMenu = v.findViewById(R.id.acivMenu);
        }
    }

    @Override
    public FolderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.folder_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String folderName = mData.get(position);
        holder.tvTitle.setText(folderName);
        holder.acivMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showPopup(holder.acivMenu, position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, FolderContentActivity.class);
                intent.putExtra("folder_name", folderName);
                mActivity.startActivity(intent);
            }
        });
    }


    private void showPopup(View v, final int i) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.folder_context_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_rename:
                        DialogFragment dialog = FolderRenameDialogFragment.newInstance(mData.get(i));
                        dialog.show(mActivity.getSupportFragmentManager(), "FolderRenameDialogFragment");
                        return true;
                    case R.id.menu_item_delete:
                        FirebaseFirestore.getInstance()
                                .collection(mUserId)
                                .document(mData.get(i))
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mData.remove(i);
                                        notifyItemRemoved(i);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                        return true;
                    default:
                        return false;

                }
            }
        });
        popup.show();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void getData() {
        mData.clear();
        FirebaseFirestore.getInstance()
                .collection(mUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                mData.add(document.getId());
                            }
                            notifyDataSetChanged();
                        }
                    }
                });
    }
}