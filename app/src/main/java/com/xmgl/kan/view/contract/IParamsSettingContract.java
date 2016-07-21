package com.xmgl.kan.view.contract;

import com.xmgl.kan.view.entity.Source;

public interface IParamsSettingContract {

    interface View {
        void onGetUrl(boolean success, Source url);
    }

    interface Presenter extends IBaseContract.IBasePresenter {

        void getUrl(String url);

        void updateUrl(Source url);

    }

}
