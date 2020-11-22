/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse;

import fr.miage.toulouse.services.ServicesBean;
import fr.miage.toulouse.services.ServicesBeanLocal;
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

/**
 * REST Web Service
 *
 * @author trongvo
 */
@Path("api")
@RequestScoped
public class ApiResource {

    //fr.miage.toulouse.services.ServicesBeanLocal servicesBean = lookupServicesBeanLocal();

    ServicesBeanLocal servicesBean;
    
    @Context
    private UriInfo context;
   

    /**
     * Creates a new instance of ApiResource
     */
    public ApiResource() {
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
        return servicesBean.sayHello("Hello", "World");
    }

    private fr.miage.toulouse.services.ServicesBeanLocal lookupServicesBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (fr.miage.toulouse.services.ServicesBeanLocal) c.lookup("java:global/EditeurPresseNumerique-ear/EditeurPresseNumerique-ejb-1.0/ServicesBean");
            //return (fr.miage.toulouse.services.ServicesBeanLocal) c.lookup("java:global/EditeurPresseNumerique-ear/EditeurPresseNumerique-ejb-1.0/ServicesBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
