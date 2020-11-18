/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.journaliste.Entity;

/**
 *
 * @author trongvo
 */
public final class Constants {
    public static final String FACTORYNAME = "jms/__defaultConnectionFactory";
    
    public static final String DESTNAME_ARCHIVE_RECEIVER = "JMSReceiverQueue";
    
    public static final String DESTNAME_ARCHIVE_PRODUCER = "JMSSenderQueue";
    
    public static final String DESTNAME_MSP_RECEIVER = "MSPReceiverQueue";
    
    public static final String FINAL_ARTICLE = "finalArticle";
    
    //public static final String DESTNAME_REC_PRODUCER = "RECSenderQueue";
    
    public static final String REC_WANT_ARTICLE = "Show Time!";
    
    public static final String EMPTY_NAME_AUTHOR = "Le nom d'auteur ne doit pas être vide!";
    
    public static final String EMPTY_CONTENT_ARTICLE = "Le contenu d'article ne doit pas être vide!";
    
    public static final String EMPTY_NAME_ARTICLE = "Le nom d'article ne doit pas être vide!";
    
    public static final String EMPTY_KEYWORDS = "Au moins UN mots clé doit être fourni!";
    
    public static final String EMPTY_LIST_ARTICLE_RECEIVED = "La liste d'articles reçu est vide!";
    
    public static final String EMPTY_LIST_ARTICLE_CHOSEN = "La liste d'articles chosi ne doit pas être vide!";
    
    
}
