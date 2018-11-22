package piratehat.appstore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import piratehat.appstore.Bean.AppDownloadInfo;
import piratehat.appstore.R;
import piratehat.appstore.config.Download;

/**
 * Created by PirateHat on 2018/11/22.
 */

public class DownloadAppAdapter extends RecyclerView.Adapter<DownloadAppAdapter.ViewHolder> {


    private List<AppDownloadInfo> mInfos;
    private Context mContext;

    public DownloadAppAdapter(List<AppDownloadInfo> infos, Context context) {
        mInfos = infos;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_download, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppDownloadInfo info = mInfos.get(position);
        if (!TextUtils.isEmpty(info.getIcon())){
            Glide.with(mContext).load(info.getIcon()).into(holder.mImvIcon);

        }

        holder.mTvName.setText(info.getName());
        holder.mProgressLoad.setProgress(info.getDownloadProgress());
        switch (info.getState()){
            case Download.State.DOWNLOADING:
                holder.mBtnDownload.setText(info.getDownloadProgress()+"%");
                break;
            case Download.State.FINISHED:
                holder.mBtnDownload.setText("已完成");
                break;
            case Download.State.PAUSE:
                holder.mBtnDownload.setText("继续");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imv_icon)
        ImageView mImvIcon;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.progress_load)
        ProgressBar mProgressLoad;
        @BindView(R.id.btn_download)
        TextView mBtnDownload;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
