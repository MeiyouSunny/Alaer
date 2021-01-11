package com.cyberalaer.hybrid.ui.discover;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.CommonQuestion;
import com.alaer.lib.api.bean.CommonQuestionList;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityBeginnerGuideBinding;
import com.cyberalaer.hybrid.util.CollectionUtils;

import java.util.List;

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
                        if (questionList != null && !CollectionUtils.isEmpty(questionList.list)) {
                            showQuestionList(questionList.list);
                        }
                    }
                });
    }

    private void showQuestionList(List<CommonQuestion> list) {
        CommQuestionAdapter adapter = new CommQuestionAdapter(getApplicationContext(), list);
        bindRoot.list.setAdapter(adapter);
    }

}