package llc.metaversenetwork.app.ui.notice;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.Notice;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityNoticeListBinding;
import llc.metaversenetwork.app.util.CollectionUtils;

import java.util.List;

/**
 * 公告列表
 */
public class NoticeListActivity extends BaseTitleActivity<ActivityNoticeListBinding> {

    @Override
    protected int titleResId() {
        return R.string.notice_list;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_notice_list;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        getNoticeList();
    }

    private void getNoticeList() {
        ApiUtil.apiService().noticeList(1, 100, 1100,
                new Callback<List<Notice>>() {
                    @Override
                    public void onResponse(List<Notice> notices) {
                        showNotices(notices);
                        if (!CollectionUtils.isEmpty(notices)) {

                        }
                    }
                });
    }

    private void showNotices(List<Notice> notices) {
        NoticeAdapter adapter = new NoticeAdapter(this, notices);
        bindRoot.list.setAdapter(adapter);
    }

}