package com.xmgl.kan.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.xmgl.kan.utils.UrlsUtils;
import com.xmgl.kan.view.entity.Params;
import com.xmgl.kan.view.entity.Source;

import java.util.Map;

/**
 *
 */
public class WebViewAdapter implements Holder<Source> {

    private WebView webView;

    @Override
    public View createView(Context context) {
        webView = new WebView(context);
        webView.clearFocus();
        return webView;
    }

    @Override
    public void UpdateUI(Context context, int position, Source url) {

        String urlName = UrlsUtils.getUrlName(url.getUrl());
        Map<String, String> urlParams = UrlsUtils.getUrlParams(url.getUrl());
        if (url.getParamlist() != null) {
            for (Params params : url.getParamlist()) {
                if (!TextUtils.isEmpty(params.getContent()))
                    urlParams.put(params.getName(), params.getContent());
            }
        }

        String finalUrl = UrlsUtils.buildUrl(urlName, urlParams);
        Log.i("OkHttp", finalUrl);
        webView.loadUrl(finalUrl);
    }

}
