package piratehat.appstore.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shizhefei.mvc.IDataAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.R;

/**
 *
 *
 * Created by PirateHat on 2018/10/28.
 */

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> implements IDataAdapter<List<AppBean>> {
    private List<AppBean> mAppBeans = new ArrayList<>();
    private Context mContext;

    private static final String TAG = "AppsAdapter";
    public AppsAdapter(Context context) {
        super();
        mContext = context;
    }

    @Override
    public void notifyDataChanged(List<AppBean> data, boolean isRefresh) {
        if (isRefresh) {
            mAppBeans.clear();
        }
        mAppBeans.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_app,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppBean appBean = mAppBeans.get(position);
        Glide.with(mContext).load(appBean.getIconUrl()).into(holder.mImvIcon);
        holder.mTvName.setText(appBean.getName());
        holder.mTvInfo.setText(appBean.getIntro());
        holder.mTvHot.setText(appBean.getHot() + appBean.getAppSize());
    }

    @Override
    public int getItemCount() {
        return mAppBeans.size();
    }

    @Override
    public boolean isEmpty() {
        return mAppBeans.size()==0;
    }

    @Override
    public List<AppBean> getData() {
        return mAppBeans;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imv_icon)
        ImageView mImvIcon;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_info)
        TextView mTvInfo;
        @BindView(R.id.tv_hot)
        TextView mTvHot;
        @BindView(R.id.btn_download)
        TextView mBtnDownload;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
