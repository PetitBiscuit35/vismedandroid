package fr.gsb.visprat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;
import android.widget.Toolbar;

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

	//prend le host
	public static String iphost = Configuration.getHost();
	//prend le path;
	public static String path = Configuration.getPath();

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

	private void setSupportActionBar(Toolbar toolbar) {
		ActionBar actionBar=getSupportActionBar();
		actionBar.hide();
		actionBar.show();
	}

	/**
	 * Initialise le paramètre menu avec le contenu de la ressource xml menu
	 * @param menu
	 * @return true si le menu doit être affiché, false sinon
	 */
	private MenuItem mMenuItem;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// cela ajoute des éléments à la barre d’action si elle est présente.
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		// Créer une intention passée à l'activité de classe settings
		Intent uneIntention;
		uneIntention = new Intent(MainActivity.this, settings.class);
		startActivity(uneIntention);
		return super.onOptionsItemSelected(item);
	}

//endregion MethodesProtegeesRedefinies
//region MethodesPrivees
    /**
     * Initialise les attributs privés référençant les widgets de l'interface utilisateur
     * Instancie les écouteurs et les affecte sur les objets souhaités
     */
	private void initialisations() {
		Toolbar toolbar=findViewById(R.id.action_settings);
		setSupportActionBar(toolbar);

	    this.buttonValider = (Button) this.findViewById(R.id.buttonValider);
	    this.editTextLogin = (EditText) this.findViewById(R.id.editTextLogin);
	    this.editTextMdp = (EditText) this.findViewById(R.id.editTextMotPasse);

	    // Ecouteurs pour les champs à renseigner
	    editTextLogin.addTextChangedListener(new CheckIfEmpty());
	    editTextMdp.addTextChangedListener(new CheckIfEmpty());

	    // Désactive le bouton de validation
	    buttonValider.setEnabled(false);

	    // on affecte un écouteur d'événement clic au bouton Valider
	    this.buttonValider.setOnClickListener(new ButtonValiderClick());		
	}
//endregion MethodesPrivees
// region ClassesInternesPrivees
	private class CheckIfEmpty implements TextWatcher
{
	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
		// Si les 2 champs ne sont pas renseignés le bouton de validation n'est pas activé
		if (editTextLogin.getText().length() > 0 && editTextMdp.getText().length() > 0) {
			buttonValider.setEnabled(true);
		}
		else {
			buttonValider.setEnabled(false);
		}
	}

	@Override
	public void afterTextChanged(Editable editable) { }
}
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