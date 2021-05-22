package com.von.rxjava.demo;


import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxJavaThreadChangeDemo implements Runnable {
    private String TAG = LogTag.TAG;

    @Override
    public void run() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe" + " thread name: " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "onNext: " + s + " thread name: " + Thread.currentThread().getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage() + " thread name: " + Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: " + " thread name: " + Thread.currentThread().getName());
            }
        };

        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            Log.i(TAG, String.format("thread name:%s", Thread.currentThread().getName()));
            emitter.onNext("1");
            emitter.onNext("2");
            emitter.onNext("3");
            emitter.onNext("4");
            emitter.onComplete();
        })
                //将被观察者切换到子线程
                // IoScheduler#scheduleDirect
                .subscribeOn(Schedulers.io())
                // 将观察者切换到主线程  需要在Android环境下运行
                // HandlerScheduler#schedule
                .observeOn(AndroidSchedulers.mainThread())
                //创建观察者并订阅
                .subscribe(observer);
        //I/RxjavaDemo: onSubscribe thread name: Thread-2
        //I/RxjavaDemo: thread name:RxCachedThreadScheduler-1
        //I/RxjavaDemo: onNext: 1 thread name: main
        //I/RxjavaDemo: onNext: 2 thread name: main
        //I/RxjavaDemo: onNext: 3 thread name: main
        //I/RxjavaDemo: onNext: 4 thread name: main
        //I/RxjavaDemo: onComplete:  thread name: main
    }


}
