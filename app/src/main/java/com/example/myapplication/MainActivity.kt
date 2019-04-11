package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private var counter = 1

    private val disposeBag: CompositeDisposable = CompositeDisposable()
    private lateinit var disposable: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val dispose = datasourceFlowable()
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//
//            .subscribe({
//                counterTV.text = "Counter: $it"
//            }, {
//                counterTV.text = "Error: ${it.message}"
//            }, {
//                counterTV.text = "Finish"
//            })

//        disposable = Observable
//            .just("1", "2", "3", "4", "5", "6")
//            .delay(1, TimeUnit.SECONDS)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Toast.makeText(applicationContext, "value is $it", Toast.LENGTH_SHORT).show()
////                Log.e("myTag", "value is $it")
//            }, {
//
//            })
//
//        disposeBag.add(disposable)
//        Handler().postDelayed(
//            {
//                Log.e("myTag", "Dispose: ${disposable.isDisposed}")
//            },
//            5000
//        )

//        Transformations
//        disposable = Observable.just("Арслан", "Темирлан", "Нариман", "Даник")
//            .delay(1, TimeUnit.SECONDS)
//            .map { it + " Великий" }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.e("myTag", "it: $it")
//            }, {
//
//            })

//        disposable = Observable.just("Арслан", "Темирлан", "Нариман", "Даник")
//            .switchMap {
//                val delayInt = Random().nextInt(5)
//
//                Observable.just("AHHAHAHA $it" ).delay(delayInt.toLong(), TimeUnit.SECONDS)
//            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.e("myTag", "it: $it")
//            }, {
//
//            })
//        disposable = Observable.just("Арслан", "Темирлан", "Нариман", "Даник")
//            .buffer(3)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.i("myTag", it.toString())
//            }, {
//
//            })

        btnClick.setOnClickListener {
            btnClick.text = counter.toString()
            counter++
        }
    }

    override fun onDestroy() {
//        disposeBag.clear()
        Log.e("myTag", "dispose on Destroy: ${disposable.isDisposed}")
        super.onDestroy()
    }


    //Observable sample
    fun datasourceObservable(): Observable<Int> {
        return Observable.create { subscriber ->
            run {
                for (i in 1..50) {
                    Thread.sleep(200)
                    subscriber.onNext(i)

                }
                subscriber.onComplete()
            }

        }
    }

    //Flowable sample
    fun datasourceFlowable(): Flowable<Int> {
        return Flowable.create({ subscriber ->
            for (i in 1..5000000) {

                subscriber.onNext(i)

            }
            subscriber.onComplete()
        }, BackpressureStrategy.LATEST)
    }

//    //Completable sample
//    fun datasourceCompletable(): Completable {
//        return Completable.create({
//
//        })
//    }


}
