package llc.metaversenetwork.app.ui.video;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.WindowManager;

import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.bean.AdVideo;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseViewBindActivity;
import llc.metaversenetwork.app.databinding.ActivityVideoBinding;
import llc.metaversenetwork.app.util.ViewUtil;

import androidx.fragment.app.Fragment;
import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;

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
        if (mAdVideo == null)
            return;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (mAdVideo.orientation == 1)
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

        bindRoot.player.setUp(mAdVideo.image, mAdVideo.title);
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