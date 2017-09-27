package com.fc.vedio.helper;

import com.fc.vedio.base.BaseModel;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 范超 on 2017/9/7
 */

public class RxHelper {

    public static <T> ObservableTransformer<BaseModel<T>, T> handleResult() {
        return new ObservableTransformer<BaseModel<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<BaseModel<T>> upstream) {
                return upstream.flatMap(new Function<BaseModel<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull BaseModel<T> tBaseModel) throws Exception {
                        if (tBaseModel.success()) {
                            return createData(tBaseModel.data);
                        } else {
                            return Observable.error(new ServerException((String) tBaseModel.message));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private static <T> ObservableSource<T> createData(T msg) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) {
                try {
                    e.onNext(msg);
                    e.onComplete();
                } catch (Exception e1) {
                    e.onError(e1);
                }
            }
        });
    }
}
