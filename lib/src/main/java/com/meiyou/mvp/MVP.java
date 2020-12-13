package com.meiyou.mvp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class MVP {
    private View mView;
    private MvpBinder binder;
    private Model mModel;
    private Presenter mPresenter;
    private static OnViewBindListener onViewBindListener;

    public MVP() {
    }

    public void bind(View view) {
        initView(view);
//        if (view instanceof Activity) {
//
//            ((Activity) view).setContentView(getLayoutResId());
//            onBind(view);
//        } else {
//            throw new IllegalArgumentException("");
//        }
    }

    public android.view.View bind(View view, LayoutInflater inflater, @Nullable ViewGroup container) {
        initView(view);
        android.view.View content = inflater.inflate(getLayoutResId(), container, false);
        return content;
    }

    void onCreate() {
        if (onViewBindListener != null) {
            onViewBindListener.onCreate();
        }
    }

    public void onBind(View view) {
        if (onViewBindListener != null) {
            onViewBindListener.onViewBind(view);
        }
    }

    void onStart() {
        if (onViewBindListener != null) {
            onViewBindListener.onStart();
        }
    }

    void onResume() {
        if (onViewBindListener != null) {
            onViewBindListener.onResume();
        }
    }

    void onPause() {
        if (onViewBindListener != null) {
            onViewBindListener.onPause();
        }
    }

    void onStop() {
        if (onViewBindListener != null) {
            onViewBindListener.onStop();
        }
    }

    void onDestroy() {
        if (onViewBindListener != null) {
            onViewBindListener.onDestroy();
        }
    }

    public View getView() {
        return mView;
    }

    public Model getModel() {
        return mModel;
    }

    Presenter getPresenter() {
        return mPresenter;
    }

    private void initView(View holder) {
        this.mView = holder;
        Class clazz = holder.getClass();
        binder = (MvpBinder) clazz.getAnnotation(MvpBinder.class);
        if (binder == null) {
            throw new IllegalArgumentException("The mView must annotation by MvpBinder," +
                    "mView = " + clazz.getName());
        }

        Class<? extends Presenter> p = binder.presenter();

        if (p.isInterface()) {
            throw new IllegalArgumentException(
                    String.format("The mView presenter can not be a interface,mView=%s,presenter=%s",
                            clazz.getName(), p.getName())
            );
        }
        try {
            mPresenter = p.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    String.format("The mView's presenter can not be new instance cause by %s,mView=%s,presenter=%s",
                            e.getCause(), clazz.getName(), p.getName())
            );
        }

        Class<? extends Model> m = binder.model();

        if (m.isInterface()) {
            throw new IllegalArgumentException(
                    String.format("The mView's model can not be a interface,mView=%s,model=%s",
                            clazz.getName(), m.getName())
            );
        }

        try {
            mModel = m.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    String.format("The mView's model can not be new instance cause by %s,mView=%s,model=%s",
                            e.getCause(), clazz.getName(), m.getName())
            );
        }

        mPresenter.setModel(mModel);
        mPresenter.setView(holder);

    }

    private int getLayoutResId() {
        int layout = binder.value();

        if (layout == -1) {
            if (mView instanceof ViewHandler) {
                layout = mView.initLayoutResId();
            }

            if (layout == -1) {
                throw new IllegalArgumentException("The library module must override the initLayoutResId");
            }

        }
        return layout;

    }

    public static void registerOnViewBindListener(OnViewBindListener onViewBindListener) {
        MVP.onViewBindListener = onViewBindListener;
    }

    public interface OnViewBindListener {
        void onViewBind(View view);

        void onCreate();

        void onStart();

        void onResume();

        void onPause();

        void onStop();

        void onDestroy();
    }

}
