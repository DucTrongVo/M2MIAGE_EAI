/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Abonnement;
import fr.miage.toulouse.gestiondto.Constants;
import java.util.List;
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
public class AbonnementFacade extends AbstractFacade<Abonnement> implements AbonnementFacadeLocal {

    @PersistenceContext(unitName = "EditeurPresseNumeriquePesistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AbonnementFacade() {
        super(Abonnement.class);
    }

    @Override
    public Abonnement findExistedAbonnement(String idUtilisateur, String codeTitre, int numberOfCopies, int durationInWeeks) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Abonnement> cq = cb.createQuery(Abonnement.class);
        Root<Abonnement> root = cq.from(Abonnement.class);
        cq.where(
            cb.and(
                cb.equal(cb.upper(root.get("idUtilisateur").as(String.class)), idUtilisateur.toUpperCase()),
                cb.equal(cb.upper(root.get("codeTitre").as(String.class)), codeTitre.toUpperCase())
            )
        );
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @Override
    public Abonnement createAbonnement(String idUtilisateur, String codeTitre, int numberOfCopies, int durationInWeeks) {
        try{
            return findExistedAbonnement(idUtilisateur, codeTitre, numberOfCopies, durationInWeeks);
        }catch(NoResultException e){
            Abonnement abonnement = new Abonnement(idUtilisateur, codeTitre, numberOfCopies, durationInWeeks);
            this.create(abonnement);
            System.out.println(Constants.CREATE_SUCCES);
            System.out.println("Create "+abonnement.toString());
            return abonnement;
        }
    }

    @Override
    public List<Abonnement> findAllAbonnement() {
        return findAll();
    }
    
}
