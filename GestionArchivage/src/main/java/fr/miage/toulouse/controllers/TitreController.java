/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import fr.miage.toulouse.gestiondto.TitreDTO;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author trongvo
 */
public class TitreController {
    private Gson gson;
    private WebTarget webTarget;
    public TitreController(Gson gson, WebTarget webTarget){
        this.gson = gson;
        this.webTarget = webTarget;
    }
    
    
    public TitreDTO createTitre(String codeTitre, String nomTitre, String description, String rythmSortie){
        try{
            String query = "api/titre/create";
            Response response = webTarget.path(query).queryParam("codeTitre", codeTitre).queryParam("nomTitre", nomTitre)
                    .queryParam("description", description).queryParam("rythmSortie", rythmSortie)
                    .request(MediaType.APPLICATION_JSON).post(null);
            String entity = response.readEntity(String.class);
            TitreDTO titreDto = gson.fromJson(entity, TitreDTO.class);
            return titreDto;
        }catch(JsonSyntaxException e){
            System.out.println("Erreur : "+e.toString());
            return null;
        }
    }
    
    public List<TitreDTO> findAllTitres(){
        try{
            String query = "api/search/titres/all";
            List<TitreDTO> response = webTarget.path(query).request(MediaType.APPLICATION_JSON).get(new GenericType<List<TitreDTO>>(){});
            System.out.println("Titre size :  "+response.size());
            System.out.println("Titre liste :  "+response.get(0));
            return response;
        }catch(Exception e){
            System.out.println("Erreur : "+e.toString());
            return null;
        }
    }
    public List<TitreDTO> findTitreByNom(String nomTitre){
        try{
            String query = "api/search/titres/nom/"+nomTitre;
            List<TitreDTO> response = webTarget.path(query).request(MediaType.APPLICATION_JSON).get(new GenericType<List<TitreDTO>>(){});
            return response;
        }catch(Exception e){
            System.out.println("Erreur : "+e.toString());
            return null;
        }
    }
    
    public List<TitreDTO> findTitreByKeywords(String keywords){
        try{
            String query = "api/search/titres/keywords/"+keywords;
            List<TitreDTO> response = webTarget.path(query).request(MediaType.APPLICATION_JSON).get(new GenericType<List<TitreDTO>>() {});
            return response;
        }catch(Exception e){
            System.out.println("Erreur : "+e.toString());
            return null;
        }
    }
}
