/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.gestionarchivage;

import fr.miage.toulouse.controllers.UserController;
import com.google.gson.Gson;
import fr.miage.toulouse.controllers.AbonnementController;
import fr.miage.toulouse.controllers.ArticleController;
import fr.miage.toulouse.controllers.PubliciteController;
import fr.miage.toulouse.controllers.TitreController;
import fr.miage.toulouse.controllers.VolumeController;
import fr.miage.toulouse.gestiondto.AbonnementDTO;
import fr.miage.toulouse.gestiondto.Constants;
import fr.miage.toulouse.gestiondto.TitreDTO;
import fr.miage.toulouse.gestiondto.UtilisateurDTO;
import fr.miage.toulouse.gestionpublicite.Publicite;
import fr.miage.toulouse.journaliste.Entity.Article;
import fr.miage.toulouse.misesouspresse.Volume;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author trongvo
 */
public class GestionArchive extends Thread{
    private Context context = null;
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private String factoryName = Constants.FACTORYNAME;
    private String destNameReceiverTopic = Constants.DESTNAME_MSP_TOPIC;
    private Destination destReceiverTopic = null;
    private Session session = null;
    private MessageConsumer receiver = null;
    
    private String host = Constants.BASIC_URL;
    private Gson gson;
    private UserController userController;
    private ArticleController articleController;
    private PubliciteController pubController;
    private TitreController titreController;
    private AbonnementController abonController;
    private VolumeController volumeController;
    private Client client;
    private WebTarget webTarget;
    
    public GestionArchive(){
        this.gson = new Gson();
        client = ClientBuilder.newClient();
        webTarget  = client.target(host);
        userController = new UserController(gson, webTarget);
        articleController = new ArticleController(gson, webTarget);
        pubController = new PubliciteController(gson, webTarget);
        titreController = new TitreController(gson, webTarget);
        abonController = new AbonnementController(gson, webTarget);
        volumeController = new VolumeController(gson, webTarget);
        
        onCreate();
    }
    
    private void onCreate(){
        titreController.createTitre(Constants.THEME_CODE_1, Constants.THEME_NAMES_1, "Jeux Vidéo", "4");
        titreController.createTitre(Constants.THEME_CODE_2, Constants.THEME_NAMES_2, "Science et Fiction", "4");
        titreController.createTitre(Constants.THEME_CODE_3, Constants.THEME_NAMES_3, "Connaissance générale", "5");
        titreController.createTitre(Constants.THEME_CODE_4, Constants.THEME_NAMES_4, "Economie en générale", "5");
    }
    
    public UtilisateurDTO loginUser(String mail, String password){
        return userController.loginUser(mail, password);
    }
    
    public UtilisateurDTO inscription(String nom, String mail, String password, String adresse, String roles){
        return userController.inscription(nom, mail, password, adresse, roles);
    }
    
    public List<TitreDTO> findTitreByNom(String nomTitre){
        return titreController.findTitreByNom(nomTitre);
    }
    
    public List<TitreDTO> findTitreByKeywords(String keywords){
        return titreController.findTitreByKeywords(keywords);
    }
    
    public AbonnementDTO abonner(String idUtilisateur, String idTitre, String numberOfCopies, String durationInWeeks){
        return abonController.createAbonnement(idUtilisateur, idTitre, numberOfCopies, durationInWeeks);
    }
    
    public List<TitreDTO> getAllTitres(){
        return titreController.findAllTitres();
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
            destReceiverTopic = (Destination) context.lookup(destNameReceiverTopic);
            // create the connection
            connection = (Connection) factory.createConnection();
            // create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // create the producer
            receiver = session.createConsumer(destReceiverTopic, "JMSType IN ('GA','SF','GS','EC')");
            // start the connection, to enable message sends
            connection.start();
            while(true){
                ObjectMessage messageReceive = (ObjectMessage) receiver.receive();
                if(messageReceive instanceof ObjectMessage){
                    Volume volume = (Volume) messageReceive.getObject();
                    String listCodeArticles = "";
                    String listCodePub = "";
                    for(Article article : volume.getListArticles()){
                        String keys = "";
                        //for(String key : article.getKeywords()){
                        for(int i=0;i<article.getKeywords().size();i++){
                            if(i+1 == article.getKeywords().size()){
                                keys += article.getKeywords().get(i);
                            }else{
                                keys += article.getKeywords().get(i)+";";
                            }
                            
                        }
                        String date = article.getDate().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
                        System.out.println("article.getDate() : "+article.getDate());
                        System.out.println("Date : "+date);
                        LocalDate parse = LocalDate.parse(date, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
                        System.out.println("Parse : "+parse);
                        articleController.createArticle(article.getNameArticle(), article.getCodeArticle(), article.getNameAuthor(), 
                                keys, article.getContent(), article.getDate().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)));
                        listCodeArticles += article.getCodeArticle()+";";
                        Thread.sleep(1000);
                    }
                    for(Publicite pub : volume.getListPublicites()){
                        pubController.createPub(pub.getNomPub(), pub.getDescriptionPub());
                        listCodePub += pub.getNomPub()+";";
                        Thread.sleep(1000);
                    }
                    System.out.println("listCodeArticles : "+listCodeArticles);
                    volumeController.postNewVolume(volume.getCodeTitre(), volume.getNumVolume(), volume.getNomVolume(), 
                            listCodeArticles, listCodePub, volume.getDateTime().format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)));
                    
                    System.out.println("Volume created in BD : "+volume.toString());
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(GestionArchive.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(GestionArchive.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GestionArchive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
