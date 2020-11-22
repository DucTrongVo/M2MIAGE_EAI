/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Publicite;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author trongvo
 */
@Local
public interface PubliciteFacadeLocal {

    void create(Publicite publicite);

    void edit(Publicite publicite);

    void remove(Publicite publicite);

    Publicite find(Object id);

    List<Publicite> findAll();

    List<Publicite> findRange(int[] range);
    
    Publicite findByNomPub(String nomPub);
    
    Publicite createPub(String nomPub, String descriptions);

    int count();
    
}
