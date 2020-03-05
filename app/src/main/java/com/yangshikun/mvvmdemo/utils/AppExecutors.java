package com.yangshikun.mvvmdemo.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by yang.shikun on 2020/3/5 13:22
 */

public class AppExecutors {
    private final Executor mDiskIO;

    private final Executor mNetworkIO;

    private final Executor mMainThread;

    private final ScheduledThreadPoolExecutor executor;

    public AppExecutors() {
        this.mDiskIO = Executors.newSingleThreadExecutor(new MyThreadFactory("single"));

        this.mNetworkIO = Executors.newFixedThreadPool(3, new MyThreadFactory("fixed"));

        this.mMainThread = new MainThreadExecutor();

        this.executor = new ScheduledThreadPoolExecutor(3, new MyThreadFactory("sc"), new ThreadPoolExecutor.AbortPolicy());

    }

    private static AppExecutors sAppExecutors;
    private static Object oj = new Object();

    public static AppExecutors getInstance() {
        synchronized (oj) {
            if (sAppExecutors == null) {
                sAppExecutors = new AppExecutors();
            }
            return sAppExecutors;
        }
    }

    class MyThreadFactory implements ThreadFactory {

        private final String name;
        private long count = 0;

        MyThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            count++;
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append("-");
            sb.append(count);
            sb.append("-Thread");
            return new Thread(r, sb.toString());
        }
    }

    public Executor diskIO() {
        return mDiskIO;
    }

    public ScheduledThreadPoolExecutor schedule() {
        return executor;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }

    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}

