package fr.gsb.visprat;

import java.util.ArrayList;

import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Classe gérant l'interface utilisateur d'une liste de départements
 * @author sio2slam
 */
public class DeptsActivity extends AppCompatActivity {
	private ListView listViewDepts;
	private ArrayList<Integer> lesDepts;
	private ArrayList<Integer> lesMedicament;
	private ArrayAdapter<Integer> unAdaptateur;

	//récupération du button avec pour id buttonMedicament
	private Button buttonMedicament;

	/**
	 * Liste des départements depts, nom de la donnée extra dans l'intention déclenchant l'activité DeptsActivity
	 */
	public static final String DEPTS = "depts";
	public static final String LOGIN = "login";
	public static final String NODEPOTL = "nodepolt";



//region MethodesProtegeesRedefinies
	/**
	 * Méthode appelée lors de la création de l'activité
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_depts);
		initialisations();

		//bouton medicament
		this.buttonMedicament = (Button) this.findViewById(R.id.buttonMedicament);
		// on affecte un écouteur d'événement clic au bouton Valider
		this.buttonMedicament.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View view) {
				Intent uneIntention;
				// crée une intention pour ouvrir l'activité qui sait gérer une liste de Médicament
				uneIntention = new Intent(DeptsActivity.this, MedicamentActivity.class);
				startActivity(uneIntention);


			}
		});
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
     * Instancie et exécute un thread séparé pour récupérer les départemen,ts
     */
	private void initialisations() {
		Intent uneIntention;
		uneIntention = getIntent();
		
		lesDepts = uneIntention.getIntegerArrayListExtra("depts");


		// Affichage du login connecté
		String login = uneIntention.getStringExtra("login");
		getSupportActionBar().setTitle(getSupportActionBar().getTitle() + " (" + login + ")");

		listViewDepts = (ListView) findViewById(R.id.listViewmedicaments);
		unAdaptateur = new ArrayAdapter<Integer>(DeptsActivity.this, android.R.layout.simple_list_item_1, lesDepts);
		// 	on associe l'adaptateur au composant ListView
		listViewDepts.setAdapter(unAdaptateur);
		listViewDepts.setOnItemClickListener(new ListViewOnItemClick() );
   	}


//endregion MethodesPrivees
//region ClassesInternesPrivees
    /**
     * Classe interne servant d'écouteur de l'événement click sur les éléments de la liste
     */
	class ListViewOnItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent uneIntention;
            // crée une intention pour passer le numéro de département à l'activité qui sait gérer une liste de médecins
            uneIntention = new Intent(DeptsActivity.this, MedecinsActivity.class);
            uneIntention.putExtra(MedecinsActivity.NODEPT, lesDepts.get(position).toString());
            DeptsActivity.this.startActivity(uneIntention);
        }
    }


//endregion ClassesInternesPrivees
}
