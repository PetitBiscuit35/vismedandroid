package fr.gsb.visprat;

import fr.gsb.visprat.metier.Visiteur;
import android.app.Application;

/**
 * Classe permettant de mémoriser les données qui doivent être visibles au niveau de l'application
 * càd au niveau de toutes les activités composant l'application
 * @author sio2slam
 *
 */
public class VisPratApplication extends Application {
	private Visiteur leVisiteur;
	/**
	 * Fournit le visiteur connecté
	 * @return Visiteur
	 */
	public Visiteur getVisiteur() {
		return leVisiteur;
	}
	/**
	 * Affecte le visiteur connecté
	 * @param unVisiteur Visiteur
	 */
	public void setVisiteur(Visiteur unVisiteur) {
		this.leVisiteur = unVisiteur;
	}	
}