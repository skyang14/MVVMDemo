package com.yangshikun.mvvmdemo.beans;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by yang.shikun on 2020/3/5 13:19
 */

@Entity
public class Bean {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String text;

    public Bean() {
    }

    @Ignore
    public Bean(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

