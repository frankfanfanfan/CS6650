/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testclient;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;

/**
 *
 * @author Frankfan
 */
public class GetTask implements Runnable {
    private String url;
    private int requestCount = 0;
    private int successCount = 0;
    private List<Long> latencies = new ArrayList<>();
    private final int LOOP = 400;
    
    public GetTask(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            NewJerseyClient client = new NewJerseyClient(url);
            for (int i = 0; i < LOOP; i++) {
                long startTime = System.currentTimeMillis();
                String result = client.getIt("3", "5");
                requestCount++;
                if (result != null) {
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
