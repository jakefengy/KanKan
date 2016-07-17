package com.xmgl.kan.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xmgl.kan.R;
import com.xmgl.kan.databinding.ListitemUrlBinding;
import com.xmgl.kan.db.entity.Urls;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class UrlsAdapter extends RecyclerView.Adapter<UrlsAdapter.ViewHolder> {

    private Context context;
    private List<Urls> mDataSource;
    private LayoutInflater inflater;
    private OnItemClickListener mItemClickListener;

    public UrlsAdapter(Context ctx, List<Urls> datas) {
        this.context = ctx;
        this.mDataSource = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);

        if (datas != null) {
            mDataSource.addAll(datas);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.listitem_url, parent, false));
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

        private ListitemUrlBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
            binding.setClick(this);

        }

        public void bind(Urls url) {
            binding.setUrl(url);
            binding.btnEnable.setText(url.getUrlenable() ? "隐藏" : "显示");
        }

        public void edit(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.editUrl(v, mDataSource.get(getLayoutPosition()));
            }
        }

        public void enableSwitch(View v) {
            final int index = getLayoutPosition();
            mDataSource.get(index).setUrlenable(!mDataSource.get(index).getUrlenable());
            binding.btnEnable.setText(mDataSource.get(index).getUrlenable() ? "隐藏" : "显示");
            if (mItemClickListener != null) {
                mItemClickListener.changeEnableUrl(v, mDataSource.get(getLayoutPosition()));
            }
        }

    }

    public interface OnItemClickListener {
        void editUrl(View view, Urls url);

        void changeEnableUrl(View view, Urls url);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public void updateSource(List<Urls> data) {
        if (data == null) {
            return;
        }

        mDataSource.clear();
        mDataSource.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<Urls> data) {
        if (data == null) {
            return;
        }

        mDataSource.addAll(data);
        notifyDataSetChanged();

    }

}
