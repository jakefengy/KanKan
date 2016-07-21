package com.xmgl.kan.view.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.xmgl.kan.R;
import com.xmgl.kan.databinding.DialogHomeMenuBinding;

import me.drakeet.materialdialog.MaterialDialog;

/**
 */
public class HomeMenuDialog extends MaterialDialog {

    private Context context;

    private OnMenuAction action;

    private DialogHomeMenuBinding binding;

    public HomeMenuDialog(Context context) {
        super(context);
        this.context = context;

        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_home_menu, null);
        binding = DataBindingUtil.bind(view);
        binding.setClick(this);

        setContentView(view);

    }

    @Override
    public void show() {
        super.show();
        binding.btnSettingParams.setFocusable(true);
        binding.btnSettingParams.requestFocus();
    }

    public void paramsSetting(View view) {
        if (action != null) {
            action.paramsSetting();
        }
    }

    public void urlSetting(View view) {
        if (action != null) {
            action.sourceSetting();
        }
    }

    public void setAction(OnMenuAction action) {
        this.action = action;
    }

    public interface OnMenuAction {
        void sourceSetting();

        void paramsSetting();
    }

}
