package com.von.rxjava.demo;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;

/**
 * rxjava demo 1
 *
 * @author Von.Wu
 * @version 1.0.0
 * @date 2021-04-24 15:50:27
 */
public class RxJavaDemo implements Runnable {
    private final String TAG = LogTag.TAG;

    @Override
    public void run() {
//        demoObserver();
        demoDelay();
//        demoObservableFlow();
    }

    private void demoObservableFlow() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("hello");
            }
        })
                // ObservableCreate
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return "abc-" + s;
                    }
                })
                // ObservableMap
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s + "-def";
                    }
                })
                // ObservableMap
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "result:" + s);
                    }
                });
    }

    private void demoDelay() {
        Disposable d = Observable.just("RxJavaDemo1 Hello world!")
                // run in Schedulers.computation() -> Schedulers#COMPUTATION -> ComputationScheduler
                .delay(2000, TimeUnit.MILLISECONDS)
                .subscribeWith(new DisposableObserver<String>() {

                    @Override
                    protected void onStart() {
                        super.onStart();
                        Log.i(TAG, "onStart");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.i(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                    }
                });
        // the sequence can now be disposed via dispose()
//        d.dispose();

    }

    private void demoObserver() {
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable t) {
                Log.i(TAG, "onError");
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        };

        // create return new ObservableCreate
        // ObservableCreate#source = new ObservableOnSubscribe<String>
        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("test1");
            emitter.onNext("test2");
            emitter.onComplete();
        });

        observable.subscribe(observer);

//        Observable<String> observable = Observable.just("test1", "test2");

//        String[] words = {"test1", "test2"};
//        Observable<String> observable = Observable.fromArray(words);

        //    @SchedulerSupport(SchedulerSupport.NONE)
        //    @Override
        //    public final void subscribe(@NonNull Observer<? super T> observer) {
        //        Objects.requireNonNull(observer, "observer is null");
        //        try {
        //            //#### return observer，即上面new出来的observer
        //            observer = RxJavaPlugins.onSubscribe(this, observer);
        //
        //            Objects.requireNonNull(observer, "The RxJavaPlugins.onSubscribe hook returned a null Observer. Please change the handler provided to RxJavaPlugins.setOnObservableSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins");
        //            //#### Observable#subscribeActual , observable = new ObservableOnSubscribe<String>
        //            //#### 即 ObservableOnSubscribe#subscribeActual
        //            subscribeActual(observer);
        //        } catch (NullPointerException e) { // NOPMD
        //            throw e;
        //        } catch (Throwable e) {
        //            Exceptions.throwIfFatal(e);
        //            // can't call onError because no way to know if a Disposable has been set or not
        //            // can't call onSubscribe because the call might have set a Subscription already
        //            RxJavaPlugins.onError(e);
        //
        //            NullPointerException npe = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
        //            npe.initCause(e);
        //            throw npe;
        //        }
        //    }


        // ObservableOnSubscribe#subscribeActual 解析
        //
        //    @Override
        //    protected void subscribeActual(Observer<? super T> observer) {
        //        CreateEmitter<T> parent = new CreateEmitter<>(observer);
        //        observer.onSubscribe(parent);
        //
        //        try {
        //        //#### observer -> CreateEmitter<T>, observable#subscribe invoke, run
        //        //#### emitter.onNext("test1");
        //        //#### emitter.onNext("test2");
        //        //#### emitter.onComplete();
        //            source.subscribe(parent);
        //        } catch (Throwable ex) {
        //            Exceptions.throwIfFatal(ex);
        //            parent.onError(ex);
        //        }
        //    }

    }
}
