package fr.gsb.visprat.daotest;

import java.util.ArrayList;
import java.util.HashMap;

import fr.gsb.visprat.metier.Medecin;
import fr.gsb.visprat.metier.Visiteur;

/**
 * Classe simulant l'appel des web services pour obtenir ou modifier les données
 * concernant visiteurs, produits et médecins
 * @author sio2slam
 */
public class PasserelleMedecin {
	/**
	 * Fournit la liste des numéros de départements dans lesquels réside au moins un médecin
	 * @return ArrayList<Integer>
	 * @throws Exception
	 */
	public static ArrayList<Integer> getLesDepts(String login, String motPasse) throws Exception {
		ArrayList<Integer> lesDepts;
		lesDepts = new ArrayList<Integer> ();
		lesDepts.add((Integer)35);
		lesDepts.add((Integer)29);
		lesDepts.add((Integer)22);
		lesDepts.add((Integer)44);
		lesDepts.add((Integer)53);
		lesDepts.add((Integer)50);
		lesDepts.add((Integer)61);
		lesDepts.add((Integer)14);
		lesDepts.add((Integer)72);

		return lesDepts;
	}
	/**
	 * Fournit la liste des médecins résidant dans le département dont le numéro est spécifié
	 * @param noDept
	 * @return ArrayList<Medecin>
	 * @throws Exception
	 */
	public static ArrayList<Medecin> getLesMedecins(int noDept, Visiteur leVisiteur) {
		ArrayList<Medecin> lesMedecins;
		Medecin unMedecin;
		lesMedecins = new ArrayList<Medecin> ();
		
		unMedecin = new Medecin(1, "Gaffé", "Dominique", "Rennes");
		unMedecin.setAdresse("30 rue des Ormes");
		unMedecin.setTel("029911223344");
		unMedecin.setCodePostal("35200");
		unMedecin.setVille("Rennes");
		lesMedecins.add(unMedecin);
		unMedecin = new Medecin(2, "Blouin", "Clotilde", "Rennes");
		unMedecin.setAdresse("124 boulevard jacques cartier");
		unMedecin.setTel("0299556677");
		unMedecin.setCodePostal("35200");
		unMedecin.setVille("Rennes");
		lesMedecins.add(unMedecin);
		unMedecin = new Medecin(3, "Zébulon", "Paul", "Rennes");
		unMedecin.setAdresse("30 rue des Fleurs");
		unMedecin.setTel("0299123456");
		unMedecin.setCodePostal("35510");
		unMedecin.setVille("Cesson-Sévigné");
		lesMedecins.add(unMedecin);
		
		return lesMedecins;
	}
	/**
	 * Prend en charge la mise à jour des données à modifier d'un médecin
	 * et rend le médecin modifié lorsque la mise à jour a été réellement réalisée
	 * @return Medecin
	 * @throws Exception dans le cas de pb de communication, d'erreur d'authentification, ....
	 */
	public static Medecin updateMedecin(Visiteur leVisiteur, Medecin leMedecin, HashMap<String, String> laHashMapToUpdate) throws Exception {
		// on met à jour le médecin pour chaque caractéristique ayant subi une modification
		leMedecin.setNom((laHashMapToUpdate.containsKey("nom")) ? laHashMapToUpdate.get("nom") : leMedecin.getNom());
		leMedecin.setPrenom((laHashMapToUpdate.containsKey("prenom")) ? laHashMapToUpdate.get("prenom") : leMedecin.getPrenom());
		leMedecin.setVille((laHashMapToUpdate.containsKey("ville")) ? laHashMapToUpdate.get("ville") : leMedecin.getVille());
		return leMedecin;
	}
}
