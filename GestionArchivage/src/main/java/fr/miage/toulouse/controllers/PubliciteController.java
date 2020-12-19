/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.controllers;

import com.google.gson.Gson;
import fr.miage.toulouse.gestiondto.Constants;
import fr.miage.toulouse.gestiondto.PubliciteDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author trongvo
 */
public class PubliciteController {
    private String host = Constants.BASIC_URL;
    private Gson gson;
    private WebTarget webTarget;
    
    public PubliciteController(Gson gson, WebTarget webTarget){
        this.gson = gson;
        this.webTarget = webTarget;
    }
    
    public PubliciteDTO createPub(String nomPub, String descriptions){
        try{
            String query = "api/publicite/create";
            Response response = webTarget.path(query).queryParam("nomPub", nomPub).queryParam("descriptions", descriptions)
                    .request(MediaType.APPLICATION_JSON).post(null);
            String entity = response.readEntity(String.class);
            PubliciteDTO pubDto = gson.fromJson(entity, PubliciteDTO.class);
            System.out.println("Publicite created : "+pubDto.toString());
            return pubDto;
        }catch(Exception e){
            System.out.println("Erreur : "+e.toString());
            return null;
        }
        
    }
}
