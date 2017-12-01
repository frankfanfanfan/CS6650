/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Config.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import model.RFIDLiftData;

/**
 *
 * @author Frankfan
 */
public class RFIDLiftDao {  
    protected static ConnectionDB connectionDB = ConnectionDB.getInstance();
    
    private static int liftVertical(int lift) {
        return ((lift - 1) / 10 + 2) * 100;
    } 
    
    public static void updateHistory(List<RFIDLiftData> list) throws SQLException {
        String sql = "insert into history values (?, ?, 1, ?)\n" +
                    "on conflict (skierid, daynum)\n" +
                    "do update\n" +
                    "set liftnumber = history.liftnumber + 1, totalvertical = history.totalvertical + ?";
        Connection con = null;
        PreparedStatement upsertStmt = null;
        
        try {
            con = connectionDB.getConnect();
            upsertStmt = con.prepareStatement(sql);
            for (RFIDLiftData rfid : list) {
                upsertStmt.setInt(1, rfid.getSkierID());
                upsertStmt.setInt(2, rfid.getDayNum());
                upsertStmt.setInt(3, liftVertical(rfid.getLiftID()));
                upsertStmt.setInt(4, liftVertical(rfid.getLiftID()));
                upsertStmt.addBatch();
            }
            
            upsertStmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("upsert problem");
        } finally {
            if (con != null) { con.close();}
            if (upsertStmt != null) { upsertStmt.close();}
        }
    }
    
    public static void insertAll(List<RFIDLiftData> list) throws SQLException {
        String sql = "insert into skierdata (\"resortID\", \"dayNum\", \"skierID\", \"liftID\", \"time\", \"vertical\") values (?, ?, ?, ?, ?, ?);";
        Connection con = null;
        PreparedStatement createStmt = null;
        
        try {
            con = connectionDB.getConnect();
            createStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (RFIDLiftData rfid : list) {
                createStmt.setInt(1, rfid.getResortID());
                createStmt.setInt(2, rfid.getDayNum());
                createStmt.setInt(3, rfid.getSkierID());
                createStmt.setInt(4, rfid.getLiftID());
                createStmt.setInt(5, rfid.getTime());
                createStmt.setInt(6, liftVertical(rfid.getLiftID()));
                createStmt.addBatch();
            }
            
            createStmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Insert problem");
        } finally {
            if (con != null) { con.close();}
            if (createStmt != null) { createStmt.close();}
        }
    }
    
    public static String getSkierData(int skierID, int dayNum) throws SQLException {
        String sql = "select * from history where skierid = ? and daynum = ?";

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        
        try {
            con = connectionDB.getConnect();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, skierID);
            stmt.setInt(2, dayNum);
            
            results = stmt.executeQuery();
            
            if(results.next()) {
                int id  = results.getInt("skierid");
                int number = results.getInt("liftnumber");
                int total  = results.getInt("totalvertical");
                int day = results.getInt("daynum");
              
                String output = "Hi dear customer! Your ID is: " + id + "\n" + 
                                "On day " + day + ", you took " + number + " lifts. \n" + 
                                "You skied " + total + " meters in total. Well done!";
                return output;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Get problem");
        } finally {
            if (con != null) {
                con.close();
            }
            if (stmt != null) {
                stmt.close(); 
            }
            if (results != null) {
                results.close();
            }
        }
        
        return null;
    }
}
