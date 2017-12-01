/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testclient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author Frankfan
 */
public class PostTask implements Runnable {

    private String url;
    private int requestCount = 0;
    private int successCount = 0;
    private List<Long> latencies = new ArrayList<>();
    private List<RFIDLiftData> RFIDList;
    
    public PostTask(String url, List<RFIDLiftData> records) {
        this.url = url;
        this.RFIDList = records;
    }

    @Override
    public void run() {
        try {
            NewJerseyClient client = new NewJerseyClient(url);
            for (RFIDLiftData record : RFIDList) {

                long startTime = System.currentTimeMillis();
                Response response = client.postData(record);
                requestCount++;
                if (response.getStatus() == 204) {
                    successCount++;
                }
                long latency = System.currentTimeMillis() - startTime;
                latencies.add(latency);
                System.out.println(latency + " " + successCount);
            }

        } catch (Exception e) {
            Logger.getLogger(PostTask.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public List<Long> getLatencies() {
        return latencies;
    }
    
}
