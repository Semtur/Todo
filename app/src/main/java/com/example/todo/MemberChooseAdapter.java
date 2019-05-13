package com.example.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ivbaranov.mli.MaterialLetterIcon;

public class MemberChooseAdapter extends RecyclerView.Adapter<MemberChooseAdapter.MyViewHolder> {

private Context mContext;

        MemberChooseAdapter(Context context) {
        mContext = context;
        }

public static class MyViewHolder extends RecyclerView.ViewHolder {
    public View v;
    MaterialLetterIcon mliUser;
    TextView tvNameAndSurname, tvNick;
    MyViewHolder(View v) {
        super(v);
        mliUser = v.findViewById(R.id.mliUser);
        tvNameAndSurname = v.findViewById(R.id.tvNameAndSurname);
        tvNick = v.findViewById(R.id.tvNick);
    }
}

    @Override
    public MemberChooseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_item, parent, false);

        return new MemberChooseAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MemberChooseAdapter.MyViewHolder holder, int position) {
        String name = "Name";
        String letter = String.valueOf(name.charAt(0));
        holder.mliUser.setLetter(letter);
        holder.tvNameAndSurname.setText("Name Surname");
        holder.tvNick.setText("Nickname");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle(mContext.getResources().getString(R.string.confirmation));
                alertDialog.setMessage(
                        "Предоставить пользователю "
                        + "Name"
                        + " "
                        + "Surname"
                        + " доступ к папке "
                        +  "\"Folder title\""
                        + "?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, mContext.getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Предоставить",
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
        return 10;
    }
}