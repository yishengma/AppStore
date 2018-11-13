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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.R;
import piratehat.appstore.adapter.GameMainAdapter;
import piratehat.appstore.adapter.SoftwareMainAdapter;
import piratehat.appstore.contract.ISoftwareContract;

/**
 *
 * Created by PirateHat on 2018/10/27.
 */

public class SoftwareFragment extends BaseFragment implements ISoftwareContract.IView{


    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.btn_download)
    ImageButton mBtnDownload;
    @BindView(R.id.rv_apps)
    RecyclerView mRvApps;
    @BindView(R.id.crv_apps)
    CoolRefreshView mCrvApps;

    private ArrayList<AppBean> mAppBeans;
    private SoftwareMainAdapter mAdapter;



    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_software;
    }

    @Override
    protected void initData(Bundle bundle) {
        mAppBeans = new ArrayList<>();
        mAdapter = new SoftwareMainAdapter(mAppBeans,mActivity);
    }

    @Override
    protected void initView() {
        mRvApps.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        mRvApps.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }
    @Override
    public void setAllApps(List<AppBean> list) {

    }

}
