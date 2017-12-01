/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testclient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frankfan
 */
public class PostMultiThread {
    
    private static final int POST_THREADS = 100;
    private static final String CLOUD = "34.215.68.92";
    private static final String LOCAL = "127.0.0.1";
    private static final String LOAD_BALANCER = "skierlb-282770536.us-west-2.elb.amazonaws.com";
    private static final String PORT = "8080";
    
    public static void main(String[] args) {
        
        String url = "http://" + LOAD_BALANCER + ":" + PORT;
        
        CSVReader csvReader = new CSVReader();
        List<RFIDLiftData> RFIDList = csvReader.getLiftList();
//        RFIDList = RFIDList.subList(0, 10000);
        
        multiThreadProcess(url, RFIDList);
    }
    
    public static void multiThreadProcess(String url, List<RFIDLiftData> RFIDList) {
        List<List<RFIDLiftData>> listsOfRecords = divideList(RFIDList, POST_THREADS);
        
        long start = System.currentTimeMillis();
        System.out.println("Client starting..... Time: " + start);
        System.out.println("All threads running......");
        
        List<PostTask> taskList = new ArrayList<>();
        
        ExecutorService executorService = Executors.newFixedThreadPool(POST_THREADS);
        
        for (int i = 0; i < POST_THREADS; i++) {
            PostTask task = new PostTask(url, listsOfRecords.get(i));
            taskList.add(task);
            executorService.submit(task);
        }
        
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            Logger.getLogger(PostMultiThread.class.getName()).log(Level.SEVERE, null , e);
        }
        
        long end = System.currentTimeMillis();
        System.out.println("All threads complete..... Time: " + end);
        
        double wallTime = (double)(end - start) / (double)1000;
        DecimalFormat df = new DecimalFormat("#.0");
        System.out.println("Test Wall Time: " + df.format(wallTime) + " seconds");
        
        dataStatistics(taskList);
    }
    
    public static void dataStatistics(List<PostTask> taskList) {
        List<Long> latencies = new ArrayList<>();
        int requestCount = 0;
        int successCount = 0;
        
        for (PostTask t : taskList) {
            requestCount += t.getRequestCount();
            successCount += t.getSuccessCount();
            latencies.addAll(t.getLatencies());
        }
        System.out.println("Total number of post request sent: " + requestCount);
        System.out.println("Total number of successful post responses: " + successCount);
        
        LatencyCounter counter = new LatencyCounter(latencies);
        counter.calculation();
    }
    
    public static List<List<RFIDLiftData>> divideList (List<RFIDLiftData> RFIDList, int number) {
        List<List<RFIDLiftData>> result = new ArrayList<List<RFIDLiftData>>();
        if (number == 0) { return result; }
        int size = RFIDList.size();
        
        for (int i = 0; i < number; i++) {
            List<RFIDLiftData> cur = new ArrayList<>();
            result.add(cur);
        }
        for (int i = 0; i < size; i++) {
            int num = i % number;
            result.get(num).add(RFIDList.get(i));
        }
        return result;
    }    
}
