package com.example.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchMembersDialogFragment extends DialogFragment {

    Toolbar localToolbar;
    Button btnOk;
    SearchView searchView;
    RecyclerView rvMembers;

    MemberChooseAdapter memberChooseAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fullscreen_dialog_search_member, container, false);

        localToolbar = v.findViewById(R.id.localToolbar);
        btnOk = v.findViewById(R.id.btnOk);
        searchView = v.findViewById(R.id.searchView);
        rvMembers = v.findViewById(R.id.rvMembers);

        localToolbar.setNavigationIcon(R.drawable.ic_close_white);
        localToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        rvMembers.setLayoutManager(new LinearLayoutManager(getActivity()));
        memberChooseAdapter = new MemberChooseAdapter(getActivity());
        rvMembers.setAdapter(memberChooseAdapter);

        return v;
    }

    @Override
    public void onStart(){
        super.onStart();

    }


}