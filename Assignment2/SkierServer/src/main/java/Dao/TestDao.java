/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.RFIDLiftData;

/**
 *
 * @author Frankfan
 */
public class TestDao {
    
    public static void main(String[] args) throws SQLException {
        RFIDLiftData rfid = new RFIDLiftData(5,3,1,21,1);
        List<RFIDLiftData> list = new ArrayList<>();
        list.add(rfid);
//        RFIDLiftDao.create(rfid);
        String test = RFIDLiftDao.getSkierData(2, 1);
        System.out.println(test);
//        RFIDLiftDao.updateHistory(list); 

    }
    
}
