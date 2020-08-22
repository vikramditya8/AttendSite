package com.example.tasgps1;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter2 extends FragmentStatePagerAdapter {

    int mNoOfTabs;
    public PageAdapter2(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                Tab3 tab3 = new Tab3();
                return tab3;
            case 1:
                Tab2 tab2 = new Tab2();
                return  tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
