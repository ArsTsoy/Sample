package com.example.myapplication;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


import java.util.Random;

public class Sample {
    public static void main(String[] args) {
        System.out.println("START");
//        Disposable disposable = dataSourceSingle()
//                .subscribe(
//                        num -> {
//                            System.out.println("onSuccess: " + num);
//                        },
//                        error -> {
//                            System.out.println("onError: " + error.getMessage());
//                        }
//                );
        Disposable disposable = dataSourceObservable()
                .subscribe(
                        num -> {
                            System.out.println("onNext: " + num);
                        },
                        error -> {
                            System.out.println("onError: " + error.getMessage());
                        }
                );
        System.out.println("END");
    }

    public static Single<Integer> dataSourceSingle(){
        return Single.create(
                emitter -> {
                    Random random = new Random();
                    Thread.sleep(2000);
                    int res = random.nextInt(2);
                    if(res == 1){
                        emitter.onSuccess(res);
                    }else {
                        emitter.onError(new Exception("Ошибка число равно не 1"));
                    }
                }
        );
    }

    public static Observable<Integer> dataSourceObservable(){
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(500);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        });
    }
}
