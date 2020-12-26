package com.coolapps.dailywater.target;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.coolapps.dailywater.target.helpers.SqliteHelper;
import com.coolapps.dailywater.target.utils.AdsUtility;
import com.coolapps.dailywater.target.utils.AppUtils;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.HashMap;



public final class WalkThroughActivity extends AppCompatActivity {

    private WalkThroughAdapter viewPagerAdapter;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(8192);
        }
        setContentView(R.layout.activity_walk_through);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        viewPagerAdapter = new WalkThroughAdapter(this, supportFragmentManager);
        ViewPager viewPager = findViewById(R.id.walkThroughPager);
        viewPager.setAdapter(viewPagerAdapter);
        ((DotsIndicator) findViewById(R.id.indicator)).setViewPager(findViewById(R.id.walkThroughPager));
        AdsUtility.InterstitialAdmob(this);
    }


    public void onStart() {
        super.onStart();
        findViewById(R.id.getStarted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(WalkThroughActivity.this, InitUserInfoActivity.class));
                finish();
                AdsUtility.showIntestitialAds();
            }
        });
    }


    private final class WalkThroughAdapter extends FragmentPagerAdapter {
        final WalkThroughActivity context;

        public WalkThroughAdapter(WalkThroughActivity context, FragmentManager fm) {
            super(fm);
            this.context = context;
        }

        public int getCount() {
            return 3;
        }

        public Fragment getItem(int i) {
            if (i == 0) {
                return new WalkThroughOne();
            }
            if (i == 1) {
                return new WalkThroughTwo();
            }
            if (i != 2) {
                return null;
            }
            return new WalkThroughThree();
        }
    }


    public static final class WalkThroughOne extends Fragment {


        public void onDestroyView() {
            super.onDestroyView();

        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.walk_through_one, container, false);
        }
    }


    public static final class WalkThroughTwo extends Fragment {




        public void onDestroyView() {
            super.onDestroyView();
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.walk_through_two, container, false);
        }
    }


    public static final class WalkThroughThree extends Fragment {


        public void onDestroyView() {
            super.onDestroyView();

        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.walk_through_three, container, false);
        }
    }
}
