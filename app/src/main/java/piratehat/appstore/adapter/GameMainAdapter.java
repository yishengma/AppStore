package piratehat.appstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by PirateHat on 2018/11/13.
 */

public class GameMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IDataAdapter<List<AppBean>> {

    private static final String TAG = "GameMainAdapter";

    private List<AppBean> mAppBeans;
    private Context mContext;

    private static OnClickListener mListener;

    public void setListener(OnClickListener listener) {
        mListener = listener;
    }

    public interface OnClickListener {
        void onClick(int id,AppBean bean);
    }

    public GameMainAdapter(List<AppBean> appBeans, Context context) {
        mAppBeans = appBeans;
        mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;


            viewHolder = new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_app, parent, false));


        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            AppBean appBean = mAppBeans.get(position);
            Glide.with(mContext).load(appBean.getIconUrl()).into(((ItemViewHolder) holder).mImvIcon);
            ((ItemViewHolder) holder).mTvInfo.setText(appBean.getIntro() );
            ((ItemViewHolder) holder).mTvName.setText(appBean.getName());
            ((ItemViewHolder) holder).mTvHot.setText(appBean.getAppSize()+ appBean.getHot());
        }
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

     class ItemViewHolder extends RecyclerView.ViewHolder {
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
