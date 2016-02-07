package restaurant.server.restaurant.server.routes;

import restaurant.server.entity.Country;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class CountryTest {
    private Client client;
    private String REST_SERVICE_URL = "http://localhost:8080/restaurant/api/CountryService/countries";
    private static final String SUCCESS_RESULT="<result>success</result>";
    private static final String PASS = "pass";
    private static final String FAIL = "fail";

    private void init(){
        this.client = ClientBuilder.newClient();
    }

    public static void main(String[] args){
        CountryTest tester = new CountryTest();
        //initialize the tester
        tester.init();
        //test get all users Web Service Method
        tester.testGetAllCountries();
        //test get user Web Service Method
        tester.testGetCountry();
        //test update user Web Service Method
        tester.testUpdateCountry();
        //test add user Web Service Method
        tester.testAddCountry();
        //test delete user Web Service Method
        tester.testDeleteCountry();
    }
    //Test: Get list of all users
    //Test: Check if list is not empty
    private void testGetAllCountries(){
        GenericType<List<Country>> list = new GenericType<List<Country>>() {};
        List<Country> countries = client
                .target(REST_SERVICE_URL)
                .request(MediaType.APPLICATION_JSON)
                .get(list);
        String result = PASS;
        if(countries.isEmpty()){
            result = FAIL;
        }
        System.out.println("Test case name: testGetAllCountries, Result: " + result );
    }
    //Test: Get User of id 1
    //Test: Check if user is same as sample user
    private void testGetCountry(){
        Country sampleCountry = new Country();
        sampleCountry.setId(1);

        Country country = client
                .target(REST_SERVICE_URL)
                .path("/{countryid}")
                .resolveTemplate("countryid", 1)
                .request(MediaType.APPLICATION_JSON)
                .get(Country.class);
        String result = FAIL;
        if(sampleCountry != null && sampleCountry.getId() == country.getId()){
            result = PASS;
        }
        System.out.println("Test case name: testGetCountry, Result: " + result );
    }
    //Test: Update User of id 1
    //Test: Check if result is success XML.
    private void testUpdateCountry(){
        Form form = new Form();
        form.param("countryName", "Zemlja");

        String callResult = client
                .target(REST_SERVICE_URL)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form,
                        MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                        String.class);
        String result = PASS;
        if(!SUCCESS_RESULT.equals(callResult)){
            result = FAIL;
        }

        System.out.println("Test case name: testUpdateCountry, Result: " + result );
    }
    //Test: Add User of id 2
    //Test: Check if result is success XML.
    private void testAddCountry(){
        Form form = new Form();
        form.param("countryName", "Dodata zemlja");

        String callResult = client
                .target(REST_SERVICE_URL)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(form,
                        MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                        String.class);

        String result = PASS;
        if(!SUCCESS_RESULT.equals(callResult)){
            result = FAIL;
        }

        System.out.println("Test case name: testAddCountry, Result: " + result );
    }
    //Test: Delete User of id 2
    //Test: Check if result is success XML.
    private void testDeleteCountry(){
        String callResult = client
                .target(REST_SERVICE_URL)
                .path("/{countryid}")
                .resolveTemplate("countryid", 2)
                .request(MediaType.APPLICATION_JSON)
                .delete(String.class);

        String result = PASS;
        if(!SUCCESS_RESULT.equals(callResult)){
            result = FAIL;
        }

        System.out.println("Test case name: testDeleteCountry, Result: " + result );
    }
}
