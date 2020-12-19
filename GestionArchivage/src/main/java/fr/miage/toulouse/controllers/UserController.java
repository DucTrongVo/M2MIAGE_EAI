/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import fr.miage.toulouse.gestiondto.Constants;
import fr.miage.toulouse.gestiondto.UtilisateurDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author trongvo
 */
public class UserController {
    
    private String host = Constants.BASIC_URL;
    private Gson gson;
    private WebTarget webTarget;
    public UserController(Gson gson, WebTarget webTarget){
        this.gson = gson;
        this.webTarget = webTarget;
    }
    
    public UtilisateurDTO loginUser(String mail, String password) {
        try {
            String query = "api/user/login";
            Response response = webTarget.path(query).queryParam("mail", mail).queryParam("password", password).
                    request(MediaType.APPLICATION_JSON).get();
            String entity = response.readEntity(String.class);
            System.out.println("Entity to String : "+entity);
            //UtilisateurDTO userDTO = response.readEntity(UtilisateurDTO.class);
            UtilisateurDTO userDTO = gson.fromJson(entity, UtilisateurDTO.class);
            System.out.println("UserDTO : "+userDTO.toString());
            return userDTO;
        }catch(JsonSyntaxException e){
            System.out.println("Erreur : "+e.toString());
            return null;
        }
    }
    
    public UtilisateurDTO inscription(String nom, String mail, String password, String adresse, String roles){
        try {
            String query = "api/user/create";
            Response response = webTarget.path(query).queryParam("nom", nom).queryParam("mail", mail).queryParam("password", password)
            //UtilisateurDTO response = webTarget.path(query).queryParam("nom", nom).queryParam("mail", mail).queryParam("password", password)
                    .queryParam("adresse", adresse).queryParam("roles", roles).request(MediaType.APPLICATION_JSON)
                    .post(null);
                    //.post(null, UtilisateurDTO.class);
            String entity = response.readEntity(String.class);
            UtilisateurDTO userDTO = gson.fromJson(entity, UtilisateurDTO.class);
            //System.out.println("Response : "+response.toString());
            System.out.println("UserDTO : "+userDTO);
            return userDTO;
        } catch(JsonSyntaxException e){
            System.out.println("Erreur : "+e.toString());
            return null;
        }
    }
}
