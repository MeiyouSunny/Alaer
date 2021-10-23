package llc.metaversenetwork.app.ui.user;

import android.content.Intent;

import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.bean.Region;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityRegionBinding;
import llc.metaversenetwork.app.util.RegionUtil;
import com.meiyou.mvp.MvpBinder;

import java.util.List;

import androidx.fragment.app.Fragment;
import likly.view.repeat.OnHolderClickListener;

@MvpBinder(
)
public class RegionActivity extends BaseTitleActivity<ActivityRegionBinding> implements OnHolderClickListener<RegionAdapter> {

    public static final int REQUEST_CODE = 1;

    public static void startForResult(Fragment fragment) {
        Intent intent = new Intent(fragment.getContext(), RegionActivity.class);
        fragment.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected int titleResId() {
        return R.string.region_number;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_region;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        showNotices();
    }

    private void showNotices() {
        List<Region> regions = new RegionUtil().parseRegions(getApplicationContext());
        bindRoot.repeatView.viewManager().bind(regions);
        bindRoot.repeatView.onClick(this);
    }

    @Override
    public void onHolderClick(RegionAdapter holder) {
        Region region = holder.getData();
        Intent data = new Intent();
        data.putExtra("region", region);
        AppConfig.DIALLING_CODE = String.valueOf(region.code);
        setResult(RESULT_OK, data);
        finish();
    }

}