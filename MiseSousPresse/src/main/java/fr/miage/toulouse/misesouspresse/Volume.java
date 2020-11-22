/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.misesouspresse;

import fr.miage.toulouse.gestionpublicite.Publicite;
import fr.miage.toulouse.journaliste.Entity.Article;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private List<Publicite> listPublicites;
    private LocalDateTime dateTime;

    public Volume(String codeTitre, String numVolume, String nomVolume, List<Article> listArticles, List<Publicite> listPublicites) {
        this.codeTitre = codeTitre;
        this.numVolume = numVolume;
        this.nomVolume = nomVolume;
        this.listArticles = listArticles;
        this.listPublicites = listPublicites;
        this.dateTime = LocalDateTime.now();
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

    public List<Publicite> getListPublicites() {
        return listPublicites;
    }

    public void setListPublicites(List<Publicite> listPublicites) {
        this.listPublicites = listPublicites;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    public String toString(){
        return "Code du Theme : "+this.codeTitre+" - Numéro du Volume : "+this.numVolume + " - Nom du Volume : "+this.nomVolume
                +" - Date de création : "+this.dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
                +" - Nombre d'article : "+listArticles.size()+" - Nombre de publicité : "+listPublicites.size();
    }
}
