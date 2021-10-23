package llc.metaversenetwork.app.ui.share;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityShareBinding;
import com.meiyou.mvp.MvpBinder;

/**
 * 邀请好友
 */
@MvpBinder()
public class ShareActivity extends BaseTitleActivity<ActivityShareBinding> {

    @Override
    protected int layoutId() {
        return R.layout.activity_share;
    }

    @Override
    protected int titleResId() {
        return R.string.invitate_friend;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

    }

}