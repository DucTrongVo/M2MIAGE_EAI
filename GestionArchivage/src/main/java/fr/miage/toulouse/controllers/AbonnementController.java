/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.controllers;

import com.google.gson.Gson;
import fr.miage.toulouse.gestiondto.AbonnementDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author trongvo
 */
public class AbonnementController {
    private Gson gson;
    private WebTarget webTarget;
    public AbonnementController(Gson gson, WebTarget webTarget){
        this.gson = gson;
        this.webTarget = webTarget;
    }
    
    public AbonnementDTO createAbonnement(String idUtilisateur, String codeTitre, String numberOfCopies, String durationInWeeks){
        try{
            String query = "api/abonnement/create";
            Response response = webTarget.path(query).queryParam("idUtilisateur", idUtilisateur).queryParam("codeTitre", codeTitre)
                    .queryParam("numberOfCopies", numberOfCopies).queryParam("durationInWeeks", durationInWeeks)
                    .request(MediaType.APPLICATION_JSON).post(null);
            String entity = response.readEntity(String.class);
            AbonnementDTO abonDto = gson.fromJson(entity, AbonnementDTO.class);
            return abonDto;
        }catch(Exception e){
            System.out.println("Erreur : "+e.toString());
            return null;
        }
    }
}
