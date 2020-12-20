package com.cyberalaer.hybrid.base;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public interface TitleControl {

    void setTitleText(@StringRes int text);

    void setTitleText(String text);

    void setTitleLeftIcon(@DrawableRes int icon);

    void setTitleRightIcon(@DrawableRes int icon);

    void setTitleRightVisible(boolean visible);

}
