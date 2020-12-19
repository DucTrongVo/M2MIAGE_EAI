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
public class UtilisateurDTO {
    private Long id;
    private String nom;
    private String mail;
    private String password;
    private String adresse;
    private String roles;
    
    public UtilisateurDTO(){};
    public UtilisateurDTO(String nom, String mail, String password, String adresse, String roles) {
        this.nom = nom;
        this.mail = mail;
        this.password = password;
        this.adresse = adresse;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
    
    @Override
    public String toString() {
        return "Utilisateur : id=" + id + " - Nom : "+nom+" - Email : "+mail+" - Adresses : "+adresse+" - Roles : "+roles;
    }
}
