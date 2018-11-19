package piratehat.appstore.utils;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import piratehat.appstore.Bean.AppBean;

/**
 *
 * Created by PirateHat on 2018/11/19.
 */

public class Cache {
    private static Cache sCache ;
    private HashMap<Integer, ArrayList<AppBean>> mMap ;

    @SuppressLint("UseSparseArrays")
    private Cache() {
         mMap = new HashMap<>();
    }

    public static  Cache getInstance(){
          if (sCache==null){
              sCache = new Cache();
          }
          return sCache;
    }

    public void setListInMap(int id, ArrayList<AppBean> list){
        mMap.put(id,list);
    }

    public void addItemInList(int id,ArrayList<AppBean> list){
         mMap.get(id).addAll(list);
    }

    public void removeList(int id){
        mMap.remove(id);
    }

    public ArrayList getList(int id){
        return mMap.get(id);
    }

}
