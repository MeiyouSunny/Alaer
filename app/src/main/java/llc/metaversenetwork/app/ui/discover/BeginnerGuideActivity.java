package llc.metaversenetwork.app.ui.discover;

import android.os.Bundle;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.CommonQuestion;
import com.alaer.lib.api.bean.CommonQuestionList;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;

import java.util.List;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityBeginnerGuideBinding;
import llc.metaversenetwork.app.ui.user.InviterInfoActivity;
import llc.metaversenetwork.app.ui.webpage.WebPageActivity;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.DataUtil;
import llc.metaversenetwork.app.util.ViewUtil;

/**
 * 新手指南
 */
public class BeginnerGuideActivity extends BaseTitleActivity<ActivityBeginnerGuideBinding> {

    @Override
    protected int titleResId() {
        return R.string.beginner_guide;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_beginner_guide;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        ApiUtil.apiService().commonQuestions(1101, 1, 100,
                new Callback<CommonQuestionList>() {
                    @Override
                    public void onResponse(CommonQuestionList questionList) {
                        questionList = DataUtil.parseCommonQuestion(questionList);
                        if (questionList != null && !CollectionUtils.isEmpty(questionList.list)) {
                            showQuestionList(questionList.list);
                        }
                    }
                });
    }

    private void showQuestionList(List<CommonQuestion> list) {
        CommQuestionAdapter adapter = new CommQuestionAdapter(this, list);
        bindRoot.list.setAdapter(adapter);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.buildGuide:
                WebPageActivity.start(this, getString(R.string.explore_guide_url), R.string.build_guide);
                break;
            case R.id.promotionGuide:
                WebPageActivity.start(this, getString(R.string.promotion_guide_url), R.string.promotion_guide);
                break;
            case R.id.invitees:
                UserData userData = UserDataUtil.instance().getUserData();
                if (userData == null)
                    return;

                ApiUtil.apiService().getFollowInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                        new Callback<TeamDetail>() {
                            @Override
                            public void onResponse(TeamDetail response) {
                                Bundle data = new Bundle();
                                data.putSerializable("inviter", response);
                                ViewUtil.gotoActivity(getContext(), InviterInfoActivity.class, data);
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                super.onError(throwable);
                            }
                        });
                break;
        }
    }

}