package com.coolapps.dailywater.target;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.coolapps.dailywater.target.utils.AdsUtility;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.HashMap;



public final class WalkThroughActivity extends AppCompatActivity {
    private HashMap _$_findViewCache;
    private WalkThroughAdapter viewPagerAdapter;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(8192);
        }
        setContentView((int) R.layout.activity_walk_through);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        this.viewPagerAdapter = new WalkThroughAdapter(this, supportFragmentManager);
        ViewPager viewPager = (ViewPager) _$_findCachedViewById(R.id.walkThroughPager);
        viewPager.setAdapter(this.viewPagerAdapter);
        ((DotsIndicator) _$_findCachedViewById(R.id.indicator)).setViewPager((ViewPager) _$_findCachedViewById(R.id.walkThroughPager));
        AdsUtility.InterstitialAdmob(this);
    }


    public void onStart() {
        super.onStart();
        ((Button) _$_findCachedViewById(R.id.getStarted)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(WalkThroughActivity.this, InitUserInfoActivity.class));
                finish();
                AdsUtility.showIntestitialAds();
            }
        });
    }


    private final class WalkThroughAdapter extends FragmentPagerAdapter {
        final WalkThroughActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public WalkThroughAdapter(WalkThroughActivity $outer, FragmentManager fm) {
            super(fm);
            this.this$0 = $outer;
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
        private HashMap _$_findViewCache;

        public void _$_clearFindViewByIdCache() {
            HashMap hashMap = this._$_findViewCache;
            if (hashMap != null) {
                hashMap.clear();
            }
        }

        public View _$_findCachedViewById(int i) {
            if (this._$_findViewCache == null) {
                this._$_findViewCache = new HashMap();
            }
            View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
            if (view != null) {
                return view;
            }
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }

        public void onDestroyView() {
            super.onDestroyView();
            _$_clearFindViewByIdCache();
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.walk_through_one, container, false);
        }
    }


    public static final class WalkThroughTwo extends Fragment {
        private HashMap _$_findViewCache;

        public void _$_clearFindViewByIdCache() {
            HashMap hashMap = this._$_findViewCache;
            if (hashMap != null) {
                hashMap.clear();
            }
        }

        public View _$_findCachedViewById(int i) {
            if (this._$_findViewCache == null) {
                this._$_findViewCache = new HashMap();
            }
            View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
            if (view != null) {
                return view;
            }
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }

        public void onDestroyView() {
            super.onDestroyView();
            _$_clearFindViewByIdCache();
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.walk_through_two, container, false);
        }
    }


    public static final class WalkThroughThree extends Fragment {
        private HashMap _$_findViewCache;

        public void _$_clearFindViewByIdCache() {
            HashMap hashMap = this._$_findViewCache;
            if (hashMap != null) {
                hashMap.clear();
            }
        }

        public View _$_findCachedViewById(int i) {
            if (this._$_findViewCache == null) {
                this._$_findViewCache = new HashMap();
            }
            View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
            if (view != null) {
                return view;
            }
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }

        public void onDestroyView() {
            super.onDestroyView();
            _$_clearFindViewByIdCache();
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.walk_through_three, container, false);
        }
    }
}
