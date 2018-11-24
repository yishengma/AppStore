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
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.Bean.BannerBean;
import piratehat.appstore.R;
import piratehat.appstore.utils.GlideImageLoader;

/**
 *
 * Created by PirateHat on 2018/10/28.
 */

public class MainAppsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IDataAdapter<List<AppBean>> {
    private static final int sBANNER = 1;
    private static final int sTAB_LAYOUT = 0;
    private static final int sITEM = 2;
    private List<AppBean> mAppBeans;
    private Context mContext;
    private boolean mIsPlay;
    private List<BannerBean> mBanners;
    private List<String> mTitles;
    private static OnTabClickListener mListener;
    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface OnClickListener {
        void click(String apkName);
    }

    public interface OnTabClickListener {
        void click(int id);
    }

    public void setTabListener(OnTabClickListener listener) {
        mListener = listener;
    }

    private static final String TAG = "MainAppsAdapter";

    public MainAppsAdapter(Context context) {
        super();
        mAppBeans = new ArrayList<>();
        mBanners = new ArrayList<>();
        mTitles = new ArrayList<>();
        mContext = context;
        mIsPlay = false;
        mAppBeans.add(null);
        mAppBeans.add(null);

    }

    @Override
    public void notifyDataChanged(List<AppBean> data, boolean isRefresh) {
        if (isRefresh) {
            mAppBeans.subList(2, mAppBeans.size()).clear();
        }
        mAppBeans.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case sTAB_LAYOUT:
                viewHolder = new NavigationViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main_navigation, parent, false));
                break;
            case sBANNER:
                viewHolder =
                        new BannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_banner, parent, false));

                break;
            case sITEM:
                viewHolder = new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_app, parent, false));
                break;
            default:
                viewHolder = new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_app, parent, false));
                break;
        }
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof BannerViewHolder && mBanners.size() != 0 && mTitles.size() != 0 ) {
            ((BannerViewHolder) holder).mBanner.releaseBanner();
            ((BannerViewHolder) holder).mBanner.setImages(mBanners);
            ((BannerViewHolder) holder).mBanner.setBannerTitles(mTitles);
            ((BannerViewHolder) holder).mBanner.start();

            return;
        }

        if (holder instanceof ItemViewHolder) {
            final AppBean appBean = mAppBeans.get(position);
            Glide.with(mContext).load(appBean.getIconUrl()).into(((ItemViewHolder) holder).mImvIcon);
            ((ItemViewHolder) holder).mTvName.setText(appBean.getName());
            ((ItemViewHolder) holder).mTvInfo.setText(appBean.getIntro());
            ((ItemViewHolder) holder).mTvHot.setText(appBean.getHot() + appBean.getAppSize());

            ((ItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener != null) {
                        mOnClickListener.click(appBean.getMpkgName());
                    }
                }
            });


        }

    }

    @Override
    public int getItemViewType(int position) {
        return position >= 2 ? 2 : position;
    }

    @Override
    public int getItemCount() {
        return mAppBeans.size();
    }

    @Override
    public boolean isEmpty() {
        return mAppBeans.size() == 0;
    }

    @Override
    public List<AppBean> getData() {
        return mAppBeans;
    }

    public void startBanner(ArrayList<BannerBean> beans, ArrayList<String> titles) {
        mBanners.addAll(beans);
        mTitles.addAll(titles);

        notifyDataSetChanged();
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

     class BannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        Banner mBanner;

        BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            mBanner.setImageLoader(new GlideImageLoader());
            mBanner.setBannerAnimation(Transformer.DepthPage);
            mBanner.isAutoPlay(true);
            mBanner.setDelayTime(1500);
            mBanner.setIndicatorGravity(BannerConfig.RIGHT);

        }
    }

     class NavigationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tab_category)
        LinearLayout mCategory;
        @BindView(R.id.tab_rank)
        LinearLayout mRank;
        @BindView(R.id.tab_tencent)
        LinearLayout mTencent;
        @BindView(R.id.tab_boutique)
        LinearLayout mBoutique;


        NavigationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ((ImageView) mCategory.getChildAt(0)).setImageResource(R.drawable.tab_category);
            ((ImageView) mRank.getChildAt(0)).setImageResource(R.drawable.tab_rank);
            ((ImageView) mTencent.getChildAt(0)).setImageResource(R.drawable.tab_tencent);
            ((ImageView) mBoutique.getChildAt(0)).setImageResource(R.drawable.tab_boutique);

            ((TextView) mCategory.getChildAt(1)).setText("分类");
            ((TextView) mRank.getChildAt(1)).setText("榜单");
            ((TextView) mTencent.getChildAt(1)).setText("腾讯");
            ((TextView) mBoutique.getChildAt(1)).setText("精品");

            mCategory.setOnClickListener(this);
            mRank.setOnClickListener(this);
            mTencent.setOnClickListener(this);
            mBoutique.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.click(v.getId());
            }
        }
    }
}