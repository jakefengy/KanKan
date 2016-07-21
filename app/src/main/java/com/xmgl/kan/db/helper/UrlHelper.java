package com.xmgl.kan.db.helper;

import com.xmgl.kan.db.dao.UrlParamsDao;
import com.xmgl.kan.db.dao.UrlsDao;

/**
 */
public class UrlHelper {

    private UrlsDao urlsDao;
    private UrlParamsDao paramsDao;

    public UrlHelper() {
        urlsDao = AppDbHelper.getInstance().getAppDaoSession().getUrlsDao();
        paramsDao = AppDbHelper.getInstance().getAppDaoSession().getUrlParamsDao();
    }

//    public List<Urls> getUrls() {
//        List<Urls> result = new ArrayList<>();
//
//        result.addAll(urlsDao.queryBuilder().where(UrlsDao.Properties.Urlenable.eq(true)).list());
//        for (Urls item : result) {
//            item.getParamlist();
//        }
//        return result;
//    }
//
//    public Urls getUrl(String url) {
//        Urls result = urlsDao.load(url);
//        result.getParamlist();
//        return result;
//    }
//
//    public void changeUrlEnable(Urls url) {
//
//        if (!url.getUrlenable()) {
//            paramsDao.queryBuilder().where(UrlParamsDao.Properties.Url.eq(url.getUrl())).buildDelete().executeDeleteWithoutDetachingEntities();
//            urlsDao.deleteByKey(url.getUrl());
//        } else {
//
//            Urls urls = urlsDao.load(url.getUrl());
//            if (urls != null) {
//                urls.setUrlenable(url.getUrlenable());
//                urlsDao.update(urls);
//            } else {
//                urlsDao.insert(url);
//            }
//
//            paramsDao.queryBuilder().where(UrlParamsDao.Properties.Url.eq(url.getUrl())).buildDelete().executeDeleteWithoutDetachingEntities();
//
//            List<UrlParams> params = url.getParamlist();
//            if (params == null) {
//                return;
//            }
//
//            for (UrlParams item : params) {
//                item.setUrl(url.getUrl());
//                paramsDao.insertOrReplace(item);
//            }
//        }
//    }
//
//    // 是否添加过
//    public boolean isSaved(String url) {
//        return urlsDao.load(url) != null;
//    }
//
//    public void saveOrUpdateUrl(Urls url, List<UrlParams> params) {
//
//        paramsDao.queryBuilder()
//                .where(UrlParamsDao.Properties.Url.eq(url.getUrl()))
//                .buildDelete()
//                .executeDeleteWithoutDetachingEntities();
//        urlsDao.deleteByKey(url.getUrl());
//
//        urlsDao.insertOrReplace(url);
//
//        if (params == null) {
//            return;
//        }
//
//        for (UrlParams item : params) {
//            item.setUrl(url.getUrl());
//            paramsDao.insertOrReplace(item);
//        }
//
//    }

}
