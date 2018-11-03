package piratehat.appstore.Bean;

import java.io.Serializable;
import java.util.Map;

/**
 *
 *
 * Created by PirateHat on 2018/11/1.
 */

public class SerializableMap implements Serializable {
    private Map mMap;

    public SerializableMap(Map map) {
        mMap = map;
    }

    public Map getMap() {
        return mMap;
    }

    public void setMap(Map map) {
        mMap = map;
    }
}
