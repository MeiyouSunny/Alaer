package llc.metaversenetwork.app.ui.user;

import com.alaer.lib.api.bean.TeamInfo;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityActiveDetailBinding;
import llc.metaversenetwork.app.ui.webpage.WebPageActivity;
import llc.metaversenetwork.app.util.NumberUtils;

/**
 * 树苗活跃度明细
 */
public class ActiveBillActivity extends BaseTitleActivity<ActivityActiveDetailBinding> {

    @Override
    protected int titleResId() {
        return R.string.active_detail;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_active_detail;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setTitleRightIcon(R.drawable.ic_question);

        TeamInfo data = (TeamInfo) getIntent().getSerializableExtra("teamInfo");
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setData(data);
    }

    @Override
    protected void onRightClick() {
        WebPageActivity.start(this, getString(R.string.activity_desc_url), R.string.activity_explain);
    }

}