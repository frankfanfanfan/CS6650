/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionDB {
    private static final String URL = "jdbc:postgresql://skier.c1epza50dlax.us-west-2.rds.amazonaws.com:5432/skier";
    private static final String URL2 = "jdbc:postgresql://newski.c1epza50dlax.us-west-2.rds.amazonaws.com:5432/newSki";
    private static final String USERNAME = "frankfan";
    private static final String PASSWORD = "frankfan";
    private static final String DRIVER = "org.postgresql.Driver";
    
    private static ConnectionDB instance;
    private BoneCP connectionPool;
    
    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }
    
    private void initialPool() {
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DB Connection problem");
        }
        
        try {
            BoneCPConfig config = new BoneCPConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USERNAME);
            config.setPassword(PASSWORD);
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
            config.setPartitionCount(1);
            connectionPool = new BoneCP(config);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnect() throws SQLException {
        if (connectionPool == null) {
            initialPool();
        }
        return connectionPool.getConnection();
    }
}
