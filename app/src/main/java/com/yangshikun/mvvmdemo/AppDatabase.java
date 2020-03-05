package com.yangshikun.mvvmdemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.blankj.utilcode.util.Utils;
import com.yangshikun.mvvmdemo.beans.Bean;
import com.yangshikun.mvvmdemo.daos.BeanDao;

/**
 * Created by yang.shikun on 2020/3/5 13:36
 */

@Database(entities = {Bean.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase database;

    public abstract BeanDao beanDao();

    public static AppDatabase getDatabase() {
        return getDatabase(Utils.getApp());
    }

    public static AppDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context, AppDatabase.class, "db_main")
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
}

