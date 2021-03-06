/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Utilisateur;
import fr.miage.toulouse.gestiondto.Constants;
import fr.miage.toulouse.gestiondto.UtilisateurDTO;
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
public class UtilisateurFacade extends AbstractFacade<Utilisateur> implements UtilisateurFacadeLocal {

    @PersistenceContext(unitName = "EditeurPresseNumeriquePesistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }

    @Override
    public Utilisateur findByNom(String nom) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Utilisateur> cq = cb.createQuery(Utilisateur.class);
        Root<Utilisateur> root = cq.from(Utilisateur.class);
        cq.where(
            cb.and(
                cb.equal(cb.upper(root.get("nom").as(String.class)), nom.toUpperCase())
            )
        );
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @Override
    public Utilisateur createUser(String nom, String mail, String password, String adresse, String roles) {
        try{
            return findByNom(nom);
        }catch(NoResultException e){
            Utilisateur user = new Utilisateur(nom, mail, password, adresse, roles);
            this.create(user);
            System.out.println(Constants.CREATE_SUCCES);
            System.out.println("Create "+user.toString());
            return user;
        }
    }
    
    @Override
    public Utilisateur findByMailAndPassword(String mail, String password){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Utilisateur> cq = cb.createQuery(Utilisateur.class);
        Root<Utilisateur> root = cq.from(Utilisateur.class);
        cq.where(
            cb.and(
                cb.equal(cb.upper(root.get("mail").as(String.class)), mail.toUpperCase()),
                cb.equal(cb.upper(root.get("password").as(String.class)), password.toUpperCase())
            )
        );
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    @Override
    public List<Utilisateur> findAllUsers() {
        return findAll();
    }
    
    @Override
    public Utilisateur findByMail(String mail){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Utilisateur> cq = cb.createQuery(Utilisateur.class);
        Root<Utilisateur> root = cq.from(Utilisateur.class);
        cq.where(
            cb.and(
                cb.equal(cb.upper(root.get("mail").as(String.class)), mail.toUpperCase())
            )
        );
        return getEntityManager().createQuery(cq).getSingleResult();
    }
}
