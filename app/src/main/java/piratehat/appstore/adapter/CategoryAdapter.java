package piratehat.appstore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import piratehat.appstore.R;

/**
 *
 * Created by PirateHat on 2018/11/2.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private static final String TAG = "CategoryAdapter";
    private Context mContext;
    private List<List<Map<String, String>>> mMapList;
    private Map<String,SimpleAdapter> mAdapters;
    private String[][] mStrings;
    private String[] mCategory;
    private int[] mImages;

    public CategoryAdapter(Context context) {
        mContext = context;
        initData();
    }
    
    private void initData(){


        mStrings = new String[][]{{"在线视频","播放器","短视频 ","直播"},
                {"新闻","电子书","漫画","轻阅读","八卦段子","听书"},
                {"聊天交友","婚恋","社区交友","表情头像"},
                {"贷款","手机银行","彩票投注","支付钱包","投资理财","记账"},
                {"在线商城","导购优惠","二手商城","生鲜电商","海淘","物流快递"},
                {"浏览器","输入法","WIFI","娱乐工具","生活工具","通讯工具"},
                {"相机","图片编辑","视频编辑","图片分享"},
                {"在线音乐","K歌","FM电台","乐器","铃声"},
                {"餐饮美食","求职招聘","汽车服务","生活服务","住房装修","票务"},
                {"学生学习","儿童早教","外语学习","成人学习","驾照"},
                {"地图导航","打车租车","火车票","旅游服务","旅游攻略","城市交通"},
                {"手机美化","垃圾清理","安全服务","省电文件","服务root"},
                {"运动健身","医疗用药","健康养生","经期孕期","美容美体"},
                {"办公软件","笔记","商家办公","邮箱"},
                {"母婴健康","游戏辅助"}
    };
        mCategory = new String[]{"视频","阅读","社交","理财","购物","工具",
                "摄影","音乐","生活","教育","旅游出行","系统","健康","办公","特色分类"};
        mMapList = new ArrayList();
        for (int i = 0; i < mCategory.length; i++) {
            ArrayList details =  new ArrayList();
            for (int j = 0; j < mStrings[i].length; j++) {
                Map<String,String> map = new HashMap<>();
                if (!TextUtils.isEmpty(mStrings[i][j])){
                    map.put("tv_category",mStrings[i][j]);
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
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false),mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setMaps(mMapList.get(position));
        Log.e(TAG, "onBindViewHolder: "+holder.mGvDetail.getAdapter().getCount());
        holder.mTvCategory.setText(mCategory[position]);


    }

    @Override
    public int getItemCount() {

        return mMapList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_category)
        ImageView mIvCategory;
        @BindView(R.id.tv_category)
        TextView mTvCategory;
        @BindView(R.id.gv_detail)
        GridView mGvDetail;
        private List<Map<String,String>> mMaps;
        private SimpleAdapter mAdapter;
        ViewHolder(View itemView,Context context) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mMaps = new ArrayList<>();
            mAdapter = new SimpleAdapter(context,mMaps, R.layout.item_gridview, new String[]{"tv_category"}, new int[]{R.id.tv_category});
            mGvDetail.setAdapter(mAdapter);
        }

        public void setMaps(List<Map<String, String>> maps) {
            mMaps = maps;
            mAdapter.notifyDataSetChanged();

        }
    }
}
