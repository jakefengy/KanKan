package com.xmgl.kan.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.xmgl.kan.R;
import com.xmgl.kan.databinding.ListitemUrlParamsBinding;
import com.xmgl.kan.view.entity.Params;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class ParamsAdapter extends RecyclerView.Adapter<ParamsAdapter.ViewHolder> {

    private Context context;
    private List<Params> mDataSource;
    private LayoutInflater inflater;

    public ParamsAdapter(Context ctx, List<Params> datas) {
        this.context = ctx;
        this.mDataSource = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);

        if (datas != null) {
            mDataSource.addAll(datas);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.listitem_url_params, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mDataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ListitemUrlParamsBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
            // 0 = false

            RxTextView.afterTextChangeEvents(binding.paramsContent)
                    .subscribe(event -> {
                        final int index = getLayoutPosition();
                        if (index >= 0)
                            mDataSource.get(index).setContent(String.valueOf((binding.paramsContent.getText())));
                    });

            binding.paramsSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
                final int index = getLayoutPosition();
                if (index >= 0)
                    mDataSource.get(index).setContent(b ? "1" : "0");
            });

        }

        public void bind(Params url) {
            binding.setParams(url);
            if (!url.getType().equals("boolean")) {
                binding.paramsContent.setVisibility(View.VISIBLE);
                binding.paramsSwitch.setVisibility(View.GONE);
                binding.paramsContent.setText(url.getContent());
            }
//            else if (url.getType().equals("int")) {
//                binding.paramsContent.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
//                binding.paramsContent.setVisibility(View.VISIBLE);
//                binding.paramsSwitch.setVisibility(View.GONE);
//            } else if (url.getType().equals("boolean")) {
//                binding.paramsContent.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
//                binding.paramsContent.setVisibility(View.GONE);
//                binding.paramsSwitch.setVisibility(View.VISIBLE);
//            }
            else {
                binding.paramsContent.setVisibility(View.GONE);
                binding.paramsSwitch.setVisibility(View.VISIBLE);
                binding.paramsSwitch.setChecked(url.getContent().equals("1"));
            }
        }

    }

    public void updateSource(List<Params> data) {
        if (data == null) {
            return;
        }

        mDataSource.clear();
        mDataSource.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<Params> data) {
        if (data == null) {
            return;
        }

        mDataSource.addAll(data);
        notifyDataSetChanged();

    }

    public List<Params> getParams() {
        return mDataSource;
    }

}
