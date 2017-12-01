/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Frankfan
 */
public class ConnectionDB {
    
    public Connection getConnect() {
        Connection connect = null;
        
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://skidb.c1epza50dlax.us-west-2.rds.amazonaws.com:5432/ski";
            String url2 = "jdbc:postgresql://skier.c1epza50dlax.us-west-2.rds.amazonaws.com:5432/skier";
            String userName = "frankfan";
            String passWord = "frankfan";
            connect = DriverManager.getConnection(url2, userName, passWord);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return connect;
    }
}
