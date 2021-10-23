package llc.metaversenetwork.app.ui.travel;

import android.view.View;

import com.alaer.lib.api.AppConfig;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityTravelBinding;
import llc.metaversenetwork.app.util.ViewUtil;

import cn.jzvd.Jzvd;
import likly.dollar.$;

/**
 * 旅游大厅
 */
public class TravelHallActivity extends BaseTitleActivity<ActivityTravelBinding> {

    @Override
    protected int titleResId() {
        return R.string.travel_hall;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_travel;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        initData();
    }

    private void initData() {
        ViewUtil.showImage(getApplicationContext(), bindRoot.player1.posterImageView, AppConfig.TRAVEL_VIDEO_PIC1);
        bindRoot.player1.setUp(AppConfig.TRAVEL_VIDEO1, "");
        ViewUtil.showImage(getApplicationContext(), bindRoot.player2.posterImageView, AppConfig.TRAVEL_VIDEO_PIC2);
        bindRoot.player2.setUp(AppConfig.TRAVEL_VIDEO2, "");
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.buy_tickets:
                $.toast().text(R.string.will_open_soon).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
    }

}