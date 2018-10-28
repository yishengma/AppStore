package piratehat.appstore.utils;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.Bean.BannerBean;


/**
 * Created by PirateHat on 2018/10/27.
 */

public class JsoupUtil {
    private static final String TAG = "JsoupUtil";
    private static JsoupUtil jsoupUtil;

    private JsoupUtil() {
    }

    public static JsoupUtil getInstance() {
        if (jsoupUtil == null) {
            synchronized (JsoupUtil.class) {
                if (jsoupUtil == null) {
                    jsoupUtil = new JsoupUtil();
                }
            }
        }
        return jsoupUtil;
    }

    public List<BannerBean> getBanner(String s) {
        Document document = Jsoup.parse(s);
        Element ul = document.getElementById("J_IndBanner");
        Elements lis = ul.getElementsByTag("li");

        List<BannerBean> bannerBeans = new ArrayList<>();
        for (Element e : lis) {
            //图片的 Url，因为是轮播图，所有两个属性轮流有值
            String style = e.attr("style");
            String original = e.attr("data-original");

            //详情的 Url
            Element a = e.getElementsByTag("a").first();

            //应用的名字
            Element div = e.getElementsByTag("div").get(1);
            Element a1 = div.getElementsByTag("a").first();
            Element h = a1.getElementsByTag("h4").first();

            BannerBean bannerBean = new BannerBean();
            bannerBean.setImageUrl(!TextUtils.isEmpty(original) ? original : style.substring(style.indexOf("(") + 1, style.indexOf(")")));

            bannerBean.setDetailUrl(a.attr("href"));
            bannerBean.setName(h.text());
            bannerBeans.add(bannerBean);
        }
        return bannerBeans;
    }

    public List<AppBean> getApplications(String s) {
        Document document = Jsoup.parse(s);
        Element ul = document.getElementsByClass("app-list clearfix").first();

        Elements li = ul.getElementsByTag("li");

        ArrayList<AppBean> beans = new ArrayList<>();


        for (Element e : li) {

            Element divRoot = e.getElementsByTag("div").first();

            Element div1 = divRoot.getElementsByTag("div").first();

            Element a1 = div1.getElementsByTag("a").get(1);

            Element span1 = div1.getElementsByTag("span").first();
            Element span2 = div1.getElementsByTag("span").get(1);
            Element a2 = div1.getElementsByTag("a").get(2);



            AppBean appBean = new AppBean();
            appBean.setName(a1.text());
            appBean.setDetailUrl(a1.attr("href"));
            appBean.setAppSize(span1.text());
            appBean.setHot(span2.text());
            appBean.setIconUrl(a2.attr("appicon"));
            appBean.setDownloadUrl(a2.attr("ex_url"));

            beans.add(appBean);




        }


        return beans;
    }
}
