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
import java.util.ArrayList;
import java.util.List;
import model.Latency;

/**
 *
 * @author Frankfan
 */
public class LatencyDao {
    protected static ConnectionDB connectionDB = ConnectionDB.getInstance();
    
    public static void insertLatency(List<Latency> latencyList) throws SQLException {
        String sql = "insert into latency (\"late\", \"type\", \"server\") values (?, ?, ?);";
        Connection con = null;
        PreparedStatement insertStmt = null;
        
        try {
            con = connectionDB.getConnect();
            insertStmt = con.prepareStatement(sql);
            for (Latency l : latencyList) {
                insertStmt.setLong(1, l.getLatency());
                insertStmt.setString(2, l.getType());
                insertStmt.setInt(3, l.getServerId());
                insertStmt.addBatch();
            }
            
            insertStmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("insert problem");
        } finally {
            if (con != null) { con.close();}
            if (insertStmt != null) { insertStmt.close();}
        }
    }
    
    public static List<Latency> getLatencies(String type, int server) throws SQLException {
        String sql = "select * from latency where type = ? and server = ?;";
        List<Latency> latencies = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        
        try {
            con = connectionDB.getConnect();
            stmt = con.prepareStatement(sql);
            stmt.setString(1, type);
            stmt.setInt(2, server);
            
            results = stmt.executeQuery();
            
            while(results.next()) {
                long late = results.getInt("late");
                String requestType = results.getString("type");
                int serverId = results.getInt("server");
                
                Latency latency = new Latency(late, requestType, serverId);
                latencies.add(latency);
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
            
        return latencies;
    }
}
