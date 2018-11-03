package piratehat.appstore.contract;

import com.shizhefei.mvc.IAsyncDataSource;

import java.util.ArrayList;
import java.util.List;

import piratehat.appstore.Bean.AppBean;

/**
 *
 * Created by PirateHat on 2018/11/3.
 */

public interface IAppsContract {


    interface IView{
        void setResult(ArrayList<AppBean> beans);
        void showError(String msg);
    }

    interface IModel extends IAsyncDataSource<List<AppBean>>{
        void getCategory(IPresenter presenter,String category);
    }

    interface IPresenter{
        void setResult(ArrayList<AppBean> beans);
        void getCategory(String category);
        void showError(String msg);
        IAsyncDataSource getAppsData(String category);
    }
}
