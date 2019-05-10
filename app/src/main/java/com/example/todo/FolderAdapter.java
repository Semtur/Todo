package com.example.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.MyViewHolder> {

    private Context mContext;

    FolderAdapter(Context context) {
        mContext = context;
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
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tvTitle.setText("Title " + position);
        holder.acivMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                showPopup(holder.acivMenu);
            }
        });

    }


    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(mContext, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.folder_toolbar_menu, popup.getMenu());
        popup.show();
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}