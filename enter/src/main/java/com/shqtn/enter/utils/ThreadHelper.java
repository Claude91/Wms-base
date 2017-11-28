package com.shqtn.enter.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ql
 *         创建时间:2017/11/27
 *         描述:
 */

public class ThreadHelper {
    private ThreadPoolExecutor singleThreadExecutor;

    public void createSingle() {
        ThreadFactory nameThreadFactory = new ThreadFactory() {
            int i = 0;

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);

                System.out.println(String.format("demo-pool-%d", i));
                i++;
                return thread;
            }
        };
//      new ThreadPoolExecutor(1,20,1,TimeUnit.MILLISECONDS,)
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        singleThreadExecutor = new ThreadPoolExecutor(5, 200, 0L, TimeUnit.MILLISECONDS, workQueue, nameThreadFactory, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
    }


    public void add(@NonNull Runnable runnable) {
        singleThreadExecutor.execute(runnable);
    }
}
