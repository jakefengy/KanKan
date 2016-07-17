package com.xmgl.kan.db.helper;

import android.text.TextUtils;

import com.xmgl.kan.db.dao.UserDao;
import com.xmgl.kan.db.entity.User;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * 用户
 */
public class UserHelper {

    public final static String DEFAULT_USER_NAME = "zeus";

    private UserDao userDao;

    public UserHelper() {
        userDao = AppDbHelper.getInstance().getAppDaoSession().getUserDao();
    }

    public List<User> getUsers() {

        List<User> users = new ArrayList<>();

        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.Logged.eq(true), UserDao.Properties.Username.notEq(DEFAULT_USER_NAME));
        qb.orderDesc(UserDao.Properties.Loggeddate);

        users.addAll(qb.list());

        return users;

    }

    public void addDefaultUser() {
        User user = new User();
        user.setUsername(DEFAULT_USER_NAME);
        user.setPassword("");
        user.setLoggeddate(System.currentTimeMillis());
        user.setLogged(true);

        userDao.insertOrReplace(user);
    }

    public User getDefaultUser() {
        return userDao.load(DEFAULT_USER_NAME);
    }

    public User getLastLoggedUser() {
        List<User> users = getUsers();
        if (users.size() > 0) {
            return users.get(0);
        }
        return getDefaultUser();

    }

    public boolean hasLoggedUser() {
        User user = getLastLoggedUser();
        return (user != null && user.getLogged());
    }

    public void saveUser(User user) {
        if (user == null) {
            return;
        }
        user.setLoggeddate(System.currentTimeMillis());
        userDao.insertOrReplace(user);
    }

    public boolean loginOut() {

        User user = getLastLoggedUser();
        if (user == null) {
            return true;
        }

        user.setLogged(false);
        user.setLoggeddate((long) 0);
        userDao.update(user);

        return true;
    }

    public void updateUser(String userId) {

        if (TextUtils.isEmpty(userId)) {
            return;
        }

        User user = userDao.load(userId);
        if (user != null) {
            user.setLoggeddate(System.currentTimeMillis());
            userDao.insertOrReplace(user);
        }
    }

    public void deleteUser(String userId) {
        if (TextUtils.isEmpty(userId)) {
            return;
        }

        userDao.deleteByKey(userId);
    }

}
