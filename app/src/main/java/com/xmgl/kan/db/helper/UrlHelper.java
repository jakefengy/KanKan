package com.xmgl.kan.db.helper;

import android.text.TextUtils;

import com.xmgl.kan.db.dao.UrlParamsDao;
import com.xmgl.kan.db.dao.UrlsDao;
import com.xmgl.kan.db.entity.UrlParams;
import com.xmgl.kan.db.entity.Urls;
import com.xmgl.kan.db.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class UrlHelper {

    private UrlsDao urlsDao;
    private UrlParamsDao paramsDao;
    private UserHelper userHelper;

    public UrlHelper() {
        urlsDao = AppDbHelper.getInstance().getAppDaoSession().getUrlsDao();
        paramsDao = AppDbHelper.getInstance().getAppDaoSession().getUrlParamsDao();
        userHelper = new UserHelper();
    }

    public List<Urls> getUrls() {
        User user = userHelper.getLastLoggedUser();

        List<Urls> urls = urlsDao.queryBuilder()
                .where(UrlsDao.Properties.Username.eq(user.getUsername())).list();

        if (urls == null) {
            urls = new ArrayList<>();
        }


        for (Urls url : urls) {
            url.setParamlist(getParams(url));
        }

        return urls;

    }

    private List<UrlParams> getParams(Urls urls) {
        List<UrlParams> params = paramsDao.queryBuilder()
                .where(UrlParamsDao.Properties.UserName.eq(urls.getUsername()), UrlParamsDao.Properties.Urlid.eq(urls.getId()))
                .list();

        if (params == null) {
            params = new ArrayList<>();
        }

        return params;

    }

    public List<Urls> getByUser(String username) {
        if (TextUtils.isEmpty(username)) {
            return null;
        }

        List<Urls> result = urlsDao.queryBuilder()
                .where(UrlsDao.Properties.Username.eq(username))
                .list();

        if (result == null) {
            result = new ArrayList<>();
        }

        for (Urls url : result) {
            url.setParamlist(getParams(url));
        }

        return result;

    }

    public void saveUrls(String username, List<Urls> urls) {

        if (TextUtils.isEmpty(username)) {
            return;
        }

        if (urls == null || urls.size() == 0) {
            return;
        }

        deleteByUser(username);

        for (Urls url : urls) {
            saveUrl(username, url);
        }

    }

    public void saveUrl(String username, Urls url) {
        url.setUsername(username);
        long id = urlsDao.insertOrReplace(url);

        if (url.getParamlist() == null) {
            url.setParamlist(new ArrayList<>());
        }

        for (UrlParams params : url.getParamlist()) {
            params.setUrlid(id);
            params.setUserName(username);
            paramsDao.insertOrReplace(params);
        }
    }

    public void deleteByUser(String username) {

        if (TextUtils.isEmpty(username)) {
            return;
        }

        paramsDao.queryBuilder()
                .where(UrlParamsDao.Properties.UserName.eq(username))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();

        urlsDao.queryBuilder()
                .where(UrlsDao.Properties.Username.eq(username))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }

    public boolean checkUserAdded(String username, String url) {
        return urlsDao.queryBuilder()
                .where(UrlsDao.Properties.Username.eq(username), UrlsDao.Properties.Url.eq(url))
                .list().size() > 0;
    }

    public void updateUrl(String username, Urls url) {
        urlsDao.queryBuilder()
                .where(UrlsDao.Properties.Username.eq(username), UrlsDao.Properties.Url.eq(url.getUrl()))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();

        paramsDao.queryBuilder()
                .where(UrlParamsDao.Properties.UserName.eq(username), UrlParamsDao.Properties.UserName.eq(url.getUrl()))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();

        url.setUsername(username);
        long id = urlsDao.insertOrReplace(url);

        if (url.getParamlist() == null) {
            url.setParamlist(new ArrayList<>());
        }

        for (UrlParams params : url.getParamlist()) {
            params.setUrlid(id);
            params.setUserName(username);
            paramsDao.insertOrReplace(params);
        }
    }

}
