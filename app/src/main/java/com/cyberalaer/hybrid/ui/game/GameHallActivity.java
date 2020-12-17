package com.cyberalaer.hybrid.ui.game;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;

/**
 * 游戏大厅
 */
public class GameHallActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.game_hall;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_game;
    }

}