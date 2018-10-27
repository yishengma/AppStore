package piratehat.appstore.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PirateHat on 2018/10/27.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{
    private List<android.support.v4.app.Fragment> mFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(Fragment fragment){
        mFragments.add(fragment);
    }
}
