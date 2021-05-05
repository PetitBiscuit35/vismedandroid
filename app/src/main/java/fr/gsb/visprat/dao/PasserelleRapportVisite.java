package fr.gsb.visprat.dao;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import fr.gsb.visprat.R;
import fr.gsb.visprat.metier.Medecin;
import fr.gsb.visprat.metier.Rapport_visite;
import fr.gsb.visprat.metier.Visiteur;

import static fr.gsb.visprat.dao.Configuration.getUrlHoteWS;

/**
 * Classe prenant en charge l'appel des web services pour obtenir ou modifier les données
 * concernant les médecins
 * @author sio2slamd
 */
public class PasserelleRapportVisite extends Passerelle {
    public static String urlRapportsVisites = getUrlHoteWS() + "index.php/visiteurs/a17/rapports"; // /visiteurs/IdVisiteur/rapports


    /**
     * Fournit la liste des médecins résidant dans le département dont le numéro est spécifié
     * @param noDept
     * @param leVisiteur visiteur connecté
     * @return ArrayList<Medecin> liste des médecins
     * @throws Exception dans le cas de pb de communication, d'erreur d'authentification, ....
     */
    public static ArrayList<Rapport_visite> getLesRapportsVisites(int noDept, Visiteur leVisiteur) throws Exception {
        ArrayList<Rapport_visite> lesRapportsVisites = null;
        Rapport_visite unRapportVisite;
        String uneURL;
        try {
            urlRapportsVisites = getUrlHoteWS() + "index.php/visiteurs/a17/rapports";
            // on prépare une requête http get pour l'URL medecins et les données d'authentification
            HttpURLConnection uneRequete = prepareHttpRequestAuth(urlRapportsVisites, "GET", leVisiteur);
            // on récupère le résultat JSON, réponse du serveur http à cette requête
            JSONObject unObjetJSON = loadResultJSON(uneRequete);

            JSONArray lesRapportsVisitesJS = unObjetJSON.getJSONArray("data");

			/* Exemple de données obtenues pour le tableau de clé medecins :
					{"data" : [
					                {"nom":"Durand", "prenom":"Catherine", "codePostal":"35200", "specialiteComplementaire":""},
					                {"nom":"Duval", "prenom":"Philippe", "codePostal":"35200", "ville":"Fougères"},
					                ....
					               ]
				    }
			*/
            // création d'un objet ArrayList en vue de contenir les numéros de départements
            lesRapportsVisites = new ArrayList<Rapport_visite> ();
            // parcours de la liste des noeuds <medecin>
            for (int i = 0 ; i < lesRapportsVisitesJS.length() ; i++) {
                // création de l'élément courant à chaque tour de boucle
                JSONObject courant = lesRapportsVisitesJS.getJSONObject(i);
                // constitution du médecin à partir de l'objet JSON courant
                unRapportVisite = getRapportVisiteFromJSONObject(courant);
                // ajoute le médecin à la collection des médecins
                lesRapportsVisites.add(unRapportVisite);
            }
        }

        catch (Exception ex) {
            Log.e("Passerelle", R.string.errException + ex.toString());
            throw ex;
        }
        return lesRapportsVisites;
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
    /*
    public static Medecin updateMedecin(Visiteur leVisiteur, Medecin leMedecin, HashMap<String, String> laHashMapToUpdate) throws Exception {
        String uneURL;
        try
        {
            urlMedecins = getUrlHoteWS() + "index.php/medecins";
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
            Log.e("Passerelle", R.string.errException + ex.toString());
            throw ex;
        }
        return leMedecin;
    } */

    /**
     * Instancie un médecin à partir d'un objet JSON contenant tous les éléments caractéristiques d'un médecin
     * @param unObjetJSON
     * @return Medecin le médecin construit à partir des données JSON
     * @throws Exception dans le cas d'erreur de format JSON ....
     */
    private static Rapport_visite getRapportVisiteFromJSONObject(JSONObject unObjetJSON) throws Exception {
        Integer unIdVisiteur, unId, unIdMedecin, unCoefConfiance, unIdMotifVisite;
        String uneDateVisite, uneDateCreaRapport, unBilan;
        Rapport_visite unRapportVisite;

        unIdVisiteur = unObjetJSON.getInt("idVisiteur");
        unId = unObjetJSON.getInt("id");
        unIdMedecin = unObjetJSON.getInt("idMedecin");
        uneDateVisite = unObjetJSON.getString("dateVisite");
        uneDateCreaRapport = unObjetJSON.getString("dateCreaRapport");
        unBilan = unObjetJSON.getString("bilan");
        unCoefConfiance = unObjetJSON.getInt("ccoefConfiance");
        unIdMotifVisite = unObjetJSON.getInt("idMotifVisite");

        unRapportVisite = new Rapport_visite(unIdVisiteur, unId, unIdMedecin, uneDateVisite, uneDateCreaRapport, unBilan, unCoefConfiance, unIdMotifVisite);
        return unRapportVisite;
    }
}