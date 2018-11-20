package piratehat.appstore.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import piratehat.appstore.R;

/**
 * Created by PirateHat on 2018/11/20.
 */

public class ImageFragment extends BaseFragment {

    @BindView(R.id.imv_icon)
    ImageView mImvIcon;

    private String mUrl;

    @Override
    protected int setLayoutResId() {
        return R.layout.fragment_image;
    }

    @Override
    protected void initData(Bundle bundle) {
        mUrl = bundle.getString("url");

    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(mUrl)) {
            Glide.with(this).load(mUrl).into(mImvIcon);
        }
    }

    @Override
    protected void initListener() {

    }

    public static ImageFragment newInstance(String url) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }


}
