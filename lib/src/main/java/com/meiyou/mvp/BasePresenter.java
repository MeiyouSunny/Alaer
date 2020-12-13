package com.meiyou.mvp;

public class BasePresenter<M extends Model, V extends View> implements Presenter<M, V> {
    private M model;
    private V view;

    @Override
    public void setModel(M model) {
        this.model = model;
    }

    @Override
    public void setView(V view) {
        this.view = view;
    }

    @Override
    public M getModel() {
        return model;
    }

    @Override
    public V getView() {
        return view;
    }

}
