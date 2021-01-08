package com.cyberalaer.hybrid.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.AdTask;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.databinding.TaskListFragmentBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListFragment extends BottomSheetDialogFragment {

    private TaskListFragmentBinding bindRoot;
    private RecyclerView mList;

    private UserData userData;

    public static TaskListFragment newInstance() {
        final TaskListFragment fragment = new TaskListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindRoot = DataBindingUtil.inflate(inflater, R.layout.task_list_fragment, container, false);
        return bindRoot.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mList = (RecyclerView) view.findViewById(R.id.list);

        bindRoot.close.setOnClickListener(v -> dismiss());

        userData = UserDataUtil.instance().getUserData();
        getTaskList();
    }

    private void getTaskList() {
        if (userData == null)
            return;
        ApiUtil.apiService().adTasks(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, "2",
                new Callback<List<AdTask>>() {
                    @Override
                    public void onResponse(List<AdTask> tasks) {
                        if (tasks != null && tasks.size() > 0) {
                            showTaskList(tasks);
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private void showTaskList(List<AdTask> tasks) {
        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.setAdapter(new TaskAdapter(getActivity(), tasks, new TaskAdapter.OnTaskClickHandler() {
            @Override
            public void onTaskClick(AdTask task) {
                startTask(task.id);
            }
        }));
    }

    private void startTask(int taskId) {
        if (userData == null)
            return;
        ApiUtil.apiService().startTask(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, String.valueOf(taskId),
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        super.onResponse(response);
                        completeTask(taskId);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private void completeTask(int taskId) {
        if (userData == null)
            return;
        ApiUtil.apiService().completeTask(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, String.valueOf(taskId),
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        getTaskList();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

}