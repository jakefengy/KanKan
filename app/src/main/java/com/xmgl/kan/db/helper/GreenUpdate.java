package com.xmgl.kan.db.helper;

import android.database.sqlite.SQLiteDatabase;

import com.xmgl.kan.db.dao.DaoMaster;


/**
 * 数据库版本升级管理
 */
public class GreenUpdate {

    public static void update(SQLiteDatabase db, int oldVersion, int newVersion) {

        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 1:
                    update1To2(db);
                    break;
                default:
                    break;
            }
        }

    }

    private static void update1To2(SQLiteDatabase db) {
        DaoMaster.dropAllTables(db, true);
        DaoMaster.createAllTables(db, true);
    }

}
