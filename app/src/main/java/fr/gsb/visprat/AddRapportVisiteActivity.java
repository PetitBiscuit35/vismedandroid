package fr.gsb.visprat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import static fr.gsb.visprat.dao.Configuration.getUrlHoteWS;
import static fr.gsb.visprat.dao.PasserelleRapportVisite.AddRapportVisite;

public class AddRapportVisiteActivity extends AppCompatActivity {
    private EditText editTextIdMedecin, editTextDateVisite, editTextDateCreaRapport, editTextBilan, editTextCoefConfiance, editTextIdMotifVisite;
    private Button buttonAjouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rapport_visite);
        initialisations();
    }

    private void initialisations() {
        editTextIdMedecin = (EditText) findViewById(R.id.editTextIdMedecin);
        editTextDateVisite = (EditText) findViewById(R.id.editTextDateVisite);
        editTextDateCreaRapport = (EditText) findViewById(R.id.editTextDateCreaRapport);
        editTextBilan = (EditText) findViewById(R.id.editTextBilan);
        editTextCoefConfiance = (EditText) findViewById(R.id.editTextCoefConfiance);
        editTextIdMotifVisite = (EditText) findViewById(R.id.editTextIdMotifVisite);

        // Désactivation du bouton ajouter
        buttonAjouter = (Button) findViewById(R.id.buttonAjouter);
        buttonAjouter.setEnabled(false);
        buttonAjouter.setOnClickListener(new OnButtonClick());

        // Ecouteurs
        editTextIdMedecin.addTextChangedListener(new CheckIfEmpty());
        editTextDateVisite.addTextChangedListener(new CheckIfEmpty());
        editTextDateCreaRapport.addTextChangedListener(new CheckIfEmpty());
        editTextBilan.addTextChangedListener(new CheckIfEmpty());
        editTextCoefConfiance.addTextChangedListener(new CheckIfEmpty());
        editTextIdMotifVisite.addTextChangedListener(new CheckIfEmpty());
    }

    private class CheckIfEmpty implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Si les 2 champs ne sont pas renseignés le bouton de validation n'est pas activé
            if (editTextIdMedecin.getText().length() > 0 && editTextDateVisite.getText().length() > 0
                    && editTextDateCreaRapport.getText().length() > 0 && editTextBilan.getText().length() > 0
                    && editTextCoefConfiance.getText().length() > 0 && editTextIdMotifVisite.getText().length() > 0) {
                buttonAjouter.setEnabled(true);
            } else {
                buttonAjouter.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private class OnButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String url = getUrlHoteWS() + "index.php/visiteurs/a17/rapports";
            Log.e("PasserelleRapportVisite", "Erreur Rapport Visite" + url);

            try { new CreateRapportVisite(); }
            catch(Exception ex)
            {

            }

            try { AddRapportVisite();} catch (Exception e) { }
        }
    }

    private class CreateRapportVisite extends AsyncTask<Object, Void, Object> {
        /**
         * Permet de lancer l'exécution de la tâche longue
         * ici, on demande à modifier les données d'un médecin
         */
        @Override
        protected Object doInBackground(Object... params) {
            try {
                AddRapportVisite();
                Log.e("PasserelleRapportVisite", "Erreur Rapport Visite");
                Toast.makeText(AddRapportVisiteActivity.this, R.string.rapportAjoute, Toast.LENGTH_LONG).show();
            } catch (Exception e) {

            }
            return "Envoyé";
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }
}

