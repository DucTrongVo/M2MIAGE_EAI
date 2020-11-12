/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.journaliste.Entity;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.jms.JMSException;
import javax.jms.Destination;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * Classe d'objet qui effectue l'envoie des articles
 * @author trongvo
 */
public class JMSSender {
    private Context context = null;
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private String factoryName = Constants.factoryName;
    private String destName = Constants.destName;
    private Destination dest = null;
    private Session session = null;
    private MessageProducer sender = null;
    
    // attribut détermine si on va fermer la connexion (openConnexion = false)
    //private boolean openConnexion;

    public JMSSender(){
    }
    
    public void sendArticle(Article article){
        try{
            //openConnexion = true;
            // create the JNDI initial context.
            context = new InitialContext();
            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);
            // look up the Destination
            dest = (Destination) context.lookup(destName);
            // create the connection
            connection = (Connection) factory.createConnection();
            // create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // create the sender
            sender = session.createProducer(dest);
            // start the connection, to enable message sends
            connection.start();
            
            // Send article
            //while(openConnexion) {
            ObjectMessage message = session.createObjectMessage(article);
            message.setJMSType(article.getCodeArticle());
            sender.send(message);
            System.out.println("Sent artcle: " + article.getCodeArticle());
                //Thread.sleep(5000);
           //}
        }catch (JMSException exception) {
            exception.printStackTrace();
        } catch (NamingException ex) {
            Logger.getLogger(JMSSender.class.getName()).log(Level.SEVERE, null, ex);
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
