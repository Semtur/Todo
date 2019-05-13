package com.example.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class AttachedFileAdapter extends RecyclerView.Adapter<AttachedFileAdapter.MyViewHolder> {

    private Context mContext;

    AttachedFileAdapter(Context context) {
        mContext = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View v;
        TextView tvFileName;
        AppCompatImageView acivCancel;

        MyViewHolder(View v) {
            super(v);
            tvFileName = v.findViewById(R.id.tvFileName);
            acivCancel = v.findViewById(R.id.acivCancel);
        }
    }

    @Override
    public AttachedFileAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attached_file_item, parent, false);
        return new AttachedFileAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AttachedFileAdapter.MyViewHolder holder, int position) {
        holder.tvFileName.setText("File " + position + ".png");
        holder.acivCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle(mContext.getResources().getString(R.string.confirmation));
                alertDialog.setMessage(
                        "Вы действительно хотите удалить файл?");
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
