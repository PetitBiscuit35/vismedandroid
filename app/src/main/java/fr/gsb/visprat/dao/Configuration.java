package fr.gsb.visprat.dao;

import android.text.Editable;

public class Configuration {

    private static String protocol = "http";
    public static String host = "100.115.28.104";
    public static String path = "sio2/arthurlevesque/WS-VisMed";



    /**
     * Fournit l'URL complète d'accès à l'API-Rest
     *
     * @return Url complète d'accès à l'API-Rest VisMed
     */
    public static String getUrlHoteWS() {
        if(host == null){
            Configuration.host = "100.115.28.104";
        }
        if(path == null){
            Configuration.path = "sio2/arthurlevesque/WS-VisMed";
        }
        String url = protocol + "://" + host + "/" + path + "/";
        return url;
    }


    public static String getHost() {
        return host;
    }

    public static String getPath() {
        return path;
    }




    public static void setHost(String host) {
        Configuration.host = host;
    }

    public static void setPath(String path) {
        Configuration.path = path;
    }
}