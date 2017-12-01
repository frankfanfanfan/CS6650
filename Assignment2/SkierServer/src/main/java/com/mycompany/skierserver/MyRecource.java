package com.mycompany.skierserver;

import Cache.PostCache;
import Dao.RFIDLiftDao;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.RFIDLiftData;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class MyRecource {

//    private RFIDLiftDao liftDao = new RFIDLiftDao();
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @param id
     * @return String that will be returned as a text/plain response.
     */
    @Path("/myvert")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(
            @QueryParam("skierId") int skierId,
            @QueryParam("dayNum") int dayNum) throws SQLException {
//        System.out.print(RFIDLiftDao.getCount());
        return RFIDLiftDao.getSkierData(skierId, dayNum);
    }
    
    @Path("/load")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postData(RFIDLiftData rfid) throws SQLException {
//        liftDao.create(rfid);
//        RFIDLiftDao.create(rfid); 
//        String info = rfid.getResortID() + " " + rfid.getDayNum();
//        System.out.println(info); 
//        return info; 
        PostCache.addData(rfid);
    }
}
