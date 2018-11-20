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
import butterknife.Unbinder;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.R;
import piratehat.appstore.adapter.SoftwareMainAdapter;
import piratehat.appstore.config.Constant;
import piratehat.appstore.contract.ISoftwareContract;
import piratehat.appstore.presenter.SoftwarePresenter;
import piratehat.appstore.ui.ClassifyActivity;

/**
 * Created by PirateHat on 2018/10/27.
 */

public class SoftwareFragment extends BaseFragment implements ISoftwareContract.IView {


    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.rv_apps)
    RecyclerView mRvApps;
    @BindView(R.id.crv_apps)
    CoolRefreshView mCrvApps;
    @BindView(R.id.btn_collection)
    ImageButton mBtnCollection;


    private ArrayList<AppBean> mAppBeans;
    private SoftwareMainAdapter mAdapter;
    private MVCHelper<List<AppBean>> mMVCHelper;
    private SoftwarePresenter mPresenter;


    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_software;
    }

    @Override
    protected void initData(Bundle bundle) {
        mAppBeans = new ArrayList<>();
        mPresenter = new SoftwarePresenter(this);
        mPresenter.getAppsList();
    }

    @Override
    protected void initView() {
        mAdapter = new SoftwareMainAdapter(mAppBeans, mActivity);
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
                ClassifyActivity.actionStart(mActivity, "软件", Constant.SOFTWARE_CATEGORIES, Constant.SOFTWARE_CATEGORY_ID);

            }
        });
    }

    @Override
    public void setAllApps(List<AppBean> list) {
        mAppBeans.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

}
