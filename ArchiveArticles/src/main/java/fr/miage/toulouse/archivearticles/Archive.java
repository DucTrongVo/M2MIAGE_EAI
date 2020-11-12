/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.archivearticles;

import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Destination;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
/**
 *
 * @author trongvo
 */
public class Archive {
    private List<Article> listArticles;

    public Archive() {
        this.listArticles = new ArrayList<>();
    }

    public List<Article> getListArticles() {
        return listArticles;
    }

    public void setListArticles(List<Article> listArticles) {
        this.listArticles = listArticles;
    }
    
    public void addNewArticle(Article article){
        this.listArticles.add(article);
    }
    
    public void removeArticleFromList(int numArticle){
        for(Article article : listArticles){
            if(article.getNumArticle() == numArticle){
                listArticles.remove(article);
            }       
        }
    }

    public void receiverArticle(){
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = Constants.factoryName;
        String destName = Constants.destName;
        Destination dest = null;
        Session session = null;
        MessageConsumer receiver = null;
        int count = 0;

        try {
            // create the JNDI initial context
            context = new InitialContext();

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);

            // look up the Destination
            dest = (Destination) context.lookup(destName);

            // create the connection
            connection = factory.createConnection();

            // create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // create the receiver
            //receiver = session.createConsumer(dest,"JMSType IN ('GOO','AMZ','OKT','MIA')");
            receiver = session.createConsumer(dest);

            // start the connection, to enable message receipt
            connection.start();

            while(true){
                Message message = receiver.receive();
                if (message instanceof ObjectMessage) {
                    ObjectMessage mess = (ObjectMessage) message;
                    Article articleReceived = (Article) mess.getObject();
                    System.out.println("Received: " + articleReceived.getCodeArticle());
                } else if (message != null) {
                    System.out.println("Received non text message");
                }
            }
        } catch (JMSException | NamingException exception) {
            exception.printStackTrace();
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
