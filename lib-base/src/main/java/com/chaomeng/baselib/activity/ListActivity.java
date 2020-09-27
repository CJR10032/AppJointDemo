package com.chaomeng.baselib.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chaomeng.baselib.R;
import com.chaomeng.baselib.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 创建者     CJR
 * 创建时间   2019/10/25 18:01
 * 描述       列表Activity, 会把清单文件中的Activity都列出来, 点击列表跳转相应Activity
 */
public class ListActivity extends BaseActivity {

    /**
     * ListView控件
     */
    private ListView mListView;

    /**
     * 所有的Activity
     */
    private ArrayList<ActivityInfo> mActivities;

    @Override
    protected void onContentView() {
        setContentView(R.layout.activity_list);
    }

    @Override
    protected void initView() {
        mListView = findViewById(R.id.listView);
    }

    @Override
    protected void initData() {
        try {
            PackageInfo pi = getPackageManager()
                    .getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            mActivities = new ArrayList<>(Arrays.asList(pi.activities));

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //  减去当前的Activity
        removeCurActivityInfo(mActivities);

        String[] dataArr = new String[mActivities.size()];
        for (int i = 0; i < mActivities.size(); i++) {
            try {
                ActivityInfo activityInfo = mActivities.get(i);
                String str = Class.forName(activityInfo.name).getSimpleName();
                dataArr[i] = dealClassName(str);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        mListView.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, dataArr));

        //  条目点击事件
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent();
            intent.setClassName(ListActivity.this, mActivities.get(position).name);
            startActivity(intent);
        });
    }

    @Override
    protected void initListener() {

    }

    /**
     * 去除当前Activity的ActivityInfo
     *
     * @param activities 所有ActivityInfo的集合
     */
    private void removeCurActivityInfo(ArrayList<ActivityInfo> activities) {
        String curName = getClass().getName();
        for (int i = 0; i < activities.size(); i++) {
            ActivityInfo activityInfo = activities.get(i);
            if (curName.equals(activityInfo.name)) {
                activities.remove(i);
                break;
            }
        }
    }

    private String dealClassName(String className) {
        return "\r\n" + "　" + className + "\r\n";
    }
}
