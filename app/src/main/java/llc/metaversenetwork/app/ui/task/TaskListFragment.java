package llc.metaversenetwork.app.ui.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.MessageQueue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.AdTask;
import com.alaer.lib.api.bean.AdVideo;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.databinding.TaskListFragmentBinding;
import llc.metaversenetwork.app.ui.video.VideoActivity;
import llc.metaversenetwork.app.util.CollectionUtils;

public class TaskListFragment extends BottomSheetDialogFragment {

    private TaskListFragmentBinding bindRoot;
    private RecyclerView mList;
    private TaskAdapter adapter;
    private UserData userData;
    private AdTask mAdTask;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        new Handler().getLooper().getQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                ((View) bindRoot.getRoot().getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
                return false;
            }
        });

        mList = (RecyclerView) view.findViewById(R.id.list);

        bindRoot.close.setOnClickListener(v -> dismiss());

        userData = UserDataUtil.instance().getUserData();
        getTaskList();
    }

    private void getTaskList() {
        if (userData == null)
            return;
        ApiUtil.apiService().queryTasks(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<List<AdTask>>() {
                    @Override
                    public void onResponse(List<AdTask> tasks) {
                        if (!CollectionUtils.isEmpty(tasks)) {
                            filterAdTask(tasks);
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
        if (CollectionUtils.isEmpty(tasks)) {
            $.toast().text(R.string.no_tasks).show();
            return;
        }
        if (adapter == null) {
            adapter = new TaskAdapter(getActivity(), tasks, new TaskAdapter.OnTaskClickHandler() {
                @Override
                public void onTaskClick(AdTask task) {
                    mAdTask = task;
                    startTask(task);
                }
            });
            mList.setLayoutManager(new LinearLayoutManager(getContext()));
            mList.setAdapter(adapter);
        } else {
            adapter.setData(tasks);
        }
    }

    // 获取公告视频
    private void getAdVideos() {
        ApiUtil.apiService().getAdVideo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, 1,
                new Callback<List<AdVideo>>() {
                    @Override
                    public void onResponse(List<AdVideo> adVideos) {
                        if (adVideos != null && !CollectionUtils.isEmpty(adVideos)) {
                            startPlayAdVideo(adVideos.get(0));
                        }
                    }
                });
    }

    private void startTask(AdTask task) {
        ApiUtil.apiService().startTask(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, String.valueOf(task.id),
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        getAdVideos();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

    // 播放公告
    private void startPlayAdVideo(AdVideo adVideo) {
        VideoActivity.startPlayFroResult(this, adVideo);
    }

    private void completeTask(int taskId) {
        if (userData == null)
            return;
        ApiUtil.apiService().completeTask(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, String.valueOf(taskId),
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        $.toast().text(R.string.task_complete).show();
                        getTaskList();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == VideoActivity.REQUEST_CODE_SPEED_UP && resultCode == Activity.RESULT_OK) {
            // 播放完成,完成任务
            completeTask(mAdTask.id);
        } else {
            $.toast().text(R.string.task_do_failed).show();
        }
    }

    private void filterAdTask(List<AdTask> tasks) {
        if (!CollectionUtils.isEmpty(tasks)) {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).action == 2) {
                    tasks.remove(i);
                    i--;
                }
            }
        }
    }

}