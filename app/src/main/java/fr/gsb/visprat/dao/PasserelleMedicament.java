package fr.gsb.visprat.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import fr.gsb.visprat.R;
import fr.gsb.visprat.metier.Medicament;

import static fr.gsb.visprat.dao.Configuration.getUrlHoteWS;

public class PasserelleMedicament extends Passerelle{
    public static String urlMedicaments = getUrlHoteWS() + "index.php/medicaments";
    /**
     * Fournit la liste des medicaments
     * @param login login nécessaire à l'authentification
     * @param motPasse mot de passe nécessaire à l'authentification
     * @return ArrayList<Integer> liste des médicaments
     * @throws Exception dans le cas de pb de communication, d'erreur d'authentification, ....
     */
    public static ArrayList<Medicament> getLesMedicaments(String login, String motPasse) throws Exception{
        ArrayList<Medicament> lesMedicaments;
        Medicament unMedicament;
        String uneURL;
        try {
            urlMedicaments = getUrlHoteWS() + "index.php/medicaments";

            // on prépare une requête http get pour l'URL depts et les données d'authentification
            HttpURLConnection uneRequete = prepareHttpRequestAuth(urlMedicaments, "GET", login, motPasse);

            // on récupère le résultat JSON, réponse du serveur http à cette requête
            JSONObject unObjetJSON = loadResultJSON(uneRequete);

            JSONArray lesMedicamentsJS = unObjetJSON.getJSONArray("data");

			/* Exemple de données obtenues pour le tableau de clé data :
					{"data" : [ {noDept:"1"}, {"noDept":2}, {noDept:"3"}, .... {noDept:"98"},
			*/

            // création d'un objet ArrayList en vue de contenir les numéros de départements
            lesMedicaments = new ArrayList<Medicament> ();
            // parcours de la liste des noeuds <medecin>
            for (int i = 0 ; i < lesMedicamentsJS.length() ; i++) {
                // création de l'élément courant à chaque tour de boucle
                JSONObject courant = lesMedicamentsJS.getJSONObject(i);
                // constitution du médecin à partir de l'objet JSON courant
                unMedicament = getMedicamentFromJSONObject(courant);
                // ajoute le médecin à la collection des médecins
                lesMedicaments.add(unMedicament);
            }
        }

        catch (Exception ex) {
            Log.e("Passerelle", R.string.errException + ex.toString());
            throw ex;
        }
        return lesMedicaments;
    }
    /**
     * Instancie un médicament à partir d'un objet JSON contenant tous les éléments caractéristiques d'un médicament
     * @param unObjetJSON
     * @return Medicament le medicament construit à partir des données JSON
     * @throws Exception dans le cas d'erreur de format JSON ....
     */
    private static Medicament getMedicamentFromJSONObject(JSONObject unObjetJSON) throws Exception {
        Integer unprixEchantillon;
        String undepotLegal,  unnomCommercial,  uncodeFamille , uncomposition , uneffets ,  uncontreIndic;
        Medicament unMedicament;

        //unId = unObjetJSON.getInt("id");
        undepotLegal = unObjetJSON.getString("depotLegal");
        unnomCommercial = unObjetJSON.getString("nomCommercial");

        unMedicament = new Medicament (undepotLegal, unnomCommercial);
        return unMedicament;
    }
}
