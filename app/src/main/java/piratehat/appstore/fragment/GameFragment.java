package piratehat.appstore.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.shizhefei.view.coolrefreshview.CoolRefreshView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.R;
import piratehat.appstore.adapter.GameMainAdapter;

/**
 * Created by PirateHat on 2018/10/27.
 */

public class GameFragment extends BaseFragment {

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.btn_download)
    ImageButton mBtnDownload;
    @BindView(R.id.rv_apps)
    RecyclerView mRvApps;
    @BindView(R.id.crv_apps)
    CoolRefreshView mCrvApps;

    private GameMainAdapter mAdapter;
    private ArrayList<AppBean> mAppBeans;

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_game;
    }

    @Override
    protected void initData(Bundle bundle) {
      mAppBeans = new ArrayList<>();
      mAdapter = new GameMainAdapter(mAppBeans,mActivity);
    }

    @Override
    protected void initView() {
       mRvApps.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
       mRvApps.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }



}
