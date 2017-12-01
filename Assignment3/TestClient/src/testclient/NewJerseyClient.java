/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testclient;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:MyRecource [/]<br>
 * USAGE:
 * <pre>
 *        NewJerseyClient client = new NewJerseyClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Frankfan
 */
public class NewJerseyClient {

    private WebTarget webTarget;
    private Client client;
    private String BASE_URI = "";

    public NewJerseyClient(String url) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        BASE_URI = url + "/SkierServer/webapi";
        webTarget = client.target(BASE_URI);
    }

    public String getIt(String skierId, String dayNum) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (skierId != null) {
            resource = resource.queryParam("skierId", skierId);
        }
        if (dayNum != null) {
            resource = resource.queryParam("dayNum", dayNum);
        }
        resource = resource.path("myvert");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public Response postData(RFIDLiftData record) throws ClientErrorException {
        Response response = webTarget.path("load").request().post(Entity.json(record));
        return response;
    }

    public void close() {
        client.close();
    }
    
}
