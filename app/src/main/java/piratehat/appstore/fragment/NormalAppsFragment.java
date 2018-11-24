package piratehat.appstore.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.R;
import piratehat.appstore.adapter.CommonAppsAdapter;
import piratehat.appstore.app.App;

/**
 *
 *
 * Created by PirateHat on 2018/11/6.
 */

public class NormalAppsFragment extends BaseFragment {

    @BindView(R.id.rv_apps)
    RecyclerView mRvApps;

    private List<AppBean> mAppBeans;
    private CommonAppsAdapter mAdapter;

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible){

        }
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_normal;
    }

    @Override
    protected void initData(Bundle bundle) {

        mAppBeans = new ArrayList<>();
        ArrayList list = (ArrayList) bundle.getSerializable("apps");
        if (list!=null ){
            mAppBeans.addAll(list);
        }
        mAdapter = new CommonAppsAdapter(mActivity, mAppBeans);

    }

    @Override
    protected void initView() {
        mRvApps.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mRvApps.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }

    public static NormalAppsFragment newInstance(ArrayList appBeans) {
        NormalAppsFragment fragment = new NormalAppsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("apps", appBeans);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getmRerWatcher().watch(this);
    }

}
