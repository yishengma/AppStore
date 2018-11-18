package piratehat.appstore.contract;

import com.shizhefei.mvc.IAsyncDataSource;

import java.util.ArrayList;
import java.util.List;

import piratehat.appstore.Bean.AppBean;

/**
 *
 * Created by PirateHat on 2018/11/13.
 */

public interface ISoftwareContract {

    interface  IView{
        void setAllApps(List<AppBean> list);
    }

    interface IModel extends   IAsyncDataSource<ArrayList<AppBean>>{
        void getAllApps(IPresenter presenter);
    }

    interface IPresenter{
        void setAppsList(ArrayList<AppBean> beans);
        void getAppsList();
        void showError(String msg);
        IAsyncDataSource loadMore();
    }
}
