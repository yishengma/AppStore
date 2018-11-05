package piratehat.appstore.contract;

import com.shizhefei.mvc.IAsyncDataSource;

import java.util.ArrayList;
import java.util.List;

import piratehat.appstore.Bean.AppBean;

/**
 *
 * Created by PirateHat on 2018/11/5.
 */

public interface ITencentContract  {

    interface IView{
        void setAppsList(ArrayList<AppBean> beans);

        void showError(String msg);

    }
    interface IModel extends IAsyncDataSource<ArrayList<AppBean>>{

        void  getAppsList(IPresenter presenter);
    }

    interface IPresenter {
        void setAppsList(ArrayList<AppBean> beans);
        void getAppsList();
        void showError(String msg);
        IAsyncDataSource loadMore();
    }



}
