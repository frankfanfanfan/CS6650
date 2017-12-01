/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import static testclient.PostMultiThread.divideList;

/**
 *
 * @author Frankfan
 */
public class TestClient {
    
    private static final String CLOUD = "34.215.68.92";
    private static final String LOCAL = "127.0.0.1";
    private static final String LOAD_BALANCER = "skierlb-282770536.us-west-2.elb.amazonaws.com";
    private static final String PORT = "8080";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String url = "http://" + LOCAL + ":" + PORT;
        NewJerseyClient client = new NewJerseyClient(url);
        System.out.println(client.getIt("3", "999"));
//        client.postData(record);
//        RFIDLiftData record = new RFIDLiftData(1,2,3,4,5);
//        Response res = client.postData(record);
//        System.out.println(res.getStatus());
    }
    
}
