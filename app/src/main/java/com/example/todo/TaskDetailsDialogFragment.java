package com.example.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class TaskDetailsDialogFragment extends DialogFragment {

    Toolbar localToolbar;
    TextView tvTaskTitle, tvDescription, tvNotificationDate, tvNotificationText;
    RecyclerView rvSubTask, rvAttachedFile;
    Switch switchIsNotification;
    CheckBox checkBoxTask;

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
        View v = inflater.inflate(R.layout.fullscreen_dialog_task_details, container, false);

        localToolbar = v.findViewById(R.id.localToolbar);
        tvTaskTitle = v.findViewById(R.id.tvTaskTitle);
        checkBoxTask = v.findViewById(R.id.checkBoxTask);
        rvSubTask = v.findViewById(R.id.rvSubTask);
        tvDescription = v.findViewById(R.id.tvDescription);
        rvAttachedFile = v.findViewById(R.id.rvAttachedFile);
        switchIsNotification = v.findViewById(R.id.switchIsNotification);
        tvNotificationDate = v.findViewById(R.id.tvNotificationDate);
        tvNotificationText = v.findViewById(R.id.tvNotificationText);

        localToolbar.setNavigationIcon(R.drawable.ic_close_white);
        localToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        tvTaskTitle.setText("My cool Task");
        tvDescription.setText("Важные моменты, объяснение и дополнения по выполнению задач. Примечания описание и другой важный текст.");

        tvNotificationDate.setText("14/05/2019");
        tvNotificationText.setText("К дедлайну осталась неделя.");

        subTaskAdapter = new SubTaskAdapter(getActivity());
        rvSubTask.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSubTask.setAdapter(subTaskAdapter);

        attachedFileAdapter = new AttachedFileAdapter(getActivity());
        rvAttachedFile.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAttachedFile.setAdapter(attachedFileAdapter);

        return v;
    }

}
