/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Titre;
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
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Titre> cq = cb.createQuery(Titre.class);
        Root<Titre> root = cq.from(Titre.class);
        cq.where(
            cb.and(
                cb.like(cb.upper(root.get("nomTitre").as(String.class)), nomTitre.toUpperCase())
            )
        );
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    @Override
    public Titre findByCodeTitre(String codeTitre){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Titre> cq = cb.createQuery(Titre.class);
        Root<Titre> root = cq.from(Titre.class);
        cq.where(
            cb.and(
                cb.like(cb.upper(root.get("codeTitre").as(String.class)), codeTitre.toUpperCase())
            )
        );
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @Override
    public Titre createTitre(String codeTitre, String nomTitre, String description, int rythmSortie) {
        try{
            return findByCodeTitre(codeTitre); 
        }catch(NoResultException e){
            Titre titre = new Titre(codeTitre, nomTitre, description, rythmSortie);
            this.create(titre);
            return titre;
        }
    }

    @Override
    public List<Titre> findAllTitre() {
        return findAll();
    }
}
