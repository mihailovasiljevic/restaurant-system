package restaurant.server.restaurant.server.routes;
import restaurant.server.entity.Country;
import restaurant.server.session.CountryDaoLocal;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/CountryService")
public class CountryService {
    @EJB
    private CountryDaoLocal countryDao;
    private static final String SUCCESS_RESULT="<result>success</result>";
    private static final String FAILURE_RESULT="<result>failure</result>";

    @GET
    @Path("/countries")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Country> getCountries(){
        return countryDao.findAll();
    }

    @GET
    @Path("/countries/{countryid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Country getCountry(@PathParam("countryid") int countryid){
        return countryDao.findById(countryid);
    }

    @PUT
    @Path("/countries")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updateCountry(@FormParam("countryName") String name,
                                @Context HttpServletResponse servletResponse) throws IOException{
        Country country = new Country();
        country.setName(name);
        Country result = countryDao.merge(country);
        if(result != null){
            return SUCCESS_RESULT;
        }else
            return FAILURE_RESULT;
    }

    @POST
    @Path("/countries")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String createCountry(@FormParam("countryName") String name,
                                @Context HttpServletResponse servletResponse) throws IOException{
        Country country = new Country();
        country.setName(name);
        Country result = countryDao.persist(country);
        if(result != null){
            return SUCCESS_RESULT;
        }else
            return FAILURE_RESULT;
    }

    @DELETE
    @Path("/countries/{countryid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteCountry(@PathParam("countryid") int countryid){
        Country country = countryDao.findById(countryid);
        if(country != null) {
            boolean result = countryDao.remove(country);
            if(result)
               return SUCCESS_RESULT;
        }

        return FAILURE_RESULT;
    }

    @OPTIONS
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSupportedOperations(){
        return "<operations>GET, PUT, POST, DELETE</operations>";
    }


}
