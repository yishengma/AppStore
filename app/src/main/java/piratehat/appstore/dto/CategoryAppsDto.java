package piratehat.appstore.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import piratehat.appstore.Bean.AppBean;

/**
 *
 * Created by PirateHat on 2018/11/3.
 */

public class CategoryAppsDto implements Mapper<List<AppBean>> {
    private int total;
    private int count;
    private boolean success;
    private String msg;
    private Obj obj;

    @Override
    public List<AppBean> transform() {
        ArrayList<AppBean> appBeans = new ArrayList<>();
        int size = obj.getAppDetails().size();
        for (int i = 0; i < size; i++) {
            appBeans.add(obj.getAppDetails().get(i).transform());
        }
        return appBeans;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Obj getObj() {
        return obj;
    }

    public void setObj(Obj obj) {
        this.obj = obj;
    }

    class Obj {
        private List<AppDto> appDetails;
        private int hasNext;

        public List<AppDto> getAppDetails() {
            return appDetails;
        }

        public void setAppDetails(List<AppDto> appDetails) {
            this.appDetails = appDetails;
        }

        public int getHasNext() {
            return hasNext;
        }

        public void setHasNext(int hasNext) {
            this.hasNext = hasNext;
        }
    }
}
