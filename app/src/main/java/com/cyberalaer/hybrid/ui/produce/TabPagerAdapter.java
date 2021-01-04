package com.cyberalaer.hybrid.ui.produce;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private String[] mTitles;
    private Fragment[] mFragments;

    public TabPagerAdapter(Context context, FragmentManager fm, String[] titles) {
        super(fm);
        mContext = context;
        mTitles = titles;
        mFragments = new Fragment[]{SeedMineFragment.newInstance(), SeedStoreFragment.newInstance(), SeedExpiredFragment.newInstance()};
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

}