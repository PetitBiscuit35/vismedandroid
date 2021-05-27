package fr.gsb.visprat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import fr.gsb.visprat.dao.PasserelleMedecin;
import fr.gsb.visprat.dao.PasserelleVisiteur;
import fr.gsb.visprat.metier.Medecin;
import fr.gsb.visprat.metier.Visiteur;

import static fr.gsb.visprat.dao.Configuration.setHost;
import static fr.gsb.visprat.dao.Configuration.setPath;

public class MotDePasseActivity extends AppCompatActivity {
    private Visiteur leVisiteur;
    private EditText ancienMdp;
    private EditText newMdp;
    private EditText confirmation;
    private Button valider;

    /**
     * Méthode appelée lors de la création de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motdepasse);
        initialisations();
    }

    /**
     * Récupère les données passées dans l'intention reçue
     * Instancie et exécute un thread séparé pour récupérer les médecins
     * Initialise les attributs privés référençant les widgets de l'interface utilisateur
     */
    private void initialisations() {
        ancienMdp = (EditText) findViewById(R.id.EditTextAncienMdp);
        newMdp = (EditText) findViewById(R.id.EditTextNewMdp);
        confirmation = (EditText) findViewById(R.id.editTextConfirmation);

        valider = (Button) findViewById(R.id.buttonUpdateMdp);

        this.valider.setOnClickListener(new changerMdp());
    }
    private class changerMdp implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            VisPratApplication monAppli;
            monAppli = (VisPratApplication) MotDePasseActivity.this.getApplication();
            leVisiteur = monAppli.getVisiteur();
            if (ancienMdp.getText().toString().equals(leVisiteur.getMotPasse())) {
                if (newMdp.getText().toString().equals(confirmation.getText().toString()))
                    if (newMdp.getText().length() >= 12){
                        new MotDePasseActivity.VisiteurUpdate().execute();
                    }
            }

        }
    }

    private class VisiteurUpdate extends AsyncTask<Object, Void, Object> {
        /**
         * Permet de lancer l'exécution de la tâche longue
         * ici, on demande à modifier les données d'un médecin
         */
        @Override
        protected Object doInBackground(Object... params) {
            VisPratApplication monAppli;
            monAppli = (VisPratApplication) MotDePasseActivity.this.getApplication();
            try {
                PasserelleVisiteur.updateVisiteur(monAppli.getVisiteur(), newMdp.getText().toString());
            }
            catch (Exception ex) {
                return ex;
            }
            return params[0];
        }
    }
}
