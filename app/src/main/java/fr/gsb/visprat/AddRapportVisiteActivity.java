package fr.gsb.visprat;

import android.content.Intent;
import android.os.Bundle;
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

        buttonAjouter = (Button) findViewById(R.id.buttonAjouter);
    }
}

