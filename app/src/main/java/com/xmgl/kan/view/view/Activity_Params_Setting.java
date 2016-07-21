package com.xmgl.kan.view.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmgl.kan.Activity_Params_SettingBinding;
import com.xmgl.kan.R;
import com.xmgl.kan.utils.RxBus;
import com.xmgl.kan.view.adapter.ParamsAdapter;
import com.xmgl.kan.view.contract.IParamsSettingContract;
import com.xmgl.kan.view.entity.RxUrlChange;
import com.xmgl.kan.view.entity.Source;
import com.xmgl.kan.view.presenter.ParamsSettingPresenter;

public class Activity_Params_Setting extends RxAppCompatActivity implements IParamsSettingContract.View {

    private final static String URL = "URL";

    private Activity_Params_SettingBinding binding;

    private IParamsSettingContract.Presenter presenter;

    private ParamsAdapter adapter;

    public static Intent getIntent(Context ctx, String url) {
        Intent intent = new Intent(ctx, Activity_Params_Setting.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(URL, url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_params_setting);

        initView();
        initData();

    }

    private void initView() {
        adapter = new ParamsAdapter(this, null);

        binding.refreshLayout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.refreshLayout.setItemAnimator(new DefaultItemAnimator());

        binding.refreshLayout.setAdapter(adapter);
    }

    private void initData() {
        presenter = new ParamsSettingPresenter(this, this);
        presenter.getUrl(getIntent().getStringExtra(URL));
    }

    public void saveChange(View view) {
        if (TextUtils.isEmpty(binding.urlDuration.getText())) {
            Toast.makeText(this, "请设置轮播时长", Toast.LENGTH_SHORT).show();
            return;
        }
        Source urls = binding.getUrl();
        urls.setParamlist(adapter.getParams());
        urls.setIntvalue(Integer.parseInt(binding.urlDuration.getText().toString()));
        presenter.updateUrl(urls);

        binding.setUrl(urls);

        Toast.makeText(this, "已设置", Toast.LENGTH_SHORT).show();
    }

    public void cancelEdit(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        RxBus.getInstance().post(new RxUrlChange());
        super.onDestroy();
    }

    @Override
    public void onGetUrl(boolean success, Source url) {
        if (success) {
            binding.setUrl(url);
            adapter.updateSource(url.getParamlist());
        }
    }
}
