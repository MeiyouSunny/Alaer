package com.cyberalaer.hybrid.ui.welcom;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.databinding.ActivityWelcomBinding;
import com.cyberalaer.hybrid.util.ViewUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class WelcomActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    ActivityWelcomBinding mViewRoot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewRoot = DataBindingUtil.setContentView(this, R.layout.activity_welcom);
        showWelcomeVideo();
    }

    private void showWelcomeVideo() {
        final Uri uriVideo = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.start);
        mViewRoot.videoView.setVideoURI(uriVideo);
        mViewRoot.videoView.start();

        mViewRoot.videoView.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        ViewUtil.gotoActivity(this, GuideActivity.class);
    }

}