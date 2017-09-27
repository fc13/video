package com.fc.vedio.cache;

import android.content.Context;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 范超 on 2017/9/7
 */

public class RxRetrofitCache {
    public static <T> Observable<T> load(final Context context, final String cacheKey,
                                         final long expireTime, Observable<T> fromNetwork, boolean forceRefresh) {
        Observable<T> fromCache = Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                T cache = (T) CacheManager.readObject(context, cacheKey, expireTime);
                if (cache != null) {
                    e.onNext(cache);
                } else {
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        fromNetwork = fromNetwork.map(new Function<T, T>() {
            @Override
            public T apply(@NonNull T t) throws Exception {
                CacheManager.saveObject(context, (Serializable) t,cacheKey);
                return t;
            }
        });

        if (forceRefresh){
            return fromNetwork;
        }else {
            return Observable.concat(fromCache,fromNetwork);
        }
    }
}
