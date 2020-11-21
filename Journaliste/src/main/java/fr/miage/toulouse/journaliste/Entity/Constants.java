/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.journaliste.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author trongvo
 */
public final class Constants {
    
    // BD user : miage20 pass: miage20
    public static final String ARTICLE = "Article";
    
    public static final String CODE_THEME = "codeTheme";
    
    public static final String DESTNAME_ARCHIVE_RECEIVER = "JMSReceiverQueue";
    
    public static final String DESTNAME_ARCHIVE_PRODUCER = "JMSSenderQueue";
    
    public static final String DESTNAME_MSP_RECEIVER = "MSPReceiverQueue";
    
    public static final String DESTNAME_MSP_PRODUCER = "MSPProducerQueue";
    
    public static final String DESTNAME_MSP_TOPIC = "MSPProducerTopic";
    
    //public static final String DESTNAME_REC_PRODUCER = "RECSenderQueue";
    
    public static final String REC_WANT_ARTICLE = "Show Time!";
    
    public static final String EMPTY_NAME_AUTHOR = "Le nom d'auteur ne doit pas être vide!";
    
    public static final String EMPTY_CONTENT_ARTICLE = "Le contenu d'article ne doit pas être vide!";
    
    public static final String EMPTY_NAME_ARTICLE = "Le nom d'article ne doit pas être vide!";
    
    public static final String EMPTY_KEYWORDS = "Au moins UN mots clé doit être fourni!";
    
    public static final String EMPTY_LIST_ARTICLE_RECEIVED = "La liste d'articles reçu est vide!";
    
    public static final String EMPTY_LIST_ARTICLE_CHOSEN = "La liste d'articles chosi ne doit pas être vide!";
    
    public static final String EMPTY_LIST_PUBS = "La liste des publicités est vide!";
    
    public static final String EMPTY_NOM_VOLUME = "Le nom du volume ne doit pas être vide!";
    
    public static final String EMPTY_NUM_VOLUME = "Il faut définir un numéro de volume!";
    
    public static final String FACTORYNAME = "jms/__defaultConnectionFactory";
    
    public static final String FINAL_ARTICLE = "finalArticle";
    
    public static final String PUBLICITE = "Publicite";
    
    public static final String SEND_FAILED = "Une erreur est parvenu! Impossible d'envoyer";
    
    public static final String SUCCES = "Succès!";
    
    public static final String SEND_SUCCEDED = "Envoyé avec succès!";
    
    public static final String TYPE = "type";
    
    public static final String THEME_NAMES_1 = "GAMES";
    public static final String THEME_NAMES_2 = "SCIENCE FICTION";
    public static final String THEME_NAMES_3 = "GENERAL SCIENCE";
    public static final String THEME_NAMES_4 = "ECONOMIC";
    public static final String THEME_CODE_1 = "GA";
    public static final String THEME_CODE_2 = "SF";
    public static final String THEME_CODE_3 = "GS";
    public static final String THEME_CODE_4 = "EC";
    
}
