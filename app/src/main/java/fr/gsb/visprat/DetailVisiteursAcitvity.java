package fr.gsb.visprat;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import fr.gsb.visprat.dao.PasserelleMedecin;
import fr.gsb.visprat.metier.Medecin;
import fr.gsb.visprat.metier.Visiteur;

public class DetailVisiteursAcitvity extends Activity {
    /**
     * visiteur, nom de la donnée extra dans l'intention déclenchant l'activité DetailMedecinActivity
     */

    private Visiteur leVisiteur;
    private int position;
    private EditText editTextMdp;
    private EditText editTextMdp1;
    private EditText editTextMdp2;

//Ecouteur événement bouton modifier
    private Button buttonUpdate;

    public static final String VISITEUR = "visiteurs";


    /**
     * Méthode appelée lors de la création de l'activité
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visiteurs_mdp);
        initialisations();
    }

    /**
     * Initialise le paramètre menu avec le contenu de la ressource xml menu
     * @param menu
     * @return true si le menu doit être affiché, false sinon
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.depts, menu);
        return true;
    }
//endregion MethodesProtegeesRedefinies
//region MethodesPrivees
    /**
     * Initialise les attributs privés référençant les widgets de l'interface utilisateur
     * Instancie les écouteurs et les affecte sur les objets souhaités
     */
    private void initialisations() {
        // récupération des widgets pour la description du médecin
        this.editTextMdp = (EditText) findViewById(R.id.editTextMdp);
        this.editTextMdp1 = (EditText) findViewById(R.id.editTextMdp1);
        this.editTextMdp2 = (EditText) findViewById(R.id.editTextMdp2);


        this.buttonUpdate = (Button) findViewById(R.id.buttonUpdatemdp);

        // récupération du visiteur véhiculé par l'intention
        Intent uneIntention;
        uneIntention = getIntent();
        //this.position = uneIntention.getIntExtra(POSITION, 0);

        // initialisation des zones d'éditions à partir du visiteur reçu

        //this.editTextMdp1.setText(this.leVisiteur.getMotPasse());
        //dandrthis.editTextMdp2.setText(this.leVisiteur.getMotPasse());

        VisPratApplication monAppli;
        monAppli = (VisPratApplication) DetailVisiteursAcitvity.this.getApplication();
        // cette méthode permet de lancer l'exécution de la tache longue
        // ici, on demande les informations de disponibilité d'une station à partir de son numéro

        leVisiteur =  monAppli.getVisiteur();

        // initialisation des écouteurs d'événements
        this.buttonUpdate.setOnClickListener(new OnButtonClick());
    }

    /**
     * Constitue un dictionnaire des clés / valeurs modifiées sur le formulaire
     * Les clés correspondent aux noms de données attendues par le Web Service
     * @return HashMap<String, String>
     */
    private HashMap<String, String> getHashMapToUpdate() {
        HashMap<String, String> laHashMap;
        laHashMap = new HashMap<String, String>();

        if (! editTextMdp.getText().toString().equals(leVisiteur.getMotPasse())) {
            laHashMap.put("mdp", editTextMdp.getText().toString());
        }

        return laHashMap;
    }
//endregion MethodesPrivees
//region ClassesInternesPrivees
    /**
     * Classe interne servant d'écouteur de l'événement click sur le bouton Modifier
     */
    private class OnButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // on demande à récupérer uniquement les champs de saisie qui ont subi une modification
            HashMap<String, String> hashMapToUpdate;
            hashMapToUpdate = getHashMapToUpdate();
            if ( hashMapToUpdate.size() == 0 ) { // aucun champ de saisie n'a été changée
                Toast.makeText(DetailVisiteursAcitvity.this, R.string.aucuneDonnees, Toast.LENGTH_LONG).show();
            }
            else if(editTextMdp.getText().toString() == leVisiteur.getMotPasse()){
                new VisiteurUpdate().execute(leVisiteur, hashMapToUpdate);
            }
            else {
                Toast.makeText(DetailVisiteursAcitvity.this, R.string.errMDP, Toast.LENGTH_LONG).show();

            }
        }
    }
    /**
     * Classe interne pour prendre en charge l'appel à un service web et sa réponse
     * La modification des données des médecins fait en effet appel au service web et peut donc prendre quelques sec.
     * A partir d'Android 3.0, une requête HTTP doit être effectuée à l'intérieur d'une tâche asynchrone
     * TypeParam1 : Object - 2 paramètres fournis à la tâche, un de type Medecin, un de type HashMap
     * TypeParam2 : Void - pas de paramètres fournis pendant la durée du traitement
     * TypeParam3 : Object - type de paramètre Medecin ou Exception
     * @see AsyncTask
     */
    private class VisiteurUpdate extends AsyncTask<Object, Void, Object> {
        /**
         * Permet de lancer l'exécution de la tâche longue
         * ici, on demande à modifier les données d'un médecin
         */
        @Override
        protected Object doInBackground(Object... params) {
            VisPratApplication monAppli;
            monAppli = (VisPratApplication) DetailVisiteursAcitvity.this.getApplication();
            try {
                PasserelleMedecin.updateMedecin(monAppli.getVisiteur(), (Medecin) params[0], (HashMap<String, String>) params[1]);
            } catch (Exception ex) {
                return ex;
            }
            return params[0];
        }
    }}
