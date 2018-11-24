package piratehat.appstore.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;

import piratehat.appstore.R;
import piratehat.appstore.adapter.CategoryAdapter;
import piratehat.appstore.app.App;
import piratehat.appstore.ui.CategoryDetailsActivity;

/**
 * Created by PirateHat on 2018/11/2.
 */

public class CategoryFragment extends BaseFragment {
    public static final int sGAMES = 0;
    public static final int sSOFTWARE = 1;
    private int mType;
    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;

    private CategoryAdapter mAdapter;

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible){

        }
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initData(Bundle bundle) {
        mType = bundle.getInt("type");
        mAdapter = new CategoryAdapter(mActivity, mType);
    }

    @Override
    protected void initView() {
        mRvCategory.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));

        mRvCategory.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setListener(new CategoryAdapter.OnClickListener() {
            @Override
            public void onClick(String kind, String[] detail, String select) {
                Bundle bundle = new Bundle();
                bundle.putString("category", kind);
                bundle.putStringArray("details", detail);
                bundle.putString("select", select);
                CategoryDetailsActivity.actionStart(mActivity, CategoryDetailsActivity.class, bundle);
            }
        });
    }

    public static CategoryFragment newInstance(int type) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getmRerWatcher().watch(this);
    }
}
