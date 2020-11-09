/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.journaliste.Entity;

import java.io.Serializable;

/**
 *
 * @author trongvo
 */
public class Article implements Serializable{
    private String nameArticle;
    private String codeArticle;
    private String nameAuthor;
    private String[] keywords;
    private String content;
    private int numArticle;
    private static int numGlobalArticle = 0;

    public Article(String nameArticle, String codeArticle, String nameAuthor, String[] keywords, String content) {
        this.nameArticle = nameArticle;
        this.codeArticle = codeArticle;
        this.nameAuthor = nameAuthor;
        this.keywords = keywords;
        this.content = content;
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

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
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
    
}
