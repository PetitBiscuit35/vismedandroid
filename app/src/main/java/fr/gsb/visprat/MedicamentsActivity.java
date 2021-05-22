package fr.gsb.visprat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import fr.gsb.visprat.dao.PasserelleMedicament;

/**
 * Classe gérant l'interface utilisateur d'une liste de départements
 * @author sio2slam
 */
public class MedicamentsActivity extends AppCompatActivity {
	private ListView listViewmedicaments;
	private ArrayList<Integer> lesMedicaments;
	private ArrayAdapter<Integer> unAdaptateur;
	/**
	 * Liste des départements depts, nom de la donnée extra dans l'intention déclenchant l'activité DeptsActivity
	 */
	public static final String LESMEDICAMENTS = "lesMedicaments";
	public static final String LOGIN = "login";


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicaments);
		initialisations();
	}

	private void initialisations() {
		Intent uneIntention;
		uneIntention = getIntent();

		lesMedicaments = uneIntention.getIntegerArrayListExtra("lesmedicaments");



		// Affichage du login connecté
		String login = uneIntention.getStringExtra("login");
		getSupportActionBar().setTitle(getSupportActionBar().getTitle() + " (" + login + ")");


		listViewmedicaments = (ListView) findViewById(R.id.listViewMedicaments);
		unAdaptateur = new ArrayAdapter<Integer>(MedicamentsActivity.this, android.R.layout.simple_list_item_1, lesMedicaments);
		// 	on associe l'adaptateur au composant ListView
		listViewmedicaments.setAdapter(unAdaptateur);

//		listViewDepts = (ListView) findViewById(R.id.listViewDepts);
//		unAdaptateur = new ArrayAdapter<Integer>(DeptsActivity.this, android.R.layout.simple_list_item_1, lesDepts);
//		// 	on associe l'adaptateur au composant ListView
//		listViewDepts.setAdapter(unAdaptateur);
	}



//endregion ClassesInternesPrivees
}


