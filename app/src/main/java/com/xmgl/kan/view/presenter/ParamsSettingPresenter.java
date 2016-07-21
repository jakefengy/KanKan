package com.xmgl.kan.view.presenter;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmgl.kan.utils.CacheUtils;
import com.xmgl.kan.view.contract.IParamsSettingContract;
import com.xmgl.kan.view.entity.Source;

import rx.Observable;
import rx.Subscriber;

public class ParamsSettingPresenter implements IParamsSettingContract.Presenter {

    private RxAppCompatActivity context;
    private IParamsSettingContract.View view;
    private CacheUtils cacheUtils;

    public ParamsSettingPresenter(RxAppCompatActivity context, IParamsSettingContract.View view) {
        this.context = context;
        this.view = view;
        this.cacheUtils = CacheUtils.getInstance();
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void getUrl(String url) {
        Subscriber<Source> subscriber = new Subscriber<Source>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (view != null) {
                    view.onGetUrl(false, null);
                }
            }

            @Override
            public void onNext(Source result) {
                if (view != null) {
                    view.onGetUrl(true, result);
                }
            }
        };

        Observable.just(cacheUtils.getSource(url))
                .flatMap(l -> {
                    if (l == null) {
                        return Observable.error(new Throwable("暂无相关数据"));
                    } else {
                        return Observable.just(l);
                    }
                })
                .subscribe(subscriber);

    }

    @Override
    public void updateUrl(Source url) {
        cacheUtils.updateSource(url);
    }
}
