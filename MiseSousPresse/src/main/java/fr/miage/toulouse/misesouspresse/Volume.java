/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.misesouspresse;

import fr.miage.toulouse.journaliste.Entity.Article;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author trongvo
 */
public class Volume implements Serializable{
    private String codeTitre;
    private String numVolume;
    private String nomVolume;
    private List<Article> listArticles;

    public Volume(String codeTitre, String numVolume, String nomVolume, List<Article> listArticles) {
        this.codeTitre = codeTitre;
        this.numVolume = numVolume;
        this.nomVolume = nomVolume;
        this.listArticles = listArticles;
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

    public List<Article> getListArticles() {
        return listArticles;
    }

    public void setListArticles(List<Article> listArticles) {
        this.listArticles = listArticles;
    }
    
    
}
