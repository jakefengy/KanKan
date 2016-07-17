package com.xmgl.kan.view.contract;

import com.xmgl.kan.db.entity.Urls;

import java.util.List;

public interface IHomeContract {

    interface View {
        void onGetUrls(boolean success, List<Urls> urlses);
    }

    interface Presenter extends IBaseContract.IBasePresenter {
        String getCurrentUser();

        void getUrls(String username);

        void getUrlsFromLoc(String username);
    }

}
