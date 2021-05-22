package com.von.rxjava.demo;

import android.util.Log;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Predicate;

public class RxJavaFilterDemo implements Runnable {
    @Override
    public void run() {

        Observable.fromArray(1, 2, 3, 4, 5, 6)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Throwable {
                        return integer % 2 == 0;
                    }
                }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(LogTag.TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.i(LogTag.TAG, String.format("filter %s 2 == 0 item : %d", "%", integer));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(LogTag.TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(LogTag.TAG, "onComplete");
            }
        });

        //I/RxjavaDemo: onSubscribe
        //I/RxjavaDemo: filter % 2 == 0 item : 2
        //I/RxjavaDemo: filter % 2 == 0 item : 4
        //I/RxjavaDemo: filter % 2 == 0 item : 6
        //I/RxjavaDemo: onComplete
    }
}
