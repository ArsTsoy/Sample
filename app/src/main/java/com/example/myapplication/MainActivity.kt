package com.example.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
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
        disposable = Observable.just("Арслан", "Темирлан", "Нариман", "Даник")

            .map { it + " Великий" }
            .delay(3, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

            .subscribe({
                Log.e("myTag", "it: $it")
            }, {

            })

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

//        disposable = Observable.just("Арслан", "Темирлан", "Нариман", "Даник")
//            .debounce (0, TimeUnit.SECONDS)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.i("myTag", it.toString())
//            }, {
//
//            })

//        disposable = Observable.just("Арслан", "Темирлан", "Нариман", "Даник", "Темирлан", "Темирлан")
//            .distinct()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    Log.i("myTag", it.toString())
//                },
//                {
//
//                }
//            )

//        disposable = Observable.just("Арслан", "Темирлан", "Темирлан", "Нариман", "Даник", "Темирлан", "Темирлан")
//            .elementAt(0)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe{
//                    Log.i("myTag", it.toString())
//                }

//        disposable = Observable.just("Арслан", "Темирлан", "Темирлан", "Нариман", "Даник", "Темирлан", "Темирлан")
//            .ignoreElements()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe{
//
//            }

//        disposable = Observable.just("Арслан", "Темирлан", "Темирлан", "Нариман", "Даник", "Темирлан", "Темирлан")
//            .skip(3)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe{
//                Log.i("myTag", it.toString())
//            }


//        //Sample ZIP operator
//        val names = Observable.just("Алексей",  "Петя", "Иван", "Дмитрий")
//        val surname = Observable.just("Иванов", "Сидоров", "Петров", "Алексеев")


//        disposeBag.add(names.zipWith(surname, object: BiFunction<String, String, String> {
//            override fun apply(t: String, u: String): String {
//                return "$t $u"
//            }
//        })
//            .subscribe{
//                Log.i("myTag", "result $it")
//            }
//        )

        fetchUsers()
            .delay(1000, TimeUnit.MILLISECONDS)
            .skip(4)
            .defaultIfEmpty("Users not found!")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.i("myTag", it)
            }


        btnClick.setOnClickListener {
            btnClick.text = counter.toString()
            counter++
        }
    }

    override fun onDestroy() {
//        disposeBag.clear()
        Log.e("d", "dispose on Destroy: ${disposable.isDisposed}")
        super.onDestroy()
    }

    fun fetchUsers(): Observable<String>{
        return Observable.just("User 1", "User 2", "User 3", "User 4")

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
        }, BackpressureStrategy.BUFFER)
    }

//    //Completable sample
//    fun datasourceCompletable(): Completable {
//        return Completable.create({
//
//        })
//    }


}

