/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.controllers;

import com.google.gson.Gson;
import fr.miage.toulouse.gestiondto.VolumeDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author trongvo
 */
public class VolumeController {
    
    private Gson gson;
    private WebTarget webTarget;
    public VolumeController(Gson gson, WebTarget webTarget){
        this.gson = gson;
        this.webTarget = webTarget;
    }
    
    public VolumeDTO postNewVolume(String codeTitre, String numVolume, String nomVolume, String listCodeArticles, String listCodePublicites, String dateTime){
        try{
            String query = "api/volume/create";
            Response response = webTarget.path(query).queryParam("codeTitre", codeTitre).queryParam("numVolume", numVolume)
                    .queryParam("nomVolume", nomVolume).queryParam("listCodeArticles", listCodeArticles)
                    .queryParam("listCodePublicites", listCodePublicites).queryParam("dateTime", dateTime)
                    .request(MediaType.APPLICATION_JSON).post(null);
            String entity = response.readEntity(String.class);
            VolumeDTO volumeDto = gson.fromJson(entity, VolumeDTO.class);
            System.out.println("Volume created : "+volumeDto.toString());
            return volumeDto;
        }catch(Exception e){
            System.out.println("Erreur : "+e.toString());
            return null;
        }
    }
}
