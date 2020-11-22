/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.gestiondto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *x
 * @author trongvo
 */
public class ArticleDTO {
    
    private String nameArticle;
    private String codeArticle;
    private String nameAuthor;
    private List<String> keywords;
    private String content;
    private Long numArticle;
    private LocalDateTime date;
    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public ArticleDTO(String nameArticle, String codeArticle, String nameAuthor, List<String> keywords, String content) {
        this.nameArticle = nameArticle;
        this.codeArticle = codeArticle;
        this.nameAuthor = nameAuthor;
        this.keywords = keywords;
        this.content = content;
        this.date = LocalDateTime.now();
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

    public Long getNumArticle() {
        return numArticle;
    }

    public void setNumArticle(Long numArticle) {
        this.numArticle = numArticle;
    }

    
    public LocalDateTime getDate(){
        return this.date;
    }
    
    @Override
    public String toString(){
        return "Code article : "+this.getCodeArticle()+" - Name article : "+this.getNameArticle()
                +" - Author : "+this.getNameAuthor()+" - Date création : "+this.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
