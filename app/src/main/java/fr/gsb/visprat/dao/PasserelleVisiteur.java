package fr.gsb.visprat.dao;

import android.util.Log;

import org.json.JSONObject;
import fr.gsb.visprat.R;
import fr.gsb.visprat.metier.Medecin;
import fr.gsb.visprat.metier.Visiteur;

import static fr.gsb.visprat.dao.Configuration.getUrlHoteWS;

public class PasserelleVisiteur extends Passerelle {
    public static String urlVisiteurs = getUrlHoteWS() + "index.php/visiteurs";

    public static Visiteur updateVisiteur(Visiteur leVisiteur, String NewMdp) throws Exception {
        String uneURL;
        try
        {
            // on met à jour le mot de passe du visiteur
            leVisiteur.setMotPasse(NewMdp);
        }
        catch (Exception ex) {
            Log.e("Passerelle", R.string.errException + ex.toString());
            throw ex;
        }
        return leVisiteur;
}
}
