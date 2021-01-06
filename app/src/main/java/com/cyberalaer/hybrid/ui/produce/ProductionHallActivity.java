package com.cyberalaer.hybrid.ui.produce;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.AdTask;
import com.alaer.lib.api.bean.TeamInfo;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityProductionHallBinding;
import com.cyberalaer.hybrid.util.TimeUtil;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.cyberalaer.hybrid.view.TextProgressBar;
import com.meiyou.mvp.MvpBinder;
import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaConfiguration;
import com.netease.nis.captcha.CaptchaListener;

import java.util.List;

import likly.dollar.$;

/**
 * 生产大厅
 */
@MvpBinder()
public class ProductionHallActivity extends BaseTitleActivity<ActivityProductionHallBinding> implements TextProgressBar.ProgressChange {

    private UserData mUserData;
    private TeamInfo mTeamInfo;
    private boolean mProgressComplete;

    private ImageView[] mStepImages;

    @Override
    protected int layoutId() {
        return R.layout.activity_production_hall;
    }

    @Override
    protected int titleResId() {
        return -1;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        int[] bgResIds = new int[]{R.drawable.img_produce_step0, R.drawable.img_produce_step2,
                R.drawable.img_produce_step3, R.drawable.img_produce_step4};
        mStepImages = new ImageView[]{bindRoot.icStepFertilize, bindRoot.icStepWater, bindRoot.icStepDisinsection, bindRoot.icStepReap};

        bindRoot.setBgPics(bgResIds);
        bindRoot.setStep(0);
        bindRoot.setStepHandler(mProduceStepHandler);
        bindRoot.progress.setProgressListener(this);

        mUserData = UserDataUtil.instance().getUserData();
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
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });

    }

    // 加速
    private void speedUp(int taskId) {
        ApiUtil.apiService().completeTask(mUserData.uuid, String.valueOf(mUserData.uid), mUserData.token, AppConfig.DIAMOND_CURRENCY, String.valueOf(taskId),
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        super.onResponse(response);
                        queryCurrentInfos();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private void startTask(int taskId) {
        ApiUtil.apiService().startTask(mUserData.uuid, String.valueOf(mUserData.uid), mUserData.token, AppConfig.DIAMOND_CURRENCY, String.valueOf(taskId),
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        super.onResponse(response);
                        speedUp(taskId);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private ProduceStep mProduceStepHandler = new ProduceStep() {
        @Override
        public void onStep(int step) {
            if (mTeamInfo.virtualMiner.todayStatus == 2) {
                $.toast().text(R.string.pls_produce_tomorrow).show();
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
                        super.onResponse(response);
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
                if (mTeamInfo != null) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("claimNewbieMiner", mTeamInfo.claimNewbieMiner);
                    ViewUtil.gotoActivity(this, SeedStoreActivity.class, bundle);
                } else {
                    ViewUtil.gotoActivity(this, SeedStoreActivity.class);
                }
                break;
            case R.id.speedUp:
                if (mTeamInfo != null && mTeamInfo.virtualMiner.todayStatus == 2) {
                    $.toast().text(R.string.pls_produce_tomorrow).show();
                    return;
                }
                getAdTasks();
                break;
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
                        onTeamInfoGet();
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
                        super.onResponse(response);
                        queryCurrentInfos();
                    }
                });
    }

    private void verifyCode() {
        final CaptchaConfiguration configuration = new CaptchaConfiguration.Builder()
                .captchaId(AppConfig.VERIFY_ID)
                .listener(new CaptchaListener() {
                    @Override
                    public void onReady() {
                    }

                    @Override
                    public void onValidate(String result, String validate, String msg) {
                        if (!TextUtils.isEmpty(validate)) {
                            finishProduce(validate);
                        }
                    }

                    @Override
                    public void onError(String s) {
                    }

                    @Override
                    public void onCancel() {
                    }
                })
                .build(getContext());
        final Captcha captcha = Captcha.getInstance().init(configuration);
        captcha.validate();

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
                        $.toast().text(msg).show();
                    }
                });
    }

    private void setUiStep() {
        if (mTeamInfo == null)
            return;
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
            mAnimaView.setScaleX(1F);
        }
    }

}