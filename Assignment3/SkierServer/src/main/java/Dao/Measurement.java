/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Latency;
import model.RFIDLiftData;

/**
 *
 * @author Frankfan
 */
public class Measurement {
    private static final int POST_REQUESTS = 200000;
    private static final int GET_REQUESTS = 40000;
    
    public static void main(String[] args) throws SQLException {
//        RFIDLiftData rfid = new RFIDLiftData(5,3,1,21,1);
//        List<RFIDLiftData> list = new ArrayList<>();
//        list.add(rfid);
//        RFIDLiftDao.insertAll(list);
//        String test = RFIDLiftDao.getSkierData(3,1);
//        System.out.println(test);
//        RFIDLiftDao.updateHistory(list); 

//        Latency latency = new Latency(1234567, "get", 1);
//        List<Latency> list = new ArrayList<>();
//        list.add(latency);
//        LatencyDao.insertLatency(list);

        List<Long> latencyList = new ArrayList<>();
        List<Latency> server1_post = LatencyDao.getLatencies("post", 1);
        List<Latency> server2_post = LatencyDao.getLatencies("post", 2);
        List<Latency> server3_post = LatencyDao.getLatencies("post", 3);
        List<Latency> server1_get = LatencyDao.getLatencies("get", 1);
        List<Latency> server2_get = LatencyDao.getLatencies("get", 2);
        List<Latency> server3_get = LatencyDao.getLatencies("get", 3);
        
        List<Latency> allPost = new ArrayList<>();
        allPost.addAll(server1_post);
        allPost.addAll(server2_post);
        allPost.addAll(server3_post);
        
        List<Latency> allGet = new ArrayList<>();
        allGet.addAll(server1_get);
        allGet.addAll(server2_get);
        allGet.addAll(server3_get);
        
        for (Latency l : server3_get) {
            latencyList.add(l.getLatency());
        }
//        calculation(latencyList, GET_REQUESTS);
        calculationOne(latencyList, 3);
        
    }
    
    public static void calculation(List<Long> latencyList, int requests) {
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
        System.out.println("Total number of request sent: " + requests);
        System.out.println("Total number of successful responses: " + count); 
        System.out.println("The Number of error is: " + (requests - count)); 
        System.out.println("99th percentile latency is: " + ninetyNineLatency + " mircro second");
        System.out.println("95th percentile latency is: " + ninetyFiveLatency + " mircro second");
        System.out.println("Median latency is: " + medianLatency + " mircro second");
        System.out.println("Mean Latency is: " + df.format(meanResult) + " mircro second");      
        System.out.println();
    }
    
    public static void calculationOne(List<Long> latencyList, int id) {
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
        System.out.println("The statictis of server " + id + " is: ");
        System.out.println("Total number of successful responses: " + count); 
        System.out.println("99th percentile latency is: " + ninetyNineLatency + " mircro second");
        System.out.println("95th percentile latency is: " + ninetyFiveLatency + " mircro second");
        System.out.println("Median latency is: " + medianLatency + " mircro second");
        System.out.println("Mean Latency is: " + df.format(meanResult) + " mircro second");    
        System.out.println();
    }
    
}
