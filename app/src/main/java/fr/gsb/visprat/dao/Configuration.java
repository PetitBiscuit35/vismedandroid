package fr.gsb.visprat.dao;

import android.text.Editable;

public class Configuration {

    private static String protocol = "http";
    public static String host = "192.168.0.27";
    public static String path = "sio2/MONTEIL/WS-VisMed";



    /**
     * Fournit l'URL complète d'accès à l'API-Rest
     *
     * @return Url complète d'accès à l'API-Rest VisMed
     */
    public static String getUrlHoteWS() {
        if(host == null){
            Configuration.host = "192.168.0.27";
        }
        if(path == null){
            Configuration.path = "sio2/MONTEIL/WS-VisMed";
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