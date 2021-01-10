package com.cyberalaer.hybrid.ui.discover;

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
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityDiscoverBinding;
import com.cyberalaer.hybrid.ui.government.AuthSuccessActivity;
import com.cyberalaer.hybrid.ui.government.RealNameAuthActivity;
import com.cyberalaer.hybrid.ui.share.ShareActivity;
import com.cyberalaer.hybrid.ui.user.MyTeamActivity;
import com.cyberalaer.hybrid.util.CollectionUtils;
import com.cyberalaer.hybrid.util.ViewUtil;

import java.util.List;

/**
 * 发现
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
        //.setPageTransformer(Transformer.DefaultTransformer);
        bindRoot.topBanner.getViewPager().setPadding(100, 40, 100, 40);
        bindRoot.topBanner.getViewPager().setClipChildren(false);
        bindRoot.topBanner.getViewPager().setPageMargin(100);

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
        switch (view.getId()) {
            case R.id.newUserGuide:
                ViewUtil.gotoActivity(this, BeginnerGuideActivity.class);
                break;
            case R.id.myTeam:
                ViewUtil.gotoActivity(this, MyTeamActivity.class);
                break;
            case R.id.share:
                ViewUtil.gotoActivity(this, ShareActivity.class);
                break;
            case R.id.auth:
                if (UserDataUtil.instance().isAuthed())
                    ViewUtil.gotoActivity(this, AuthSuccessActivity.class);
                else
                    ViewUtil.gotoActivity(this, RealNameAuthActivity.class);
                break;
        }
    }

}