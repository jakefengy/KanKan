package com.xmgl.kan;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Generator GreenDao
 */
public class AppGenerator {

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(3, "com.xmgl.kan.db.entity");

        addUser(schema);
        addUrls(schema);
        addUrlParams(schema);

        schema.setDefaultJavaPackageDao("com.xmgl.kan.db.dao");
        schema.enableKeepSectionsByDefault();
        schema.enableActiveEntitiesByDefault();
        new DaoGenerator().generateAll(schema, "./dbgenerator/src/main/java");
    }

    private static void addUser(Schema schema) {
        Entity user = schema.addEntity("User");

        user.setTableName("User");
        user.addStringProperty("username").primaryKey();
        user.addStringProperty("password");
        user.addLongProperty("loggeddate");
        user.addBooleanProperty("logged");
    }

    private static void addUrls(Schema schema) {
        Entity url = schema.addEntity("Urls");

        /**
         * orgno : XMGLJ
         * orgname : 厦门市公路局
         * name : 演武大桥
         * url : http://www.highway.xm.fj.cn/XMHW/webservices?TYPE=DOWNLOAD&ATTACH_ID=TA15060511171993400001
         * intvalue : 3
         */

        url.addIdProperty();
        url.addStringProperty("username");
        url.addStringProperty("name");
        url.addStringProperty("orgno");
        url.addStringProperty("orgname");
        url.addStringProperty("url");
        url.addIntProperty("intvalue");
        url.addBooleanProperty("urlenable");

    }

    private static void addUrlParams(Schema schema) {

        // paramlist : [{"name":"a","type":"text","required":false,"rowid":"webx-ui-31"},{"name":"b","type":"int","required":false,"rowid":"webx-ui-32"}]

        Entity params = schema.addEntity("UrlParams");

        params.addIdProperty();
        params.addStringProperty("userName");
        params.addLongProperty("urlid");
        params.addStringProperty("name");
        params.addStringProperty("type"); // 文本框、数字、布尔
        params.addBooleanProperty("required");
        params.addStringProperty("rowid");
        params.addStringProperty("content");


    }

}