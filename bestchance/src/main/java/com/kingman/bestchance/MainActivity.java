package com.kingman.bestchance;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.kingman.bestchance.adapter.SingleMemberSelectAdapter;
import com.kingman.bestchance.model.Selecter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    /**
     * 掷硬币的Fragment
     */
    private CoinFragment coinFragment;

    /**
     * 掷骰子的Fragment
     */
    private CrapsFragment crapsFragment;

    /**
     * 圆盘抽奖的Fragment
     */
    private TurnTableFragment turnTableFragment;

    /**
     * 瓶子的Fragment,(真心话大冒险的瓶子)
     */
    private BottleFragment bottleFragment;

    /**
     * 随机数的Fragment
     */
    private RandomFragment randomFragment;

    public ViewPager pager;

    private RecyclerView recyclerViewCategory;

    private SingleMemberSelectAdapter adapter2;
    private List<Selecter> selecters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        recyclerViewCategory = (RecyclerView) findViewById(R.id.recyclerView_category);

        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapter2.setCurrentSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//这里用线性显示 类似于list view

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 5);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // 交错网格布局管理器
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        // 设置布局管理器

        recyclerViewCategory.setLayoutManager(gridLayoutManager);

        adapter2 = new SingleMemberSelectAdapter(this);
        selecters = new ArrayList<Selecter>();
        selecters.add(new Selecter(R.drawable.icon_coin, getString(R.string.coin)));
        selecters.add(new Selecter(R.drawable.icon_craps, getString(R.string.craps)));
        selecters.add(new Selecter(R.drawable.share_lottery, getString(R.string.truntable)));
        selecters.add(new Selecter(R.drawable.bottle, getString(R.string.bottle)));
        selecters.add(new Selecter(R.drawable.random, getString(R.string.random)));

        recyclerViewCategory.setAdapter(adapter2);
        adapter2.setItems(selecters);
        recyclerViewCategory.setItemAnimator(null); //new DefaultItemAnimator()
        recyclerViewCategory.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        recyclerViewCategory,
                        new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(View view, int position) {
                                pager.setCurrentItem(position);
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        }){

                });
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = { "Coin", "Craps", "TurnTable", "Bottle", "Random"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (coinFragment == null) {
                        coinFragment = new CoinFragment();
                    }
                    return coinFragment;
                case 1:
                    if (crapsFragment == null) {
                        crapsFragment = new CrapsFragment();
                    }
                    return crapsFragment;
                case 2:
                    if (turnTableFragment == null) {
                        turnTableFragment = new TurnTableFragment();
                    }
                    return turnTableFragment;
                case 3:
                    if (bottleFragment == null) {
                        bottleFragment = new BottleFragment();
                    }
                    return bottleFragment;

                case 4:
                    if (randomFragment == null) {
                        randomFragment = new RandomFragment();
                    }
                    return randomFragment;

                default:
                    return null;
            }
        }

    }
}
