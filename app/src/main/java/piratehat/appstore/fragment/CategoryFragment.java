package piratehat.appstore.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import piratehat.appstore.R;
import piratehat.appstore.adapter.CategoryAdapter;

/**
 * Created by PirateHat on 2018/11/2.
 */

public class CategoryFragment extends BaseFragment {
    public static final int sGAMES = 1;
    public static final int sSOFTWARE = 1;
    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;


    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        mRvCategory.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        mRvCategory.setAdapter(new CategoryAdapter(mActivity));
    }

    @Override
    protected void initListener() {

    }

    public static CategoryFragment newInstance(int type) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        return fragment;
    }


}
