package com.xmgl.kan.network;


import com.xmgl.kan.network.entity.BaseEntity;
import com.xmgl.kan.view.entity.Source;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 业务请求接口
 */
public interface Apis {

    @GET("ums.board.list")
    Observable<BaseEntity<List<Source>>> getUrls();

}
