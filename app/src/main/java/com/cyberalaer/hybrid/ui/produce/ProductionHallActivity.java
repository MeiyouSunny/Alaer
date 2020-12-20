package com.cyberalaer.hybrid.ui.produce;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityProductionHallBinding;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.meiyou.mvp.MvpBinder;

/**
 * 生产大厅
 */
@MvpBinder()
public class ProductionHallActivity extends BaseTitleActivity<ActivityProductionHallBinding> {

    @Override
    protected int layoutId() {
        return R.layout.activity_production_hall;
    }

    @Override
    protected int titleResId() {
        return R.string.produce_hall;
    }

    @Override
    public void onViewCreated() {
        int[] bgResIds = new int[]{R.drawable.img_produce_step1, R.drawable.img_produce_step2,
                R.drawable.img_produce_step3, R.drawable.img_produce_step4, R.drawable.img_produce_step1};
        bindRoot.setBgPics(bgResIds);
        bindRoot.setStep(0);
        bindRoot.setStepHandler(mProduceStepHandler);
    }

    private ProduceStep mProduceStepHandler = new ProduceStep() {
        @Override
        public void onStep(int step) {
            bindRoot.setStep(step);
        }
    };

    public interface ProduceStep {
        void onStep(int step);
    }

    @Override
    public void click(android.view.View view) {
        if(view.getId() == R.id.toBase) {
            ViewUtil.gotoActivity(this, ProductionBaseActivity.class);
        }
    }

}