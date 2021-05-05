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
	public static final String DEPTS = "depts";
	public static final String LOGIN = "login";


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicaments);
		initialisations();
	}

	private void initialisations() {
		Intent uneIntention;
		uneIntention = getIntent();

		//lesDepts = uneIntention.getIntegerArrayListExtra("depts");

		// Affichage du login connecté
		String login = uneIntention.getStringExtra("login");
		getSupportActionBar().setTitle(getSupportActionBar().getTitle() + " (" + login + ")");

		listViewmedicaments = (ListView) findViewById(R.id.listViewmedicaments);
		//unAdaptateur = new ArrayAdapter<Integer>(MedicamentsActivity.this, android.R.layout.simple_list_item_1, lesDepts);
		// 	on associe l'adaptateur au composant ListView
		listViewmedicaments.setAdapter(unAdaptateur);
		//listViewmedicaments.setOnItemClickListener(new MedecinsActivity().ListViewOnItemClick() );
	}
//endregion ClassesInternesPrivees
}
