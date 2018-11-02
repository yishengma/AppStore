package piratehat.appstore.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.githang.statusbar.StatusBarCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import piratehat.appstore.R;
import piratehat.appstore.adapter.ViewPagerAdapter;
import piratehat.appstore.fragment.CategoryFragment;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tl_navigation)
    TabLayout mTlNavigation;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorBackgroundWhite), true);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolBar.setTitle("分类");

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(CategoryFragment.newInstance(CategoryFragment.sSOFTWARE));
        viewPagerAdapter.addFragment(CategoryFragment.newInstance(CategoryFragment.sGAMES));
        mVpContent.setAdapter(viewPagerAdapter);
        mTlNavigation.setupWithViewPager(mVpContent);


        mTlNavigation.getTabAt(0).setText("软件");
        mTlNavigation.getTabAt(1).setText("游戏");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tools, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_search:

                break;
            case R.id.item_download:

                break;
            case android.R.id.home: {
                finish();

            }

        }
        return false;

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CategoryActivity.class);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, Bundle bundle) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }
}
