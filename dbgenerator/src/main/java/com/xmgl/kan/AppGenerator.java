package com.xmgl.kan;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generator GreenDao
 */
public class AppGenerator {

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(3, "com.xmgl.kan.db.entity");

        addUrls(schema);

        schema.setDefaultJavaPackageDao("com.xmgl.kan.db.dao");
        schema.enableKeepSectionsByDefault();
        schema.enableActiveEntitiesByDefault();
        new DaoGenerator().generateAll(schema, "./dbgenerator/src/main/java");
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

        url.addStringProperty("url").primaryKey();
        url.addStringProperty("name");
        url.addStringProperty("orgno");
        url.addStringProperty("orgname");
        url.addIntProperty("intvalue");
        url.addBooleanProperty("urlenable");


        Entity params = schema.addEntity("UrlParams");

        params.addIdProperty();
        Property personUrl = params.addStringProperty("url").notNull()
                .getProperty();
        params.addStringProperty("name");
        params.addStringProperty("type"); // 文本框、数字、布尔
        params.addBooleanProperty("required");
        params.addStringProperty("rowid");
        params.addStringProperty("content");

        ToMany personDepts = url.addToMany(params, personUrl);
        personDepts.setName("paramlist");

    }

}