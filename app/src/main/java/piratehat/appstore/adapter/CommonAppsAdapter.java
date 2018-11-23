package piratehat.appstore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shizhefei.mvc.IDataAdapter;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.R;

/**
 *
 * Created by PirateHat on 2018/11/3.
 */

public class CommonAppsAdapter extends RecyclerView.Adapter<CommonAppsAdapter.ViewHolder> implements IDataAdapter<List<AppBean>> {

    private Context mContext;
    private List<AppBean> mAppBeans;

    public CommonAppsAdapter(Context context, List<AppBean> appBeans) {
        mContext = context;
        mAppBeans = appBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_app, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppBean appBean = mAppBeans.get(position);
        Glide.with(mContext).load(appBean.getIconUrl()).into(holder.mImvIcon);
        holder.mTvHot.setText(appBean.getHot()+appBean.getAppSize());
        holder.mTvName.setText(appBean.getName());
        holder.mTvInfo.setText(appBean.getIntro());

    }

    @Override
    public int getItemCount() {
        return mAppBeans.size();
    }

    @Override
    public void notifyDataChanged(List<AppBean> appBeans, boolean isRefresh) {
        if (!isRefresh){
            mAppBeans.addAll(appBeans);
            notifyDataSetChanged();
        }

    }

    @Override
    public List<AppBean> getData() {
        return mAppBeans;
    }

    @Override
    public boolean isEmpty() {
        return mAppBeans.size()==0;
    }

     class ViewHolder extends RecyclerView.ViewHolder {

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
