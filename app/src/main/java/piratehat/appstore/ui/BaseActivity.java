package piratehat.appstore.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import piratehat.appstore.R;

/**
 *
 * Created by PirateHat on 2018/11/3.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setResId());
        ButterKnife.bind(this);
        initData(getIntent().getBundleExtra("bundle"));
        initView();
        initListener();

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
    protected abstract int setResId();
    protected abstract void initView();
    protected abstract void initData(Bundle bundle);
    protected abstract void initListener();

    public static void actionStart(Context context,Class clazz){
        Intent intent = new Intent(context,clazz);
        context.startActivity(intent);
    }
    public static void actionStart(Context context,Class clazz,Bundle bundle){
        Intent intent = new Intent(context,clazz);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }
}
