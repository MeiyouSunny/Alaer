package com.cyberalaer.hybrid.ui.video;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.WindowManager;

import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.bean.AdVideo;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseViewBindActivity;
import com.cyberalaer.hybrid.databinding.ActivityVideoBinding;
import com.cyberalaer.hybrid.util.ViewUtil;

import androidx.fragment.app.Fragment;
import cn.jzvd.Jzvd;

/**
 * 视频播放
 */
public class VideoActivity extends BaseViewBindActivity<ActivityVideoBinding> {

    public static final int REQUEST_CODE = 1;
    private AdVideo mAdVideo;

    @Override
    protected int layoutId() {
        return R.layout.activity_video;
    }

    public static void startPlayFroResult(Fragment fragment, AdVideo adVideo) {
        Intent intent = new Intent(fragment.getContext(), VideoActivity.class);
        intent.putExtra("adVideo", adVideo);
        fragment.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        mAdVideo = (AdVideo) getIntent().getSerializableExtra("adVideo");
        if (mAdVideo == null)
            return;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (mAdVideo.orientation == 1)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ViewUtil.showImage(getApplicationContext(), bindRoot.player.posterImageView, AppConfig.TRAVEL_VIDEO_PIC2);
        bindRoot.player.progressBar.setEnabled(false);
        bindRoot.player.setUp(mAdVideo.image, mAdVideo.title);
        bindRoot.player.setScreenFullscreen();
        bindRoot.player.setOnCompleteListener(new VideoPlayer.OnCompleteListener() {
            @Override
            public void onComplete() {
                setResult(RESULT_OK);
                finish();
            }
        });
        bindRoot.player.startVideo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}