package fr.gsb.visprat.dao;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import fr.gsb.visprat.metier.Medecin;
import fr.gsb.visprat.metier.Visiteur;

/**
 * Classe prenant en charge l'appel des web services pour obtenir ou modifier les données
 * concernant les médecins
 * @author sio2slamd
 */
public class PasserelleMedecin extends Passerelle {
    public static String urlMedecins = Configuration.getUrlHoteWS() + "index.php/medecins";

    public static String urlDepts = Configuration.getUrlHoteWS() + "index.php/medecins/departement/";

    public static String filtreDept = "departement";

    /**
     * Fournit la liste des départements
     * @param login login nécessaire à l'authentification
     * @param motPasse mot de passe nécessaire à l'authentification
     * @return ArrayList<Integer> liste des départements
     * @throws Exception dans le cas de pb de communication, d'erreur d'authentification, ....
     */
    public static ArrayList<Integer> getLesDepts(String login, String motPasse) throws Exception{
        ArrayList<Integer> lesDepts;
        Integer unNumeroDept;
        try
        {

            // on prépare une requête http get pour l'URL depts et les données d'authentification
            HttpURLConnection uneRequete = prepareHttpRequestAuth(urlDepts, "GET", login, motPasse);

            // on récupère le résultat JSON, réponse du serveur http à cette requête
            JSONObject unObjetJSON = loadResultJSON(uneRequete);

            JSONArray lesDeptsJS = unObjetJSON.getJSONArray("data");

			/* Exemple de données obtenues pour le tableau de clé data :
					{"data" : [ {noDept:"1"}, {"noDept":2}, {noDept:"3"}, .... {noDept:"98"},
			*/
            // création d'un objet ArrayList
            lesDepts = new ArrayList<Integer> ();
            // parcours de la liste des noeuds <dept>
            for (int i = 0 ; i < lesDeptsJS.length() ; i++)
            {	// création de l'élement courant à chaque tour de boucle
                JSONObject courant = lesDeptsJS.getJSONObject(i);
                // lecture de la balise <name>
                unNumeroDept = getUnNumeroDeptFromJSONObject(courant);
                // ajoute le nom de district à l'objet lesNomsDeDistrict
                lesDepts.add(unNumeroDept);
            }
            return lesDepts;
        }
        catch (Exception ex) {
            Log.e("Passerelle", "Erreur exception : " + ex.toString());
            throw ex;
        }
    }
    /**
     * Fournit la liste des médecins résidant dans le département dont le numéro est spécifié
     * @param noDept
     * @param leVisiteur visiteur connecté
     * @return ArrayList<Medecin> liste des médecins
     * @throws Exception dans le cas de pb de communication, d'erreur d'authentification, ....
     */
    public static ArrayList<Medecin> getLesMedecins(int noDept, Visiteur leVisiteur) throws Exception {
        ArrayList<Medecin> lesMedecins = null;
        Medecin unMedecin;
        String uneURL;
        try {
            uneURL = urlMedecins + "/" + filtreDept + "/" + noDept;
            // on prépare une requête http get pour l'URL medecins et les données d'authentification
            HttpURLConnection uneRequete = prepareHttpRequestAuth(uneURL, "GET", leVisiteur);
            // on récupère le résultat JSON, réponse du serveur http à cette requête
            JSONObject unObjetJSON = loadResultJSON(uneRequete);

            JSONArray lesMedecinsJS = unObjetJSON.getJSONArray("data");

			/* Exemple de données obtenues pour le tableau de clé medecins :
					{"data" : [
					                {"nom":"Durand", "prenom":"Catherine", "codePostal":"35200", "specialiteComplementaire":""},
					                {"nom":"Duval", "prenom":"Philippe", "codePostal":"35200", "ville":"Fougères"},
					                ....
					               ]
				    }
			*/
            // création d'un objet ArrayList en vue de contenir les numéros de départements
            lesMedecins = new ArrayList<Medecin> ();
            // parcours de la liste des noeuds <medecin>
            for (int i = 0 ; i < lesMedecinsJS.length() ; i++) {
                // création de l'élément courant à chaque tour de boucle
                JSONObject courant = lesMedecinsJS.getJSONObject(i);
                // constitution du médecin à partir de l'objet JSON courant
                unMedecin = getMedecinFromJSONObject(courant);
                // ajoute le médecin à la collection des médecins
                lesMedecins.add(unMedecin);
            }
        }

		catch (Exception ex) {
            Log.e("Passerelle", "Erreur exception : " + ex.toString());
            throw ex;
        }
        return lesMedecins;
    }

    /**
     * Prend en charge la mise à jour des données à modifier d'un médecin
     * et rend le médecin modifié lorsque la mise à jour a été réellement réalisée
     * @param leVisiteur visiteur connecté
     * @param leMedecin le médecin initial
     * @param laHashMapToUpdate dictionnaire regroupant clés et valeurs des données modifiées à envoyer
     * @return Medecin le médecin avec ses données modifiées
     * @throws Exception dans le cas de pb de communication, d'erreur d'authentification, ....
     */
    public static Medecin updateMedecin(Visiteur leVisiteur, Medecin leMedecin, HashMap<String, String> laHashMapToUpdate) throws Exception {
        String uneURL;
        try
        {
            uneURL = urlMedecins + "/" + leMedecin.getId();
            // on prépare une requête http PUT avec les données à mettre à jour
            HttpURLConnection uneConnexion = prepareHttpRequestWithData(uneURL,"PUT", leVisiteur, laHashMapToUpdate);
            // on récupère le résultat JSON, réponse du serveur http à cette requête
            JSONObject result = loadResultJSON(uneConnexion);

            // on met à jour le médecin pour chaque caractéristique ayant subi une modification
            leMedecin.setNom((laHashMapToUpdate.containsKey("nom")) ? laHashMapToUpdate.get("nom") : leMedecin.getNom());
            leMedecin.setPrenom((laHashMapToUpdate.containsKey("prenom")) ? laHashMapToUpdate.get("prenom") : leMedecin.getPrenom());
            leMedecin.setVille((laHashMapToUpdate.containsKey("ville")) ? laHashMapToUpdate.get("ville") : leMedecin.getVille());

            leMedecin.setAdresse((laHashMapToUpdate.containsKey("adresse")) ? laHashMapToUpdate.get("adresse") : leMedecin.getAdresse());
            leMedecin.setCodePostal((laHashMapToUpdate.containsKey("codePostal")) ? laHashMapToUpdate.get("codePostal") : leMedecin.getCodePostal());
            leMedecin.setTel((laHashMapToUpdate.containsKey("tel")) ? laHashMapToUpdate.get("tel") : leMedecin.getTel());
            leMedecin.setEmail((laHashMapToUpdate.containsKey("email")) ? laHashMapToUpdate.get("email") : leMedecin.getEmail());
            leMedecin.setSpecialiteComplementaire((laHashMapToUpdate.containsKey("specialiteComplementaire")) ? laHashMapToUpdate.get("specialiteComplementaire") : leMedecin.getSpecialiteComplementaire());
        }
        catch (Exception ex) {
            Log.e("Passerelle", "Erreur exception : \n" + ex.toString());
            throw ex;
        }
        return leMedecin;
    }

    /**
     * Instancie un médecin à partir d'un objet JSON contenant tous les éléments caractéristiques d'un médecin
     * @param unObjetJSON
     * @return Medecin le médecin construit à partir des données JSON
     * @throws Exception dans le cas d'erreur de format JSON ....
     */
    private static Medecin getMedecinFromJSONObject(JSONObject unObjetJSON) throws Exception {
        Integer unId;
        String unNom, unPrenom, uneVille, uneAdresse, unCodePostal, unTel, unEmail, uneSpecComp;
        Medecin unMedecin;

        unId = unObjetJSON.getInt("id");
        unNom = unObjetJSON.getString("nom");
        unPrenom = unObjetJSON.getString("prenom");
        uneAdresse = unObjetJSON.getString("adresse");
        uneVille = unObjetJSON.getString("ville");
        unCodePostal = unObjetJSON.getString("codePostal");
        unTel = unObjetJSON.getString("tel");
        unEmail = unObjetJSON.getString("email");
        uneSpecComp = unObjetJSON.getString("specialiteComplementaire");
        unMedecin = new Medecin (unId, unNom, unPrenom, uneAdresse, unCodePostal, uneVille, unTel, unEmail, uneSpecComp);
        return unMedecin;
    }
    /**
     * Instancie un département à partir d'un élément portant noDept comme nom de balise
     * @param unObjetJSON
     * @return Integer
     * @throws Exception dans le cas d'erreur de format JSON ....
     */
    private static Integer getUnNumeroDeptFromJSONObject(JSONObject unObjetJSON) throws Exception {
        Integer num;
        num = (Integer) Integer.parseInt(unObjetJSON.getString("noDepts"));
        return num;
    }
}