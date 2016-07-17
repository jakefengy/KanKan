package com.xmgl.kan.view.presenter;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmgl.kan.db.entity.Urls;
import com.xmgl.kan.db.helper.UrlHelper;
import com.xmgl.kan.db.helper.UserHelper;
import com.xmgl.kan.network.Network;
import com.xmgl.kan.view.contract.IHomeContract;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomePresenter implements IHomeContract.Presenter {

    private RxAppCompatActivity context;
    private IHomeContract.View view;

    private UserHelper userHelper;
    private UrlHelper urlHelper;

    public HomePresenter(RxAppCompatActivity context, IHomeContract.View view) {
        this.context = context;
        this.view = view;
        this.urlHelper = new UrlHelper();
        this.userHelper = new UserHelper();
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public String getCurrentUser() {
        return userHelper.getDefaultUser().getUsername();
    }

    @Override
    public void getUrls(String username) {

        Subscriber<List<Urls>> subscriber = new Subscriber<List<Urls>>() {
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
            public void onNext(List<Urls> result) {
                if (view != null) {
                    view.onGetUrls(true, result);
                }
            }
        };

        Network.getApis().getUrls()
                .compose(Network.check())
                .compose(context.bindToLifecycle())
                .flatMap(result -> {
                    if (result == null || result.size() == 0) {
                        return getFromLocal();
                    } else {
                        urlHelper.saveUrls(username, result);
                        return Observable.just(urlHelper.getByUser(username));
                    }
                })
                .flatMap(result -> {
                    if (result == null || result.size() == 0) {
                        return Observable.error(new Throwable("数据获取失败"));
                    } else {
                        return Observable.just(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    @Override
    public void getUrlsFromLoc(String username) {
        Subscriber<List<Urls>> subscriber = new Subscriber<List<Urls>>() {
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
            public void onNext(List<Urls> result) {
                if (view != null) {
                    view.onGetUrls(true, result);
                }
            }
        };

        getFromLocal()
                .compose(context.bindToLifecycle())
                .flatMap(result -> {
                    if (result == null) {
                        return Observable.error(new Throwable("数据获取失败"));
                    } else {
                        return Observable.just(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    private Observable<List<Urls>> getFromLocal() {
        return Observable.just(urlHelper.getUrls());
    }

}
