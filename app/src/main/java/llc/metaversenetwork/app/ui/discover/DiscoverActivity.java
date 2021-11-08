package llc.metaversenetwork.app.ui.discover;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.Banner;
import com.alaer.lib.api.bean.BannerList;
import com.alaer.lib.data.UserDataUtil;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.List;

import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityDiscoverBinding;
import llc.metaversenetwork.app.ui.share.ShareActivity;
import llc.metaversenetwork.app.ui.user.MyTeamActivity;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.ViewUtil;

/**
 * 發現
 */
public class DiscoverActivity extends BaseTitleActivity<ActivityDiscoverBinding> {

    @Override
    protected int titleResId() {
        return R.string.discover;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_discover;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        initData();
    }

    private void initData() {
        ApiUtil.apiService().banners(1100, 1, 100,
                new Callback<BannerList>() {
                    @Override
                    public void onResponse(BannerList bannerList) {
                        if (bannerList != null && !CollectionUtils.isEmpty(bannerList.list))
                            showBanner(bannerList.list);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    public void showBanner(List<Banner> banners) {
        bindRoot.topBanner.getViewPager().setPageMargin(50);
        bindRoot.topBanner.getViewPager().setOffscreenPageLimit(3);
        bindRoot.topBanner.setPageTransformer(new ScaleInTransformer());

        bindRoot.topBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, banners)
                .setPointViewVisible(true)
                .setPageIndicator(new int[]{R.drawable.ic_banner_indicator, R.drawable.ic_banner_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        bindRoot.topBanner.startTurning(3 * 1000);
    }

    public class LocalImageHolderView implements Holder<Banner> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Banner data) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ViewUtil.showImage(getContext(), imageView, data.image);
        }
    }

    @Override
    public void click(View view) {
        if (view.getId() != R.id.newUserGuide) {
            if (!judgeLogined())
                return;
        }

        switch (view.getId()) {
            case R.id.newUserGuide:
                ViewUtil.gotoActivity(this, BeginnerGuideActivity.class);
                break;
            case R.id.myTeam:
                ViewUtil.gotoActivity(this, MyTeamActivity.class);
                break;
            case R.id.share:
                if (UserDataUtil.instance().isFrom3DAccount())
                    $.toast().text(R.string.will_open_soon).show();
                else
                    ViewUtil.gotoActivity(this, ShareActivity.class);
                break;
            case R.id.auth:
                ViewUtil.gotoAuthPage(this);
                break;
            case R.id.myCity:
                $.toast().text(R.string.will_open_soon).show();
//                if (UserDataUtil.instance().isAuthed())
//                    ViewUtil.gotoActivity(this, MyCityActivity.class);
//                else
//                    ViewUtil.gotoActivity(this, RealNameAuthActivity.class);
                break;
        }
    }

}