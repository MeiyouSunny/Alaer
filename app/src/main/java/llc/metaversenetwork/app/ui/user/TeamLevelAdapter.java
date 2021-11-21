package llc.metaversenetwork.app.ui.user;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.TeamLevel;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.data.TeamLevelUtil;
import llc.metaversenetwork.app.databinding.ItemTeamLevelBinding;
import llc.metaversenetwork.app.util.CollectionUtils;

public class TeamLevelAdapter extends RecyclerView.Adapter<TeamLevelAdapter.ViewHolder> {

    List<TeamLevel> data;
    TeamLevelUtil teamLevelUtil;
    Resources resources;
    int[] bgLevels = new int[]{R.drawable.bg_team_level1, R.drawable.bg_team_level2, R.drawable.bg_team_level3, R.drawable.bg_team_level4};
    String[] levelLables;

    public TeamLevelAdapter(List<TeamLevel> data) {
        this.data = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTeamLevelBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_team_level,
                        parent, false);

        resources = parent.getResources();
        if (teamLevelUtil == null)
            teamLevelUtil = new TeamLevelUtil(parent.getResources());
        levelLables = resources.getStringArray(R.array.team_level);

        UserData userData = UserDataUtil.instance().getUserData();
        ;
        ApiUtil.apiService().teamStarLevel(userData.uuid, String.valueOf(userData.uid), userData.token,
                AppConfig.DIAMOND_CURRENCY,
                new Callback<List<TeamLevel>>() {
                    @Override
                    public void onResponse(List<TeamLevel> levels) {
                        parserLevelLabels(binding, levels);
                    }
                });

        return new ViewHolder(binding);
    }

    private void parserLevelLabels(ItemTeamLevelBinding binding, List<TeamLevel> levels) {
        if (!CollectionUtils.isEmpty(levels)) {
            levelLables = new String[levels.size()];
            for (int i = 0; i < levels.size(); i++) {
                levelLables[i] = levels.get(i).name;
            }
            binding.setLevelLables(levelLables);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamLevel teamLevel = data.get(position);
        holder.binding.setBgLevels(bgLevels);
        holder.binding.setLevelLables(levelLables);
        holder.binding.setTeamLevel(data.get(position));
        holder.binding.setLevelUtil(teamLevelUtil);

        holder.binding.label1.setText(resources.getString(R.string.auth_person_num, teamLevel.refAuthNum));
        holder.binding.label2.setText(resources.getString(R.string.team_activity_is, teamLevel.teamActiveness));
        holder.binding.label3.setText(resources.getString(R.string.area_little_activity, teamLevel.minorActiveness));
        holder.binding.levelReward.setText(teamLevelUtil.parseLevelReward(teamLevel));
        final int level = MyTeamActivity.mTeamLevel;
        boolean isCurrent = false;
        if (level > 0 && (level - 1) == position)
            isCurrent = true;
        holder.binding.labelCurrent.setVisibility(isCurrent ? View.VISIBLE : View.INVISIBLE);

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemTeamLevelBinding binding;

        public ViewHolder(ItemTeamLevelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
