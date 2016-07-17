package com.xmgl.kan.view.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmgl.kan.ActivityUrlsBinding;
import com.xmgl.kan.R;
import com.xmgl.kan.db.entity.Urls;
import com.xmgl.kan.utils.RxBus;
import com.xmgl.kan.view.adapter.ParamsAdapter;
import com.xmgl.kan.view.adapter.UrlsAdapter;
import com.xmgl.kan.view.contract.IUrlsContract;
import com.xmgl.kan.view.entity.RxUrlChange;
import com.xmgl.kan.view.presenter.UrlsPresenter;

import java.util.List;

public class ActivityUrls extends RxAppCompatActivity implements IUrlsContract.View, UrlsAdapter.OnItemClickListener {

    private ActivityUrlsBinding binding;

    private IUrlsContract.Presenter presenter;

    private UrlsAdapter adapter;
    private ParamsAdapter paramsAdapter;

    public enum ViewMode {
        Edit, View
    }

    private ViewMode mode = ViewMode.View;

    public static Intent getIntent(Context ctx) {
        Intent intent = new Intent(ctx, ActivityUrls.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_urls);

        initView();
        initData();

    }

    private void initView() {
        adapter = new UrlsAdapter(this, null);
        adapter.setOnItemClickListener(this);

        binding.urlRefreshLayout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.urlRefreshLayout.setItemAnimator(new DefaultItemAnimator());

        binding.urlRefreshLayout.setAdapter(adapter);

        paramsAdapter = new ParamsAdapter(this, null);

        binding.paramsRefreshLayout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.paramsRefreshLayout.setItemAnimator(new DefaultItemAnimator());

        binding.paramsRefreshLayout.setAdapter(paramsAdapter);

        changeMode();
    }

    private void initData() {
        presenter = new UrlsPresenter(this, this);
        presenter.getUrls();
    }

    public void saveChange(View view) {

        Urls urls = binding.getUrl();
        urls.setParamlist(paramsAdapter.getParams());

        presenter.updateUrl(urls);

    }

    public void cancelEdit(View view) {
        mode = ViewMode.View;
        changeMode();
    }

    @Override
    public void editUrl(View view, Urls url) {
        mode = ViewMode.Edit;
        changeMode();

        binding.setUrl(url);
        paramsAdapter.updateSource(url.getParamlist());
    }

    @Override
    public void changeEnableUrl(View view, Urls url) {
        presenter.updateUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (mode == ViewMode.Edit) {
            mode = ViewMode.View;
            changeMode();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        RxBus.getInstance().post(new RxUrlChange());
        super.onDestroy();
    }

    @Override
    public void onGetUrls(boolean success, List<Urls> urls) {
        if (!success) {
            Toast.makeText(this, "数据获取失败", Toast.LENGTH_SHORT).show();
            return;
        }

        adapter.updateSource(urls);

    }

    @Override
    public void onUpdateUrl(boolean success) {
        if (success) {
            RxBus.getInstance().post(new RxUrlChange());
        }
        mode = ViewMode.View;
        changeMode();
    }

    // 更新view
    private void changeMode() {
        binding.setMode(mode);
    }

}
