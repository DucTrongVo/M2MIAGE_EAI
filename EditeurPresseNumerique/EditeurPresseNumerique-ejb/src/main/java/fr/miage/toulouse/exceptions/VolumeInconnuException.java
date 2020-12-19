/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.exceptions;

import fr.miage.toulouse.gestiondto.Constants;

/**
 *
 * @author trongvo
 */
public class VolumeInconnuException extends Exception{
    public VolumeInconnuException(){
        System.out.println(Constants.UNKNOWN_VOLUME);
    };
    
    
}
