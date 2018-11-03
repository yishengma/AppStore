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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.R;


/**
 *
 * Created by PirateHat on 2018/11/1.
 */

public class RankAppsAdapter extends  RecyclerView.Adapter<RankAppsAdapter.ItemViewHolder> {

    private List<AppBean> mAppBeans;
    private Context mContext;

    public RankAppsAdapter(List<AppBean> appBeans, Context context) {
        mAppBeans = appBeans;
        mContext = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_app,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        AppBean appBean = mAppBeans.get(position);
        Glide.with(mContext).load(appBean.getIconUrl()).into(holder.mImvIcon);
        holder.mTvHot.setText(appBean.getHot());
        holder.mTvName.setText(appBean.getName());
        //holder.mBtnDownload

    }

    @Override
    public int getItemCount() {
        return mAppBeans.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
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

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
