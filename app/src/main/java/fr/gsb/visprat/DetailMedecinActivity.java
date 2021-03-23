package fr.gsb.visprat;

import androidx.appcompat.app.AppCompatActivity;
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

/**
 * Classe gérant l'interface utilisateur de la saisie des données d'un médecin
 * @author sio2slam
 */
public class DetailMedecinActivity extends AppCompatActivity {
	/**
	 * medecin, nom de la donnée extra dans l'intention déclenchant l'activité DetailMedecinActivity
	 */
	public static final String MEDECIN = "medecin";
	public static final String POSITION = "position";
	private Medecin leMedecin;
    private int position;
    private EditText editTextNom;
    private EditText editTextPrenom;
    private EditText editTextVille;
    private Button buttonUpdate;
//region MethodesProtegeesRedefinies
    /**
	 * Méthode appelée lors de la création de l'activité
     * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medecin_detail);
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
        this.editTextNom = (EditText) findViewById(R.id.editTextNom);
        this.editTextPrenom = (EditText) findViewById(R.id.editTextPrenom);
        this.editTextVille = (EditText) findViewById(R.id.editTextVille);
        this.buttonUpdate = (Button) findViewById(R.id.buttonUpdate);

        // récupération du médecin véhiculé par l'intention
        Intent uneIntention;
        uneIntention = getIntent();
		this.position = uneIntention.getIntExtra(POSITION, 0);
        this.leMedecin = uneIntention.getParcelableExtra(MEDECIN);
        // initialisation des zones d'éditions à partir du médecin reçu
		this.editTextNom.setText(this.leMedecin.getNom());
		this.editTextPrenom.setText(this.leMedecin.getPrenom());
		this.editTextVille.setText(this.leMedecin.getVille());
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

        if (! editTextNom.getText().toString().equals(leMedecin.getNom())) {
            laHashMap.put("nom", editTextNom.getText().toString());
        }
        if (! editTextPrenom.getText().toString().equals(leMedecin.getPrenom())) {
            laHashMap.put("prenom", editTextPrenom.getText().toString());
        }
        if (! editTextVille.getText().toString().equals(leMedecin.getVille())) {
            laHashMap.put("ville", editTextVille.getText().toString());
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
				Toast.makeText(DetailMedecinActivity.this, "Aucune donnée modifiée", Toast.LENGTH_LONG).show();
			}
			else { // il faut demander la mise à jour du médecin
				new MedecinUpdate().execute(leMedecin, hashMapToUpdate);
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
	private class MedecinUpdate extends AsyncTask<Object, Void, Object> {
		/**
		 * Permet de lancer l'exécution de la tâche longue
		 * ici, on demande à modifier les données d'un médecin
		 */
		@Override
		protected Object doInBackground(Object... params) {
           VisPratApplication monAppli;
            monAppli = (VisPratApplication) DetailMedecinActivity.this.getApplication();
	    	try {
	    		PasserelleMedecin.updateMedecin(monAppli.getVisiteur(), (Medecin) params[0], (HashMap<String, String>) params[1]);
	    	}
	    	catch (Exception ex) {
	    		return ex;
	    	}
			return params[0];
		}
		/**
		 * Méthode automatiquement appelée quand la tâche longue se termine
		 * ici, on teste le type de résultat reçu, exception ou liste
		 * @param result objet de type Medecin ou Exception
		 */
		@Override
		protected void onPostExecute(Object result) {
			if ( result instanceof Exception ) {
				Exception ex = (Exception) result;
				Toast.makeText(DetailMedecinActivity.this, getString(R.string.msgErrUpdateMedecin) + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			else {
				// retour à l'activité appelante
                Intent uneIntention;
                uneIntention = new Intent(DetailMedecinActivity.this, MedecinsActivity.class);
                // on renvoie la position sauvegardée et le médecin modifié
                uneIntention.putExtra(POSITION, position);
                uneIntention.putExtra(MEDECIN, (Medecin) result);
                setResult(RESULT_OK, uneIntention);
                finish();
			}
		}
	}
//endregion ClassesInternesPrivees
}