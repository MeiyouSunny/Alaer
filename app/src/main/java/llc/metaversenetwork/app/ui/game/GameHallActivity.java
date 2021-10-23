package llc.metaversenetwork.app.ui.game;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityLoginBinding;

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