package piratehat.appstore.adapter;

import android.annotation.SuppressLint;
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
 * Created by PirateHat on 2018/10/28.
 */

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {



    private List<AppBean> mAppBeans;
    private Context mContext;

    public AppsAdapter(List<AppBean> appBeans, Context context) {
        mAppBeans = appBeans;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppBean appBean = mAppBeans.get(position);
        Glide.with(mContext).load(appBean.getIconUrl()).into(holder.mImvIcon);
        holder.mTvName.setText(appBean.getName());
        holder.mTvInfo.setText(appBean.getIntro());
        holder.mTvHot.setText(appBean.getHot()+appBean.getAppSize());


    }

    @Override
    public int getItemCount() {
        return mAppBeans.size();
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
