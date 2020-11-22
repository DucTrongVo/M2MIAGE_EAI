/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.services;

import fr.miage.toulouse.entities.Article;
import fr.miage.toulouse.entities.Publicite;
import fr.miage.toulouse.gestiondto.VolumeDTO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author trongvo
 */
@Local
public interface ServicesBeanLocal {
    
    public VolumeDTO createVolume(String codeTitre, String numVolume, String nomVolume, List<Article> listArticles, List<Publicite> listPublicites);
    
    public VolumeDTO findVolumeByNumber(String numVolume);
    
    public VolumeDTO findVolumeByCodeTitre(String codeTitre);
    
    public String sayHello(String nom, String des);
}
