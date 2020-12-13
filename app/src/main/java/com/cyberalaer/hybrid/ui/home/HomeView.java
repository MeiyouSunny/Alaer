package com.cyberalaer.hybrid.ui.home;

import com.cyberalaer.hybrid.base.ViewBind;
import com.meiyou.mvp.View;

public interface HomeView<ActivityHomeBinding, HomePresenter> extends View, ViewBind {
    void showError(String content);
}
