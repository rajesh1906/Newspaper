package com.metrial.chrajeshkumar.newspaper.Authentication;

/**
 * Created by ChRajeshKumar on 10/3/2016.
 */
public class Twitter {
    public static final String consumer_key = "w94yAkzc52ucb8thQgiTpNgF0";
    public static final String secret_key = "5XA06BbwjSPsLqWilVFqhzyDg8YOiKzELejgu6yX98un7iyT8i";

    // Preference Constants
    public static String PREFERENCE_NAME = "twitter_oauth";
    public static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    public static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    public static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

    public static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";

    // Twitter oauth urls
    public static final String URL_TWITTER_AUTH = "auth_url";
    public static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    public static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
}
