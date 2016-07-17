package com.xmgl.kan.view.contract;

import com.xmgl.kan.db.entity.Urls;

import java.util.List;

public interface IUrlsContract {

    interface View {
        void onGetUrls(boolean success, List<Urls> urls);

        void onUpdateUrl(boolean success);
    }

    interface Presenter extends IBaseContract.IBasePresenter {
        void getUrls();

        void updateUrl(Urls url);
    }

}
