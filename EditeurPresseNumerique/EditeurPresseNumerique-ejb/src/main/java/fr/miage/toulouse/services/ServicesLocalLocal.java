/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.services;

import fr.miage.toulouse.entities.Article;
import fr.miage.toulouse.entities.Publicite;
import fr.miage.toulouse.gestiondto.AbonnementDTO;
import fr.miage.toulouse.gestiondto.ArticleDTO;
import fr.miage.toulouse.gestiondto.PubliciteDTO;
import fr.miage.toulouse.gestiondto.TitreDTO;
import fr.miage.toulouse.gestiondto.UtilisateurDTO;
import fr.miage.toulouse.gestiondto.VolumeDTO;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author trongvo
 */
@Local
public interface ServicesLocalLocal {
    
    UtilisateurDTO findUserById(Long idUser);
    
    TitreDTO findTitreById(Long idTitre);
    
    //VolumeDTO createVolume(VolumeDTO volumeDto);
    VolumeDTO createVolume(String codeTitre, String numVolume, String nomVolume, List<Article> listArticles, List<Publicite> listPublicites, String dateTime);
    
    VolumeDTO findVolumeByNumber(String numVolume, String codeTitre);
    
    List<VolumeDTO> findVolumeByCodeTitre(String codeTitre);
    
    String sayHello(String nom, String des);
    
    UtilisateurDTO createUser(String nom, String mail, String password, String adresse, String roles);
    
    AbonnementDTO abonnerUnTitre(UtilisateurDTO userDTO, TitreDTO titreDTO, int numberOfCopies, int duration);
    
    UtilisateurDTO login(String email, String password);
    
    ArticleDTO createArticle(String nameArticle, String codeArticle, String nameAuthor, List<String> keywords, String content, String date);
    
    PubliciteDTO createPublicite(String nomPub, String descriptions);
    
    TitreDTO createTitre(String codeTitre, String nomTitre, String description, int rythmSortie);
    
    List<TitreDTO> findTitresByKeywords(String keywords);
    
    List<TitreDTO> findTitresByNom(String nomTitre);
    
    Article findArticleByCode(String codeArticle);
    
    Publicite findPubByNom(String nomPub);
    
    List<TitreDTO> findAllTitres();
    
    List<AbonnementDTO> findAllAbonnements();
    
    List<UtilisateurDTO> findAllUsers();
    
    UtilisateurDTO findUsereByMail(String mail);
    
    TitreDTO findTitreByCode(String codeTitre);
}
