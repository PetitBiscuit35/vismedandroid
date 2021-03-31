package fr.gsb.visprat.metier;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Classe regroupant les caractéristiques d'un médecin
 * @author sio2slam
 */
public class Medecin implements Serializable {
	private int id;
	private String nom;
	private String prenom;
	private String adresse;
	private String codePostal;
	private String ville;
	private String tel;
	private String email;
	private String specialiteComplementaire;
    /**
     * Instancie un médecin avec toutes ses propriétés renseignées
     */
    public Medecin(int unId, String unNom, String unPrenom, String uneAdresse, String unCP, String uneVille, String unTel, String unEmail, String uneSpecComp) {
        this.id = unId;
        this.nom = unNom;
        this.prenom	= unPrenom;
        this.adresse = uneAdresse;
        this.codePostal = unCP;
        this.ville = uneVille;
        this.tel = unTel;
        this.email = unEmail;
        this.specialiteComplementaire = uneSpecComp;
    }
    /**
     * Instancie un médecin avec id, nom, prénom et ville renseignés
     */
    public Medecin(int unId, String unNom, String unPrenom, String uneVille) {
        this.id = unId;
        this.nom = unNom;
        this.prenom	= unPrenom;
        this.adresse = "";
        this.codePostal = "";
        this.ville = uneVille;
        this.tel = "";
        this.email = "";
        this.specialiteComplementaire = "";
    }
	/**
	 * Fournit l'id du médecin
	 * @return id du médecin
	 */
	public int getId() {
		return this.id;
	}
    /**
     * Fournit le nom du médecin
     * @return nom du médecin
     */
    public String getNom() {
        return this.nom;
    }
    /**
     * Modifie le nom du médecin
     * @param unNom
     */
    public void setNom(String unNom) {
        this.nom = unNom;
    }
	/**
	 * Fournit le prénom du médecin
	 * @return prénom du médecin
	 */
	public String getPrenom() {
		return this.prenom;
	}
    /**
     * Modifie le prénom du médecin
     * @param unPrenom
     */
    public void setPrenom(String unPrenom) {
        this.prenom = unPrenom;
    }
	/**
	 * Fournit l'adresse du médecin
	 * @return adresse du médecin
	 */
	public String getAdresse() {
		return adresse;
	}
	/**
	 * Affecte l'adresse du médecin
	 * @param adresse
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	/**
	 * Fournit le code postal du médecin
	 * @return code postal du médecin
	 */
	public String getCodePostal() {
		return codePostal;
	}
	/**
	 * Affecte le code postal du médecin
	 * @param codePostal
	 */

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	/**
	 * Fournit la ville du médecin
	 * @return ville du médecin
	 */
	public String getVille() {
		return ville;
	}
	/**
	 * Affecte la ville du médecin
	 * @param ville
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}
	/**
	 * Fournit le téléphone du médecin
	 * @return téléphone du médecin
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * Affecte le téléphone du médecin
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * Fournit l'adresse mail du médecin
	 * @return adresse mail du médecin
	 */
	public String getEmail() {
		return this.email;
	}
	/**
	 * Affecte l'adresse mail du médecin
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Fournit la spécialité complémentaire du médecin
	 * @return specialité complémentaire du médecin
	 */
	public String getSpecialiteComplementaire() {
		return specialiteComplementaire;
	}
	/**
	 * Affecte la spécialité complémentaire du médecin
	 * @param specialiteComplementaire
	 */	
	public void setSpecialiteComplementaire(String specialiteComplementaire) {
		this.specialiteComplementaire = specialiteComplementaire;
	}
	/**
	 * Fournit le numéro de département du médecin
	 * @return numéro de département du médecin
	 */
	public int getNoDept() {
		int noDept = 0;
		if ( this.codePostal.length() >= 2 ) {
			noDept = Integer.parseInt(this.codePostal.substring(0, 1));
		}
		return noDept;
	}
	/**
	 * Fournit la représentation textuelle d'un médecin
	 * @return représentation textuelle
	 */
	@Override
	public String toString() {
		return this.nom + "-" + this.prenom + "-" + this.ville;
	}

}
