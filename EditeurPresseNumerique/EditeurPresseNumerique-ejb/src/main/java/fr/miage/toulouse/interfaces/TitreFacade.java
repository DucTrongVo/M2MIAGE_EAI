/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Titre;
import fr.miage.toulouse.entities.Volume;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author trongvo
 */
@Stateless
public class TitreFacade extends AbstractFacade<Titre> implements TitreFacadeLocal {

    @EJB
    TitreFacadeLocal titreFacadeLocal;
    
    @EJB
    VolumeFacadeLocal volumeFacadeLocal;
    
    @PersistenceContext(unitName = "EditeurPresseNumeriquePesistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TitreFacade() {
        super(Titre.class);
    }

    @Override
    public List<Titre> findByNom(String nomTitre) {
        List<Titre> listTitre = titreFacadeLocal.findAll();
        List<Titre> listFound = new ArrayList<>();
        for(Titre titre : listTitre){
            if(titre.getNomTitre().contains(nomTitre)){
                listFound.add(titre);
            }
        }
        return listFound;
    }

    @Override
    public List<Titre> findByKeywords(String keywords) {
        List<Titre> listTitres = titreFacadeLocal.findAll();
        List<Titre> listTitresFound = new ArrayList<>();
        List<String> listKeysReceived = Arrays.asList(keywords.split(";"));
        for(Titre titre : listTitres){
            List<String> cloneListKey = new ArrayList<>(listKeysReceived);
            cloneListKey.retainAll(getKeywords(titre));
            if(cloneListKey.size() > 0){
                listTitresFound.add(titre);
            }
        }
        
        return listTitresFound;
    }

    @Override
    public List<String> getKeywords(Titre titre) {
        List<String> listKeywords = new ArrayList<>();
//        for(Volume volume : volumeFacadeLocal.findVolumeByCodeTitre(titre.getCodeTitre())){
//            for(Article article : volume.getListArticles()){
//                List<String> keywords = article.getKeywords();
//                for(String key : keywords){
//                    if(!listKeywords.contains(key)){
//                        listKeywords.add(key);
//                    }
//                }
//            }
//        }
        return listKeywords;
    }
}
