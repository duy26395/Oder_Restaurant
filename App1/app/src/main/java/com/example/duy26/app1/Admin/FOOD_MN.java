package com.example.duy26.app1.Admin;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duy26.app1.R;

public class FOOD_MN extends Fragment{
    TabLayout tabLayout;
    ViewPager viewPage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mn_food, container, false);

        viewPage = (ViewPager)view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout)view.findViewById(R.id.idtab);
        tabLayout.setupWithViewPager(viewPage);
        Fragment_FOOD_MN adapter = new Fragment_FOOD_MN(((AppCompatActivity)getContext()).getSupportFragmentManager());
        viewPage.setAdapter(adapter);
        return view;
    }

}
