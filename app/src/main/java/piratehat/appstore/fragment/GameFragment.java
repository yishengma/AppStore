package piratehat.appstore.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.shizhefei.mvc.MVCCoolHelper;
import com.shizhefei.mvc.MVCHelper;
import com.shizhefei.view.coolrefreshview.CoolRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.R;
import piratehat.appstore.adapter.GameMainAdapter;
import piratehat.appstore.config.Constant;
import piratehat.appstore.contract.IGameContract;
import piratehat.appstore.presenter.GamePresenter;
import piratehat.appstore.ui.ClassifyActivity;

/**
 * Created by PirateHat on 2018/10/27.
 */

public class GameFragment extends BaseFragment implements IGameContract.IView {

    @BindView(R.id.et_search)
    EditText mEtSearch;

    @BindView(R.id.rv_apps)
    RecyclerView mRvApps;
    @BindView(R.id.crv_apps)
    CoolRefreshView mCrvApps;
    private static final String TAG = "GameFragment";
    @BindView(R.id.btn_collection)
    ImageButton mBtnCollection;

    private GameMainAdapter mAdapter;
    private ArrayList<AppBean> mAppBeans;
    private MVCHelper<List<AppBean>> mMVCHelper;
    private GamePresenter mPresenter;

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_game;
    }

    @Override
    protected void initData(Bundle bundle) {
        mAppBeans = new ArrayList<>();
        mAdapter = new GameMainAdapter(mAppBeans, mActivity);
        mPresenter = new GamePresenter(this);
        mPresenter.getAppsList();

    }

    @Override
    protected void initView() {
        mRvApps.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRvApps.setAdapter(mAdapter);
        mMVCHelper = new MVCCoolHelper<>(mCrvApps);
        mMVCHelper.setDataSource(mPresenter.loadMore());
        mMVCHelper.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mBtnCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassifyActivity.actionStart(mActivity, "游戏", Constant.CAME_CATEGORIES, Constant.CAME_CATEGORY_ID);

            }
        });
    }

    @Override
    public void setAllApps(List<AppBean> list) {
        mAppBeans.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

}
