package llc.metaversenetwork.app.ui.produce;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.SeedMine;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.meiyou.mvp.MvpBinder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import likly.dialogger.Dialogger;
import likly.view.repeat.OnHolderClickListener;
import likly.view.repeat.RepeatView;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.databinding.FragmentSeedMineListBinding;
import llc.metaversenetwork.app.ui.dialog.DialogInputSecondPwd;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.NeteaseCaptcha;
import llc.metaversenetwork.app.util.StringUtil;
import llc.metaversenetwork.app.util.ToastUtil;
import llc.metaversenetwork.app.view.GradViewItemDecoration;

/**
 * 种子商店:我的树苗
 */
@MvpBinder(
)
public class SeedMineFragment extends BaseBindFragment<FragmentSeedMineListBinding> implements RepeatView.OnRetryListener, OnHolderClickListener<AdapterSeedMine> {

    UserData userData;
    private boolean claimNewbieMiner;
    String mTradePhraseCode;
    SeedMine mSeed;

    public static SeedMineFragment newInstance(boolean claimNewbieMiner) {
        SeedMineFragment fragment = new SeedMineFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("claimNewbieMiner", claimNewbieMiner);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_seed_mine_list;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        initData();
    }

    public void refresh() {
        initData();
    }

    private void initData() {
        claimNewbieMiner = getArguments().getBoolean("claimNewbieMiner");
        userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().mySeeds(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                1, 1, AppConfig.PAGE_SIZE_DEFAULT,
                new Callback<List<SeedMine>>() {
                    @Override
                    public void onResponse(List<SeedMine> data) {
                        showData(data);
                    }
                });
    }

    boolean isFirstShow = true;

    private void showData(List<SeedMine> data) {
        if (isFirstShow) {
            isFirstShow = false;
            bindRoot.repeatView.getRecyclerView().setClipToPadding(false);
            bindRoot.repeatView.getRecyclerView().addItemDecoration(new GradViewItemDecoration(getContext(), 6));
            bindRoot.repeatView.onClick(this);
        }

        if (!CollectionUtils.isEmpty(data)) {
            bindRoot.repeatView.viewManager().bind(data);
            bindRoot.repeatView.layoutAdapterManager().showRepeatView();
        } else {
            if (claimNewbieMiner) {
                bindRoot.repeatView.layoutAdapterManager().showEmptyView();
            } else {
                bindRoot.repeatView.layoutAdapterManager().showRetryView();
                bindRoot.repeatView.onRetry(this);
            }
        }

    }

    @Override
    public void onRetry() {
        ((SeedStoreActivity) getActivity()).showPage(1);
    }

    @Override
    public void onHolderClick(AdapterSeedMine adapterSeedMine) {
        mSeed = adapterSeedMine.getData();
        if (mSeed == null || mSeed.remainNum > 3)
            return;
        // 续期
        showSecondPwdDialog();
    }

    private void showSecondPwdDialog() {
        DialogInputSecondPwd dialog = new DialogInputSecondPwd();
        dialog.setListener(pwd -> confirmTransactionCode(pwd));
        Dialogger.newDialog(getContext()).holder(dialog).gravity(Gravity.CENTER).cancelable(false).show();
    }

    private void confirmTransactionCode(String pwd) {
        if (userData == null)
            return;

        final String pwdMD5 = StringUtil.toMD5(pwd + AppConfig.MD5_KEY_TEMP_SECOND + userData.uid);
        ApiUtil.apiService().confirmTransactionCode(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, pwdMD5,
                new Callback<String>() {
                    @Override
                    public void onResponse(String json) {
                        if (!TextUtils.isEmpty(json)) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(json);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (jsonObject.has("code")) {
                                mTradePhraseCode = jsonObject.optString("code");
                                verifyCode();
                            }
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });
    }

    private void verifyCode() {
        NeteaseCaptcha.start(getContext(), new NeteaseCaptcha.OnCaptchaListener() {
            @Override
            public void onCaptchaSuccess(String validate) {
                seedRenewal(validate, mTradePhraseCode);
            }

            @Override
            public void onCaptchaError(String msg) {
                ToastUtil.text(msg).show();
            }
        });
    }

    private void seedRenewal(String validate, String tradePhraseCode) {
        ApiUtil.apiService().seedRenewal(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                validate, AppConfig.VERIFY_ID, tradePhraseCode, mSeed.id,
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        ToastUtil.text(R.string.renewal_success).show();
                        initData();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });
    }

}