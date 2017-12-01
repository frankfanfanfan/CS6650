/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:MyResource [myresource]<br>
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
    private static String BASE_URI = "";
//    private static String BASE_URI = "http://184.72.123.61:8080/mavenproject/webapi";

    public NewJerseyClient(String ip, String port) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        BASE_URI = "http://" + ip + ":" + port + "/mavenproject/webapi";
        webTarget = client.target(BASE_URI).path("myresource");
    }

    public String postText(Object requestEntity) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.TEXT_PLAIN), String.class);
    }

    public String getIt() throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public void close() {
        client.close();
    }
}
