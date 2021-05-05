package fr.gsb.visprat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import fr.gsb.visprat.metier.Medecin;

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
}

