package com.example.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskIsDoneFragment extends Fragment implements DialogDismissListener{

    private RecyclerView rvTask;
    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.task_list_fragment, container, false);

        rvTask = v.findViewById(R.id.rvTask);
        String folderName = getArguments().getString(FolderContentActivity.ARG_FOLDER_NAME);
        taskAdapter = new TaskAdapter(getActivity(), getActivity().getSupportFragmentManager(), "done", folderName);
        rvTask.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTask.setAdapter(taskAdapter);

        return v;
    }

    @Override
    public void onDismiss() {
        taskAdapter.getTasks();
    }
}