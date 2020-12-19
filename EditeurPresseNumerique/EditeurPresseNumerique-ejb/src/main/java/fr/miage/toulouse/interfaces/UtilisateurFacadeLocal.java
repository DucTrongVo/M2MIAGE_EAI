/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Utilisateur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author trongvo
 */
@Local
public interface UtilisateurFacadeLocal {

    void create(Utilisateur utilisateur);

    void edit(Utilisateur utilisateur);

    void remove(Utilisateur utilisateur);

    Utilisateur find(Object id);

    List<Utilisateur> findAll();
    
    List<Utilisateur> findAllUsers();

    List<Utilisateur> findRange(int[] range);
    
    Utilisateur findByNom(String nom);
    
    public Utilisateur findByMail(String mail);
    
    Utilisateur findByMailAndPassword(String mail, String password);
    
    Utilisateur createUser(String nom, String mail, String password, String adresse, String roles);

    int count();
    
}
