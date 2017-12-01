/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testclient;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Frankfan
 */
public class LatencyCounter {
    private List<Long> latencyList;
    
    public LatencyCounter(List<Long> latencyList) {
        this.latencyList = latencyList;
    }
    
    public void calculation() {
        Collections.sort(latencyList);
        int count = latencyList.size();
        DecimalFormat df = new DecimalFormat("#.0");
        
        long medianLatency = latencyList.get(count / 2);
        long ninetyNineLatency = latencyList.get(count / 100 * 99);
        long ninetyFiveLatency = latencyList.get(count / 100 * 95);
        
        long meanLatency = 0;
        for (long latency : latencyList) {
            meanLatency += latency;
        }
        
        double meanResult = (double)meanLatency / (double)count;

        System.out.println("99th percentile latency is: " + ninetyNineLatency + " ms");
        System.out.println("95th percentile latency is: " + ninetyFiveLatency + " ms");
        System.out.println("Median latency is: " + medianLatency + " ms");
        System.out.println("Mean Latency is: " + df.format(meanResult) + " ms");
    }
    
}
