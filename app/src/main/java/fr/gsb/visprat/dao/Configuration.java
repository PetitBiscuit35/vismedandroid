package fr.gsb.visprat.dao;

public class Configuration {

    private static String protocol = "https";
    private static String host; //= "192.168.0.23";
    private static String path; //= "sio2/simon/WS-VisMed";

    /**
     * Fournit l'URL complète d'accès à l'API-Rest
     *
     * @return Url complète d'accès à l'API-Rest VisMed
     */
    public static String getUrlHoteWS()
    {
        if(host == null){
            setHost("192.168.0.23");
        }
        if(path == null){
            setPath("sio2/simon/WS-VisMed");
        }
        String url = protocol + "://" + host + "/" + path + "/";
        return url;
    }
    public static String getHost() {
        if(host == null){
            setHost("192.168.0.23");
        }
        return host;
    }

    public static String getPath() {
        if(path == null){
            setPath("sio2/simon/WS-VisMed");
        }
        return path;
    }

    public static void setHost(String host) {
        Configuration.host = host;
    }

    public static void setPath(String path) {
        Configuration.path = path;
    }
}