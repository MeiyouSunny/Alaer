package com.cyberalaer.hybrid.data;

import android.content.res.Resources;

import com.alaer.lib.api.bean.TeamLevel;
import com.cyberalaer.hybrid.R;

public class TeamLevelUtil {

    private Resources mRes;
    private SeedDataUtil seedDataUtil;

    public TeamLevelUtil(Resources mRes) {
        this.mRes = mRes;
        seedDataUtil = new SeedDataUtil(mRes);
    }

    public String parseLevelName(TeamLevel teamLevel) {
        if (teamLevel == null)
            return mRes.getString(R.string.no_team_level);

        return teamLevel.name;
    }

    public String parseLevelReward(TeamLevel teamLevel) {
        if (teamLevel == null)
            return "";
        final String seedTypeName = mRes.getString(seedDataUtil.getSeedName(teamLevel.minerType));
        return mRes.getString(R.string.team_level_reward_is, seedTypeName, teamLevel.bonusRate);
    }

    public int parseLevelImg(TeamLevel teamLevel) {
        if (teamLevel == null)
            return R.drawable.ic_team_level0;
        final int[] levelImgs = new int[]{R.drawable.ic_team_level0, R.drawable.ic_team_level1, R.drawable.ic_team_level2,
                R.drawable.ic_team_level3, R.drawable.ic_team_level4, R.drawable.ic_team_level5, R.drawable.ic_team_level6};
        if (teamLevel.level < levelImgs.length)
            return levelImgs[teamLevel.level];

        return levelImgs[0];
    }

    public String parseLevelDesc(TeamLevel teamLevel) {
        return mRes.getString(R.string.team_level_desc_is, teamLevel.refAuthNum, teamLevel.teamActiveness);
    }

}
