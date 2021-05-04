package fr.gsb.visprat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.Serializable;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import fr.gsb.visprat.dao.Configuration;
import fr.gsb.visprat.dao.HttpsTrustManager;
import fr.gsb.visprat.dao.PasserelleMedecin;
import fr.gsb.visprat.metier.Medecin;
import fr.gsb.visprat.metier.Visiteur;

import static fr.gsb.visprat.dao.Configuration.setHost;
import static fr.gsb.visprat.dao.Configuration.setPath;

public class settings extends AppCompatActivity {
    private EditText host;
    private EditText path;
    private Button buttonConfiguration;

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

        // Afficher le text dans les editText
        host.setText(Configuration.getHost());
        path.setText(Configuration.getPath());

        // An affecte un écouteur d'événement clic au bouton Valider
        // Bouton
        this.buttonConfiguration.setOnClickListener(new EnvoyerModif());
    }

        private class EnvoyerModif implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                setHost(host.getText().toString());
                setPath(path.getText().toString());
                settings.this.finish();
            }
        }


    }







