/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Article;
import fr.miage.toulouse.entities.Publicite;
import fr.miage.toulouse.entities.Volume;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author trongvo
 */
@Local
public interface VolumeFacadeLocal {

    void create(Volume volume);

    void edit(Volume volume);

    void remove(Volume volume);

    Volume find(Object id);

    List<Volume> findAll();
    
    List<Volume> findAllVolume();

    List<Volume> findRange(int[] range);
    
    Volume createVolume(String codeTitre, String numVolume, String nomVolume, List<Article> listArticles, List<Publicite> listPublicites, String dateTime);
    
    //List<Volume> findVolumeByCodeTitre(String codeTitre);
    
    Volume findVolumeByNumber(String numVolume, String codeTitre);

    int count();
    
}
