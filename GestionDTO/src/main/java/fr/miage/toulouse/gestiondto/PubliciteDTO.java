/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.gestiondto;

/**
 *
 * @author trongvo
 */
public class PubliciteDTO {
    private Long id;
    private String nomPub;
    private String descriptionPub;

    public PubliciteDTO(){};
    public PubliciteDTO(String nomPub, String descriptionPub) {
        this.nomPub = nomPub;
        this.descriptionPub = descriptionPub;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
}
