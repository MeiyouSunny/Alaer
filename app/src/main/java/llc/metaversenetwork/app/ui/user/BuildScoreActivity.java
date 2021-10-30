package llc.metaversenetwork.app.ui.user;

import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.bean.Balance;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityBuildScoreBinding;
import llc.metaversenetwork.app.ui.webpage.WebPageActivity;
import llc.metaversenetwork.app.util.NumberUtils;

/**
 * 元宇積分
 */
public class BuildScoreActivity extends BaseTitleActivity<ActivityBuildScoreBinding> {

    @Override
    protected int titleResId() {
        return R.string.build_integral_detail;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_build_score;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setTitleRightIcon(R.drawable.ic_question);

        Balance balance = (Balance) getIntent().getSerializableExtra("balance");
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setBalance(balance);
    }

    @Override
    protected void onRightClick() {
        WebPageActivity.start(this, AppConfig.BUILD_SCORE_EXPLAIN, R.string.build_score_explain);
    }

}