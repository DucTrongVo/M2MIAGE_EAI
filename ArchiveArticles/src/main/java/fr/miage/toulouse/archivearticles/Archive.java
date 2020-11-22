/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.archivearticles;

import fr.miage.toulouse.journaliste.Entity.Article;
import fr.miage.toulouse.gestiondto.Constants;
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
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
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

    /**
     * Return a list of article by its topic
     * @param codeTitre code of theme
     * @return list of article in a same topic
     */
//    public List<Article> getArticleByTitreCode(String codeTitre){
//        List<Article> listFound = new ArrayList<>();
//        for(Article article : listArticles){
//            if(article.getCodeTitre().equals(codeTitre)){
//                listFound.add(article);
//            }
//        }
//        return listFound;
//    }
    
    /**
     * Listen for message send from Journaliste and Redacteur En Chef.
     * Return a liste of article by topic to Redadcteur en Chef on demand
     */
    public void receiverArticle(){
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = Constants.FACTORYNAME;
        String destNameReceiver = Constants.DESTNAME_ARCHIVE_RECEIVER;
        String destNameProducer = Constants.DESTNAME_ARCHIVE_PRODUCER;
        Destination destReceiver = null;
        Destination destProducer = null;
        Session session = null;
        MessageConsumer receiver = null;
        MessageProducer producer = null;

        try {
            // create the JNDI initial context
            context = new InitialContext();

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);

            // look up the Destination
            destReceiver = (Destination) context.lookup(destNameReceiver);
            destProducer = (Destination) context.lookup(destNameProducer);

            // create the connection
            connection = factory.createConnection();

            // create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // create the receiver
            //receiver = session.createConsumer(destReceiver,"JMSType IN ('GOO','AMZ','OKT','MIA')");
            receiver = session.createConsumer(destReceiver);
            // create the producer 
            producer = session.createProducer(destProducer);
            
            // start the connection, to enable message receipt
            connection.start();

            while(true){
                Message messageReceive = receiver.receive();
                if(messageReceive instanceof TextMessage){
                    TextMessage mess = (TextMessage) messageReceive;
                    System.out.println("Code Titre received from REC : "+mess.getText());
                    List<Article> listFound = getListArticles();
                    if(listFound.size() > 0 && mess.getText().equals(Constants.REC_WANT_ARTICLE)){
                        for(Article article : listFound){
                            ObjectMessage objectMessage = session.createObjectMessage(article);
                            if(article.equals(listFound.get(listFound.size() - 1))){
                                objectMessage.setBooleanProperty(Constants.FINAL_ARTICLE, true);
                            }else{
                                objectMessage.setBooleanProperty(Constants.FINAL_ARTICLE, false);
                            }
                            //ObjectMessage objectMessage = sessionConsumer.createObjectMessage(this.listArticles.get(0));
                            producer.send(objectMessage);
                            System.out.println("Send to REC : "+article.toString());
                        }
                    }
                }
                 
                else if (messageReceive instanceof ObjectMessage) {
                    ObjectMessage mess = (ObjectMessage) messageReceive;
                    Article articleReceived = (Article) mess.getObject();
                    System.out.println("Received from Journaliste : " + articleReceived.toString());
                    this.addNewArticle(articleReceived);
                }
                else if (messageReceive != null) {
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
                    System.out.println("Context Archive close");
                } catch (NamingException exception) {
                    exception.printStackTrace();
                }
            }

            // close the connection
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connexion Archive close");
                } catch (JMSException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
