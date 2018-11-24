package piratehat.appstore.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;




import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.R;
import piratehat.appstore.adapter.RankAppsAdapter;
import piratehat.appstore.app.App;

/**
 *
 * Created by PirateHat on 2018/10/31.
 */

public class RankFragment extends BaseFragment {


    @BindView(R.id.rv_apps)
    RecyclerView mRvApps;

    private List<AppBean> mAppBeans;
    private RankAppsAdapter mAppsAdapter;

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible){

        }
    }
    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_rank;
    }

    @Override
    protected void initData(Bundle bundle) {

        mAppBeans = new ArrayList<>();
        ArrayList list = (ArrayList) bundle.getSerializable("apps");
        if (list!=null ){
            mAppBeans.addAll(list);
        }

        mAppsAdapter = new RankAppsAdapter(mAppBeans,mActivity);
    }

    @Override
    protected void initView() {
        mRvApps.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        mRvApps.setAdapter(mAppsAdapter);
    }

    @Override
    protected void initListener() {

    }

    public static RankFragment newInstance(ArrayList appBeans){
        RankFragment fragment = new RankFragment();
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
