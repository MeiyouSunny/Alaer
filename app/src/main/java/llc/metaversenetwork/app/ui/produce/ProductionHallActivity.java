package llc.metaversenetwork.app.ui.produce;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.AdTask;
import com.alaer.lib.api.bean.AdVideo;
import com.alaer.lib.api.bean.TeamInfo;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.meiyou.mvp.MvpBinder;

import java.util.List;

import androidx.annotation.Nullable;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityProductionHallBinding;
import llc.metaversenetwork.app.ui.video.VideoActivity;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.NeteaseCaptcha;
import llc.metaversenetwork.app.util.TimeUtil;
import llc.metaversenetwork.app.util.ToastUtil;
import llc.metaversenetwork.app.util.ViewUtil;
import llc.metaversenetwork.app.view.TextProgressBar;

/**
 * 生产大厅
 */
@MvpBinder()
public class ProductionHallActivity extends BaseTitleActivity<ActivityProductionHallBinding> implements TextProgressBar.ProgressChange {

    private UserData mUserData;
    private TeamInfo mTeamInfo;
    private boolean mProgressComplete;
    private ImageView[] mStepImages;
    private AdTask mAdTask;

    @Override
    protected int layoutId() {
        return R.layout.activity_production_hall;
    }

    @Override
    protected int titleResId() {
        return R.string.exploring_metauniverse;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        int[] bgResIds = new int[]{R.drawable.img_produce_step0, R.drawable.img_produce_step2,
                R.drawable.img_produce_step3, R.drawable.img_produce_step4};
        int[] bgBigResIds = new int[]{R.drawable.bg_produce_1, R.drawable.bg_produce_2,
                R.drawable.bg_produce_3, R.drawable.bg_produce_4};
        mStepImages = new ImageView[]{bindRoot.icStepFertilize, bindRoot.icStepWater, bindRoot.icStepDisinsection, bindRoot.icStepReap};

        bindRoot.setBgBigPics(bgBigResIds);
        bindRoot.setBgPics(bgResIds);
        bindRoot.setStep(0);
        bindRoot.setStepHandler(mProduceStepHandler);
        bindRoot.progress.setProgressListener(this);

        mUserData = UserDataUtil.instance().getUserData();
        queryCurrentInfos();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        queryCurrentInfos();
    }

    // 获取任务列表
    private void getAdTasks() {
        ApiUtil.apiService().adTasks(mUserData.uuid, String.valueOf(mUserData.uid), mUserData.token, AppConfig.DIAMOND_CURRENCY, "2",
                new Callback<List<AdTask>>() {
                    @Override
                    public void onResponse(List<AdTask> tasks) {
                        if (tasks != null && tasks.size() > 0) {
                            startTask(tasks.get(0).id);
                            mAdTask = tasks.get(0);
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });

    }

    // 加速
    private void speedUp(int taskId) {
        ApiUtil.apiService().completeTask(mUserData.uuid, String.valueOf(mUserData.uid), mUserData.token, AppConfig.DIAMOND_CURRENCY, String.valueOf(taskId),
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        ToastUtil.text("加速完成!").show();
                        queryCurrentInfos();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });
    }

    private void startTask(int taskId) {
        ApiUtil.apiService().startTask(mUserData.uuid, String.valueOf(mUserData.uid), mUserData.token, AppConfig.DIAMOND_CURRENCY, String.valueOf(taskId),
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        getAdVideos(VideoActivity.REQUEST_CODE_SPEED_UP);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });
    }

    // 获取公告视频
    private void getAdVideos(int type) {
        ApiUtil.apiService().getAdVideo(mUserData.uuid, String.valueOf(mUserData.uid), mUserData.token, AppConfig.DIAMOND_CURRENCY, 1,
                new Callback<List<AdVideo>>() {
                    @Override
                    public void onResponse(List<AdVideo> adVideos) {
                        if (adVideos != null && !CollectionUtils.isEmpty(adVideos)) {
                            startPlayAdVideo(adVideos.get(0), type);
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });
    }

    // 播放公告
    private void startPlayAdVideo(AdVideo adVideo, int requestCode) {
        VideoActivity.startPlayFroResult(this, adVideo, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == VideoActivity.REQUEST_CODE_SPEED_UP) {
                // 加速
                speedUp(mAdTask.id);
            } else if (requestCode == VideoActivity.REQUEST_CODE_FINISH_PRODUCE) {
                // 收获
                if (!TextUtils.isEmpty(mVlidate))
                    finishProduce(mVlidate);
            }
        } else {
            ToastUtil.text("任務未完成!").show();
        }
    }

    private ProduceStep mProduceStepHandler = new ProduceStep() {
        @Override
        public void onStep(int step) {
            if (mTeamInfo == null)
                return;

            if (step == -1) {
                // 领取
                gotoSeedStore();
                return;
            }

            if (mTeamInfo.virtualMiner.todayStatus == 2) {
                ToastUtil.text(R.string.pls_produce_tomorrow).show();
                return;
            }
            if (mTeamInfo.virtualMiner.todayStatus == 0) {
                // 开始种植
                produceStart();
                return;
            }
            if ((mTeamInfo.virtualMiner.step == 2 && mProgressComplete) || mTeamInfo.virtualMiner.step >= 3) {
                // 收获
                verifyCode();
            } else {
                // 更新步骤
                updateProduceStep();
            }
        }
    };

    private void produceStart() {
        ApiUtil.apiService().produceStart(mUserData.uuid, String.valueOf(mUserData.uid), mUserData.token,
                AppConfig.DIAMOND_CURRENCY,
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        queryCurrentInfos();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });
    }

    @Override
    public void onProgressComplete() {
        // 当前进度倒计时完
        mProgressComplete = true;
        setUiStep();
    }

    public interface ProduceStep {
        void onStep(int step);
    }

    @Override
    public void click(android.view.View view) {
        switch (view.getId()) {
            case R.id.toBase:
                gotoSeedStore();
                break;
            case R.id.speedUp:
                if (mTeamInfo != null && mTeamInfo.virtualMiner.todayStatus == 2) {
                    ToastUtil.text(R.string.pls_produce_tomorrow).show();
                    return;
                }
                getAdTasks();
                break;
        }
    }

    private void gotoSeedStore() {
        if (mTeamInfo != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("claimNewbieMiner", mTeamInfo.claimNewbieMiner);
            bundle.putInt("index", 1);
            ViewUtil.gotoActivity(this, SeedStoreActivity.class, bundle);
        } else {
            ViewUtil.gotoActivity(this, SeedStoreActivity.class);
        }
    }

    private void queryCurrentInfos() {
        if (mUserData == null)
            return;

        ApiUtil.apiService().info(mUserData.uuid, String.valueOf(mUserData.uid), mUserData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<TeamInfo>() {
                    @Override
                    public void onResponse(TeamInfo teamInfo) {
                        mTeamInfo = teamInfo;
                        if (mTeamInfo != null)
                            UserDataUtil.instance().setClaimNewbieMiner(mTeamInfo.claimNewbieMiner);
                        onTeamInfoGet();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });
    }

    private void finishProduce(String validate) {
        if (mUserData == null)
            return;

        ApiUtil.apiService().produceFinish(mUserData.uuid, String.valueOf(mUserData.uid), mUserData.token,
                AppConfig.DIAMOND_CURRENCY, AppConfig.VERIFY_ID, validate,
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        queryCurrentInfos();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });
    }

    String mVlidate;

    // 验证->播放广告->收获
    private void verifyCode() {
        NeteaseCaptcha.start(getContext(), new NeteaseCaptcha.OnCaptchaListener() {
            @Override
            public void onCaptchaSuccess(String validate) {
                mVlidate = validate;
                getAdVideos(VideoActivity.REQUEST_CODE_FINISH_PRODUCE);
            }

            @Override
            public void onCaptchaError(String msg) {
                ToastUtil.text(msg).show();
            }
        });
    }

    private void onTeamInfoGet() {
        if (mTeamInfo == null)
            return;

        // step
        mProgressComplete = false;
        setUiStep();

        // 设置进度条
        bindRoot.progress.setTimes(TimeUtil.parseTimeToMillies(mTeamInfo.virtualMiner.stepStartTime),
                TimeUtil.parseTimeToMillies(mTeamInfo.virtualMiner.stepEndTime),
                TimeUtil.parseTimeToMillies(mTeamInfo.stime));
    }

    private void updateProduceStep() {
        if (mUserData == null)
            return;
        ApiUtil.apiService().updateProduceStep(mUserData.uuid, String.valueOf(mUserData.uid), mUserData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        queryCurrentInfos();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });
    }

    private void setUiStep() {
        if (mTeamInfo == null)
            return;

        // 未领取
        if (!mTeamInfo.claimNewbieMiner) {
            bindRoot.setStep(-1);
            return;
        }

        int step = 0;
        int produceStep = mTeamInfo.virtualMiner.step;
        if (mTeamInfo.virtualMiner.todayStatus == 0 || mTeamInfo.virtualMiner.todayStatus == 2) {
            // 今天未开始或者已经结束
            step = 0;
        } else {
            if (produceStep == 0 && !mProgressComplete) {
                step = 0;
            } else if ((produceStep == 0 && mProgressComplete) || (produceStep == 1 && !mProgressComplete)) {
                step = 1;
            } else if ((produceStep == 1 && mProgressComplete) || (produceStep == 2 && !mProgressComplete)) {
                step = 2;
            } else if ((produceStep == 2 && mProgressComplete) || produceStep > 2) {
                step = 3;
            }
        }

        bindRoot.setStep(step);

        // animation
        stopAnimation();
        if ((step == 0 && mTeamInfo.virtualMiner.todayStatus == 0) || mProgressComplete) {
            startAnim(mStepImages[step]);
        }
    }

    private void startAnim(ImageView view) {
        mAnimator = AnimatorInflater.loadAnimator(this, R.animator.anima_scale);
        mAnimator.setTarget(view);
        mAnimator.start();
        mAnimaView = view;
    }

    Animator mAnimator;
    ImageView mAnimaView;

    private void stopAnimation() {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
            mAnimator = null;
        }
        if (mAnimaView != null) {
            mAnimaView.setScaleX(1F);
            mAnimaView.setScaleY(1F);
        }
    }

}