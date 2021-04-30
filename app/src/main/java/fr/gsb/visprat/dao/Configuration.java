package fr.gsb.visprat.dao;

public class Configuration {
    private static String protocol = "http";
    private static String host = "192.168.0.34";
    private static String path = "sio2/LEVESQUE/WS-VisMed";

    /**
     * Fournit l'URL complète d'accès à l'API-Rest
     * @return Url complète d'accès à l'API-Rest VisMed
     */
    public static String getUrlHoteWS() {
        String url = protocol + "://" + host + "/" + path + "/";
        return url;
    }
}
