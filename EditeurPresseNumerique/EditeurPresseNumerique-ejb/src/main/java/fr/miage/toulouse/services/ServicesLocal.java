/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.services;

import com.google.gson.Gson;
import fr.miage.toulouse.entities.Abonnement;
import fr.miage.toulouse.entities.Article;
import fr.miage.toulouse.entities.Publicite;
import fr.miage.toulouse.entities.Titre;
import fr.miage.toulouse.entities.Utilisateur;
import fr.miage.toulouse.entities.Volume;
import fr.miage.toulouse.gestiondto.AbonnementDTO;
import fr.miage.toulouse.gestiondto.ArticleDTO;
import fr.miage.toulouse.gestiondto.Constants;
import fr.miage.toulouse.gestiondto.PubliciteDTO;
import fr.miage.toulouse.gestiondto.TitreDTO;
import fr.miage.toulouse.gestiondto.UtilisateurDTO;
import fr.miage.toulouse.gestiondto.VolumeDTO;
import fr.miage.toulouse.interfaces.AbonnementFacadeLocal;
import fr.miage.toulouse.interfaces.ArticleFacadeLocal;
import fr.miage.toulouse.interfaces.PubliciteFacadeLocal;
import fr.miage.toulouse.interfaces.TitreFacadeLocal;
import fr.miage.toulouse.interfaces.UtilisateurFacadeLocal;
import fr.miage.toulouse.interfaces.VolumeFacadeLocal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 *
 * @author trongvo
 */
@Stateless
public class ServicesLocal implements ServicesLocalLocal {

    @EJB
    VolumeFacadeLocal volumeFacadeLocal;
    
    @EJB
    ArticleFacadeLocal articleFacadeLocal;
    
    @EJB
    UtilisateurFacadeLocal utilisateurFacadeLocal;
    
    @EJB
    AbonnementFacadeLocal abonnementFacadeLocal;
    
    @EJB
    TitreFacadeLocal titreFacadeLocal;
    
    @EJB
    PubliciteFacadeLocal publiciteFacadeLocal;
    
    private Gson gson;
    
    public ServicesLocal(){
        this.gson = new Gson();
    }
    
    @Override
    public VolumeDTO createVolume(String codeTitre, String numVolume, String nomVolume, List<Article> listArticles, List<Publicite> listPublicites, String dateTime) {
    //public VolumeDTO createVolume(VolumeDTO volumeDTO) {
        //VolumeDTO volumeDto = volumeFacadeLocal.createVolume(codeTitre, numVolume, nomVolume, listArticles, listPublicites);
        List<ArticleDTO> listArticlesDto = new ArrayList<>();
        List<PubliciteDTO> listPublicitesDto = new ArrayList<>();
        for(Article article : listArticles){
            ArticleDTO articleDto = new ArticleDTO(article.getNameArticle(), article.getCodeArticle(), 
                    article.getNameAuthor(), article.getKeywords(), article.getContent(), article.getDate());
            articleDto.setId(article.getId());
            listArticlesDto.add(articleDto);
        }
        for(Publicite pub : listPublicites){
            PubliciteDTO pubDto = new PubliciteDTO(pub.getNomPub(), pub.getDescriptionPub());
            pub.setId(pub.getId());
            listPublicitesDto.add(pubDto);
        }
        Volume volume = volumeFacadeLocal.createVolume(codeTitre, numVolume, nomVolume, listArticles, listPublicites, dateTime);
        VolumeDTO volumeDto = new VolumeDTO(volume.getCodeTitre(), volume.getNumVolume(), volume.getNomVolume(), 
                listArticlesDto, listPublicitesDto, volume.getDateTime());
        volumeDto.setId(volume.getId());
        return volumeDto;
    }

    @Override
    public VolumeDTO findVolumeByNumber(String numVolume, String codeTitre){
        try{
            Volume volume = volumeFacadeLocal.findVolumeByNumber(numVolume, codeTitre);
            VolumeDTO volumeDTO = getVolumeDTOFromVolume(volume);
            return volumeDTO;
        }catch(NoResultException e){
            Logger.getLogger(ServicesLocal.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(Constants.UNKNOWN_VOLUME);
            return null;
        }
    }

    @Override
    public List<VolumeDTO> findVolumeByCodeTitre(String codeTitre) {
        List<Volume> listVolume = volumeFacadeLocal.findAll();
        List<VolumeDTO> listVolumeFound = new ArrayList<>();

        for(Volume volume : listVolume){
            if(volume.getCodeTitre().equals(codeTitre)){
                VolumeDTO volumeDTO = getVolumeDTOFromVolume(volume);
                listVolumeFound.add(volumeDTO);
            }
        }
        return listVolumeFound;
    }

    @Override
    public String sayHello(String nom, String des){
        Publicite t = new Publicite(nom,des);
        return this.gson.toJson(t);
    }
    
    public List<ArticleDTO> createListArticleDTOWithListArticle(List<Article> listArticles){
        List<ArticleDTO> listArticleDTO = new ArrayList<>();
        for(Article article : listArticles){
            ArticleDTO articleDTO = new ArticleDTO(article.getNameArticle(),article.getCodeArticle(), article.getNameAuthor(), 
                    article.getKeywords(), article.getContent(), article.getDate());
            articleDTO.setId(article.getId());
            listArticleDTO.add(articleDTO);
        }
        
        return listArticleDTO;
    }
    
    public List<PubliciteDTO> createListPubliciteDTOWithListPublicite(List<Publicite> listPublicites){
        List<PubliciteDTO> listPubliciteDTO = new ArrayList<>();
        for(Publicite pub : listPublicites){
            PubliciteDTO pubDTO = new PubliciteDTO(pub.getNomPub(), pub.getDescriptionPub());
            pubDTO.setId(pub.getId());
            listPubliciteDTO.add(pubDTO);
        }
        return listPubliciteDTO;
    }
    
    public VolumeDTO getVolumeDTOFromVolume(Volume volume){
        VolumeDTO volumeDTO = new VolumeDTO(volume.getCodeTitre(), volume.getNumVolume(), volume.getNomVolume(), 
                                            createListArticleDTOWithListArticle(volume.getListArticles()), 
                                            createListPubliciteDTOWithListPublicite(volume.getListPublicites()), volume.getDateTime());
        volumeDTO.setId(volume.getId());
        return volumeDTO;
    }
    
    @Override
    public UtilisateurDTO createUser(String nom, String mail, String password, String adresse, String roles){
        Utilisateur user = utilisateurFacadeLocal.createUser(nom, mail, password, adresse, roles);
        UtilisateurDTO userDTO = new UtilisateurDTO(nom, mail, password, adresse, roles);
        userDTO.setId(user.getId());
        return userDTO;
    }
    
    @Override
    public AbonnementDTO abonnerUnTitre(UtilisateurDTO userDTO, TitreDTO titreDTO, int numberOfCopies, int duration){
        try{
            Utilisateur user = utilisateurFacadeLocal.findByMailAndPassword(userDTO.getMail(), userDTO.getPassword());
            Titre titre = titreFacadeLocal.findByCodeTitre(titreDTO.getCodeTitre());
            if(user.getRoles().contains(Constants.ROLES[1])){
                Abonnement abn = abonnementFacadeLocal.createAbonnement(user.getMail(), titre.getCodeTitre(), numberOfCopies, duration);
                AbonnementDTO abnDTO = new AbonnementDTO(abn.getIdUtilisateur(), abn.getCodeTitre(), numberOfCopies, duration);
                abnDTO.setId(abn.getId());
                return abnDTO;
            }else{
                System.out.println(Constants.NO_RIGHT);
                return null;
            }
        }catch(Exception e){
            Logger.getLogger(ServicesLocal.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Exception : "+e.toString());
            return null;
        }
    }
    
    @Override
    public UtilisateurDTO login(String email, String password){
        try{
            Utilisateur user = utilisateurFacadeLocal.findByMailAndPassword(email, password);
            UtilisateurDTO userDTO = new UtilisateurDTO(user.getNom(), email, password, user.getAdresse(), user.getRoles());
            userDTO.setId(user.getId());
            return userDTO;
        }catch(NoResultException e){
            System.out.println(Constants.UNKNOWN_USER);
            return null;
        }
        
    }

    @Override
    public ArticleDTO createArticle(String nameArticle, String codeArticle, String nameAuthor, List<String> keywords, String content, String date) {
        try{
            Article article = articleFacadeLocal.createArticle(nameArticle, codeArticle, nameAuthor, keywords, content, date);
            ArticleDTO articleDTO = new ArticleDTO(nameArticle, codeArticle, nameAuthor, keywords, content, date);
            article.setDate(article.getDate());
            articleDTO.setId(article.getId());
            return articleDTO;
        }catch(Exception e){
            System.out.println(Constants.CREATE_FAILED);
            System.out.println("Exception : "+e.toString());
            return null;
        }
       
    }

    @Override
    public PubliciteDTO createPublicite(String nomPub, String descriptions) {
        try{
            Publicite pub = publiciteFacadeLocal.createPub(nomPub, descriptions);
            PubliciteDTO pubDto = new PubliciteDTO(nomPub, descriptions);
            pubDto.setId(pub.getId());
            return pubDto;
        }catch(Exception e){
            System.out.println(Constants.CREATE_FAILED);
            System.out.println("Exception : "+e.toString());
            return null;
        }
    }

    @Override
    public TitreDTO createTitre(String codeTitre, String nomTitre, String description, int rythmSortie) {
        try{
            Titre titre = titreFacadeLocal.createTitre(codeTitre, nomTitre, description, rythmSortie);
            TitreDTO titreDTO = new TitreDTO(codeTitre, nomTitre, description, rythmSortie);
            titreDTO.setId(titre.getId());
            return titreDTO;
        }catch(Exception e){
            System.out.println(Constants.CREATE_FAILED);
            System.out.println("Exception : "+e.toString());
            return null;
        }
    }
    
    @Override
    public List<TitreDTO> findTitresByKeywords(String keywords) {
        List<String> keywordsGiven = Arrays.asList(keywords.split(";"));
        List<Titre> listTitres = titreFacadeLocal.findAllTitre();
        List<TitreDTO> listDTO = new ArrayList<>();
        for(Titre titre : listTitres){
            List<String> keys = getKeywords(titre);
            List<String> keyToCompare = new ArrayList<>(keywordsGiven);
            keyToCompare.retainAll(keys);
            if(keyToCompare.size() > 0){
                listDTO.add(new TitreDTO(titre.getCodeTitre(), titre.getNomTitre(), titre.getDescription(), titre.getRythmSortie()));
            }
        }
        return listDTO;
    }

    public List<String> getKeywords(Titre titre) {
        List<String> listKeywords = new ArrayList<>();
        for(Volume volume : volumeFacadeLocal.findAllVolume()){
            if(volume.getCodeTitre().equals(titre.getCodeTitre())){
                for(Article article : volume.getListArticles()){
                    List<String> keywords = article.getKeywords();
                    for(String key : keywords){
                        if(!listKeywords.contains(key)){
                            listKeywords.add(key);
                        }
                    }
                }
            }
            
        }
        return listKeywords;
    }

    @Override
    public List<TitreDTO> findTitresByNom(String nomTitre) {
        try{
            List<Titre> titres = titreFacadeLocal.findByNom(nomTitre);
            List<TitreDTO> titresDTO = new ArrayList<>();
            for(Titre titre : titres){
                titresDTO.add(new TitreDTO(titre.getCodeTitre(), titre.getNomTitre(), titre.getDescription(), titre.getRythmSortie()));
            }
            return titresDTO;
        }catch(Exception e){
            System.out.println(Constants.UNKNOWN_TITRE);
            System.out.println("Exception : "+e.toString());
            return null;
        }
    }

    @Override
    public UtilisateurDTO findUserById(Long idUser) {
        try{
            Utilisateur user = utilisateurFacadeLocal.find(idUser);
            UtilisateurDTO userDTO = new UtilisateurDTO(user.getNom(), user.getMail(), user.getPassword(), user.getAdresse(), user.getRoles());
            userDTO.setId(user.getId());
            return userDTO;
        }catch(Exception e){
            System.out.println(Constants.UNKNOWN_USER);
            System.out.println("Exception : "+e.toString());
            return null;
        }
        
        
    }

    @Override
    public TitreDTO findTitreById(Long idTitre) {
        try{
            Titre titre = titreFacadeLocal.find(idTitre);
            TitreDTO titreDTO = new TitreDTO(titre.getCodeTitre(), titre.getNomTitre(), titre.getDescription(), titre.getRythmSortie());
            titreDTO.setId(titre.getId());
            return titreDTO;
        }catch(Exception e){
            System.out.println(Constants.UNKNOWN_TITRE);
            System.out.println("Exception : "+e.toString());
            return null;
        }
    }

    @Override
    public Article findArticleByCode(String codeArticle) {
        try{
            return articleFacadeLocal.findByCodeArticle(codeArticle);
        }catch(Exception e){
            System.out.println(Constants.UNKNOWN_ARTICLE);
            System.out.println("Exception : "+e.toString());
            return null;
        }
    }

    @Override
    public Publicite findPubByNom(String nomPub) {
        try{
            return publiciteFacadeLocal.findByNomPub(nomPub);
        }catch(Exception e){
            System.out.println(Constants.UNKNOWN_PUB);
            System.out.println("Exception : "+e.toString());
            return null;
        }
    }

    @Override
    public List<TitreDTO> findAllTitres() {
        List<Titre> titres = titreFacadeLocal.findAll();
        List<TitreDTO> titresDto = new ArrayList<>();
        for(Titre titre : titres){
            TitreDTO titreDto = new TitreDTO(titre.getCodeTitre(), titre.getNomTitre(), titre.getDescription(), titre.getRythmSortie());
            titreDto.setId(titre.getId());
            titresDto.add(titreDto);
        }
        return titresDto;
    }

    @Override
    public List<AbonnementDTO> findAllAbonnements() {
        List<Abonnement> abons = abonnementFacadeLocal.findAllAbonnement();
        List<AbonnementDTO> abonsDto = new ArrayList<>();
        for(Abonnement abon : abons){
            AbonnementDTO abonDto = new AbonnementDTO(abon.getIdUtilisateur(),abon.getCodeTitre(),abon.getNumberOfCopies(),abon.getDurationInWeeks());
            abonDto.setDateBegin(abon.getDateBegin());
            abonDto.setId(abon.getId());
            abonsDto.add(abonDto);
        }
        return abonsDto;
    }

    @Override
    public List<UtilisateurDTO> findAllUsers() {
        List<UtilisateurDTO> usersDto = new ArrayList<>();
        for(Utilisateur user : utilisateurFacadeLocal.findAllUsers()){
            UtilisateurDTO userDto = new UtilisateurDTO(user.getNom(), user.getMail(), user.getPassword(), user.getAdresse(), user.getRoles());
            userDto.setId(user.getId());
            usersDto.add(userDto);
        }
        
        return usersDto;
    }

    @Override
    public UtilisateurDTO findUsereByMail(String mail) {
        Utilisateur user = utilisateurFacadeLocal.findByMail(mail);
        UtilisateurDTO usereDto = new UtilisateurDTO(user.getNom(), user.getMail(), user.getPassword(), user.getAdresse(), user.getRoles());
        usereDto.setId(user.getId());
        return usereDto;
    }

    @Override
    public TitreDTO findTitreByCode(String codeTitre) {
        Titre titre = titreFacadeLocal.findByCodeTitre(codeTitre);
        TitreDTO titreDto = new TitreDTO(titre.getCodeTitre(), titre.getNomTitre(), titre.getDescription(), titre.getRythmSortie());
        titreDto.setId(titre.getId());
        return titreDto;
    }
}
