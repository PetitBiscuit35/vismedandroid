package fr.gsb.visprat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import fr.gsb.visprat.dao.PasserelleMedicament;
import fr.gsb.visprat.metier.Medecin;
import fr.gsb.visprat.metier.Medicament;
import fr.gsb.visprat.metier.Visiteur;

/**
 * Classe gérant l'interface utilisateur d'une liste de départements
 * @author sio2slam
 */
public class MedicamentsActivity extends AppCompatActivity {
	private ListView listViewmedicaments;
	private ArrayList<Medicament> lesMedicaments;
	private ArrayAdapter<Medicament> unAdaptateur;
	/**
	 * Liste des départements depts, nom de la donnée extra dans l'intention déclenchant l'activité DeptsActivity
	 */
	public static final String LESMEDICAMENTS = "lesMedicaments";
	public static final String LOGIN = "login";
	public static final String MDP = "mdp";


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicaments);
		initialisations();
	}

	private void initialisations() {
		Intent uneIntention;
		uneIntention = getIntent();

		// Affichage du login connecté
		String login = uneIntention.getStringExtra("login");
		getSupportActionBar().setTitle(getSupportActionBar().getTitle() + " (" + login + ")");

		String mdp = uneIntention.getStringExtra("mdp");

		new MedicamentGet().execute(login, mdp);

		listViewmedicaments = (ListView) findViewById(R.id.listViewMedicaments);

		// unAdaptateur = new ArrayAdapter<Medicament>(MedicamentsActivity.this, android.R.layout.simple_list_item_1, lesMedicaments);
		// 	on associe l'adaptateur au composant ListView
		// listViewmedicaments.setAdapter(unAdaptateur);

//		listViewDepts = (ListView) findViewById(R.id.listViewDepts);
//		unAdaptateur = new ArrayAdapter<Integer>(DeptsActivity.this, android.R.layout.simple_list_item_1, lesDepts);
//		// 	on associe l'adaptateur au composant ListView
//		listViewDepts.setAdapter(unAdaptateur);
	}

	private class MedicamentGet extends AsyncTask<String, Void, Object> {
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
				lesMedicaments = PasserelleMedicament.getLesMedicaments((String) params[0], (String) params[1]);
			} catch (Exception ex) {
				return ex;
			}
			return lesMedicaments;
		}

		@Override
		protected void onPostExecute(Object result) {
			// Cette méthode est automatiquement appelée quand la tache longue se termine
			// ici, on teste le type de résultat, exception ou liste
			if ( result instanceof Exception ) {
				Exception ex = (Exception) result;
				Toast.makeText(MedicamentsActivity.this, R.string.msgErrRecupMedecins + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			else {
				lesMedicaments = (ArrayList<Medicament>) result;
				unAdaptateur = new ArrayAdapter<Medicament>(MedicamentsActivity.this, android.R.layout.simple_list_item_1, lesMedicaments);
				// on associe l'adaptateur au composant ListView
				listViewmedicaments.setAdapter(unAdaptateur);
			}
		}


//endregion ClassesInternesPrivees
}

}


