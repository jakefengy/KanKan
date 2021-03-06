package com.xmgl.kan.view.contract;

import com.xmgl.kan.view.entity.Source;

import java.util.List;

public interface IHomeContract {

    interface View {
        void onGetUrls(boolean success, List<Source> urls);
    }

    interface Presenter extends IBaseContract.IBasePresenter {
        void getUrls();
    }

}
