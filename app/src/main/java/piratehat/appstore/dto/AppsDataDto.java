package piratehat.appstore.dto;

import java.util.ArrayList;
import java.util.List;

import piratehat.appstore.Bean.AppBean;

/**
 *
 *
 * Created by PirateHat on 2018/10/28.
 */

public class AppsDataDto implements Mapper<List<AppBean>>{
   private int count;
   private List<AppDto> obj;
   private boolean success;
   private String msg;


    @Override
    public List<AppBean> transform() {
        ArrayList<AppBean> appBeans = new ArrayList<>();
        int size = obj.size();
        for (int i = 0; i < size; i++) {
            appBeans.add(obj.get(i).transform());
        }
        return appBeans;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AppDto> getObj() {
        return obj;
    }

    public void setObj(List<AppDto> obj) {
        this.obj = obj;
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
}
