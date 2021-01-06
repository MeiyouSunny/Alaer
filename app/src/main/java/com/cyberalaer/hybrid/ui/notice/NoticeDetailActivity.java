package com.cyberalaer.hybrid.ui.notice;

import com.alaer.lib.api.bean.Notice;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityNoticeDetailBinding;

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

        bindRoot.webView.loadData(notice.content, "", "UTF-8");
    }

}