package llc.metaversenetwork.app.ui.education;

import android.view.View;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityLoginBinding;

import likly.dollar.$;

/**
 * 教育大厅
 */
public class EducationHallActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.education_hall;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_education;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.shortVideo:
            case R.id.film:
            case R.id.novel:
                $.toast().text(R.string.will_open_soon).show();
                break;
        }
    }

}