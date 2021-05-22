package com.von.rxjava.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * rxjava demo main activity
 *
 * @author Von.Wu
 * @version 1.0.0
 * @date 2021-05-16 09:24:23
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Thread(RxJavaDemo1()).start()
//        Thread(FlowableDemo()).start()
//        Thread(RxJavaMapDemo()).start()
//        Thread(RxJavaFlatMapDemo()).start()
//        Thread(RxJavaFilterDemo()).start()
        Thread(RxJavaThreadChangeDemo()).start()

    }
}