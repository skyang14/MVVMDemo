package com.yangshikun.mvvmdemo.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.yangshikun.mvvmdemo.beans.Bean;

import java.util.List;

/**
 * Created by yang.shikun on 2020/3/5 13:20
 */

@Dao
public interface BeanDao {
    @Query("select * from Bean")
    List<Bean> queryAll();

    @Query("select * from Bean")
    LiveData<List<Bean>> queryAllLiveData();

    // OnConflictStrategy.REPLACE表示如果已经有数据，那么就覆盖掉
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Bean bean);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Bean> beans);

    @Update
    void update(Bean bean);

    @Delete
    void delete(Bean bean);

    //倒序，取全部的数据
    @Query("SELECT * FROM bean ORDER BY bean.id DESC ")
    LiveData<List<Bean>> loadAllDESC();

    //倒序，取最新的length条数据
    @Query("SELECT * FROM bean ORDER BY bean.id DESC LIMIT 0,:length")
    LiveData<List<Bean>> loadLiveDESC(int length);

    @Query("DELETE FROM bean")
    void deleteAll();
}

