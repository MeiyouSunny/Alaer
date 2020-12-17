package com.cyberalaer.hybrid.ui.welcom;

import android.media.MediaPlayer;
import android.net.Uri;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseViewBindActivity;
import com.cyberalaer.hybrid.databinding.ActivityWelcomBinding;
import com.cyberalaer.hybrid.ui.home.HomeActivity;
import com.cyberalaer.hybrid.util.ViewUtil;

import likly.dollar.$;

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
        boolean needShowGuide = $.config().getBoolean("showGuide", true);
        $.config().putBoolean("showGuide", false);
        if (needShowGuide)
            ViewUtil.gotoActivity(this, GuideActivity.class);
        else
            ViewUtil.gotoActivity(this, HomeActivity.class);
    }

}