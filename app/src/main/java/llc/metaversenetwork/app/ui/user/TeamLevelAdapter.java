package llc.metaversenetwork.app.ui.user;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alaer.lib.api.bean.TeamLevel;
import com.alaer.lib.data.UserDataUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.data.TeamLevelUtil;
import llc.metaversenetwork.app.databinding.ItemTeamLevelBinding;

public class TeamLevelAdapter extends RecyclerView.Adapter<TeamLevelAdapter.ViewHolder> {

    List<TeamLevel> data;
    TeamLevelUtil teamLevelUtil;
    Resources resources;

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

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamLevel teamLevel = data.get(position);
        holder.binding.setTeamLevel(data.get(position));
        holder.binding.setLevelUtil(teamLevelUtil);

        holder.binding.label1.setText(resources.getString(R.string.auth_person_num, teamLevel.refAuthNum));
        holder.binding.label2.setText(resources.getString(R.string.team_activity_is, teamLevel.teamActiveness));
        holder.binding.label3.setText(resources.getString(R.string.area_little_activity, teamLevel.minorActiveness));
        final int level = UserDataUtil.instance().teamLevel();
        holder.binding.labelCurrent.setVisibility((position + 1) == level ? View.VISIBLE : View.INVISIBLE);

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
