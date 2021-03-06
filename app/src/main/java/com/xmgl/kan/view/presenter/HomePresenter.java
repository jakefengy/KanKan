package com.xmgl.kan.view.presenter;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmgl.kan.utils.CacheUtils;
import com.xmgl.kan.view.contract.IHomeContract;
import com.xmgl.kan.view.entity.Source;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomePresenter implements IHomeContract.Presenter {

    private RxAppCompatActivity context;
    private IHomeContract.View view;

    private CacheUtils cacheUtils;

    public HomePresenter(RxAppCompatActivity context, IHomeContract.View view) {
        this.context = context;
        this.view = view;
        this.cacheUtils = CacheUtils.getInstance();
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void getUrls() {

        Subscriber<List<Source>> subscriber = new Subscriber<List<Source>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (view != null) {
                    view.onGetUrls(false, null);
                }
            }

            @Override
            public void onNext(List<Source> result) {
                if (view != null) {
                    view.onGetUrls(true, result);
                }
            }
        };

        Observable.just(cacheUtils.getSources())
                .compose(context.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

}
