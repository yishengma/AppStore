package piratehat.picture.loader;

import java.util.HashMap;
import java.util.Map;



/**
 *
 * Created by PirateHat on 2019/2/21.
 */
public class LoaderManager {

    private Map<String, ILoader> mStringILoaderMap = new HashMap<>();

    private static LoaderManager mInstance = new LoaderManager();

    private LoaderManager() {
        register("http", new NetImageLoader());
        register("https", new NetImageLoader());
        register("file", new DiskImageLoader());
    }


    public static LoaderManager getInstance() {
        return mInstance;
    }

    private void register(String schema, ILoader loader) {
        mStringILoaderMap.put(schema, loader);
    }

    /**
     * 根据名称获取对应load类型
     * @param schema
     * @return
     */
    public ILoader getLoaderByType(String schema) {
        if (mStringILoaderMap.containsKey(schema)) {
            return mStringILoaderMap.get(schema);
        }
        return null;
    }

}
