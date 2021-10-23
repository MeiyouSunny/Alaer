package llc.metaversenetwork.app.ui.welcom;

import android.media.MediaPlayer;
import android.net.Uri;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseViewBindActivity;
import llc.metaversenetwork.app.databinding.ActivityWelcomBinding;
import llc.metaversenetwork.app.ui.home.HomeActivity;
import llc.metaversenetwork.app.util.ViewUtil;

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