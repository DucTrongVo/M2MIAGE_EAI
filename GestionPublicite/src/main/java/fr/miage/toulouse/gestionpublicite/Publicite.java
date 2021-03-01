/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.gestionpublicite;

import java.io.Serializable;

/**
 *
 * @author trongvo
 */
public class Publicite implements Serializable{
    private String nomPub;
    private String descriptionPub;

    public Publicite(String nomPub, String descriptionPub) {
        this.nomPub = nomPub;
        this.descriptionPub = descriptionPub;
    }

    public String getNomPub() {
        return nomPub;
    }

    public void setNomPub(String nomPub) {
        this.nomPub = nomPub;
    }

    public String getDescriptionPub() {
        return descriptionPub;
    }

    public void setDescriptionPub(String descriptionPub) {
        this.descriptionPub = descriptionPub;
    }
    
    @Override
    public String toString(){
        return "Nom du pub : "+nomPub+" - Description : "+descriptionPub;
    }
}
