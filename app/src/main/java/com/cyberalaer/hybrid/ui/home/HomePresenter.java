package com.cyberalaer.hybrid.ui.home;

import com.meiyou.mvp.Presenter;
import com.meiyou.mvp.SimpleModel;

public interface HomePresenter extends Presenter<SimpleModel, HomeView> {

    void getData();
}
