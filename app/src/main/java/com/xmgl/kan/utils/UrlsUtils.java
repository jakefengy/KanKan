package com.xmgl.kan.utils;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class UrlsUtils {

    public static String getUrlName(String url) {
        return getUrlPath(url);
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> getUrlParams(String URL) {
        Map<String, String> mapRequest = new HashMap<>();

        String[] arrSplit = null;

        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    public static String buildUrl(String urlPath, Map<String, String> map) {

        if (urlPath.trim().length() == 0) {
            return "";
        }

        if (map.size() == 0) {
            return urlPath;
        }

        String result = urlPath.trim();

        for (Map.Entry<String, String> entry : map.entrySet()) {

            if (result.contains("?")) {
                result += "&" + entry.getKey() + "=" + entry.getValue();
            } else {
                result += "?" + entry.getKey() + "=" + entry.getValue();
            }
        }

        return result;

    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;

        strURL = strURL.trim();

        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        return strAllParam;
    }

    private static String getUrlPath(String strUrl) {
        String strAllParam = null;
        String[] arrSplit = null;

        strUrl = strUrl.trim();

        arrSplit = strUrl.split("[?]");
        if (strUrl.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[0] != null) {
                    strAllParam = arrSplit[0];
                }
            }
        }

        return strAllParam;
    }


    public static void main(String... args) {

        String url = "http://www.highway.xm.fj.cn/XMHW/webservices?TYPE=DOWNLOAD&ATTACH_ID=TA15060511171993400001";

        Map<String, String> params = UrlsUtils.getUrlParams(url);
        System.out.println(params);

        String urlPath = UrlsUtils.getUrlName(url);
        System.out.println(urlPath);

        String buildUrl = buildUrl(urlPath, params);
        System.out.println(buildUrl);

    }

}
