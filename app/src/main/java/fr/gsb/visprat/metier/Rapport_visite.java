package fr.gsb.visprat.metier;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import static android.icu.lang.UCharacter.toUpperCase;

/**
 * Classe regroupant les caractéristiques d'un rapport de visite
 * @author sio2slam
 */
public class Rapport_visite implements Serializable {
    private int idVisiteur;
    private int id;
    private int idMedecin;
    private String dateVisite;
    private String dateCreaRapport;
    private String bilan;
    private int coefConfiance;
    private int idMotifVisite;
    /**
     * Instancie un rapport de visite avec toutes ses propriétés renseignées
     */
    public Rapport_visite(int unIdVisiteur, int unId, int unIdMedecin, String uneDateVisite, String uneDateCreaRapport, String unBilan, int unCoefConfiance, int unIdMotifVisite) {
        this.idVisiteur = unIdVisiteur;
        this.id = unId;
        this.idMedecin = unIdMedecin;
        this.dateVisite = uneDateVisite;
        this.dateCreaRapport = uneDateCreaRapport;
        this.bilan = unBilan;
        this.coefConfiance = unCoefConfiance;
        this.idMotifVisite = unIdMotifVisite;
    }
    /**
     * Instancie un rapport de visite avec idVisiteur, id, idMedecin, dateVisite, dateCreaRapport, coefConfiance, idMotifVisite
     */
    public Rapport_visite(int unIdVisiteur, int unId, int unIdMedecin, String uneDateVisite, String uneDateCreaRapport, int unCoefConfiance, int unIdMotifVisite) {
        this.idVisiteur = unIdVisiteur;
        this.id = unId;
        this.idMedecin = unIdMedecin;
        this.dateVisite = uneDateVisite;
        this.dateCreaRapport = uneDateCreaRapport;
        this.bilan = "RAS";
        this.coefConfiance = unCoefConfiance;
        this.idMotifVisite = unIdMotifVisite;
    }
    /**
     * Fournit l'idVisiteur du rapport de visite
     * @return idVisiteur du rapport de visite
     */
    public int getIdVisiteur() {
        return this.idVisiteur;
    }
    /**
     * Fournit l'id du rapport de visite
     * @return id du rapport de visite
     */
    public int getId() {
        return this.id;
    }
    /**
     * Fournit l'id médecin du rapport de visite
     * @return idMedecin du rapport de visite
     */
    public int getIdMedecin() {
        return this.idMedecin;
    }
    /**
     * Fournit la date de visite du rapport de visite
     * @return dateVisite du rapport de visite
     */
    public String getDateVisite() {
        return this.dateVisite;
    }
    /**
     * Fournit la date de création du rapport de visite
     * @return dateCreaRapport du rapport de visite
     */
    public String getDateCreaRapport() {
        return dateCreaRapport;
    }
    /**
     * Fournit le bilan du rapport de visite
     * @return bilan du rapport de visite
     */
    public String getBilan() {
        return bilan;
    }
    /**
     * Fournit le coefficient de confiance du rapport de visite
     * @return coefConfiance du rapport de visite
     */
    public int getCoefConfiance() {
        return this.coefConfiance;
    }
    /**
     * Fournit l'id du motif de visite du rapport de visite
     * @return idMotifVisite du rapport de visite
     */
    public int getIdMotifVisite() { return this.idMotifVisite; }
    /**
     * Fournit la représentation textuelle d'un médecin
     * @return représentation textuelle
     */
    @Override
    public String toString() {
        return this.id + " " + this.dateVisite + " ( Bilan : "
                + this.bilan;
    }

}
