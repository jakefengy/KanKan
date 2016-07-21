package com.xmgl.kan.view.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.xmgl.kan.R;
import com.xmgl.kan.databinding.ActivityHomeSettingBinding;

public class Activity_Home_Setting extends RxAppCompatActivity {

    private final static String Url = "Url";

    private ActivityHomeSettingBinding binding;

    public static Intent getIntent(Context ctx, String url) {
        Intent intent = new Intent(ctx, Activity_Home_Setting.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Url, url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_setting);
    }

    public void paramsSetting(View view) {
        if (!TextUtils.isEmpty(getIntent().getStringExtra(Url))) {
            startActivity(Activity_Params_Setting.getIntent(this, getIntent().getStringExtra(Url)));
            finish();
        }
    }

    public void urlSetting(View view) {
        startActivity(Activity_Url_Setting.getIntent(this));
        finish();
    }


}
