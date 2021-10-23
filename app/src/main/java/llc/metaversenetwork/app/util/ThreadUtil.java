package llc.metaversenetwork.app.util;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.RequiresApi;

public class ThreadUtil {

    private static ExecutorService mExecutor;
    private static Handler mUIHandler;

    public static void runThread(Runnable runnable) {
        if (mExecutor == null) {
            synchronized (ThreadUtil.class) {
                mExecutor = Executors.newFixedThreadPool(2);
            }
        }
        mExecutor.execute(runnable);
    }

    public static void runUIThread(Runnable runnable) {
        if (mUIHandler == null) {
            synchronized (ThreadUtil.class) {
                mUIHandler = new Handler(Looper.getMainLooper());
            }
        }
        mUIHandler.post(runnable);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void runIdle(MessageQueue.IdleHandler idleHandler) {
        mUIHandler.getLooper().getQueue().addIdleHandler(idleHandler);
    }

}
