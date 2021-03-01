/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Article;
import fr.miage.toulouse.entities.Publicite;
import fr.miage.toulouse.entities.Volume;
import fr.miage.toulouse.gestiondto.Constants;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author trongvo
 */
@Stateless
public class VolumeFacade extends AbstractFacade<Volume> implements VolumeFacadeLocal {

    @EJB
    VolumeFacadeLocal volumeFacadeLocal;
    
    @PersistenceContext(unitName = "EditeurPresseNumeriquePesistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VolumeFacade() {
        super(Volume.class);
    }

    @Override
    public Volume findVolumeByNumber(String numVolume, String codeTitre) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Volume> cq = cb.createQuery(Volume.class);
        Root<Volume> root = cq.from(Volume.class);
        cq.where(
            cb.and(
                cb.equal(cb.upper(root.get("numVolume").as(String.class)), numVolume.toUpperCase()),
                cb.equal(cb.upper(root.get("codeTitre").as(String.class)), codeTitre.toUpperCase())
            )
        );
       
        return getEntityManager().createQuery(cq).getSingleResult();
    }

//    @Override
//    public List<Volume> findVolumeByCodeTitre(String codeTitre) {
//        List<Volume> listVolume = volumeFacadeLocal.findAll();
//        List<Volume> listVolumeFound = new ArrayList<>();
//        
//        for(Volume volume : listVolume){
//            if(volume.getCodeTitre().equals(codeTitre)){
//                listVolumeFound.add(volume);
//            }
//        }
//        return listVolumeFound;
//    }

    @Override
    public Volume createVolume(String codeTitre, String numVolume, String nomVolume, List<Article> listArticles, List<Publicite> listPublicites, String dateTime) {
        try{
            return findVolumeByNumber(numVolume, codeTitre);
        }catch(NoResultException e){
            Volume volume = new Volume(codeTitre, numVolume, nomVolume, listArticles, listPublicites, dateTime);
            this.create(volume);
            System.out.println(Constants.CREATE_SUCCES);
            System.out.println("Create Volume : "+volume.toString());
            return volume;
        }
    }

    @Override
    public List<Volume> findAllVolume() {
        return findAll();
    }
    
}
