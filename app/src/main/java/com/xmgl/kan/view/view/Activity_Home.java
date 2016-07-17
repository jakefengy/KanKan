package com.xmgl.kan.view.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmgl.kan.Activity_HomeBinding;
import com.xmgl.kan.R;
import com.xmgl.kan.db.entity.Urls;
import com.xmgl.kan.utils.RxBus;
import com.xmgl.kan.view.adapter.WebViewAdapter;
import com.xmgl.kan.view.contract.IHomeContract;
import com.xmgl.kan.view.entity.RxUrlChange;
import com.xmgl.kan.view.presenter.HomePresenter;

import java.util.List;

public class Activity_Home extends RxAppCompatActivity implements IHomeContract.View, OnItemClickListener {

    private Activity_HomeBinding binding;

    private IHomeContract.Presenter presenter;

    public static Intent getIntent(Context ctx) {
        Intent intent = new Intent(ctx, Activity_Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        initView();
        initData();
        initUrlChanged();

    }

    private void initView() {
        binding.banner.setOnItemClickListener(this);
    }

    private void initData() {
        presenter = new HomePresenter(this, this);
        presenter.getUrlsFromLoc(presenter.getCurrentUser());
    }

    private void initUrlChanged() {
        RxBus.getInstance().toObservable(RxUrlChange.class)
                .compose(bindToLifecycle())
                .subscribe(change -> presenter.getUrlsFromLoc(presenter.getCurrentUser()));
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        System.out.println("onKeyDown.KeyCode = " + keyCode);

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            startActivity(ActivityUrls.getIntent(this));
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onGetUrls(boolean success, List<Urls> urls) {
        if (success) {
            initBanner(urls);
        } else {
            Toast.makeText(this, "数据获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initBanner(List<Urls> urls) {
        binding.banner
                .setPages(() -> new WebViewAdapter(), urls)
                .setPageIndicator(new int[]{R.mipmap.icon_dos_glass, R.mipmap.icon_dos_blue})
                .setOnItemClickListener(this);
    }


}
