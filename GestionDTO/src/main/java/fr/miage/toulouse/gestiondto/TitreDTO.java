/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.gestiondto;

/**
 *
 * @author trongvo
 */
public class TitreDTO {
    private Long id;
    private String codeTitre;
    private String nomTitre;
    private String description;
    private int rythmSortie;

    public TitreDTO(){};
    public TitreDTO(String codeTitre, String nomTitre, String description, int rythmSortie) {
        this.codeTitre = codeTitre;
        this.nomTitre = nomTitre;
        this.description = description;
        this.rythmSortie = rythmSortie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTitre() {
        return codeTitre;
    }

    public void setCodeTitre(String codeTitre) {
        this.codeTitre = codeTitre;
    }

    public String getNomTitre() {
        return nomTitre;
    }

    public void setNomTitre(String nomTitre) {
        this.nomTitre = nomTitre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRythmSortie() {
        return rythmSortie;
    }

    public void setRythmSortie(int rythmSortie) {
        this.rythmSortie = rythmSortie;
    }
    
    @Override
    public String toString() {
        return "Titre:  id=" + id + " - code=" + codeTitre+" - nom="+nomTitre+" - rythm="+rythmSortie+" - desc="+description;
    }
}
