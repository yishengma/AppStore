package piratehat.appstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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

public class GameMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 1;
    private static final int HEAD = 0;


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
        mAppBeans = new ArrayList<>();
        mAppBeans.add(null);
        mAppBeans.addAll(appBeans);
        mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == HEAD) {
            viewHolder = new HeaderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_two_navigation, parent, false));
        }
        if (viewType == ITEM) {
            viewHolder = new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_app, parent, false));
        }
        assert viewHolder != null;
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            AppBean appBean = mAppBeans.get(position);
            Glide.with(mContext).load(appBean.getIconUrl()).into(((ItemViewHolder) holder).mImvIcon);
            ((ItemViewHolder) holder).mTvInfo.setText(appBean.getIntro() + appBean.getAppSize());
            ((ItemViewHolder) holder).mTvName.setText(appBean.getName());
            ((ItemViewHolder) holder).mTvHot.setText(appBean.getHot());
        }
    }

    @Override
    public int getItemCount() {
        return mAppBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == HEAD ? HEAD : ITEM;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tab_category)
        LinearLayout mCategory;
        @BindView(R.id.tab_collection)
        LinearLayout mCollection;

        HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ((ImageView)mCategory.getChildAt(0)).setImageResource(R.drawable.tab_category);
            ((ImageView)mCollection.getChildAt(0)).setImageResource(R.drawable.tab_collection);

            ((TextView)mCategory.getChildAt(1)).setText("分类");
            ((TextView)mCollection.getChildAt(1)).setText("合集");
            mCategory.setOnClickListener(this);
            mCollection.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mListener!=null){
                mListener.onClick(v.getId(),null);
            }
        }
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
