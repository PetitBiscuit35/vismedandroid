package fr.gsb.visprat.dao;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import fr.gsb.visprat.R;
import fr.gsb.visprat.metier.Medecin;
import fr.gsb.visprat.metier.Medicament;
import fr.gsb.visprat.metier.Visiteur;

import static fr.gsb.visprat.dao.Configuration.getUrlHoteWS;

/**
 * Classe prenant en charge l'appel des web services pour obtenir ou modifier les données
 * concernant les médecins
 * @author sio2slamd
 */
public class PasserelleVisiteur extends Passerelle {
    public static String urlVisiteur = getUrlHoteWS() + "index.php/visiteurs";

    /**
     * Prend en charge la mise à jour des données à modifier d'un Visiteur
     * et rend le visiteur modifié lorsque la mise à jour a été réellement réalisée
     * @param leVisiteur visiteur connecté
     * @param leVisiteur le médecin initial
     * @param laHashMapToUpdate dictionnaire regroupant clés et valeurs des données modifiées à envoyer
     * @return Visiteur visiteur avec ses données modifiées
     * @throws Exception dans le cas de pb de communication, d'erreur d'authentification, ....
     */
    public static Visiteur updateVisiteur(Visiteur leVisiteur, HashMap<String, String> laHashMapToUpdate) throws Exception {
        String uneURL;
        try
        {
            urlVisiteur = getUrlHoteWS() + "index.php/visiteurs";
            uneURL = urlVisiteur + "/" + leVisiteur;
            // on prépare une requête http PUT avec les données à mettre à jour
            HttpURLConnection uneConnexion = prepareHttpRequestWithData(uneURL,"PUT", leVisiteur, laHashMapToUpdate);
            // on récupère le résultat JSON, réponse du serveur http à cette requête
            JSONObject result = loadResultJSON(uneConnexion);

            // on met à jour le visiteur pour le mdp ayant subi une modification
            leVisiteur.setMdp((laHashMapToUpdate.containsKey("mdp")) ? laHashMapToUpdate.get("mdp") : leVisiteur.getMotPasse());

        }
        catch (Exception ex) {
            Log.e("Passerelle", R.string.errException + ex.toString());
            throw ex;
        }
        return leVisiteur;
    }

    }
