package fr.gsb.visprat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.gsb.visprat.metier.Medecin;

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

    private class CheckIfEmpty implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Si les 2 champs ne sont pas renseignés le bouton de validation n'est pas activé
                if (editTextIdMedecin.getText().length() > 0 && editTextDateVisite.getText().length() > 0
                    && editTextDateCreaRapport.getText().length() > 0 && editTextBilan.getText().length() > 0
                    && editTextCoefConfiance.getText().length() > 0 && editTextIdMotifVisite.getText().length() > 0)
                { buttonAjouter.setEnabled(true); }
                else { buttonAjouter.setEnabled(false); }
        }
        @Override
        public void afterTextChanged(Editable editable) { }
    }

    private class OnButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // String uneURL = getUrlHoteWS() + "index.php/visiteurs/a17/rapports";
            Toast.makeText(AddRapportVisiteActivity.this, R.string.rapportAjoute, Toast.LENGTH_LONG).show();
            try {
                AddRapportVisite();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

