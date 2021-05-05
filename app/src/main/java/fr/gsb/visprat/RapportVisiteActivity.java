package fr.gsb.visprat;

import java.util.ArrayList;

import fr.gsb.visprat.dao.PasserelleMedecin;
import fr.gsb.visprat.dao.PasserelleRapportVisite;
import fr.gsb.visprat.metier.Medecin;
import fr.gsb.visprat.metier.Rapport_visite;

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
public class RapportVisiteActivity extends AppCompatActivity {
    public static final int CODE_UPDATE = 1;

    ListView listViewRapportsVisites;
    TextView textViewInviteRapportsVisites;
    ArrayList<Rapport_visite> lesRapportsVisites;
    ArrayAdapter<Rapport_visite> unAdaptateur;

//region MethodesProtegeesRedefinies
    /**
     * Méthode appelée lors de la création de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapports_visites);
        initialisations();
    }

    /**
     * Méthode appelée lors de la réception du résultat de l'activité DetailMedecinActivity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int position;
        if (resultCode == RESULT_OK) {

            if (requestCode == CODE_UPDATE) {
                Rapport_visite leRapportVisite;
                position = data.getIntExtra("position", 0);
                leRapportVisite = (Rapport_visite) data.getSerializableExtra("RapportVisite");
                this.lesRapportsVisites.set(position, leRapportVisite);
                this.unAdaptateur.notifyDataSetChanged();
            }
        }
    }
//endregion MethodesProtegeesRedefinies
//region MethodesPrivees
    /**
     * Récupère les données passées dans l'intention reçue
     * Instancie et exécute un thread séparé pour récupérer les médecins
     * Initialise les attributs privés référençant les widgets de l'interface utilisateur
     */
    private void initialisations() {
        textViewInviteRapportsVisites = (TextView) findViewById(R.id.textViewInviteMedecins);
        listViewRapportsVisites = (ListView) findViewById(R.id.listViewRapportsVisites);
        new RapportsVisitesGet().execute();
        // listViewRapportsVisites.setOnItemClickListener(new ListViewOnItemClick());
    }
//endregion MethodesPrivees
//region ClassesInternesPrivees

    /**
     * Classe interne servant d'écouteur de l'événement click sur les éléments de la liste
     */
    /*
    private class ListViewOnItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent uneIntention;
            // crée une intention pour passer le médecin qui va gérer le détail des données du médecin
            uneIntention = new Intent(MedecinsActivity.this, DetailMedecinActivity.class);
            uneIntention.putExtra(DetailMedecinActivity.POSITION, position);
            uneIntention.putExtra(DetailMedecinActivity.MEDECIN, lesMedecins.get(position));
            MedecinsActivity.this.startActivityForResult(uneIntention, CODE_UPDATE);
        }
    } */
    /**
     * Classe interne pour prendre en charge l'appel à un service web et sa réponse
     * La consultation des médecins d'un département fait en effet appel à un service web et
     * peut donc prendre quelques sec.
     * A partir d'Android 3.0, une requête HTTP doit être effectuée à l'intérieur d'une tache asynchrone
     * TypeParam1 : String - numéro du département
     * TypeParam2 : Void - pas de paramètres fournis pendant la durée du traitement
     * TypeParam3 : Object - type de paramètre ArrayList<Medecin> ou Exception
     * @see AsyncTask
     */
    private class RapportsVisitesGet extends AsyncTask<String, Void, Object> {
        /**
         * Permet de lancer l'exécution de la tache longue
         * ici, on demande les médecins d'un département donné
         */
        @Override
        protected Object doInBackground(String... params) {
            ArrayList<Rapport_visite> lesDonnees;
            VisPratApplication monAppli;
            monAppli = (VisPratApplication) RapportVisiteActivity.this.getApplication();
            // cette méthode permet de lancer l'exécution de la tache longue
            // ici, on demande les informations de disponibilité d'une station à partir de son numéro
            try {
                lesDonnees = PasserelleRapportVisite.getLesRapportsVisites(monAppli.getVisiteur());
            }
            catch (Exception ex) {
                return ex;
            }
            return lesDonnees;
        }

        /**
         * Méthode automatiquement appelée quand la tache longue se termine
         * ici, on teste le type de résultat, exception ou liste
         * @param result objet de type ArrayList<Medecin> ou Exception
         */
        @Override
        protected void onPostExecute(Object result) {
            // cette méthode est automatiquement appelée quand la tache longue se termine
            // ici, on teste le type de résultat, exception ou liste
            if ( result instanceof Exception ) {
                Exception ex = (Exception) result;
                Toast.makeText(RapportVisiteActivity.this, "Erreur récupération des rapports de visites" + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            else {
                lesRapportsVisites = (ArrayList<Rapport_visite>) result;
                unAdaptateur = new ArrayAdapter<Rapport_visite>(RapportVisiteActivity.this, android.R.layout.simple_list_item_1, lesRapportsVisites);
                // on associe l'adaptateur au composant ListView
                listViewRapportsVisites.setAdapter(unAdaptateur);
            }
        }
    }
//endregion ClassesInternesPrivees
}
