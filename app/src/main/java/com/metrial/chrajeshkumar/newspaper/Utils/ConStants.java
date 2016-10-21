package com.metrial.chrajeshkumar.newspaper.Utils;

/**
 * Created by ChRajeshKumar on 9/16/2016.
 */
public class ConStants {



    /*CLASS SHOW CONTROL*/
    final public static int DIALOG_CONTROL = 100;
    final public static int KEYBOARD_CONTROL = 200;
    final public static int ACTIVITY_CONTROL = 300;


    /*TEXT SHOW CONTROL*/
    final public static int NETWORK_CONNECTION_ERROR = 1;
    public static int YOUTUBE_ERROR = 2;
    final public static int ERROR_SEARCH = 3;
    final public static int EMAIL_ERROR = 4;
    final public static int PASSWORD_ERROR = 5;


    /*TEXT FOR IDENTIFICATION*/
    final public static String ENGLISH = "English";
    final public static String TELUGU = "Telugu";
    final public static String HINDI = "Hindi";
    final public static String TAMIL = "Tamil";
    final public static String MALAYALAM = "Malayalam";

    /*TEXT FOR SHARING*/
    final public static String FACEBOOK = "Facebook";
    final public static String TWITTER = "Twitter";
    final public static String IMGULER = "Imguler";
    final public static String INSTAGRAM = "Instagram";

    /*TEXT FOR CATEGORIES*/
    final public static String SPORTS = "sports";
    final public static String TECHNOLOGY = "technology";
    final public static String ENTERTAINMENT = "entertainments";

   public  enum IDNTIFIACTION{ ENGLISH(1), TELUGU(2), HINDI(3), TAMIL(4),MALAYALAM(5);
        int identifaction;
        IDNTIFIACTION(int identifaction) {
            this.identifaction = identifaction;
        }
    }
}
