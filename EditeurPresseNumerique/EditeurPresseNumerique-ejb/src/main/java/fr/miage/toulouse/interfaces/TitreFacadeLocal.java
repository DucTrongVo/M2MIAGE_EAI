/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Titre;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author trongvo
 */
@Local
public interface TitreFacadeLocal {

    void create(Titre titre);

    void edit(Titre titre);

    void remove(Titre titre);

    Titre find(Object id);

    List<Titre> findAll();

    List<Titre> findRange(int[] range);
    
    List<Titre> findByNom(String nomTitre);
    
    public Titre findByCodeTitre(String codeTitre);
    
    Titre createTitre(String codeTitre, String nomTitre, String description, int rythmSortie);
    
    List<Titre> findAllTitre();
    
//    List<String> getKeywords(Titre titre);
//    
//    List<Titre> findByKeywords(String keywords );

    int count();
    
}
