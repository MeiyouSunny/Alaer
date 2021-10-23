package llc.metaversenetwork.app.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 验证码倒计时
 */
public class VerifyCodeCounter {
    private final int MSG_TYPE_COUNT = 1;
    private final int COUNT_MAX = 60;

    private static VerifyCodeCounter instance = new VerifyCodeCounter();
    private Handler mHandler;
    private int count;
    private AppCompatButton countView;

    public static VerifyCodeCounter getInstance() {
        if (instance == null)
            instance = new VerifyCodeCounter();
        return instance;
    }

    private VerifyCodeCounter() {
        count = COUNT_MAX;

        if (mHandler == null)
            mHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if (msg.what == MSG_TYPE_COUNT) {
                        if (count == 0) {
                            countOver();
                        } else {
                            count--;
                            if (countView != null) {
                                countView.setText(count + "s");
                            }
                            sendMsg();
                        }
                    }
                }
            };
    }

    public void startCounter(AppCompatButton countView) {
        this.countView = countView;
        countView.setEnabled(false);
        countView.setText(count + "s");
        sendMsg();
    }

    private void sendMsg() {
        if (mHandler != null) {
            removeMsg();
            mHandler.sendEmptyMessageDelayed(MSG_TYPE_COUNT, 1000);
        }
    }

    private void removeMsg() {
        if (mHandler != null)
            mHandler.removeMessages(MSG_TYPE_COUNT);
    }

    private void countOver() {
        count = COUNT_MAX;
        removeMsg();
//        if (countView != null) {
//            countView.setText("发送");
//            countView.setEnabled(true);
//        }
    }

    public void destory() {
        countOver();
//        mHandler = null;
        countView = null;
    }

}
