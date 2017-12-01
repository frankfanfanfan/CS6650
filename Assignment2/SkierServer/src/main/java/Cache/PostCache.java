/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cache;

import Dao.RFIDLiftDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import model.RFIDLiftData;

/**
 *
 * @author Frankfan
 */
public class PostCache {
    private static BlockingQueue<RFIDLiftData> dataList = new ArrayBlockingQueue<>(500);
    private static int count;
    
    public static synchronized void addData (RFIDLiftData record) throws SQLException {
        dataList.offer(record);
        count++;
        System.out.print(dataList.size() + " " + count); 
        if (dataList.size() == 500) {
            List<RFIDLiftData> sendList = new ArrayList<>(dataList);
            RFIDLiftDao.insertAll(sendList);
            RFIDLiftDao.updateHistory(sendList);
            dataList.clear();
        } 
    }
}
