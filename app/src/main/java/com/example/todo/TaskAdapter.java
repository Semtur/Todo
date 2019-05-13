package com.example.todo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
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
import java.util.Map;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    private static  final String TAG = "TaskAdapter";

    private Context mContext;
    private FragmentManager myFm;
    private String mTaskState;
    private List<String> mTasks;
    private String mFolderName;
    private String mUserId;


    TaskAdapter(Context context, FragmentManager fm, String taskState, String folderName) {
        mContext = context;
        myFm = fm;
        mTaskState = taskState;
        mTasks = new ArrayList<>();
        mFolderName = folderName;
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getTasks();
    }

    public void getTasks() {
        mTasks.clear();
        FirebaseFirestore.getInstance()
                .collection(mUserId)
                .document(mFolderName)
                .collection("tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                if (mTaskState.isEmpty()) {
                                    mTasks.add(document.getId());
                                } else if (data.get("state").toString().equals(mTaskState)) {
                                    mTasks.add(document.getId());
                                }
                            }
                            notifyDataSetChanged();
                        }
                    }
                });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View v;
        CheckBox checkBox;
        TextView tvTitle;
        AppCompatImageView acivMenu;
        MyViewHolder(View v) {
            super(v);
            checkBox = v.findViewById(R.id.checkBox);
            tvTitle = v.findViewById(R.id.tvTitle);
            acivMenu = v.findViewById(R.id.acivMenu);
        }
    }

    @Override
    public TaskAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);

        return new TaskAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TaskAdapter.MyViewHolder holder, final int position) {
        holder.tvTitle.setText(mTasks.get(position));
        holder.acivMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                showPopup(holder.acivMenu, position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDetailsDialogFragment dialog = new TaskDetailsDialogFragment();
                dialog.show(myFm, "TaskDetailsDialogFragment");
            }
        });
    }

    private void showPopup(View v, final int i) {
        PopupMenu popup = new PopupMenu(mContext, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.folder_context_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_rename:
                        DialogFragment dialog = TaskRenameDialogFragment.newInstance(mFolderName, mTasks.get(i));
                        dialog.setTargetFragment(myFm.findFragmentById(R.id.viewPager), 0);
                        dialog.show(myFm, "TaskRenameDialogFragment");
                        return true;
                    case R.id.menu_item_delete:
                        FirebaseFirestore.getInstance()
                                .collection(mUserId)
                                .document(mFolderName)
                                .collection("tasks")
                                .document(mTasks.get(i))
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mTasks.remove(i);
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
        return mTasks.size();
    }
}