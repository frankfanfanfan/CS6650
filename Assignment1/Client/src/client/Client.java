/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Frankfan
 */
public class Client {
    
    public static void requestByGet() {
        CloseableHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet("http://184.72.123.61:8080/mavenproject/webapi/myresource");
            System.out.println("Running GET..." + httpGet.getURI());
            
            HttpResponse response = httpClient.execute(httpGet);
            
            HttpEntity entity = response.getEntity();
            System.out.println("--------------------------------------");
            
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
                System.out.println("Response content: " + EntityUtils.toString(entity));
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }   
    
    public static void requestByPost() {
        CloseableHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://localhost:8080/mavenproject/webapi/myresource");
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("123", ""));
        String testString = "Frank123";
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams);
//            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "text/plain");
            httpPost.setEntity(uefEntity);
            
            HttpResponse response;
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            
            System.out.println("executing request " + httpPost.getURI());
            if (entity != null) {
                 System.out.println("Response content: " + EntityUtils.toString(entity,"UTF-8"));
            }
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        requestByPost();
//        requestByGet();
    }
    
}
