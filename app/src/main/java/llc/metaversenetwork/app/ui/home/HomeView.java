package llc.metaversenetwork.app.ui.home;

import llc.metaversenetwork.app.base.ViewBind;
import com.meiyou.mvp.View;

public interface HomeView<ActivityHomeBinding, HomePresenter> extends View, ViewBind {
    void showError(String content);
}
