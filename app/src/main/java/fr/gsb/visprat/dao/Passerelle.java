package fr.gsb.visprat.dao;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.Exception;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

import org.json.JSONObject;

import fr.gsb.visprat.metier.*;

/**
 * Classe regroupant des méthodes statiques utilitaires pour la gestion des web services avec
 * des résultats au format JSON
 * @author sio2slam
 */
public class Passerelle {
	protected static String urlHoteWS = "http://192.168.43.135/SIO2/SIO2-20192020/PPE41-GSB-APIRest/index.php/";
//	protected String urlHoteWS = "http://172.17.193.200/SIO2/WS_VISPRAT/index.php/";

	/**
	 * Fournit le flux JSON reçu suite à l'appel du service web localisé à l'uri spécifié
	 * @param uneRequeteHTTP
	 * @return JSONObject données JSON figurant dans le corps de la réponse HTTP
	 * @throws Exception
	 */
	protected static JSONObject loadResultJSON(HttpURLConnection uneRequeteHTTP) throws Exception{
		JSONObject unJsonObject = null;
		String leResultat;
		String messageStatut;
		InputStream unFluxEnEntree;
		int codeStatutHttp;

		// vérification du code réponse de la réponse HTTP avec récupération du flux de données
		codeStatutHttp = uneRequeteHTTP.getResponseCode();
		// le flux de données n'est pas récupéré de la même manière suivant le code réponse
		if ( codeStatutHttp == HttpURLConnection.HTTP_OK) {
			unFluxEnEntree = uneRequeteHTTP.getInputStream();
		}
		else {
			unFluxEnEntree = uneRequeteHTTP.getErrorStream();
		}

		// conversion du flux en chaîne
		leResultat = convertStreamToString(unFluxEnEntree);

		// transformation de la chaîne en objet JSON
		JSONObject json = new JSONObject(leResultat);

		// génération d'une exception si code réponse non ok avec lecture de la donnée de clé status
		if ( codeStatutHttp != HttpURLConnection.HTTP_OK) {
			messageStatut = json.getString("status");
			Log.e("Passerelle", "Code statut " + codeStatutHttp + " Message statut : " + messageStatut);
			throw new Exception("Code statut " + codeStatutHttp + " Message statut : " + messageStatut);
		}

		return json;
	}
	/**
	 * Prépare la requête HTTP Post à partir d'une uri et des données hashMapToSend à envoyer dans
	 * le corps de la requête
	 * @param uri URI demandée
	 * @param hashMapToSend dictionnaire regroupant clés et valeurs des données modifiées à envoyer
	 * @return HttpUrlConnection requête HTTP préparée
	 * @throws Exception
	 */
	protected static HttpURLConnection prepareHttpRequestWithData(String uri,  String method, Visiteur leVisiteur, HashMap<String, String> hashMapToSend) throws Exception{
		HttpURLConnection cnx = prepareHttpRequestAuth(uri, method, leVisiteur);

		String data = "";
		// constitution des données à envoyer sous forme de couples clés / valeurs
		// parcours de chacun des éléments du dictionnaire hashMapToUpdate
		for (String key : hashMapToSend.keySet()) {
			data +=  key + "=" + URLEncoder.encode((String) hashMapToSend.get(key), "UTF-8") + "&";
		}
		data = data.substring(0,data.length()-1);

		// écriture des données sur le flux de sortie
		OutputStream os = cnx.getOutputStream();
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(os, "UTF-8"));
		writer.write(data);
		// fermeture des flux en sortie
		writer.close();
		os.close();

		return cnx;
	}

	/**
	 * Prépare une requête http avec les données d'authentification
	 * @return HttpURLConnection
	 * @param uri  URL demandée
	 * @param method méthode de la requête (GET, POST, PUT, DELETE, etc.)
	 * @param login login nécessaire à l'authentification
	 * @param motPasse mot de passe nécessaire à l'authentification
	 * @throws Exception
	 */
	protected static HttpURLConnection prepareHttpRequestAuth(String uri, String method, String login, String motPasse)  throws Exception {
		URL url = new URL(uri);
		HttpURLConnection cnx = (HttpURLConnection) url.openConnection();
		cnx.setRequestMethod(method);
		// ajoute les données d'authentification dans le champ d'entête Authorization
		String auth = login + ":" + motPasse;
		byte[] encodedBytes = android.util.Base64.encode(auth.getBytes(), android.util.Base64.DEFAULT);
		String authHeaderValue = "Basic " + new String(encodedBytes);
		cnx.setRequestProperty("Authorization", authHeaderValue);
		return cnx;
	}
	/**
	 * Prépare une requête http avec les données d'authentification
	 * @param uri  URL demandée
	 * @param method méthode de la requête (GET, POST, PUT, DELETE, etc.)
	 * @param leVisiteur visiteur connecté
	 * @return HttpURLConnection requête HTTP préparée
	 * @throws Exception
	 */
	protected static HttpURLConnection prepareHttpRequestAuth(String uri, String method, Visiteur leVisiteur)  throws Exception {
		return prepareHttpRequestAuth(uri, method, leVisiteur.getLogin(), leVisiteur.getMotPasse());
	}
	/**
	 * Lecture de l'intégralité du contenu à partir du flux en entrée is
	 * @param is  flux d'entrée
	 * @return string chaîne lue sur le fluc d'entrée
	 */
	private static String convertStreamToString(InputStream is) {
		// instanciation d'un flux bufferisé sur le flux en entrée
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		// instanciation d'un constructeur de chaîne pour mettre bout à bout les caractères reçus
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		}
		catch (IOException e) {
		}
		finally {
			try {
				is.close();  // fermeture du flux en entrée
			}
			catch (IOException e) {
				Log.e("Passerelle", "IOException pour convertir le flux en chaîne " + e.toString());
			}
		}
		return sb.toString();
	}
}