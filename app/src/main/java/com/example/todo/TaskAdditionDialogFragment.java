package com.example.todo;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

public class TaskAdditionDialogFragment extends DialogFragment {

    Toolbar localToolbar;
    Button btnOk, btnAddSubtitle, btnAttachFile;
    TextInputEditText tietTaskName, tietNewSubTask, tietDescription, tietDate, tietNotificationText;
    RecyclerView rvSubTask, rvAttachedFile;
    Switch switchIsNotification;

    SubTaskAdapter subTaskAdapter;
    AttachedFileAdapter attachedFileAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fullscreen_dialog_task_addition, container, false);

        localToolbar = v.findViewById(R.id.localToolbar);
        btnOk = v.findViewById(R.id.btnOk);
        tietTaskName = v.findViewById(R.id.tietTaskName);
        rvSubTask = v.findViewById(R.id.rvSubTask);
        tietNewSubTask = v.findViewById(R.id.tietNewSubTask);
        btnAddSubtitle = v.findViewById(R.id.btnAddSubtitle);
        tietDescription = v.findViewById(R.id.tietDescription);
        rvAttachedFile = v.findViewById(R.id.rvAttachedFile);
        btnAttachFile = v.findViewById(R.id.btnAttachFile);
        switchIsNotification = v.findViewById(R.id.switchIsNotification);
        tietDate = v.findViewById(R.id.tietDate);
        tietNotificationText = v.findViewById(R.id.tietNotificationText);

        localToolbar.setNavigationIcon(R.drawable.ic_close_white);
        localToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });


        tietDate.setVisibility(View.GONE);
        tietNotificationText.setVisibility(View.GONE);

        switchIsNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tietDate.setVisibility(View.VISIBLE);
                    tietNotificationText.setVisibility(View.VISIBLE);
                } else {
                    tietDate.setVisibility(View.GONE);
                    tietNotificationText.setVisibility(View.GONE);
                }
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        subTaskAdapter = new SubTaskAdapter(getActivity());
        rvSubTask.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSubTask.setAdapter(subTaskAdapter);

        attachedFileAdapter = new AttachedFileAdapter(getActivity());
        rvAttachedFile.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAttachedFile.setAdapter(attachedFileAdapter);

        return v;
    }

}