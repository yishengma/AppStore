package piratehat.appstore.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 * Created by PirateHat on 2018/10/27.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected View mRootView;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity =getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutResId(),container,false);
        mUnbinder = ButterKnife.bind(this,mRootView);
        initData(getArguments());
        initView();
        initListener();
        return mRootView;

    }

    protected abstract int setLayoutResId();
    protected abstract void initData(Bundle bundle);
    protected abstract void initView();
    protected abstract void initListener();

    protected void hideSoftInput(){
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(mActivity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
