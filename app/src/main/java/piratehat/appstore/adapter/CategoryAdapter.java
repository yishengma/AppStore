package piratehat.appstore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.shizhefei.mvc.IDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.R;
import piratehat.appstore.config.Constant;
import piratehat.appstore.fragment.CategoryFragment;

/**
 * Created by PirateHat on 2018/11/2.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>  {
    private static final String TAG = "CategoryAdapter";

    private Context mContext;
    private List<List<Map<String, String>>> mMapList;
    private Map<String, SimpleAdapter> mAdapters;
    private String[][] mDetailCategory;
    private String[] mCategory;
    private int[] mImages;
    private OnClickListener mListener;

    public void setListener(OnClickListener listener) {
        mListener = listener;
    }

    public interface OnClickListener {
        void onClick(String kind, String[] detail, String select);
    }

    public CategoryAdapter(Context context, int type) {
        mContext = context;
        initData(type);
    }

    private void initData(int type) {
        if (type == CategoryFragment.sSOFTWARE) {
            mDetailCategory = Constant.SOFEWARE;
            mCategory = Constant.SOFTWARE_CATEGORY;
            mImages = new int[]{
                    R.drawable.iv_software_1,
                    R.drawable.iv_software_2,
                    R.drawable.iv_software_3,
                    R.drawable.iv_software_4,
                    R.drawable.iv_software_5,
                    R.drawable.iv_software_6,
                    R.drawable.iv_software_7,
                    R.drawable.iv_software_8,
                    R.drawable.iv_software_9,
                    R.drawable.iv_software_10,
                    R.drawable.iv_software_11,
                    R.drawable.iv_software_12,
                    R.drawable.iv_software_13,
                    R.drawable.iv_software_14,
                    R.drawable.iv_software_15,};
        }

        if (type == CategoryFragment.sGAMES) {
            mDetailCategory = Constant.GAME;
            mCategory = Constant.GAME_CATEGORY;
            mImages = new int[]{
                    R.drawable.iv_game_1,
                    R.drawable.iv_game_2,
                    R.drawable.iv_game_3,
                    R.drawable.iv_game_4,
                    R.drawable.iv_game_5,
                    R.drawable.iv_game_6,
                    R.drawable.iv_game_7,
                    R.drawable.iv_game_8,
                    R.drawable.iv_game_9,
                    R.drawable.iv_game_10,};
        }
        mMapList = new ArrayList();
        for (int i = 0; i < mCategory.length; i++) {
            ArrayList details = new ArrayList();
            for (int j = 0; j < mDetailCategory[i].length; j++) {
                Map<String, String> map = new HashMap<>();
                if (!TextUtils.isEmpty(mDetailCategory[i][j])) {
                    map.put("tv_category", mDetailCategory[i][j]);
                }
                details.add(map);
            }
            mMapList.add(details);
        }
        mAdapters = new HashMap<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false), mContext, mMapList.get(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int outIndex = position;
        holder.mTvCategory.setText(mCategory[outIndex]);
        holder.mIvCategory.setImageResource(mImages[outIndex]);

        holder.mGvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onClick(mCategory[outIndex], mDetailCategory[outIndex], mMapList.get(outIndex).get(position).get("tv_category"));
                }
            }
        });
        holder.mRlKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(mCategory[outIndex], mDetailCategory[outIndex], mCategory[outIndex]);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMapList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


     class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_category)
        ImageView mIvCategory;
        @BindView(R.id.tv_category)
        TextView mTvCategory;
        @BindView(R.id.gv_detail)
        GridView mGvDetail;
        @BindView(R.id.rl_kind)
        RelativeLayout mRlKind;
        private SimpleAdapter mAdapter;

        ViewHolder(View itemView, Context context, List<Map<String, String>> maps) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mAdapter = new SimpleAdapter(context, maps, R.layout.item_gridview, new String[]{"tv_category"}, new int[]{R.id.tv_category});
            mGvDetail.setNumColumns(3);
            mGvDetail.setAdapter(mAdapter);


        }

    }
}
