package piratehat.appstore.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.githang.statusbar.StatusBarCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import piratehat.appstore.R;
import piratehat.appstore.adapter.ViewPagerAdapter;
import piratehat.appstore.config.Constant;
import piratehat.appstore.fragment.RefreshAppsFragment;

public class ClassifyActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tl_navigation)
    TabLayout mTlNavigation;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;

    private String[] mClassifies;
    private int[] mCategoryId;

    private String mCategory;

    @Override
    protected int setResId() {
        return R.layout.activity_category_details;
    }

    @Override
    protected void initView() {
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorBackgroundWhite), true);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolBar.setTitle(mCategory);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        for (int i = 0; i < mClassifies.length; i++) {

            viewPagerAdapter.addFragment(RefreshAppsFragment.newInstance(mCategoryId[i]));
        }

        mVpContent.setAdapter(viewPagerAdapter);
        mTlNavigation.setupWithViewPager(mVpContent);


        for (int i = 0; i < mClassifies.length; i++) {
            mTlNavigation.getTabAt(i).setText(mClassifies[i]);
        }
    }

    @Override
    protected void initData(Bundle bundle) {
        mCategory = bundle.getString("category");
        mClassifies = bundle.getStringArray("classify");
        mCategoryId = bundle.getIntArray("id");
    }

    @Override
    protected void initListener() {

    }

    public static void actionStart(Context context, String category, String[] classify, int[] id) {
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        bundle.putIntArray("id", id);
        bundle.putStringArray("classify", classify);
        Intent intent = new Intent(context, ClassifyActivity.class);
        intent.putExtra("bundle",bundle);
        context.startActivity(intent);

    }


}
