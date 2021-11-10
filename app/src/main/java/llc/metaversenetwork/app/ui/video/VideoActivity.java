package llc.metaversenetwork.app.ui.video;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.WindowManager;

import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.bean.AdVideo;

import java.util.Random;

import androidx.fragment.app.Fragment;
import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseViewBindActivity;
import llc.metaversenetwork.app.databinding.ActivityVideoBinding;
import llc.metaversenetwork.app.util.ViewUtil;
import llc.metaversenetwork.app.view.JZMediaSystemAssertFolder;

/**
 * 视频播放
 */
public class VideoActivity extends BaseViewBindActivity<ActivityVideoBinding> implements VideoPlayer.OnTimeChnaged {

    /* 加速 */
    public static final int REQUEST_CODE_SPEED_UP = 1;
    /* 收获 */
    public static final int REQUEST_CODE_FINISH_PRODUCE = 2;
    private AdVideo mAdVideo;

    @Override
    protected int layoutId() {
        return R.layout.activity_video;
    }

    public static void startPlayFroResult(Fragment fragment, AdVideo adVideo) {
        Intent intent = new Intent(fragment.getContext(), VideoActivity.class);
        intent.putExtra("adVideo", adVideo);
        fragment.startActivityForResult(intent, REQUEST_CODE_SPEED_UP);
    }

    public static void startPlayFroResult(Activity activity, AdVideo adVideo, int requestCode) {
        Intent intent = new Intent(activity, VideoActivity.class);
        intent.putExtra("adVideo", adVideo);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        JZUtils.clearSavedProgress(getContext(), null);

        mAdVideo = (AdVideo) getIntent().getSerializableExtra("adVideo");
//        if (mAdVideo == null)
//            return;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (mAdVideo != null && mAdVideo.orientation == 1)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ViewUtil.showImage(getApplicationContext(), bindRoot.player.posterImageView, AppConfig.TRAVEL_VIDEO_PIC2);
        bindRoot.player.progressBar.setEnabled(false);
        bindRoot.player.progressBar.setVisibility(View.INVISIBLE);

        bindRoot.player.currentTimeTextView.setVisibility(View.INVISIBLE);
        bindRoot.player.totalTimeTextView.setVisibility(View.INVISIBLE);
        bindRoot.player.bottomProgressBar.setVisibility(View.INVISIBLE);
        bindRoot.player.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        bindRoot.player.setUp(mAdVideo.image, mAdVideo.title);
        String title = "";
        String video = "welcom.mp4";
        if (mAdVideo != null) {
            title = mAdVideo.title;
            video = getVideoName();
        } else {
            bindRoot.time.setVisibility(View.GONE);
        }
        bindRoot.player.setUp(video, title, JzvdStd.SCREEN_NORMAL, JZMediaSystemAssertFolder.class);

        bindRoot.player.setScreenFullscreen();
        bindRoot.player.setOnCompleteListener(new VideoPlayer.OnCompleteListener() {
            @Override
            public void onComplete() {
                setResult(RESULT_OK);
                finish();
            }
        });
        bindRoot.player.setTimeListener(this);
        bindRoot.player.startVideo();
    }

    private String getVideoName() {
        final String[] videos = {"task-1.mp4", "task-2.mp4", "task-3.mp4"};
        Random rand = new Random();
        int index = rand.nextInt(3);
        return videos[index];
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private int TIME_COUNT_MAX = 30;

    @Override
    public void onTimeChanged(int seconds) {
        if (seconds > 30) {
            bindRoot.time.setVisibility(View.GONE);
            bindRoot.layoutClose.setVisibility(View.VISIBLE);
            return;
        }
        bindRoot.time.setText((TIME_COUNT_MAX - seconds) + "s");
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.close:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

}