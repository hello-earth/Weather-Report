package org.huakai.weatherreport.weatherreport;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import me.yokeyword.indexablerv.SimpleHeaderAdapter;

/**
 * 选择城市
 * Created by YoKey on 16/10/7.
 */
public class PickCityActivity extends AppCompatActivity {
    private List<CityEntity> mDatas;
    private SearchFragment mSearchFragment;
    private SearchView mSearchView;
    private ProgressBar mProgressBar;
    private String city;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        intent = getIntent();
        Bundle bundle = intent.getExtras();
        city = bundle.getString("city");

        findViewById(R.id.icon_nagivitor).setVisibility(View.GONE);
        findViewById(R.id.split_view).setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("选择城市");
        toolbar.setNavigationIcon(R.mipmap.button_back_down);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );

        mSearchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment);
        IndexableLayout indexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);
        mSearchView = (SearchView) findViewById(R.id.searchview);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        // setAdapter
        CityAdapter adapter = new CityAdapter(this);
        indexableLayout.setAdapter(adapter);
        // set Datas
        mDatas = initDatas();
        adapter.setDatas(mDatas, new IndexableAdapter.IndexCallback<CityEntity>() {
            @Override
            public void onFinished(List<CityEntity> datas) {
                // 数据处理完成后回调
                mSearchFragment.bindDatas(mDatas);
                mProgressBar.setVisibility(View.GONE);
            }
        });

        // set Listener
        adapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<CityEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, CityEntity entity) {
//                if (originalPosition >= 0) {
//                    Toast.makeText(PickCityActivity.this, "选中:" + entity.getName() + "  当前位置:" + currentPosition + "  原始所在数组位置:" + originalPosition,Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(PickCityActivity.this, "选中Header:" + entity.getName() + "  当前位置:" + currentPosition,Toast.LENGTH_SHORT).show();
//                }
                String city = entity.getPinyin();
                city = city.substring(0,city.length()-3);
                Intent intent = new Intent(PickCityActivity.this, MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("city", city);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                PickCityActivity.this.finish();
            }
        });
//
//        adapter.setOnItemTitleClickListener(new IndexableAdapter.OnItemTitleClickListener() {
//            @Override
//            public void onItemClick(View v, int currentPosition, String indexTitle) {
//                Toast.makeText(PickCityActivity.this, "选中:" + indexTitle + "  当前位置:" + currentPosition,Toast.LENGTH_SHORT).show();
//            }
//        });

        // 添加 HeaderView DefaultHeaderAdapter接收一个IndexableAdapter, 使其布局以及点击事件和IndexableAdapter一致
        // 如果想自定义布局,点击事件, 可传入 new IndexableHeaderAdapter

        // 热门城市
        indexableLayout.addHeaderAdapter(new SimpleHeaderAdapter<>(adapter, "热", "热门城市", iniyHotCityDatas()));
        // 定位
        final List<CityEntity> gpsCity = iniyGPSCityDatas();
        final SimpleHeaderAdapter gpsHeaderAdapter = new SimpleHeaderAdapter<>(adapter, "定", "当前城市", gpsCity);
        indexableLayout.addHeaderAdapter(gpsHeaderAdapter);

        // 模拟定位
        indexableLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                gpsCity.get(0).setName(city+"市");
                gpsHeaderAdapter.notifyDataSetChanged();
            }
        }, 3000);

        // 搜索Demo
        initSearch();
    }

    private List<CityEntity> initDatas() {
        List<CityEntity> list = new ArrayList<>();
        List<String> cityStrings = Arrays.asList(getResources().getStringArray(R.array.city_array));
        for (String item : cityStrings) {
            CityEntity cityEntity = new CityEntity();
            cityEntity.setName(item);
            list.add(cityEntity);
        }
        return list;
    }

    private List<CityEntity> iniyHotCityDatas() {
        List<CityEntity> list = new ArrayList<>();
        CityEntity city = new CityEntity("北京市");
        city.setPinyin("beijingshi");
        list.add(city);
        city = new CityEntity("上海市");
        city.setPinyin("shanghaishi");
        list.add(city);
        city = new CityEntity("杭州市");
        city.setPinyin("hangzhoushi");
        list.add(city);
        city = new CityEntity("广州市");
        city.setPinyin("guangzhoushi");
        list.add(city);
        return list;
    }

    private List<CityEntity> iniyGPSCityDatas() {
        List<CityEntity> list = new ArrayList<>();
        list.add(new CityEntity("定位中..."));
        return list;
    }

    private void initSearch() {
        getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0) {
                    if (mSearchFragment.isHidden()) {
                        getSupportFragmentManager().beginTransaction().show(mSearchFragment).commit();
                    }
                } else {
                    if (!mSearchFragment.isHidden()) {
                        getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();
                    }
                }

                mSearchFragment.bindQueryText(newText);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!mSearchFragment.isHidden()) {
            // 隐藏 搜索
            mSearchView.setQuery(null, false);
            getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();
            return;
        }
        PickCityActivity.this.finish();
    }
}