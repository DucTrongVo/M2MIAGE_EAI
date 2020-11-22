/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Abonnement;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author trongvo
 */
@Local
public interface AbonnementFacadeLocal {

    void create(Abonnement abonnement);

    void edit(Abonnement abonnement);

    void remove(Abonnement abonnement);

    Abonnement find(Object id);

    List<Abonnement> findAll();

    List<Abonnement> findRange(int[] range);
    
    Abonnement findExistedAbonnement(Long idUtilisateur, Long idTitre, int numberOfCopies, int durationInWeeks);
    
    Abonnement createAbonnement(Long idUtilisateur, Long idTitre, int numberOfCopies, int durationInWeeks);

    int count();
    
}
