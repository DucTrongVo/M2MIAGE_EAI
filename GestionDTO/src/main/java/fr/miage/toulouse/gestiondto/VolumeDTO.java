/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.gestiondto;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author trongvo
 */
public class VolumeDTO {
    
    private String codeTitre;
    private String numVolume;
    private String nomVolume;
    private List<ArticleDTO> listArticles;
    private List<PubliciteDTO> listPublicites;
    private LocalDateTime dateTime;

    public VolumeDTO(String numVolume, String nomVolume, List<ArticleDTO> listArticles, List<PubliciteDTO> listPublicites, LocalDateTime dateTime) {
        this.numVolume = numVolume;
        this.nomVolume = nomVolume;
        this.listArticles = listArticles;
        this.listPublicites = listPublicites;
        this.dateTime = dateTime;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    
}
