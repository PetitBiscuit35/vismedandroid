package fr.gsb.visprat.metier;

/**
 * Classe regroupant les caractéristiques d'un visiteur médical
 * @author sio2slam
*/
public class Visiteur {
	private String login;
	private String motPasse;
	private String nom;
	private String prenom;
	/**
	 * Instancie un visiteur
	 */
	public Visiteur(String unLogin, String unMotPasse) {
		this.login = unLogin;
		this.motPasse = unMotPasse;
		this.nom = "";
		this.prenom = "";
	}
	/**
	 * Fournit le login du visiteur
	 * @return login du visiteur
	 */
	public String getLogin() {
		return this.login;
	}
	/**
	 * Fournit le mot de passe du visiteur
	 * @return mot de passe du visiteur
	 */
	public String getMotPasse() {
		return this.motPasse;
	}
	/**
	 * Fournit le nom du visiteur
	 * @return nom du visiteur
	 */
	public String getNom() {
		return this.nom;
	}
	/**
	 * Fournit le prénom du visiteur
	 * @return prénom du visiteur
	 */
	public String getPrenom() {
		return this.prenom;
	}
	/**
	 * Fournit la représentation textuelle d'une instance de Visiteur
	 * @return représentation textuelle
	 */
	public String toString() {
		return this.login + ":" + this.nom + ":" + this.prenom;
	}
}
