package llc.metaversenetwork.app.data;

import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.alaer.lib.api.bean.TeamLevel;

import llc.metaversenetwork.app.R;

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

    public SpannableStringBuilder parseLevelReward(TeamLevel teamLevel) {
        if (teamLevel == null)
            return null;
        final String seedTypeName = mRes.getString(seedDataUtil.getSeedName(teamLevel.minerType));
        String result = mRes.getString(R.string.team_level_reward_is, seedTypeName, teamLevel.bonusRate);
        SpannableStringBuilder spannableString = new SpannableStringBuilder(result);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF7171")),
                result.indexOf(seedTypeName), result.indexOf(seedTypeName) + seedTypeName.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF7171")),
                result.indexOf(String.valueOf(teamLevel.bonusRate)), result.indexOf(String.valueOf(teamLevel.bonusRate)) + String.valueOf(teamLevel.bonusRate).length() + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public int parseLevelImg(TeamLevel teamLevel) {
        if (teamLevel == null)
            return R.drawable.ic_team_level0;
        final int[] levelImgs = new int[]{R.drawable.ic_team_level0, R.drawable.ic_team_level1, R.drawable.ic_team_level2,
                R.drawable.ic_team_level3, R.drawable.ic_team_level4};
        if (teamLevel.level < levelImgs.length)
            return levelImgs[teamLevel.level];

        return levelImgs[0];
    }

    public String parseLevelDesc(TeamLevel teamLevel) {
        return mRes.getString(R.string.team_level_desc_is, teamLevel.refAuthNum, teamLevel.teamActiveness);
    }

}
