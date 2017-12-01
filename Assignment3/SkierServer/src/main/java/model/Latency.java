/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Frankfan
 */
public class Latency {
    private long latency;
    private String type;
    private int serverId;
    
    public Latency(long latency, String type, int serverId) {
        this.latency = latency;
        this.type = type;
        this.serverId = serverId;
    }
    
    public long getLatency() {
        return this.latency;
    }
    
    public String getType() {
        return this.type;
    }
    
    public int getServerId() {
        return this.serverId;
    }
}
