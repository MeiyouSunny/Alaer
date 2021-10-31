package llc.metaversenetwork.app.ui.notice;

import com.alaer.lib.api.bean.Notice;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityNoticeDetailBinding;

/**
 * 公告详情
 */
public class NoticeDetailActivity extends BaseTitleActivity<ActivityNoticeDetailBinding> {

    @Override
    protected int titleResId() {
        return R.string.notice_detail;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_notice_detail;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        showNotice();
    }

    private void showNotice() {
        Notice notice = (Notice) getIntent().getSerializableExtra("notice");

        bindRoot.webView.setBackgroundColor(0);
        bindRoot.webView.getBackground().setAlpha(0);
        bindRoot.webView.loadData(notice.content, "", "UTF-8");
    }

}