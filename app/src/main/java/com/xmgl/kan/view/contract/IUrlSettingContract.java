package com.xmgl.kan.view.contract;

import com.xmgl.kan.view.entity.Source;

import java.util.List;

public interface IUrlSettingContract {

    interface View {
        void onGetUrls(boolean success, List<Source> urls);

        void onUpdateUrl(boolean success, Source urls);
    }

    interface Presenter extends IBaseContract.IBasePresenter {
        void getUrls();

        void updateUrl(Source url);
    }

}
