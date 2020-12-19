/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.journaliste.Entity;

import fr.miage.toulouse.gestiondto.Constants;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *x
 * @author trongvo
 */
public class Article implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String nameArticle;
    private String codeArticle;
    private String nameAuthor;
    private List<String> keywords;
    private String content;
    private int numArticle;
    //private String codeTitre;
    private LocalDate date;
    private static int numGlobalArticle = 0;
    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Article(String nameArticle, String codeArticle, String nameAuthor, List<String> keywords, String content) {
        this.nameArticle = nameArticle;
        this.codeArticle = codeArticle;
        this.nameAuthor = nameAuthor;
        this.keywords = keywords;
        this.content = content;
        //this.codeTitre = codeTitre;
        this.date = LocalDate.now();
        this.numArticle = Article.numGlobalArticle + 1;
    }

    public String getNameArticle() {
        return nameArticle;
    }

    public void setNameArticle(String nameArticle) {
        this.nameArticle = nameArticle;
    }

    public String getCodeArticle() {
        return codeArticle;
    }

    public void setCodeArticle(String codeArticle) {
        this.codeArticle = codeArticle;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumArticle() {
        return numArticle;
    }

    public void setNumArticle(int numArticle) {
        this.numArticle = numArticle;
    }

//    public String getCodeTitre() {
//        return codeTitre;
//    }
//
//    public void setCodeTitre(String codeTitre) {
//        this.codeTitre = codeTitre;
//    }
    
    public LocalDate getDate(){
        return this.date;
    }
    
    @Override
    public String toString(){
        return "Code article : "+this.getCodeArticle()+" - Name article : "+this.getNameArticle()+ " - Keywords : "+this.getKeywords()
                +" - Author : "+this.getNameAuthor()+" - Date création : "+this.getDate().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
    }
}
