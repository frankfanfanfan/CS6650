/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Frankfan
 */
public class Task implements Runnable {
    
    private String ip;
    private String port;
    private int iterations;
    private int id;
    private int count = 0;
    private List<Long> getTimeList = new ArrayList<>();
    private List<Long> postTimeList = new ArrayList<>();
    
    public Task(String ip, String port, int iterations, int id) {
        this.ip = ip;
        this.port = port;
        this.iterations = iterations;
        this.id = id;
    }
    public void run() {
        NewJerseyClient client = new NewJerseyClient(ip, port);
        for (int i = 0; i < iterations; i++) {
//            System.out.println("Thread " + id + ". Starting: " + i);
            long start = System.currentTimeMillis();
            String getResult = client.getIt();
            long end = System.currentTimeMillis();
            getTimeList.add(end - start);
            count++;
//            System.out.println("Thread " + id + ". Completed: " + i);
        }
        for (int i = 0; i < iterations; i++) {
            long start = System.currentTimeMillis();
            String postResult = client.postText(id);
            long end = System.currentTimeMillis();
            postTimeList.add(end - start);
            count++;
        }
    } 
    public int getCount() {
        return count;
    }
    
    public List<Long> getGetTimeList() {
        return getTimeList;
    }
    
    public List<Long> getPostTimeList() {
        return postTimeList;
    }
}
