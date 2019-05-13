package com.example.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.MyViewHolder> {

    private Context mContext;

    SubTaskAdapter(Context context) {
        mContext = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View v;
        CheckBox checkBox;
        TextView tvTitle;
        AppCompatImageView acivCancel;

        MyViewHolder(View v) {
            super(v);
            checkBox = v.findViewById(R.id.checkBox);
            tvTitle = v.findViewById(R.id.tvTitle);
            acivCancel = v.findViewById(R.id.acivCancel);
        }
    }

    @Override
    public SubTaskAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_task_item, parent, false);
        return new SubTaskAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SubTaskAdapter.MyViewHolder holder, int position) {
        holder.tvTitle.setText("Subtask " + position);
        holder.acivCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle(mContext.getResources().getString(R.string.confirmation));
                alertDialog.setMessage(
                        "Вы действительно хотите удалить подзадачу?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, mContext.getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Да, удалить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}