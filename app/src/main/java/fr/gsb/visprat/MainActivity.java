package fr.gsb.visprat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import fr.gsb.visprat.dao.*;
import fr.gsb.visprat.metier.Visiteur;

/**
 * Classe gérant l'interface utilisateur de connexion d'un visiteur médical
 * @author baraban
 */
public class MainActivity extends AppCompatActivity {
	private Button buttonValider;
	private EditText editTextLogin, editTextMdp;
	private ArrayList<Integer> lesDepts;
	String login, mdp;

//region MethodesProtegeesRedefinies
	/**
	 * Méthode appelée lors de la création de l'activité
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
//endregion MethodesProtegeesRedefinies
//region MethodesPrivees
    /**
     * Initialise les attributs privés référençant les widgets de l'interface utilisateur
     * Instancie les écouteurs et les affecte sur les objets souhaités
     */
	private void initialisations() {
	    this.buttonValider = (Button) this.findViewById(R.id.buttonValider);
	    this.editTextLogin = (EditText) this.findViewById(R.id.editTextLogin);
	    this.editTextMdp = (EditText) this.findViewById(R.id.editTextMotPasse);
	    
	    // on affecte un écouteur d'événement clic au bouton Valider
	    this.buttonValider.setOnClickListener(new ButtonValiderClick());		
	}
//endregion MethodesPrivees
// region ClassesInternesPrivees
    /**
     * Classe interne servant d'écouteur de l'événement click sur le bouton Valider
     */
	private class ButtonValiderClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			login = editTextLogin.getText().toString();
			mdp = editTextMdp.getText().toString();
			if ( ! login.equals("") && ! mdp.equals("") ) {
				// instancie et exécute un thread séparé pour vérifier la connexion
				new DeptsGet().execute(login, mdp);
			}
			else {
				Toast.makeText(MainActivity.this, R.string.msgErrChampVide, Toast.LENGTH_LONG).show();
			}
		}
	}
	/**
	 * Classe interne pour prendre en charge l'appel à un service web et sa réponse
	 * La vérification de la connexion d'un visiteur fait en effet appel au service web et peut donc prendre quelques sec.
	 * à partir d'Android 3.0, une requête HTTP doit être effectuée à l'intérieur d'une tache asynchrone
	 * TypeParam1 : String - paramètres login et mdp
	 * TypeParam2 : Void - pas de paramètres fournis pendant la durée du traitement
	 * TypeParam3 : Object - type de paramètre Visiteur ou Exception
	 * @see AsyncTask
	 */
	private class DeptsGet extends AsyncTask<String, Void, Object> {
		@Override
		/**
		 * Permet de lancer l'exécution de la tache longue
		 * ici, on demande à vérifier la connexion d'un visiteur à partir de son login et mot de passe
		 */
		protected Object doInBackground(String... params) {
			// cette méthode permet de lancer l'exécution de la tache longue
			// ici, on demande l'authentification du visiteur
			Visiteur leVisiteur = null;
	    	try {
	    		lesDepts = PasserelleMedecin.getLesDepts((String)params[0], (String)params[1]);
	    	}
	    	catch (Exception ex) {
	    		return ex;
	    	}
	    	return lesDepts;
		}

		/**
		 * Méthode automatiquement appelée quand la tache longue se termine
		 * ici, on teste le type de résultat, exception ou liste
		 * @param result objet de type Visiteur ou Exception 
		 */
		@Override
		protected void onPostExecute(Object result) {
			// cette méthode est automatiquement appelée quand la tache longue se termine
			// ici, on affiche les informations récupérées
			VisPratApplication monAppli;
			Intent uneIntention;
			if ( result instanceof Exception ) {
				Exception ex = (Exception) result;
				Toast.makeText(MainActivity.this, getString(R.string.msgErrConnexionInvalide) + "\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			else {
				Visiteur leVisiteur = new Visiteur(login, mdp);
				// conserve le visiteur dans le contexte de l'application
				// il pourra ainsi être retrouvé dans n'importe quelle activité
				monAppli = (VisPratApplication) MainActivity.this.getApplication();
				monAppli.setVisiteur(leVisiteur);
				// crée une intention passée à l'activité de classe DeptsActivity
				uneIntention = new Intent(MainActivity.this, DeptsActivity.class);
				uneIntention.putExtra(DeptsActivity.LOGIN, login);
				uneIntention.putExtra(DeptsActivity.DEPTS, lesDepts);
				startActivity(uneIntention);
			}
		}
	}
//endregion ClassesInternesPrivees
}