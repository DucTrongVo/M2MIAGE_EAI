/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.gestiondto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author trongvo
 */
public class VolumeDTO {
    private Long id;
    private String codeTitre;
    private String numVolume;
    private String nomVolume;
    private List<ArticleDTO> listArticles;
    private List<PubliciteDTO> listPublicites;
    private String dateTime;

    public VolumeDTO(){};
 
    public VolumeDTO(String codeTitre, String numVolume, String nomVolume, List<ArticleDTO> listArticles, List<PubliciteDTO> listPublicites, String dateTime) {
        this.codeTitre = codeTitre;
        this.numVolume = numVolume;
        this.nomVolume = nomVolume;
        this.listArticles = listArticles;
        this.listPublicites = listPublicites;
        this.dateTime = dateTime != "" ? dateTime : LocalDate.now().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
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

    public String getNumVolume() {
        return numVolume;
    }

    public void setNumVolume(String numVolume) {
        this.numVolume = numVolume;
    }

    public String getNomVolume() {
        return nomVolume;
    }

    public void setNomVolume(String nomVolume) {
        this.nomVolume = nomVolume;
    }

    public List<ArticleDTO> getListArticles() {
        return listArticles;
    }

    public void setListArticles(List<ArticleDTO> listArticles) {
        this.listArticles = listArticles;
    }

    public List<PubliciteDTO> getListPublicites() {
        return listPublicites;
    }

    public void setListPublicites(List<PubliciteDTO> listPublicites) {
        this.listPublicites = listPublicites;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    
    @Override
    public String toString() {
        return "Code du Theme : "+this.codeTitre+" - Numéro du Volume : "+this.numVolume + " - Nom du Volume : "+this.nomVolume
                +" - Date de création : "+this.dateTime
                +" - Nombre d'article : "+listArticles.size()+" - Nombre de publicité : "+listPublicites.size();
    }
}
