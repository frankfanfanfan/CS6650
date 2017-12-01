/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frankfan
 */
public class ClientTest {
    public static void main(String[] args) {

//        NewJerseyClient client = new NewJerseyClient("184.72.123.61", "8080");
//        System.out.println(client.postText("Frankhahaha"));
        threadMeasurement(10, 100, "35.163.150.55", "8080");
    }
    
    public static List<Task> threadProcess(int threads, int iterations, String ip, String port) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
        List<Task> taskList = new ArrayList<>();
        
        for (int i = 0; i < threads; i++) {
            Task task = new Task(ip, port, iterations, i);
            executor.submit(task);
            taskList.add(task);
        }
        
        executor.shutdown();
        
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return taskList;
    }
    
    public static void multiThread(int threads, int iterations, String ip, String port) {
        
        int count = 0;
        
        long start = System.currentTimeMillis();
	System.out.println("Client starting..... Time: " + start);
        System.out.println("All threads running......");
        
        List<Task> taskList = threadProcess(threads, iterations, ip, port);
        
        long end = System.currentTimeMillis();
        System.out.println("All threads complete..... Time: " + end);
        
        for (Task t : taskList) {
            count += t.getCount();
        }
        
        System.out.println("Total number of request sent: " + iterations * threads * 2);
        System.out.println("Total number of successful responses: " + count);
        
        double wallTime = (double)(end - start) / (double)1000;
        DecimalFormat df = new DecimalFormat("#.0");
        System.out.println("Test Wall Time: " + df.format(wallTime) + " seconds");
    }
           
    public static void threadMeasurement(int threads, int iterations, String ip, String port) {
        long start = System.currentTimeMillis();
        List<Long> requestTimeList = new ArrayList<>();
        System.out.println("All threads running......");
        
        List<Task> taskList = threadProcess(threads, iterations, ip, port);
        
        long end = System.currentTimeMillis();
        
        double wallTime = (double)(end - start) / (double)1000;
        DecimalFormat df = new DecimalFormat("#.0");
        System.out.println("Test Wall Time: " + df.format(wallTime) + " seconds");
        
        int count = 0;
        for (Task t : taskList) {
            count += t.getCount();
            requestTimeList.addAll(t.getGetTimeList());
            requestTimeList.addAll(t.getPostTimeList());
        }
        
        Collections.sort(requestTimeList);
        long medianLatency = requestTimeList.get(count / 2);
        long ninetyNineLatency = requestTimeList.get(count / 100 * 99);
        long ninetyFiveLatency = requestTimeList.get(count / 100 * 95);
        
        long meanLatency = 0;
        for (long rTime : requestTimeList) {
            meanLatency += rTime;
        }
        
        double meanResult = (double)meanLatency / (double)count;
        
        
        System.out.println("Total number of request sent: " + iterations * threads * 2);
        System.out.println("Total number of successful responses: " + count);
        
        System.out.println("99th percentile latency is: " + ninetyNineLatency);
        System.out.println("95th percentile latency is: " + ninetyFiveLatency);
        System.out.println("Median latency is: " + medianLatency);
        System.out.println("Mean Latency is: " + df.format(meanResult));
    }
    
}
