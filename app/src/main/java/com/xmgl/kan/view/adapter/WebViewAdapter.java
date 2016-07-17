package com.xmgl.kan.view.adapter;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.xmgl.kan.db.entity.Urls;
import com.xmgl.kan.utils.UrlsUtils;

import java.util.Map;

/**
 *
 */
public class WebViewAdapter implements Holder<Urls> {

    private WebView webView;

    @Override
    public View createView(Context context) {
        webView = new WebView(context);
        webView.clearFocus();
        return webView;
    }

    @Override
    public void UpdateUI(Context context, int position, Urls url) {

        String urlName = UrlsUtils.getUrlName(url.getUrl());
        Map<String, String> urlParams = UrlsUtils.getUrlParams(url.getUrl());

        String finalUrl = UrlsUtils.buildUrl(urlName, urlParams);

        webView.loadUrl(finalUrl);
    }

}
