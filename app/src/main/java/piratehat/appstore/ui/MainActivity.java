package piratehat.appstore.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.githang.statusbar.StatusBarCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import piratehat.appstore.R;
import piratehat.appstore.adapter.ViewPagerAdapter;
import piratehat.appstore.fragment.GameFragment;
import piratehat.appstore.fragment.MainFragment;
import piratehat.appstore.fragment.ManagerFragment;
import piratehat.appstore.fragment.SoftwareFragment;
import piratehat.appstore.utils.BottomNavigationViewHelper;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    @BindView(R.id.bnv_navigation)
    BottomNavigationView mBnvNavigation;
    private MenuItem mMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorBackgroundWhite), true);

        BottomNavigationViewHelper.disableShiftMode(mBnvNavigation);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment());
        adapter.addFragment(new GameFragment());
        adapter.addFragment(new SoftwareFragment());
        adapter.addFragment(new ManagerFragment());
        mVpContent.setAdapter(adapter);
    }

    private void initListener() {


        mBnvNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_main:
                        mVpContent.setCurrentItem(0);
                        break;
                    case R.id.item_game:
                        mVpContent.setCurrentItem(1);
                        break;
                    case R.id.item_software:
                        mVpContent.setCurrentItem(2);
                        break;
                    case R.id.item_manager:
                        mVpContent.setCurrentItem(3);
                        break;
                }
                return false;
            }

        });

        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mMenuItem != null) {
                    mMenuItem.setChecked(false);
                } else {
                    mBnvNavigation.getMenu().getItem(0).setChecked(false);
                }
                mMenuItem = mBnvNavigation.getMenu().getItem(position);
                mMenuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

}
