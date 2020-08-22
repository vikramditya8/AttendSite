package com.example.tasgps1;
import android.net.Uri;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class OriginActivity extends AppCompatActivity implements Tab3.OnFragmentInteractionListener,Tab2.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout2);
        tabLayout.addTab(tabLayout.newTab().setText("User Register"));
        tabLayout.addTab(tabLayout.newTab().setText("Admin Login"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager2 = (ViewPager)findViewById(R.id.pager2);
        final PageAdapter2 adapter2 = new PageAdapter2(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager2.setAdapter(adapter2);
        viewPager2.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
