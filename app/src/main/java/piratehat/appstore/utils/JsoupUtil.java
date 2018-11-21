package piratehat.appstore.utils;

import android.text.TextUtils;
import android.util.ArrayMap;
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
import piratehat.appstore.Bean.AppDetailBean;
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
            createIndApp(i, listMap, ind_rank.get(i));
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

    private void createIndApp(int i, Map<String, List<AppBean>> map, Element div) {
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

    public Map getBoutiqueApps(String msg) {
        Map<String, List<AppBean>> map = new HashMap<>();
        Document document = Jsoup.parse(msg);
        getBoutiqueApp(map, document);
        getBoutiqueGame(map, document);
        getCategoryBody(map, document);
        getUnion(map, document);

        return map;

    }

    private void getBoutiqueApp(Map map, Document document) {
        List<AppBean> beans = new ArrayList<>();
        Element box = document.getElementsByClass("boutique-app-box").first();
        Elements apps = box.getElementsByClass("com-vertical-app");
        for (Element app : apps) {
            beans.add(createApp(app));
        }
        map.put("精品游戏", beans);
    }

    private void getBoutiqueGame(Map map, Document document) {
        List<AppBean> beans = new ArrayList<>();
        Element box = document.getElementsByClass("ind-boutique-game").first();
        Elements apps = box.getElementsByClass("com-vertical-app");
        for (Element app : apps) {
            beans.add(createApp(app));
        }
        map.put("精品软件", beans);
    }


    private AppBean createApp(Element app) {
        AppBean appBean = new AppBean();
        Element div = app.getElementsByClass("com-vertical-type").first();

        appBean.setIntro(div.text());

        Element a = app.getElementsByClass("T_ComEventAppIns com-install-btn").first();

        appBean.setDownloadUrl(a.attr("ex_url"))
                .setName(a.attr("appname"))
                .setMpkgName(a.attr("apk"))
                .setIconUrl(a.attr("appicon"));
        return appBean;
    }


    private void getCategoryBody(Map map, Document document) {
        Element div = document.getElementsByClass("category-tab-body").first();
        Elements lis = div.getElementsByTag("li");
        String[] titles = new String[]{"角色扮演", "生活", "理财", "社交"};
        for (int i = 0, size = lis.size(); i < size; i++) {
            map.put(titles[i], createBodys(lis.get(i)));
        }
    }

    private List<AppBean> createBodys(Element li) {
        Elements div = li.getElementsByClass("com-crosswise-app");
        List<AppBean> beans = new ArrayList<>();
        for (Element comApp : div) {
            AppBean bean = new AppBean();
            Element info = comApp.getElementsByClass("app-info").first();

            Element downcount = info.getElementsByClass("down-count").first();

            bean.setHot(downcount.text());

            Element a = comApp.getElementsByClass("T_ComEventAppIns com-install-btn").first();

            bean.setDownloadUrl(a.attr("ex_url"))
                    .setName(a.attr("appname"))
                    .setMpkgName(a.attr("apk"))
                    .setIconUrl(a.attr("appicon"));

            beans.add(bean);
        }
        return beans;
    }

    private void getUnion(Map map, Document document) {

        map.put("男生", getUnionApp(document.getElementsByClass("union-left").first()));
        map.put("女生", getUnionApp(document.getElementsByClass("union-right").first()));
    }

    private List<AppBean> getUnionApp(Element union) {
        Elements apps = union.getElementsByClass("com-vertical-lit-app");
        List<AppBean> appBeans = new ArrayList<>();
        for (Element app : apps) {
            AppBean appBean = new AppBean();
            Element install = app.getElementsByClass("T_ComEventAppIns com-install-lit-btn").first();
            appBean.setDownloadUrl(install.attr("ex_url"))
                    .setName(install.attr("appname"))
                    .setMpkgName(install.attr("apk"))
                    .setIconUrl(install.attr("appicon"));

            appBeans.add(appBean);
        }
        return appBeans;

    }

    public AppDetailBean getAppDetailInfo(String s) {

        Document document = Jsoup.parse(s);
        AppDetailBean bean = new AppDetailBean();
        Element element = document.getElementById("J_DetDataContainer");
        Element element1 = element.getElementsByTag("div").first();
        Element element19 = element1.getElementsByClass("det-ins-container J_Mod ").first();
        Element element2 = element19.getElementsByClass("det-icon").first();
        Element element17 = element2.getElementsByTag("img").first();

        bean.setIcon(element17.attr("src"));//
        Element element20 = element19.getElementsByClass("det-ins-data").first();
        Element element21 = element20.getElementsByClass("det-name").first();
        Element element18 = element21.getElementsByClass("det-name-int").first();

        bean.setName(element18.text());//
        Element element22 = element20.getElementsByClass("det-star-box").first();
        Element element3 = element22.getElementsByClass("com-blue-star-num").first();
        Element element23 = element22.getElementsByClass("det-comment-num").first();
        Element element5 = element23.getElementsByTag("a").first();
        bean.setScore(element3.text() + element5.text());//

        Element element24 = element20.getElementsByClass("det-insnum-line").first();
        Element element6 = element24.getElementsByClass("det-ins-num").first();
        Element element7 = element24.getElementsByClass("det-size").first();

        bean.setBaseInfo(element6.text() + "~" + element7.text());//
        bean.setSize(element7.text());

        Element element29 = element1.getElementsByClass("det-pic-scroll-container").first();
        Element element30 = element29.getElementsByClass("pic-turn-hover-box").first();
        Element element31 = element30.getElementsByClass("pic-turn-hidden-box").first();
        Element element8 = element31.getElementById("J_PicTurnImgBox");
        Elements element9 = element8.getElementsByClass("pic-img-box");
        ArrayList<String> list = new ArrayList<>();
        for (Element element10 : element9) {
            Element element11 = element10.getElementsByTag("img").first();
            list.add(element11.attr("data-src"));
        }

        bean.setImageList(list); //
        Element element32 = element1.getElementsByClass("det-othinfo-container J_Mod").first();
        Element element10 = element32.getElementsByClass("det-othinfo-tit").first();
        Element element11 = element32.getElementsByClass("det-othinfo-data").first();
        Element element12 = element32.getElementsByClass("det-othinfo-tit").get(1);
        Element element13 = element32.getElementsByClass("det-othinfo-data").get(1);
        Element element14 = element32.getElementsByClass("det-othinfo-tit").get(2);
        Element element15 = element32.getElementsByClass("det-othinfo-data").get(2);


        String builder = (element10.text() + element11.text() + "\n") +
                element12.text() + element13.text() + "\n" +
                element14.text() + element15.text();

        bean.setInfo(builder);//

        Element element16 = element1.getElementsByClass("det-intro-container").first();
        Element element25 = element16.getElementsByClass("det-intro-text").first();

        Element element26 = element25.getElementsByClass("det-app-data-info").first();
        String s1 = element26.text();
        String s2 = s1.replace("<br>", "\\r\\n");
        String s3 = s2.replace("\"", "");
        bean.setDetailInfo(s3);
        Element element4 = element19.getElementsByClass("det-ins-btn-box").first();
        Element element27 = element4.getElementsByTag("a").first();
        bean.setDownloadUrl(element27.attr("ex_url"));
        return bean;

    }

}
