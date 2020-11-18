/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.misesouspresse;

import fr.miage.toulouse.gestionpublicite.Publicite;
import fr.miage.toulouse.journaliste.Entity.Article;
import fr.miage.toulouse.journaliste.Entity.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author trongvo
 */
public class MiseSousPresse extends Thread{
    private HashMap<String,List<Article>> listArtciles;
    private List<Publicite> listPublicites;
    private int nbrArticles;
    
    private Context context = null;
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private String factoryName = Constants.FACTORYNAME;
    //private String destNameProducer = Constants.DESTNAME_ARCHIVE_RECEIVER;
    private String destNameReceiver = Constants.DESTNAME_MSP_RECEIVER;
    //private String destNameRECProducer = Constants.DESTNAME_ARCHIVE_PRODUCER;
    //private Destination destProducer = null;
    private Destination destReceiver = null;
    private Session session = null;
    //private MessageProducer producer = null;
    private MessageConsumer receiver = null;

    public HashMap<String, List<Article>> getListArtciles() {
        return listArtciles;
    }

    public List<Publicite> getListPublicites() {
        return listPublicites;
    }

    public int getNbrArticles() {
        return nbrArticles;
    }
    
    public MiseSousPresse(){
        listArtciles = new HashMap<>();
        listPublicites = new ArrayList<>();
        nbrArticles = 0;
    }
    
    @Override
    public void run(){
        try {
            //openConnexion = true;
            // create the JNDI initial context.
            context = new InitialContext();
            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);
            // look up the Destination
            destReceiver = (Destination) context.lookup(destNameReceiver);
            // create the connection
            connection = (Connection) factory.createConnection();
            // create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // create the producer
            receiver = session.createConsumer(destReceiver);
            // start the connection, to enable message sends
            connection.start();
            while(true){
                ObjectMessage messageReceive = (ObjectMessage) receiver.receive();
                nbrArticles = 0;
                if(messageReceive instanceof ObjectMessage){
                    String typeMessage = messageReceive.getStringProperty(Constants.TYPE);
                    switch (typeMessage) {
                        case Constants.ARTICLE:
                            Article articleReceive = (Article) messageReceive.getObject();
                            System.out.println("MSP Received Article : "+articleReceive.toString());
                            String themeReceive = messageReceive.getStringProperty(Constants.CODE_THEME);
                            if(listArtciles.containsKey(themeReceive)){
                                listArtciles.get(themeReceive).add(articleReceive);
                            }else{
                                listArtciles.put(themeReceive, Arrays.asList(articleReceive));
                            }
                            nbrArticles += 1;
                            break;
                        case Constants.PUBLICITE:
                            Publicite pub = (Publicite) messageReceive.getObject();
                            System.out.println("MSP Received Publicité : "+pub.toString());
                            this.listPublicites.add(pub);
                            break;
                        default:
                            System.out.println("MSP Received unknown message : "+messageReceive.toString());
                            break;
                    }
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(MiseSousPresse.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(MiseSousPresse.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close the context
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException exception) {
                    exception.printStackTrace();
                }
            }

            // close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
