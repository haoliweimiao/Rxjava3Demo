package com.von.rxjava.demo;

import android.util.Log;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;

/**
 * Rxjava Flowable demo
 *
 * @author Von.Wu
 * @version 1.0.0
 * @date 2021-05-16 09:21:00
 */
public class FlowableDemo implements Runnable {
    @Override
    public void run() {


//        Flowable.just("Hello world").subscribe(System.out::println);

//        Flowable.just("FlowableDemo Hello world").subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Throwable {
//                Log.i(LogTag.TAG, s);
//            }
//        });

        Flowable<Integer> flow1 = Flowable.range(1, 5)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Throwable {
                        return integer * integer;
                    }
                });

        Flowable<Integer> flow2 = flow1.filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Throwable {
                return integer % 2 == 0;
            }
        });


        flow2.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Throwable {
                Log.i(LogTag.TAG, "" + integer);
            }
        });

//        demoBackpressure();
    }

    private void demoBackpressure() {

        Flowable<String> source = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {

                // signal an item
                emitter.onNext("FlowableDemo Hello");

                // could be some blocking operation
                Thread.sleep(1000);

                // the consumer might have cancelled the flow
                if (emitter.isCancelled()) {
                    return;
                }

                emitter.onNext("FlowableDemo World");

                Thread.sleep(1000);

                // the end-of-sequence has to be signaled, otherwise the
                // consumers may never finish
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        Log.d(LogTag.TAG, "FlowableDemo Subscribe!");

        source.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                Log.d(LogTag.TAG, s);
            }
        });

        Log.d(LogTag.TAG, "FlowableDemo Done!");
    }

}
