/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Abonnement;
import fr.miage.toulouse.entities.Utilisateur;
import fr.miage.toulouse.gestiondto.Constants;
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
    public Abonnement findExistedAbonnement(Long idUtilisateur, Long idTitre, int numberOfCopies, int durationInWeeks) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Abonnement> cq = cb.createQuery(Abonnement.class);
        Root<Abonnement> root = cq.from(Abonnement.class);
        cq.where(
            cb.and(
                cb.equal(cb.upper(root.get("idUtilisateur").as(String.class)), String.valueOf(idUtilisateur)),
                cb.equal(cb.upper(root.get("idTitre").as(String.class)), String.valueOf(idTitre)),
                cb.equal(cb.upper(root.get("numberOfCopies").as(String.class)), String.valueOf(numberOfCopies)),
                cb.equal(cb.upper(root.get("durationInWeeks").as(String.class)), String.valueOf(durationInWeeks))
            )
        );
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @Override
    public Abonnement createAbonnement(Long idUtilisateur, Long idTitre, int numberOfCopies, int durationInWeeks) {
        try{
            return findExistedAbonnement(idUtilisateur, idTitre, numberOfCopies, durationInWeeks);
        }catch(NoResultException e){
            Abonnement abonnement = new Abonnement(idUtilisateur, idTitre, numberOfCopies, durationInWeeks);
            this.create(abonnement);
            System.out.println(Constants.CREATE_SUCCES);
            System.out.println("Create "+abonnement.toString());
            return abonnement;
        }
    }
    
}
