package com.yangshikun.mvvmdemo.utils.pinyin;

import android.util.Log;

import com.blankj.utilcode.util.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 汉语拼音字典初始化
 *
 */

public class HanyuPinyinProperties {
    private static final String TAG = "HanyuPinyinProperties";
    public static Properties p = new Properties();

    public static void init() {
        try {
            if (p.isEmpty()){
                Log.e(TAG, "p为空，初始化一次");
                InputStream inputStream = Utils.getApp().getAssets().open("hanyu_pinyin.txt");
                p.load(inputStream);
            }else{
                Log.e(TAG, "p不为空");
            }
        } catch (IOException e) {
            Log.e(TAG, "init: e=="+e.toString() );
            e.printStackTrace();
        }
    }

}
