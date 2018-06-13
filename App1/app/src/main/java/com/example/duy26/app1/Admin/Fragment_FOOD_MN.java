package com.example.duy26.app1.Admin;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Fragment_FOOD_MN extends FragmentStatePagerAdapter {


    public Fragment_FOOD_MN(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
               return new Fragment_FOOD();
            case 1:
                return new Fragment_TYPE_FOOD();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Sản Phẩm";
            case 1:
                return "Loại Sản Phẩm";
        }
        return null;
    }
}
