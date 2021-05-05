package fr.gsb.visprat.metier;

import java.io.Serializable;

public class Medicament implements Serializable {

    private String depotLegal;
    private String nomCommercial;
    private String codeFamille;
    private String composition;
    private String effets;
    private String contreIndic;
    private int prixEchantillon;
    /**
     * Instancie un visiteur
     */
    public Medicament(String undepotLegal, String unnomCommercial, String uncodeFamille ,String uncomposition ,String uneffets , String uncontreIndic, int unprixEchantillon) {
        this.depotLegal = undepotLegal;
        this.nomCommercial = unnomCommercial;
        this.codeFamille = uncodeFamille;
        this.composition = uncomposition;
        this.effets = uneffets;
        this.contreIndic = uncontreIndic;
        this.prixEchantillon = unprixEchantillon;
    }

//    /**
//     * Fournit le login du visiteur
//     * @return login du visiteur
//     */
//    public String getLogin() {
//        return this.login;
//    }
//    /**
//     * Fournit le mot de passe du visiteur
//     * @return mot de passe du visiteur
//     */
//    public String getMotPasse() {
//        return this.motPasse;
//    }
//    /**
//     * Fournit le nom du visiteur
//     * @return nom du visiteur
//     */
//    public String getNom() {
//        return this.nom;
//    }
//    /**
//     * Fournit le prénom du visiteur
//     * @return prénom du visiteur
//     */
//    public String getPrenom() {
//        return this.prenom;
//    }
//    /**
//     * Fournit la représentation textuelle d'une instance de Visiteur
//     * @return représentation textuelle
//     */
//    public String toString() {
//        return this.login + ":" + this.nom + ":" + this.prenom;
//    }
}
