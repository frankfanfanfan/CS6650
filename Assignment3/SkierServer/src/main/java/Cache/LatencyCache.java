/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cache;

import Dao.LatencyDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import model.Latency;

/**
 *
 * @author Frankfan
 */
public class LatencyCache {
    private static final int QUEUE_SIZE = 1000;
    private static BlockingQueue<Latency> dataList = new ArrayBlockingQueue<>(QUEUE_SIZE);
    private static int count;
    
    public static synchronized void addData (Latency record) throws SQLException {
        dataList.offer(record);
        count++;
        System.out.print(dataList.size() + " " + count); 
        if (dataList.size() == QUEUE_SIZE) {
            List<Latency> sendList = new ArrayList<>(dataList);
            LatencyDao.insertLatency(sendList);
            dataList.clear();
        }  
    }
}
