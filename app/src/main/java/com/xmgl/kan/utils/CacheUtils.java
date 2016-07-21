package com.xmgl.kan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xmgl.kan.view.entity.Source;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class CacheUtils {

    private static final String Url_Key = "Url_Key";

    private SharedPreferences shared;
    private Gson gson;

    private List<String> urls = new ArrayList<>();
    private List<Source> sourceList = new ArrayList<>();

    private CacheUtils() {
        gson = new Gson();
    }

    private static class SingletonHolder {
        private static final CacheUtils INSTANCE = new CacheUtils();
    }

    public void init(Context context) {
        shared = context.getSharedPreferences("KanKan", Context.MODE_PRIVATE);

        String strUrls = shared.getString(Url_Key, "");
        if (!TextUtils.isEmpty(strUrls)) {
            sourceList.addAll(gson.fromJson(strUrls, new TypeToken<List<Source>>() {
            }.getType()));
            for (Source source : sourceList) {
                urls.add(source.getUrl());
            }
        }

    }

    public void saveCache() {
        shared.edit().putString(Url_Key, gson.toJson(sourceList)).apply();
    }

    public static CacheUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public List<Source> getSources() {
        return sourceList;
    }

    public Source getSource(String url) {
        if (TextUtils.isEmpty(url)) {
            return new Source();
        }
        int index = urls.indexOf(url);
        if (index >= 0) {
            return sourceList.get(index);
        }

        return new Source();
    }

    public void changeUrlEnable(Source source) {
        if (source == null || TextUtils.isEmpty(source.getUrl())) {
            return;
        }

        int index = urls.indexOf(source.getUrl());

        if (!source.getUrlenable()) {
            if (index >= 0) {
                urls.remove(index);
                sourceList.remove(index);
            }
        } else {
            if (index < 0) {
                urls.add(source.getUrl());
                sourceList.add(source);
            } else {
                sourceList.get(index).setUrlenable(source.getUrlenable());
            }
        }

    }

    public boolean isSaved(String url) {
        return urls.indexOf(url) >= 0;
    }

    public void updateSource(Source source) {
        if (source == null || TextUtils.isEmpty(source.getUrl())) {
            return;
        }

        int index = urls.indexOf(source.getUrl());
        if (index >= 0) {
            sourceList.remove(index);
            sourceList.add(index, source);
        }
    }

}
