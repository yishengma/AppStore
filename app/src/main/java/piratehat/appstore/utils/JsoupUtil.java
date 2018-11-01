package piratehat.appstore.utils;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, List<AppBean>> getRankApps(String s) {
        Document document = Jsoup.parse(s);
        Element root = document.getElementsByClass("ind-week-rank").first();
        Element ul = root.getElementById("J_RankTabBody");
        Elements li = ul.children();
        Map<String, List<AppBean>> listMap = new HashMap<>();
        initAppsMap(listMap);
        for (int i = 0, size = li.size(); i < size; i++) {

            Element ul1 = li.get(i).getElementsByClass("rank-body T_RankBody").first();
            Elements li1 = ul1.getElementsByTag("li");
            for (Element e : li1) {
                addWeekRankApps(i, listMap, createWeekApp(e));
            }

        }

        Elements ind_rank = document.getElementsByClass("ind-rank");

        for (int i = 0, size = ind_rank.size(); i < size; i++) {
              createIndApp(i,listMap,ind_rank.get(i));
        }

        return listMap;
    }

    private void addWeekRankApps(int i, Map<String, List<AppBean>> map, AppBean appBean) {
        if (i == 0) {
            map.get("游戏下载").add(appBean);
        } else {
            map.get("软件下载").add(appBean);
        }
    }

    private void createIndApp(int i,Map<String, List<AppBean>> map,Element div) {
        Element ul = div.getElementsByClass("rank-body T_RankBody").first();
        Elements li1 = ul.children();
        for (Element e : li1) {
            addRankAppBean(i, map, createWeekApp(e));
        }

    }

    private void addRankAppBean(int i, Map<String, List<AppBean>> map, AppBean appBean) {
        if (i == 0) {
            map.get("棋牌游戏").add(appBean);
        } else if (i == 1) {
            map.get("动作游戏").add(appBean);
        } else {
            map.get("社交软件").add(appBean);
        }
    }

    private AppBean createWeekApp(Element element) {
        AppBean appBean = new AppBean();
        Element div = element.getElementsByClass("rank-info").first();

        Element div1 = div.getElementsByClass("down-count").first();

        Element span = div1.getElementsByTag("span").first();
        appBean.setHot(span.text());

        Element div2 = div.getElementsByClass("hover-ins-btn-box T_HoverInsBtnBox").first();

        Element a = div2.getElementsByTag("a").first();

        appBean.setDownloadUrl(a.attr("ex_url"));
        appBean.setName(a.attr("appname"));
        appBean.setMpkgName(a.attr("apk"));
        appBean.setIconUrl(a.attr("appicon"));

        return appBean;


    }

    private void initAppsMap(Map<String, List<AppBean>> map) {
        String[] msg = new String[]{"游戏下载", "软件下载", "棋牌游戏", "动作游戏", "社交软件"};
        for (int i = 0; i < 5; i++) {
            ArrayList<AppBean> list = new ArrayList<>();
            map.put(msg[i], list);
        }

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
