package piratehat.appstore.ui;

import java.util.HashMap;

import static piratehat.appstore.config.Download.State.NEW;

/**
 *
 * Created by PirateHat on 2018/11/21.
 */

public class DownloadUtil {


    private static DownloadUtil sDownlaod;



    private HashMap<String, State> mMap;

    private DownloadUtil() {
        mMap = new HashMap<>();
    }


    public static DownloadUtil getInstance() {
        if (sDownlaod == null) {
            sDownlaod = new DownloadUtil();
        }
        return sDownlaod;
    }

    public State getState(String url) {
        State state =  mMap.get(url);
        if (state==null){
            state = new State();
            state.setState(NEW);
        return state;
        }
        return state;
    }

    public HashMap<String, State> getMap() {
        return mMap;
    }

    public void setState(int state, String url){
       State state1 =  mMap.get(url);
       if (state1!=null){
           state1.setState(state);
       }
       mMap.put(url,state1);
    }




    class State {
        private int mState;

        public int getState() {
            return mState;
        }

        public void setState(int state) {
            mState = state;
        }
    }
}
