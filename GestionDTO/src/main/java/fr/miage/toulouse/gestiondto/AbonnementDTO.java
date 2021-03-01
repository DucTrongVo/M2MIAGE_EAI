/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.gestiondto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author trongvo
 */
public class AbonnementDTO {
    private Long id;
    private String idUtilisateur;
    private String codeTitre;
    private int numberOfCopies;
    private int durationInWeeks;
    private String dateBegin;

    public AbonnementDTO(){};
    public AbonnementDTO(String idUtilisateur, String codeTitre, int numberOfCopies, int durationInWeeks) {
        this.idUtilisateur = idUtilisateur;
        this.codeTitre = codeTitre;
        this.numberOfCopies = numberOfCopies;
        this.durationInWeeks = durationInWeeks;
        this.dateBegin = LocalDate.now().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getCodeTitre() {
        return codeTitre;
    }

    public void setCodeTitre(String codeTitre) {
        this.codeTitre = codeTitre;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public int getDurationInWeeks() {
        return durationInWeeks;
    }

    public void setDurationInWeeks(int durationInWeeks) {
        this.durationInWeeks = durationInWeeks;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }
    
}
