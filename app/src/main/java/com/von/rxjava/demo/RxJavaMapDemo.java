package com.von.rxjava.demo;

import android.util.Log;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;

public class RxJavaMapDemo implements Runnable {
    @Override
    public void run() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("flat map test");
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Throwable {
                return s + " add footer by map";
            }
        })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        Log.d(LogTag.TAG, s);
                    }
                });
    }
}
