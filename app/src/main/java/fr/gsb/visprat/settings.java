package fr.gsb.visprat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

import fr.gsb.visprat.dao.Configuration;
import fr.gsb.visprat.dao.PasserelleMedecin;
import fr.gsb.visprat.metier.Medecin;
import fr.gsb.visprat.metier.Visiteur;


public class settings extends AppCompatActivity {
    private EditText host;
    private EditText path;
    private Button buttonConfiguration;
    public static final String SHARED_PREFS = "sharedPrefs";

    public static final String IPHOST = "iphost";

    public static final String PATH = "path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        initialisations();
    }

    private void initialisations() {


        this.host = (EditText) this.findViewById(R.id.host);
        this.path = (EditText) this.findViewById(R.id.path);
        this.buttonConfiguration = (Button) this.findViewById(R.id.buttonUpdateConfiguration);


        //afficher le text dans les editText
        host.setText(Configuration.getHost());
        path.setText(Configuration.getPath());


        // on affecte un écouteur d'événement clic au bouton Valider
        //Bouton
        this.buttonConfiguration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent uneIntention;
//
//            // crée une intention passée à l'activité de classe settings
//            uneIntention = new Intent(settings.this, Configuration.class);
//            uneIntention.putExtra(Configuration.host, host.getText());
//            uneIntention.putExtra(Configuration.path, path.getText());
//            startActivity(uneIntention);

                Configuration.setHost(host.getText().toString());
                Configuration.setPath(path.getText().toString());
                settings.this.finish();
            }
        });

    }


}






