/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Publicite;
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
public class PubliciteFacade extends AbstractFacade<Publicite> implements PubliciteFacadeLocal {

    @PersistenceContext(unitName = "EditeurPresseNumeriquePesistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PubliciteFacade() {
        super(Publicite.class);
    }

    @Override
    public Publicite findByNomPub(String nomPub) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Publicite> cq = cb.createQuery(Publicite.class);
        Root<Publicite> root = cq.from(Publicite.class);
        cq.where(
            cb.and(
                cb.equal(cb.upper(root.get("nomPub").as(String.class)), nomPub.toUpperCase())
            )
        );
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @Override
    public Publicite createPub(String nomPub, String descriptions) {
        try{
            return findByNomPub(nomPub);
        }catch(NoResultException e){
            Publicite publicite = new Publicite(nomPub, descriptions);
            this.create(publicite);
            System.out.println(Constants.CREATE_SUCCES);
            System.out.println("Create "+publicite.toString());
            return publicite;
        }
    }
    
}
