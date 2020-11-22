/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.services;

import com.google.gson.Gson;
import fr.miage.toulouse.entities.Article;
import fr.miage.toulouse.entities.Publicite;
import fr.miage.toulouse.entities.Volume;
import fr.miage.toulouse.gestiondto.ArticleDTO;
import fr.miage.toulouse.gestiondto.PubliciteDTO;
import fr.miage.toulouse.gestiondto.VolumeDTO;
import fr.miage.toulouse.interfaces.AbonnementFacadeLocal;
import fr.miage.toulouse.interfaces.ArticleFacadeLocal;
import fr.miage.toulouse.interfaces.PubliciteFacadeLocal;
import fr.miage.toulouse.interfaces.TitreFacadeLocal;
import fr.miage.toulouse.interfaces.UtilisateurFacadeLocal;
import fr.miage.toulouse.interfaces.VolumeFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author trongvo
 */
@Stateless
public class ServicesBean implements ServicesBeanLocal {

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
    
    public ServicesBean(){
        this.gson = new Gson();
    }
    
    @Override
    public VolumeDTO createVolume(String codeTitre, String numVolume, String nomVolume, List<Article> listArticles, List<Publicite> listPublicites) {
        Volume volume = volumeFacadeLocal.createVolume(codeTitre, numVolume, nomVolume, listArticles, listPublicites);
        List<ArticleDTO> listArticleDto = new ArrayList<>();
        List<PubliciteDTO> listPubliciteDto = new ArrayList<>();
        for(Article article : volume.getListArticles()){
            ArticleDTO articleDTO = new ArticleDTO(article.getNameArticle(), article.getCodeArticle(), article.getNameAuthor(), article.getKeywords(), article.getContent());
            listArticleDto.add(articleDTO);
        }
        for(Publicite pub : volume.getListPublicites()){
            listPubliciteDto.add(new PubliciteDTO(pub.getNomPub(), pub.getDescriptionPub()));
        }
        VolumeDTO volumeDTO = new VolumeDTO(volume.getCodeTitre(),volume.getNomVolume(), listArticleDto, listPubliciteDto, volume.getDateTime());
        return volumeDTO;
    }

    @Override
    public VolumeDTO findVolumeByNumber(String numVolume) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VolumeDTO findVolumeByCodeTitre(String codeTitre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String sayHello(String nom, String des){
        Publicite t = new Publicite(nom,des);
        return this.gson.toJson(t);
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
