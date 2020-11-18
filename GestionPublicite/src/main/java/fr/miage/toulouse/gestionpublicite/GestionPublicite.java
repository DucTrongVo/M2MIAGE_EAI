/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.gestionpublicite;

import fr.miage.toulouse.journaliste.Entity.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
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
public class GestionPublicite {
    List<Publicite> listPublicite;
    private Context context = null;
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private String factoryName = Constants.FACTORYNAME;
    private String destNameProducer = Constants.DESTNAME_MSP_RECEIVER;
    private Destination destProducer = null;
    private Session session = null;
    private MessageProducer producer = null;
    public GestionPublicite(){
        listPublicite = new ArrayList<>();
        listPublicite.add(new Publicite("P_01", "C'est un pub pour les voitures BMW! 200K € chaque!"));
        listPublicite.add(new Publicite("P_02", "C'est un pub pour les viandes bovine farncais! WOW juste WOW!"));
        listPublicite.add(new Publicite("P_03", "C'est un pub pour Karamat! Le start up de Lamine Musk!"));
    }
    
    public boolean sendPubsToMiseSousPresse(){
        boolean sendWIthSuccess = true;
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
            
            for(int i=0;i<listPublicite.size();i++){
                ObjectMessage objectMessage = session.createObjectMessage(listPublicite.get(i));
                objectMessage.setStringProperty(Constants.TYPE, Constants.PUBLICITE);
                producer.send(objectMessage);
                System.out.println("Gestion Publicité send pub : "+listPublicite.get(i).toString());
            }
        } catch (NamingException ex) {
            Logger.getLogger(GestionPublicite.class.getName()).log(Level.SEVERE, null, ex);
            sendWIthSuccess = false;
        } catch (JMSException ex) {
            Logger.getLogger(GestionPublicite.class.getName()).log(Level.SEVERE, null, ex);
            sendWIthSuccess = false;
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
        return sendWIthSuccess;
    }
}
