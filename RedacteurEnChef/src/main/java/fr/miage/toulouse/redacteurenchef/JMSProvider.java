/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.redacteurenchef;

import fr.miage.toulouse.journaliste.Entity.Article;
import fr.miage.toulouse.journaliste.Entity.Constants;
import java.awt.List;
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
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author trongvo
 */
public class JMSProvider {
    private Context context = null;
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private String factoryName = Constants.FACTORYNAME;
    private String destNameProducer = Constants.DESTNAME_ARCHIVE_RECEIVER;
    private String destNameReceiver = Constants.DESTNAME_ARCHIVE_PRODUCER;
    private Destination destProducer = null;
    private Destination destReceiver = null;
    private Session session = null;
    private MessageProducer producer = null;
    private MessageConsumer receiver = null;
    private boolean stopListen = false;
    
    public JMSProvider(){};
    
    public void listenToArchiveArticle(){
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
            stopListen = false;
            while(!stopListen){
                ObjectMessage messageReceive = (ObjectMessage) receiver.receive();
                if(messageReceive instanceof ObjectMessage){
                    Article listReceive = (Article) messageReceive.getObject();
                    System.out.println("REC Received : "+listReceive.getCodeArticle());
                    stopListen = true;
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(JMSProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(JMSProvider.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
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
    public void getArticle(){
        try {
            //openConnexion = true;
            // create the JNDI initial context.
            context = new InitialContext();
            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);
            // look up the Destination
            destProducer = (Destination) context.lookup(destNameProducer);
            // create the connection
            connection = (Connection) factory.createConnection();
            // create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // create the producer
            producer = session.createProducer(destProducer);
            // start the connection, to enable message sends
            connection.start();
            
            TextMessage message = session.createTextMessage();
            message.setText("ARTICLES?");
            producer.send(message);
            System.out.println("REC Send question: "+message.getText());
        } catch (NamingException ex) {
            Logger.getLogger(JMSProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(JMSProvider.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
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
    
    public void sendArticles(){
        try {
            //openConnexion = true;
            // create the JNDI initial context.
            context = new InitialContext();
            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);
            // look up the Destination
            destReceiver = (Destination) context.lookup(destNameProducer);
            // create the connection
            connection = (Connection) factory.createConnection();
            // create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // create the producer
            producer = session.createProducer(destReceiver);
            // start the connection, to enable message sends
            connection.start();
            
            
            
        } catch (NamingException ex) {
            Logger.getLogger(JMSProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(JMSProvider.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
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
