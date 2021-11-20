package llc.metaversenetwork.app.ui.dialog;

import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.SharedUserDetail;
import com.alaer.lib.api.bean.TeamLevel;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.api.bean.UserLevelList;
import com.alaer.lib.data.UserDataUtil;

import java.util.List;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseDialogHolder;
import llc.metaversenetwork.app.databinding.DialogSharedUserDetailBinding;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.NumberUtils;

public class DialogUserDetail extends BaseDialogHolder<DialogSharedUserDetailBinding> {

    SharedUserDetail userDetail;

    public DialogUserDetail(SharedUserDetail userDetail) {
        super(R.layout.dialog_shared_user_detail);
        this.userDetail = userDetail;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setData(userDetail);

        UserData userData = UserDataUtil.instance().getUserData();
        ApiUtil.apiService().userLevels(userData.uuid, String.valueOf(userData.uid), userData.token,
                AppConfig.DIAMOND_CURRENCY,
                new Callback<UserLevelList>() {
                    @Override
                    public void onResponse(UserLevelList levels) {
                        showUserLvel(levels);
                    }
                });

        ApiUtil.apiService().teamStarLevel(userData.uuid, String.valueOf(userData.uid), userData.token,
                AppConfig.DIAMOND_CURRENCY,
                new Callback<List<TeamLevel>>() {
                    @Override
                    public void onResponse(List<TeamLevel> levels) {
                        showTeamLevel(levels);
                    }
                });
    }

    private void showTeamLevel(List<TeamLevel> levels) {
        int level = userDetail.teamLevel;
        if (level == 0) {
            bindRoot.teamLevel.setText(R.string.no_team_level);
            return;
        }

        if (levels != null && !CollectionUtils.isEmpty(levels) && level < levels.size()) {
            bindRoot.teamLevel.setText(levels.get(level - 1).name);
        }
    }

    private void showUserLvel(UserLevelList levels) {
        int level = userDetail.level;
        if (levels != null && !CollectionUtils.isEmpty(levels.list) && level <= levels.list.size()) {
            bindRoot.userLevel.setText(levels.list.get(level).name);
        }
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;
        }
    }

}
