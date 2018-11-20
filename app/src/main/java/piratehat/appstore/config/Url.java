package piratehat.appstore.config;

/**
 * Created by PirateHat on 2018/10/27.
 */

public class Url {
    //广场的
    public static final String MAIN_PAGE = "https://sj.qq.com/myapp/";


    //获取所有的应用的信息
    public static final String LOAD_MORE = "https://sj.qq.com/myapp/cate/appList.htm?orgame=1&categoryId=0&pageSize=20&pageContext=";


    //分类种类
    public static final String CATEGORY = "https://android.myapp.com/myapp/searchAjax.htm?kw=";

    public static final String[] MORE_MODE = new String[]{"&pns=&sid="
            , "&pns=MTA=&sid=0",
            "&pns=MjA=&sid=0",
            "&pns=MzA=&sid=0",
            "&pns=NDA=&sid=0",
            "&pns=NTA=&sid=0",
            "&pns=NjA=&sid=0"};
    // 腾讯的种类
    public static final String TENCENT_PAGE = "https://sj.qq.com/myapp/cate/appList.htm?orgame=1&categoryId=-10&pageSize=20&pageContext=";


    public static final String SOFTWARE_ALL = "https://sj.qq.com/myapp/cate/appList.htm?orgame=1&categoryId=0&pageSize=20&pageContext=";

    public static final String GAME_ALL = "https://sj.qq.com/myapp/cate/appList.htm?orgame=2&categoryId=0&pageSize=20&pageContext=";
    public static final String CLASSIFY_ROOT = "https://sj.qq.com/myapp/cate/appList.htm?orgame=2&categoryId=";
    public static final String CLASSIFY_CONTEXT = "&pageSize=20&pageContext=";
    public static final String DETAIL_INFO = "https://sj.qq.com/myapp/detail.htm?apkName=";

}
