package fr.gsb.visprat.metier;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe regroupant les caractéristiques d'un médecin
 * @author sio2slam
 */
public class Medecin implements Parcelable {
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
	 * Instancie un médecin à partir d'un parcel
	 */
	public Medecin(Parcel in) {
		this.id = in.readInt();
		this.nom = in.readString();
		this.prenom	= in.readString();
		this.adresse	= in.readString();
		this.codePostal	= in.readString();
		this.ville	= in.readString();
		this.tel	= in.readString();
		this.email	= in.readString();
		this.specialiteComplementaire	= in.readString();
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

	/**
	 * Fournit le bitmask indiquant si l'instance contient des types d'objets spéciaux,
     * ici valeur 0 retournée car aucun objet spécial contenu dans une instance de classe Medecin
	 * @return représentation textuelle
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * Construit le parcel dest à partir de l'instance d'un médecin
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.getId());
		dest.writeString(this.getNom());
		dest.writeString(this.getPrenom());
		dest.writeString(this.getAdresse());
		dest.writeString(this.getCodePostal());
		dest.writeString(this.getVille());
		dest.writeString(this.getTel());
		dest.writeString(this.getEmail());
		dest.writeString(this.getSpecialiteComplementaire());
	}

	/**
	 * Constructeur statique de l'interface Parcelable utilisé pour instancier des objets et des
	 * tableaux d'objets de la classe.
	 */
	public static final Parcelable.Creator<Medecin> CREATOR = new Parcelable.Creator<Medecin>()
	{
		@Override
		public Medecin createFromParcel(Parcel source)
		{
			return new Medecin(source);
		}

		@Override
		public Medecin[] newArray(int size)
		{
			return new Medecin[size];
		}
	};
}
