package llc.metaversenetwork.app.ui.home;

import com.meiyou.mvp.BasePresenter;
import com.meiyou.mvp.SimpleModel;

public class HomePresenterImpl extends BasePresenter<SimpleModel, HomeView> implements HomePresenter {
    @Override
    public void getData() {
        getView().showError("Error..");
    }

}
