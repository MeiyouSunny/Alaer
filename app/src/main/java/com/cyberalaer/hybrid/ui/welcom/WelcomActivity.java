package com.cyberalaer.hybrid.ui.welcom;

import android.media.MediaPlayer;
import android.net.Uri;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseViewBindActivity;
import com.cyberalaer.hybrid.databinding.ActivityWelcomBinding;
import com.cyberalaer.hybrid.util.ViewUtil;

public class WelcomActivity extends BaseViewBindActivity<ActivityWelcomBinding> implements MediaPlayer.OnCompletionListener {

    @Override
    public void onViewCreated() {
        showWelcomeVideo();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_welcom;
    }

    private void showWelcomeVideo() {
        final Uri uriVideo = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.start);
        bindRoot.videoView.setVideoURI(uriVideo);
        bindRoot.videoView.start();

        bindRoot.videoView.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        ViewUtil.gotoActivity(this, GuideActivity.class);
    }

}