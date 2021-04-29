package fr.gsb.visprat.dao;

public class Configuration {
    private static String protocol = "https";
    private static String host = "192.168.0.23";
    private static String path = "sio2/simon/WS-VisMed";

    /**
     * Fournit l'URL complète d'accès à l'API-Rest
     * @return Url complète d'accès à l'API-Rest VisMed
     */
    public static String getUrlHoteWS() {
        String url = protocol + "://" + host + "/" + path + "/";
        return url;
    }
}
