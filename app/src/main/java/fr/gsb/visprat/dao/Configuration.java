package fr.gsb.visprat.dao;

public class Configuration {
    private static String protocol = "http";
    private static String host = "100.115.28.78";
    private static String path = "sio2/MONTEIL/WS-VisMed";

    /**
     * Fournit l'URL complète d'accès à l'API-Rest
     * @return Url complète d'accès à l'API-Rest VisMed
     */
    public static String getUrlHoteWS() {
        String url = protocol + "://" + host + "/" + path + "/";
        return url;
    }
}
