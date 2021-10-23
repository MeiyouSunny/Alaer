package llc.metaversenetwork.app.util;

import android.content.Context;
import android.text.TextUtils;

import com.alaer.lib.api.AppConfig;
import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaConfiguration;
import com.netease.nis.captcha.CaptchaListener;

import androidx.annotation.IntDef;

/**
 * 网页行为验证
 */
public class NeteaseCaptcha {

    public final static int STEP1 = 1, STEP2 = 2;

    @IntDef({STEP1, STEP2})
    public @interface STEP {
    }

    public static void start(Context context, @STEP int step, OnCaptchaListener listener) {
        NeteaseCaptcha captcha = new NeteaseCaptcha();
        captcha.verifyCode(context, step, listener);
    }

    public static void start(Context context, OnCaptchaListener listener) {
        start(context, STEP1, listener);
    }

    private void verifyCode(Context context, @STEP int step, OnCaptchaListener listener) {
        final CaptchaConfiguration configuration = new CaptchaConfiguration.Builder()
                .captchaId(AppConfig.VERIFY_ID)
                .listener(new CaptchaListener() {
                    @Override
                    public void onReady() {
                    }

                    @Override
                    public void onValidate(String result, String validate, String msg) {
                        if (!TextUtils.isEmpty(validate)) {
                            if (listener != null)
                                listener.onCaptchaSuccess(validate);
                        } else {
                            if (listener != null)
                                listener.onCaptchaError(msg);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (listener != null)
                            listener.onCaptchaError(msg);
                    }

                    @Override
                    public void onCancel() {
                    }
                })
                .build(context);
        final Captcha captcha = Captcha.getInstance().init(configuration);
        captcha.validate();
    }

    public interface OnCaptchaListener {
        void onCaptchaSuccess(String validate);

        void onCaptchaError(String msg);
    }

}
