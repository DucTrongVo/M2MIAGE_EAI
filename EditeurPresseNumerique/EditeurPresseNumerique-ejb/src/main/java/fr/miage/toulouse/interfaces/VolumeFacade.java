/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Volume;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author trongvo
 */
@Stateless
public class VolumeFacade extends AbstractFacade<Volume> implements VolumeFacadeLocal {

    @EJB
    VolumeFacadeLocal volumeFacadeLocal;
    
    @PersistenceContext(unitName = "EditeurPresseNumeriquePesistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VolumeFacade() {
        super(Volume.class);
    }

    @Override
    public Volume findVolumeByNumber(String numVolume) {
        List<Volume> listVolumes = volumeFacadeLocal.findAll();
        for(Volume volume : listVolumes){
            if(volume.getNumVolume().equals(numVolume)){
                return volume;
            }
        }
        return null;
    }

    @Override
    public List<Volume> findVolumeByCodeTitre(String codeTitre) {
        List<Volume> listVolume = volumeFacadeLocal.findAll();
        List<Volume> listVolumeFound = new ArrayList<>();
        
        for(Volume volume : listVolume){
            if(volume.getCodeTitre().equals(codeTitre)){
                listVolumeFound.add(volume);
            }
        }
        return listVolumeFound;
    }
    
}
