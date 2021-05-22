package com.von.rxjava.demo;

import android.util.Log;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;

public class RxJavaFlatMapDemo implements Runnable {
    @Override
    public void run() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("flat map test");
            }
        }).flatMap(s -> {
            return Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                    emitter.onNext(s + " add footer by flatmap");
                }
            });
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                Log.d(LogTag.TAG, s);
            }
        });

        // D/RxjavaDemo: flat map test add footer by flatmap
    }
}
