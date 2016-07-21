package com.xmgl.kan.view.presenter;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmgl.kan.network.Network;
import com.xmgl.kan.utils.CacheUtils;
import com.xmgl.kan.view.contract.IUrlSettingContract;
import com.xmgl.kan.view.entity.Source;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UrlSettingPresenter implements IUrlSettingContract.Presenter {

    private RxAppCompatActivity context;
    private IUrlSettingContract.View view;
    private CacheUtils cacheUtils;

    public UrlSettingPresenter(RxAppCompatActivity context, IUrlSettingContract.View view) {
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

        Network.getApis().getUrls()
                .compose(context.bindToLifecycle())
                .compose(Network.check())
                .flatMap(urls -> {

                    List<Source> result = new ArrayList<>();

                    for (Source url : urls) {
                        if (cacheUtils.isSaved(url.getUrl())) {
                            result.add(cacheUtils.getSource(url.getUrl()));
                        } else {
                            result.add(url);
                        }
                    }

                    return Observable.just(result);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    @Override
    public void updateUrl(Source url) {
        cacheUtils.changeUrlEnable(url);
    }
}
