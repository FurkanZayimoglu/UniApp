package com.furkanzayimoglu.btchallange.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.furkanzayimoglu.btchallange.R;
import com.furkanzayimoglu.btchallange.adapters.ViewPagerAdapter;
import com.furkanzayimoglu.btchallange.fragments.ListFragment;
import com.furkanzayimoglu.btchallange.fragments.MapFragment;
import com.furkanzayimoglu.btchallange.model.UniModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class UniNameActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {R.drawable.listee, R.drawable.images};
    private ArrayList<UniModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni_name);

        list = getIntent().getParcelableArrayListExtra("list");
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }


    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ListFragment.newInstance(list), "Liste");
        adapter.addFragment(MapFragment.newInstance(list), "Harita");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();
    }


}



