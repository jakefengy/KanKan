package com.xmgl.kan.view.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmgl.kan.Activity_Url_SettingBinding;
import com.xmgl.kan.R;
import com.xmgl.kan.utils.RxBus;
import com.xmgl.kan.view.adapter.UrlsAdapter;
import com.xmgl.kan.view.contract.IUrlSettingContract;
import com.xmgl.kan.view.entity.RxUrlChange;
import com.xmgl.kan.view.entity.Source;
import com.xmgl.kan.view.presenter.UrlSettingPresenter;

import java.util.List;

public class Activity_Url_Setting extends RxAppCompatActivity implements IUrlSettingContract.View {

    private Activity_Url_SettingBinding binding;

    private IUrlSettingContract.Presenter presenter;

    private UrlsAdapter adapter;

    public static Intent getIntent(Context ctx) {
        Intent intent = new Intent(ctx, Activity_Url_Setting.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_url_setting);

        initView();
        initData();

    }

    private void initView() {

        adapter = new UrlsAdapter(this, null);
        adapter.setOnItemClickListener(new UrlsAdapter.OnItemClickListener() {
            @Override
            public void editUrl(View view, Source url) {
                startActivity(Activity_Params_Setting.getIntent(Activity_Url_Setting.this, url.getUrl()));
                finish();
            }

            @Override
            public void changeEnableUrl(View view, Source url) {
                presenter.updateUrl(url);
            }
        });

        binding.refreshLayout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.refreshLayout.setItemAnimator(new DefaultItemAnimator());

        binding.refreshLayout.setAdapter(adapter);

        binding.refreshLayout.setFocusable(true);
        binding.refreshLayout.requestFocus();

    }

    private void initData() {
        presenter = new UrlSettingPresenter(this, this);
        presenter.getUrls();
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        RxBus.getInstance().post(new RxUrlChange());
        super.onDestroy();
    }

    @Override
    public void onGetUrls(boolean success, List<Source> urls) {
        if (success)
            adapter.updateSource(urls);
    }

    @Override
    public void onUpdateUrl(boolean success, Source urls) {

    }
}
