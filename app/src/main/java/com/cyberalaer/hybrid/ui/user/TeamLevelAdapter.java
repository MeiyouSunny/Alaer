package com.cyberalaer.hybrid.ui.user;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alaer.lib.api.bean.TeamLevel;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.data.TeamLevelUtil;
import com.cyberalaer.hybrid.databinding.ItemTeamLevelBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TeamLevelAdapter extends RecyclerView.Adapter<TeamLevelAdapter.ViewHolder> {

    List<TeamLevel> data;
    TeamLevelUtil teamLevelUtil;

    public TeamLevelAdapter(List<TeamLevel> data) {
        this.data = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTeamLevelBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_team_level,
                        parent, false);

        if (teamLevelUtil == null)
            teamLevelUtil = new TeamLevelUtil(parent.getResources());

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setTeamLevel(data.get(position));
        holder.binding.setLevelUtil(teamLevelUtil);
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
