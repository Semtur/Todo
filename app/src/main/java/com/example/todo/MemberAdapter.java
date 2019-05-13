package com.example.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ivbaranov.mli.MaterialLetterIcon;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolder> {

private Context mContext;

        MemberAdapter(Context context) {
        mContext = context;
        }

public static class MyViewHolder extends RecyclerView.ViewHolder {
    public View v;
    MaterialLetterIcon mliUser;
    TextView tvNameAndSurname, tvNick;
    AppCompatImageView acivCancelMember;
    MyViewHolder(View v) {
        super(v);
        mliUser = v.findViewById(R.id.mliUser);
        tvNameAndSurname = v.findViewById(R.id.tvNameAndSurname);
        tvNick = v.findViewById(R.id.tvNick);
        acivCancelMember = v.findViewById(R.id.acivCancelMember);
    }
}

    @Override
    public MemberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_item, parent, false);

        return new MemberAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MemberAdapter.MyViewHolder holder, int position) {
        String name = "Name";
        String letter = String.valueOf(name.charAt(0));
        holder.mliUser.setLetter(letter);
        holder.tvNameAndSurname.setText("Name Surname");
        holder.tvNick.setText("Nickname");
        holder.acivCancelMember.setVisibility(View.VISIBLE);
        holder.acivCancelMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle(mContext.getResources().getString(R.string.confirmation));
                alertDialog.setMessage(
                        "Вы действительно хотите запретить пользователю "
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
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Запретить",
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