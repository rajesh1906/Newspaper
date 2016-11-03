package com.metrial.chrajeshkumar.newspaper.Utils;

/**
 * Created by ChRajeshKumar on 8/31/2016.
 */
public class Endpoints {

    public static String searching = "";
    public static String nextpagetoken = "";
    public static String search_string = "";

    public static String time_of_india = "http://timesofindia.indiatimes.com/";
    public static String hindustan_times = "http://www.hindustantimes.com/";
    public static String indian_express = "http://indianexpress.com/";
    public static String the_hindu = "http://www.thehindu.com/";
    public static String dc = "http://www.deccanchronicle.com/";
    public static String businessstandard = "http://www.business-standard.com/";
    public static String eenadu = "http://www.eenadu.net/";
    public static String andhra_bhoomi = "http://www.andhrabhoomi.net/";
    public static String andhra_joythi = "http://www.andhrajyothy.com/";
    public static String sakshi = "http://www.sakshi.com/";
    public static String namaste_el = "http://www.namasthetelangaana.com/";
    public static String vartha = "http://www.indiapress.org/gen/news.php/Vaartha/";
    public static String amar_ujala = "http://epaper.amarujala.com/?format=img";
    public static String dainik_jagran = "http://epaper.jagran.com/homepage.aspx";
    public static String hindi_milap = "http://www.webmilap.com/index.aspx";
    public static String nava_bharat = "http://epaper.navabharat.org/";
    public static String prabhat_khabar = "http://epaper.prabhatkhabar.com/";
    public static String national_duniya = "http://epapernationalduniya.in/";

    //tamil
    public static String dina_thanthi = "http://www.dailythanthi.com/News/World";
    public static String dinakaran = "http://epaper.dinakaran.com/index.php?rt=index/frontpage/20160927/01";
    public static String dinamalar = "http://www.dinamalar.com/latest_main.asp";
    public static String dina_mani = "http://epaper.dinamani.com/";
    public static String maalai_malar = "http://www.maalaimalar.com/";
    public static String thinaboomi = "http://www.thinaboomi.com/";

    //malayalam
    public static String malayala_manorama = "http://www.manoramaonline.com/news.html";
    public static String mathrubhumi = "http://www.mathrubhumi.com/";
    public static String deshabhimani = "http://www.deshabhimani.com/";
    public static String madhyamam = "http://www.madhyamam.com/";
    public static String mangalam = "http://www.mangalam.com/";
    public static String kerala_kaumudi = "http://www.indiapress.info/keralakaumudi.malayalam.html";

    //Fetch_category urls(Technology)
    public static String bbc_technology = "http://www.bbc.com/news/technology";
    public static String gadget_360 = "http://gadgets.ndtv.com/";
    public static String info_world = "http://www.infoworld.com/category/mobile-technology/";
    public static String mobile_tech_today = "http://www.mobile-tech-today.com/";
    public static String tech_news_world = "http://www.technewsworld.com/";
    public static String techgig = "https://www.techgig.com/tech-news/editors-pick";

    //Fetch category urls (Sports)
    public static String tennis_news = "http://www.espn.com/tennis/";
    public static String criclet_news = "http://www.espncricinfo.com/";
    public static String hockey_news = "http://www.fih.ch/";
    public static String volleyball_news = "http://www.fivb.com/en/about/news";
    public static String table_tennis = "http://www.tabletennisprss.co.uk/";
    public static String football_news = "http://www.football365.com/";

    //Fetch category urls (Entertainment)
    public static String movies = "http://movieweb.com/movies/2016/";
    public static String songs = "http://gaana.com/";
    public static String beauty_tips = "http://www.instyle.com/beauty/25-best-star-beauty-tips-all-time";
    public static String health = "http://www.health.com/";
    public static String weather = "http://worldweather.wmo.int/en/home.html";
    public static String astrology = "";

    public static String getSearching(String search_string, int limit) {

//        searching="https://www.googleapis.com/youtube/v3/videos?chart=mostPopular&part=snippet&key="+Config.YOUTUBE_API_KEY+"&maxResults="+15+"&pageToken="+nextpagetoken;
        //searching="https://www.googleapis.com/youtube/v3/videos?chart=mostPopular&part=snippet&key=";

        searching = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + search_string + "&type=video&key=" + Config.YOUTUBE_API_KEY + "&maxResults=" + limit + "&pageToken=" + nextpagetoken;
        return searching;

    }


}
