package com.meiyou.mvp;

public interface Presenter<M extends Model, V extends View> {

    void setModel(M model);

    void setView(V view);

    M getModel();

    V getView();

}
