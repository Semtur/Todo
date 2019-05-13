package com.example.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskInProcessFragment extends Fragment {

    RecyclerView rvTask;
    TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.task_list_fragment, container, false);

        rvTask = v.findViewById(R.id.rvTask);
        taskAdapter = new TaskAdapter(getActivity());
        rvTask.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTask.setAdapter(taskAdapter);

        return v;
    }
}
