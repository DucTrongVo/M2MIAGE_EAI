/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import fr.miage.toulouse.gestiondto.ArticleDTO;
import fr.miage.toulouse.gestiondto.Constants;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author trongvo
 */
public class ArticleController {
    private String host = Constants.BASIC_URL;
    private Gson gson;
    private WebTarget webTarget;
    
    public ArticleController(Gson gson, WebTarget webTarget){
        this.gson = gson;
        this.webTarget = webTarget;
    }
    
    public ArticleDTO createArticle(String nameArticle, String codeArticle, String nameAuthor, String keywords, String content, String date){
        try{
            //ArticleDTO a = new ArticleDTO(nameArticle, codeArticle, nameAuthor, Arrays.asList(keywords.split(";")), content);
            //System.out.println("Article a : "+a.toString());
            System.out.println("date in controller : "+date);
            String query = "api/article/create";
            Response response = webTarget.path(query).queryParam("nameArticle", nameArticle).queryParam("codeArticle", codeArticle).queryParam("nameAuthor", nameAuthor).
            //ArticleDTO response = webTarget.path(query).queryParam("nameArticle", nameArticle).queryParam("codeArticle", codeArticle).queryParam("nameAuthor", nameAuthor).
                    queryParam("keywords", keywords).queryParam("content", content).queryParam("date", date)
                    .request(MediaType.APPLICATION_JSON).post(null);
                    //request(MediaType.APPLICATION_JSON).post(null, ArticleDTO.class);
            String entity = response.readEntity(String.class);
            System.out.println("Entity "+entity);
            ArticleDTO articleDto = gson.fromJson(entity, ArticleDTO.class);
            System.out.println("Article created : "+articleDto.toString());
            return null;
        }catch(JsonSyntaxException e){
            System.out.println("Erreur : "+e.toString());
            return null;
        }
    }
}
