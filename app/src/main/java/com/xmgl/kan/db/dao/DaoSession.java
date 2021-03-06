package com.xmgl.kan.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.xmgl.kan.db.entity.UrlParams;
import com.xmgl.kan.db.entity.Urls;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig urlsDaoConfig;
    private final DaoConfig urlParamsDaoConfig;

    private final UrlsDao urlsDao;
    private final UrlParamsDao urlParamsDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        urlsDaoConfig = daoConfigMap.get(UrlsDao.class).clone();
        urlsDaoConfig.initIdentityScope(type);

        urlParamsDaoConfig = daoConfigMap.get(UrlParamsDao.class).clone();
        urlParamsDaoConfig.initIdentityScope(type);

        urlsDao = new UrlsDao(urlsDaoConfig, this);
        urlParamsDao = new UrlParamsDao(urlParamsDaoConfig, this);

        registerDao(Urls.class, urlsDao);
        registerDao(UrlParams.class, urlParamsDao);
    }
    
    public void clear() {
        urlsDaoConfig.getIdentityScope().clear();
        urlParamsDaoConfig.getIdentityScope().clear();
    }

    public UrlsDao getUrlsDao() {
        return urlsDao;
    }

    public UrlParamsDao getUrlParamsDao() {
        return urlParamsDao;
    }

}
