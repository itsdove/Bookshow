package com.example.bookshow;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.bookshow.game.Fragmentgame;
import com.example.bookshow.mj.Fragmentmj;
import com.example.bookshow.ts.data.Book;

import com.example.bookshow.ts.data.DataPcakage;
import com.example.bookshow.ts.Fragmentts;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPage;
    private String[] tabTitles;//tab的标题
    private List<Fragment> mDatas = new ArrayList<>();//ViewPage2的Fragment容器
    Fragmentts frgOne = new Fragmentts();
    DataPcakage dataPcakage=new DataPcakage();
    private int mSelectPosition;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
        setContentView(R.layout.activity_main);
        initData();
        mTabLayout = findViewById(R.id.tablelayout);
        mViewPage = findViewById(R.id.viewpage);
        MyViewPageAdapter mAdapter = new MyViewPageAdapter(this, mDatas);
        mViewPage.setAdapter(mAdapter);
        new TabLayoutMediator(mTabLayout, mViewPage, (tab, position) -> tab.setText(tabTitles[position])).attach();
        mViewPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
        //TabLayout的选中改变监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    //初始化数据
    private void initData() {
        tabTitles = new String[]{"图书", "网页", "地图","游戏"};
        Fragmentxw frgTwo = new Fragmentxw();
        Fragmentmj frgthree = new Fragmentmj();
        Fragmentgame fragmentgame=new Fragmentgame();
        mDatas.add(frgOne);
        mDatas.add(frgTwo);
        mDatas.add(frgthree);
        mDatas.add(fragmentgame);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
        launcherAdd.launch(intent);
        return true;
    }
    ActivityResultLauncher<Intent> launcherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if(resultCode== RESULT_OK){
                if(null==data)return;
                Book book = new Book(data.getStringExtra("name"), R.drawable.book_3);
                frgOne.bookList.add(book);
                frgOne.adapter.notifyItemChanged(mSelectPosition);
                dataPcakage.save(MainActivity.this,frgOne.bookList);
            }
        }
    });
    public void onBackPressed() {
        super.onBackPressed();
        dataPcakage.save(MainActivity.this,frgOne.bookList);
    }


}