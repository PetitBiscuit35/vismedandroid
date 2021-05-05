package fr.gsb.visprat;

import java.util.ArrayList;

import fr.gsb.visprat.dao.PasserelleMedecin;
import fr.gsb.visprat.metier.Medecin;
import fr.gsb.visprat.metier.Medicament;
import fr.gsb.visprat.metier.Visiteur;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Classe gérant l'interface utilisateur d'une liste de médecins
 * @author sio2slam
 */
public class MedicamentActivity extends AppCompatActivity {
	public static final int CODE_UPDATE = 1;


	ListView listViewMedicament;
	TextView textViewInvitemedicaments;
	private ArrayList<Integer> lesMedicaments;
	ArrayAdapter<Medecin> unAdaptateur;


	/**
	 * Liste des Médicament depts, nom de la donnée extra dans l'intention déclenchant l'activité DeptsActivity
	 */


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicaments);
		initialisations();
	}



	/**
	 * Récupère les données passées dans l'intention reçue
	 * Instancie et exécute un thread séparé pour récupérer les médecins
	 * Initialise les attributs privés référençant les widgets de l'interface utilisateur
	 */
	private void initialisations() {
		Intent uneIntention;
		uneIntention = getIntent();



		textViewInvitemedicaments = (TextView) findViewById(R.id.textViewInvitemedicaments);
		String uneChaine = textViewInvitemedicaments.getText().toString()  + ' ' ;
		textViewInvitemedicaments.setText(uneChaine);
		listViewMedicament = (ListView) findViewById(R.id.listViewMedicaments);
		//listViewMedicament.setOnItemClickListener(new MedicamentActivity().ListViewOnItemClick());

	}

//	private AdapterView.OnItemClickListener ListViewOnItemClick() {
//		@Override
//		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
//		}

	private class MedicamentGet extends AsyncTask<String, Void, Object> {
		/**
		 * Permet de lancer l'exécution de la tache longue
		 * ici, on demande les médecins d'un département donné
		 */
		@Override
		protected Object doInBackground(String... params) {
			ArrayList<Medicament> lesDonnees;
			VisPratApplication monAppli;
			monAppli = (VisPratApplication) MedicamentActivity.this.getApplication();
			// cette méthode permet de lancer l'exécution de la tache longue
			// ici, on demande les informations de disponibilité d'une station à partir de son numéro
			try {
				lesDonnees = PasserelleMedecin.getLesMedicaments((String)params[0], (String)params[1]);
			}
			catch (Exception ex) {
				return ex;
			}
			return lesDonnees;
		}
//	}


	}}



