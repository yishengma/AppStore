package piratehat.appstore.contract;

import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;

import java.util.List;

import piratehat.appstore.Bean.AppBean;

/**
 *
 * Created by PirateHat on 2018/10/29.
 */

public interface IAppDataSource<T> {
    RequestHandle refresh(ResponseSender<T> sender);

    RequestHandle loadMore(ResponseSender<T> sender);

    boolean hasMore();

}
