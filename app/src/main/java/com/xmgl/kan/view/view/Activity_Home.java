package com.xmgl.kan.view.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmgl.kan.Activity_HomeBinding;
import com.xmgl.kan.R;
import com.xmgl.kan.utils.CacheUtils;
import com.xmgl.kan.utils.RxBus;
import com.xmgl.kan.view.adapter.WebViewAdapter;
import com.xmgl.kan.view.contract.IHomeContract;
import com.xmgl.kan.view.dialog.HomeMenuDialog;
import com.xmgl.kan.view.entity.RxUrlChange;
import com.xmgl.kan.view.entity.Source;
import com.xmgl.kan.view.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;

public class Activity_Home extends RxAppCompatActivity implements IHomeContract.View, HomeMenuDialog.OnMenuAction {

    private Activity_HomeBinding binding;

    private IHomeContract.Presenter presenter;

    private HomeMenuDialog dialog;

    private List<Source> urls;

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
        dialog = new HomeMenuDialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setAction(this);
    }

    private void initData() {
        urls = new ArrayList<>();
        presenter = new HomePresenter(this, this);
        presenter.getUrls();
    }

    private void initUrlChanged() {
        RxBus.getInstance().toObservable(RxUrlChange.class)
                .compose(bindToLifecycle())
                .subscribe(change -> presenter.getUrls());
    }

    @Override
    public void sourceSetting() {
        startActivity(Activity_Url_Setting.getIntent(this));
        dialog.dismiss();
    }

    @Override
    public void paramsSetting() {

        int index = binding.banner.getCurrentItem();
        if (index == -1 || urls.size() == 0) {
            return;
        }

        startActivity(Activity_Params_Setting.getIntent(this, urls.get(index).getUrl()));
        dialog.dismiss();
        binding.banner.setCanLoop(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        System.out.println("onKeyDown.KeyCode = " + keyCode);

        if (keyCode == KeyEvent.KEYCODE_MENU) {

            String url = "";

            int index = binding.banner.getCurrentItem();
            if (index == -1 || urls.size() == 0) {
                url = "";
            } else {
                url = urls.get(index).getUrl();
            }

            startActivity(Activity_Home_Setting.getIntent(this, url));
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (binding.banner != null) {
            binding.banner.startTurning(3000);
            binding.banner.setCanLoop(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (binding.banner != null) {
            binding.banner.setCanLoop(false);
            binding.banner.stopTurning();
        }
    }


    @Override
    protected void onDestroy() {
        presenter.destroy();
        CacheUtils.getInstance().saveCache();
        super.onDestroy();
    }

    @Override
    public void onGetUrls(boolean success, List<Source> urls) {
        if (success) {
            this.urls.clear();
            this.urls.addAll(urls);
            initBanner(urls);
        }
    }

    private void initBanner(List<Source> urls) {

        List<Source> result = new ArrayList<>();
        result.addAll(urls);

        binding.banner
                .setPages(() -> new WebViewAdapter(), result)
                .setPageIndicator(new int[]{R.mipmap.icon_dos_glass, R.mipmap.icon_dos_blue});

        if (result.size() == 1) {
            binding.banner.setCanLoop(false);
            binding.banner.stopTurning();
        } else {
            binding.banner.setCanLoop(true);
            binding.banner.startTurning(result.get(0).getIntvalue() * 1000);
        }

        binding.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.banner.startTurning(urls.get(position).getIntvalue() * 1000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
