package com.xmgl.kan.view.presenter;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmgl.kan.db.entity.Urls;
import com.xmgl.kan.db.helper.UrlHelper;
import com.xmgl.kan.db.helper.UserHelper;
import com.xmgl.kan.network.Network;
import com.xmgl.kan.view.contract.IUrlsContract;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UrlsPresenter implements IUrlsContract.Presenter {

    private RxAppCompatActivity context;
    private IUrlsContract.View view;
    private UserHelper userHelper;
    private UrlHelper urlHelper;

    public UrlsPresenter(RxAppCompatActivity context, IUrlsContract.View view) {
        this.context = context;
        this.view = view;
        this.userHelper = new UserHelper();
        this.urlHelper = new UrlHelper();
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void getUrls() {

        List<Urls> finalUrls = new ArrayList<>();

        Subscriber<Urls> subscriber = new Subscriber<Urls>() {
            @Override
            public void onCompleted() {
                if (view != null) {
                    view.onGetUrls(true, finalUrls);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (view != null) {
                    view.onGetUrls(false, null);
                }
            }

            @Override
            public void onNext(Urls url) {
                finalUrls.add(url);
            }
        };

        Network.getApis().getUrls()
                .compose(Network.check())
                .compose(context.bindToLifecycle())
                .flatMap(result -> {
                    if (result == null || result.size() == 0) {
                        return Observable.error(new Throwable());
                    } else {
                        return Observable.from(result);
                    }
                })
                .flatMap(urls -> {
                    urls.setUrlenable(urlHelper.checkUserAdded(userHelper.getDefaultUser().getUsername(), urls.getUrl()));
                    return Observable.just(urls);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    @Override
    public void updateUrl(Urls url) {
        urlHelper.updateUrl(userHelper.getDefaultUser().getUsername(), url);
    }
}
