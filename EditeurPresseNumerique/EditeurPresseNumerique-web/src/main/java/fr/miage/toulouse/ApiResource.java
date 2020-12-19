/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse;

import com.google.gson.Gson;
import fr.miage.toulouse.entities.Article;
import fr.miage.toulouse.entities.Publicite;
import fr.miage.toulouse.gestiondto.AbonnementDTO;
import fr.miage.toulouse.gestiondto.ArticleDTO;
import fr.miage.toulouse.gestiondto.Constants;
import fr.miage.toulouse.gestiondto.PubliciteDTO;
import fr.miage.toulouse.gestiondto.TitreDTO;
import fr.miage.toulouse.gestiondto.UtilisateurDTO;
import fr.miage.toulouse.gestiondto.VolumeDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;
import fr.miage.toulouse.services.ServicesLocalLocal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author trongvo
 */
@Path("api")
@RequestScoped
public class ApiResource {

    ServicesLocalLocal servicesLocal = lookupServicesLocalLocal();

    //fr.miage.toulouse.services.ServicesBeanLocal1 servicesLocal = lookupServicesBeanLocal();

    //ServicesLocalLocal servicesLocal;
    
    @Context
    private UriInfo context;
   
    private Gson gson;
    /**
     * Creates a new instance of ApiResource
     */
    public ApiResource() {
        this.gson = new Gson();
    }

    /**
     * Retrieves representation of an instance of fr.miage.toulouse.ApiResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ApiResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
    @GET
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHello(){
        return servicesLocal.sayHello("Hello", "World");
    }
    
    @GET
    @Path("/user/login")
    @Consumes(MediaType.APPLICATION_JSON )
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("mail") String mail, @QueryParam("password") String password){
        UtilisateurDTO userDTO = servicesLocal.login(mail,password);
        return Response.created(context.getAbsolutePath()).status(Response.Status.CREATED).entity(userDTO).build();
    }
    
    @POST
    @Path("/user/create")
    @Consumes(MediaType.APPLICATION_JSON )
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@QueryParam("nom") String nom, @QueryParam("mail")String mail, @QueryParam("password")String password, 
            @QueryParam("adresse")String adresse,@QueryParam("roles") String roles){

        UtilisateurDTO userDTO = servicesLocal.createUser(nom, mail, password, adresse, roles);
        return Response.created(context.getAbsolutePath()).status(Response.Status.CREATED).entity(userDTO).build();
    }
    
    @POST
    @Path("/volume/create")
    @Consumes(MediaType.APPLICATION_JSON )
    @Produces(MediaType.APPLICATION_JSON)
    public Response postNewVolume(@QueryParam("codeTitre") String codeTitre, @QueryParam("numVolume") String numVolume, 
                                    @QueryParam("nomVolume") String nomVolume, @QueryParam("listCodeArticles") String listCodeArticles,
                                    @QueryParam("listCodePublicites") String listCodePublicites, @QueryParam("dateTime") String dateTime){
        List<String> codesArticles = Arrays.asList(listCodeArticles.split(";"));
        List<String> codesPubs = Arrays.asList(listCodePublicites.split(";"));
        List<Article> listArticles = new ArrayList<>();
        List<Publicite> listPublicites = new ArrayList<>();
        for(String codeArticle : codesArticles){
            Article article = servicesLocal.findArticleByCode(codeArticle);
            listArticles.add(article);
        }
        
        for(String nomPb : codesPubs){
            Publicite pub = servicesLocal.findPubByNom(nomPb);
            listPublicites.add(pub);
        }
        
        // dateTimeLocal = LocalDate.parse(dateTime, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
        VolumeDTO volumeDto = servicesLocal.createVolume(codeTitre, numVolume, nomVolume, listArticles, listPublicites, dateTime);
        
        return Response.created(context.getAbsolutePath()).status(Response.Status.CREATED).entity(volumeDto).build();
    }
    
    @POST
    @Path("/article/create")
    @Consumes(MediaType.APPLICATION_JSON )
    @Produces(MediaType.APPLICATION_JSON)
    public Response postNewArticle(@QueryParam("nameArticle") String nameArticle, @QueryParam("codeArticle") String codeArticle, 
                                    @QueryParam("nameAuthor") String nameAuthor, @QueryParam("keywords") String keywords, 
                                    @QueryParam("content") String content, @QueryParam("date") String date){
        List<String> keys = Arrays.asList(keywords.split(";"));
        ArticleDTO articleDTO = servicesLocal.createArticle(nameArticle, codeArticle, nameAuthor, keys, content, date);
        return Response.created(context.getAbsolutePath()).status(Response.Status.CREATED).entity(articleDTO).build();
    }
    
    @POST
    @Path("/publicite/create")
    @Consumes(MediaType.APPLICATION_JSON )
    @Produces(MediaType.APPLICATION_JSON)
    public Response postNewPublicite(@QueryParam("nomPub") String nomPub, @QueryParam("descriptions") String descriptions){
        
        PubliciteDTO pubDTO = servicesLocal.createPublicite(nomPub, descriptions);
        return Response.created(context.getAbsolutePath()).status(Response.Status.CREATED).entity(pubDTO).build();
    }
    
    @POST
    @Path("/titre/create")
    @Consumes(MediaType.APPLICATION_JSON )
    @Produces(MediaType.APPLICATION_JSON)
    public Response postNewTitre(@QueryParam("codeTitre") String codeTitre, @QueryParam("nomTitre") String nomTitre, 
                                @QueryParam("description") String description, @QueryParam("rythmSortie") int rythmSortie){
        TitreDTO titreSTO = servicesLocal.createTitre(codeTitre, nomTitre, description, rythmSortie);
        return Response.created(context.getAbsolutePath()).status(Response.Status.CREATED).entity(titreSTO).build();
    }
    
    @POST
    @Path("/abonnement/create")
    @Consumes(MediaType.APPLICATION_JSON )
    @Produces(MediaType.APPLICATION_JSON)
    public Response abonnerUnTitre(@QueryParam("idUtilisateur") String idUtilisateur, @QueryParam("codeTitre") String codeTitre, 
                                     @QueryParam("numberOfCopies") String numberOfCopies, @QueryParam("durationInWeeks") String durationInWeeks){
        UtilisateurDTO userDTO = servicesLocal.findUsereByMail(idUtilisateur);
        TitreDTO titreDTO = servicesLocal.findTitreByCode(codeTitre);
        AbonnementDTO abonnementDTO = servicesLocal.abonnerUnTitre(userDTO, titreDTO, Integer.parseInt(numberOfCopies), Integer.parseInt(durationInWeeks));
        if(abonnementDTO == null){
            return Response.created(context.getAbsolutePath()).status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }else{
            return Response.created(context.getAbsolutePath()).status(Response.Status.CREATED).entity(abonnementDTO).build();
        }
        
    }
    
    @GET
    @Path("/abonnements")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAbonnements(){
        List<AbonnementDTO> abonsDto = servicesLocal.findAllAbonnements();
        if(abonsDto == null){
            return Response.created(context.getAbsolutePath()).status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }else{
            GenericEntity<List<AbonnementDTO>> listResult = new GenericEntity<List<AbonnementDTO>>(abonsDto){};
            return Response.ok(listResult).build();
        }
    }
    
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(){
        List<UtilisateurDTO> usersDto = servicesLocal.findAllUsers();
        if(usersDto == null){
            return Response.created(context.getAbsolutePath()).status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }else{
            GenericEntity<List<UtilisateurDTO>> listResult = new GenericEntity<List<UtilisateurDTO>>(usersDto){};
            return Response.ok(listResult).build();
        }
    }
    
    @GET
    @Path("search/titres/nom/{nomTitre}")
    @Consumes(MediaType.APPLICATION_JSON )
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTitreByNom (@PathParam("nomTitre") String nomTitre){
        List<TitreDTO> titresDto = servicesLocal.findTitresByNom(nomTitre);
        if(titresDto == null){
            return Response.created(context.getAbsolutePath()).status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }else{
            GenericEntity<List<TitreDTO>> listResult = new GenericEntity<List<TitreDTO>>(titresDto){};
            return Response.ok(listResult).build();
        }
    }
    
    @GET
    @Path("search/titres/keywords/{keywords}")
    @Consumes(MediaType.APPLICATION_JSON )
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTitreByKeywords (@PathParam("keywords") String keywords){
        List<TitreDTO> titresDto = servicesLocal.findTitresByKeywords(keywords);
        if(titresDto == null){
            return Response.created(context.getAbsolutePath()).status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }else{
            GenericEntity<List<TitreDTO>> listResult = new GenericEntity<List<TitreDTO>>(titresDto){};
            return Response.ok(listResult).build();
        }
    }
    
    @GET
    @Path("search/titres/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllTitres (){
        List<TitreDTO> titresDto = servicesLocal.findAllTitres();
        if(titresDto == null){
            return Response.created(context.getAbsolutePath()).status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
        }else{
            GenericEntity<List<TitreDTO>> listResult = new GenericEntity<List<TitreDTO>>(titresDto){};
            return Response.ok(listResult).build();
            //return Response.created(context.getAbsolutePath()).status(Response.Status.INTERNAL_SERVER_ERROR).entity(listResult).build();
        }
    }
//    private fr.miage.toulouse.services.ServicesLocalLocal lookupServicesBeanLocal() {
//        try {
//            javax.naming.Context c = new InitialContext();
//            return (fr.miage.toulouse.services.ServicesLocalLocal) c.lookup("java:global/EditeurPresseNumerique-ear/EditeurPresseNumerique-ejb-1.0/ServicesBean");
//            //return (fr.miage.toulouse.services.ServicesBeanLocal1) c.lookup("java:global/EditeurPresseNumerique-ear/EditeurPresseNumerique-ejb-1.0/ServicesBean");
//        } catch (NamingException ne) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//            throw new RuntimeException(ne);
//        }
//    }

    private ServicesLocalLocal lookupServicesLocalLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ServicesLocalLocal) c.lookup("java:global/EditeurPresseNumerique-ear/EditeurPresseNumerique-ejb-1.0/ServicesLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
