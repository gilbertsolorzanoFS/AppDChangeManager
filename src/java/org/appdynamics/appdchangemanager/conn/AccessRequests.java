/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.conn;

import org.appdynamics.appdchangemanager.data.CnConnection;
import org.appdynamics.appdchangemanager.data.ItemLocation;
import org.appdynamics.appdchangemanager.data.Request;
import org.appdynamics.appdchangemanager.resources.ACMS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.util.logging.Level;
import java.util.logging.Logger;


import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author gilbert.solorzano
 */
public class AccessRequests {
    private static final Logger log=Logger.getLogger(AccessRequests.class.getName());
    private Connection conn =null;
    
    public void deleteRequestChdId(Long il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.REQUEST_DELETE_BY_CHG_ID);
            ps.setLong(1, il);
            ps.execute();
            //conn.commit();
            
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.REQUEST_DELETE_BY_CHG_ID).append(". Parameters changeId: ")
                    .append(il)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
    }
    
    public void deleteRequest(Long il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.REQUEST_DELETE_INDIV_ID);
            ps.setLong(1, il);
            ps.execute();
            
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.REQUEST_DELETE_INDIV_ID).append(". Parameters id: ")
                    .append(il)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
    }
    
    public void deleteRequest(Request il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.REQUEST_DELETE_INDIV);
            ps.setLong(1, il.getChangeId());
            ps.setString(2, il.getItemName());
            ps.setString(3, il.getItemType());
            ps.setLong(4, il.getSrcItemLocation().getId());
            ps.setLong(5, il.getDestItemLocation().getId());
            ps.setString(6, il.getNewVersion());
            ps.setString(7, il.getOrigVersion());
            ps.setString(8, il.getNewVersionSrc());
            ps.setString(9, il.getOrigVersionSrc());
            ps.execute();
            
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.REQUEST_DELETE_INDIV).append(". Parameters change id")
                    .append(il.getChangeId()).append(" item name ").append(il.getItemName())
                    .append(" item type ").append(il.getItemType()).append(" srcLocationId ")
                    .append(il.getSrcItemLocation().getId()).append(" destLocationId ").append(il.getDestItemLocation().getId())
                    .append(" new version ").append(il.getNewVersion()).append(" orig version ")
                    .append(il.getOrigVersion()).append(" orig location ").append(il.getOrigVersionSrc())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
    }
    
    public void addItemLocation(ItemLocation item){
        AccessItemLocation loc=new AccessItemLocation();
        ItemLocation tmp=null;
        if(item.getLocationType() == 0){
            tmp=loc.getSingleItemLocation(item.getController(), item.getApplication());
        }else{
            tmp=loc.getSingleItemLocation(item.getController(), item.getApplication(), item.getTier());
        }
        if(tmp == null){
         loc.addItemLocation(item);
        }else{item.setId(tmp.getId());}
    }
    
    public void addCnConnection(CnConnection cnn){
        AccessConnection conn=new AccessConnection();
        CnConnection tmp=conn.getCheckCnConnection(cnn.getAuthId(), cnn.getControllerId());
        
        if(tmp == null){
            conn.addCnConnection(cnn);
        }else{
            cnn.setId(tmp.getId());
        }
    }
    
    public void addRequests(Request il){
        
       
        addCnConnection(il.getSrcConn());
        addCnConnection(il.getDestConn());
        
        addItemLocation(il.getSrcItemLocation());
        addItemLocation(il.getDestItemLocation());
        
        Request tmp=getCheckRequest(il);
        if(tmp != null){log.log(Level.WARNING,il.toErrorHelp());return;}
        
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            //changeId, itemName, itemType, sourceLocationId, destLocationId, newVersion, origVersion,
            //newVersionSrc, orgVersionSrc, executionTime, completedBy, completionTime, success
            ps = conn.prepareStatement(ACMS.REQUEST_INSERT_INDIV,Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, il.getChangeId());
            ps.setString(2, il.getItemName());
            ps.setString(3, il.getItemType());
            ps.setLong(4, il.getSrcItemLocation().getId());
            ps.setLong(5, il.getDestItemLocation().getId());
            ps.setString(6, il.getNewVersion());
            ps.setString(7, il.getOrigVersion());
            ps.setString(8, il.getNewVersionSrc());
            ps.setString(9, il.getOrigVersionSrc());
            ps.setLong(10,il.getExecutionTime());
            ps.setString(11, il.getCompletedBy());
            ps.setLong(12, il.getCompletionTime());
            ps.setInt(13, ACMS.convertToInt(il.isSuccess()));
            ps.setLong(14, il.getSrcConn().getId());
            ps.setLong(15, il.getDestConn().getId());
            
            ps.execute();
            rs=ps.getGeneratedKeys();
            while(rs.next()){
                il.setId(rs.getLong(1));
            }
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.REQUEST_INSERT_INDIV).append(". Parameters change id")
                    .append(il.getChangeId()).append(" item name ").append(il.getItemName())
                    .append(" item type ").append(il.getItemType()).append(" srcLocationId ")
                    .append(il.getSrcItemLocation().getId()).append(" destLocationId ").append(il.getDestItemLocation().getId())
                    .append(" new version ").append(il.getNewVersion()).append(" orig version ")
                    .append(il.getOrigVersion()).append(" orig location ").append(il.getOrigVersionSrc())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        

    }
    public void updateRequest(Request il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            //changeId, itemName, itemType, sourceLocationId, destLocationId, newVersion, origVersion,
            //newVersionSrc, orgVersionSrc, executionTime, completedBy, completionTime, success
            ps = conn.prepareStatement(ACMS.REQUEST_UPDATE_INDIV_ID);
            ps.setLong(1, il.getChangeId());
            ps.setString(2, il.getItemName());
            ps.setString(3, il.getItemType());
            ps.setLong(4, il.getSrcItemLocation().getId());
            ps.setLong(5, il.getDestItemLocation().getId());
            ps.setString(6, il.getNewVersion());
            ps.setString(7, il.getOrigVersion());
            ps.setString(8, il.getNewVersionSrc());
            ps.setString(9, il.getOrigVersionSrc());
            ps.setLong(10,il.getExecutionTime());
            ps.setString(11, il.getCompletedBy());
            ps.setLong(12, il.getCompletionTime());
            ps.setInt(13, ACMS.convertToInt(il.isSuccess()));
            ps.setLong(14, il.getSrcConn().getId());
            ps.setLong(15, il.getDestConn().getId());
            ps.setLong(16, il.getId());
            
            rs = ps.executeQuery();
           
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.REQUEST_UPDATE_INDIV_ID).append(". Parameters change id")
                    .append(il.getChangeId()).append(" item name ").append(il.getItemName())
                    .append(" item type ").append(il.getItemType()).append(" srcLocationId ")
                    .append(il.getSrcItemLocation().getId()).append(" destLocationId ").append(il.getDestItemLocation().getId())
                    .append(" new version ").append(il.getNewVersion()).append(" orig version ")
                    .append(il.getOrigVersion()).append(" orig location ").append(il.getOrigVersionSrc())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        

    }
    
    public void getConnection(Request req, Long srcConn, Long destConn){
        AccessConnection ac=new AccessConnection();
        CnConnection cn = ac.getCnConnection(srcConn);
        CnConnection cn1 = ac.getCnConnection(destConn);
        req.setSrcConn(cn);
        req.setDestConn(cn1);
    }
    
    public Request getCheckRequest(Request il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        Request item=null;
        try{
            
            ps = conn.prepareStatement(ACMS.REQUEST_SELECT_CHECK);
            ps.setLong(1,il.getChangeId());
            ps.setString(2, il.getItemName());
            ps.setString(3, il.getItemType());
            rs = ps.executeQuery();
            while(rs.next()){
                item=new Request(rs.getLong(1),rs.getLong(2),rs.getString(3),rs.getString(4),
                        getLocation(rs.getLong(5)),getLocation(rs.getLong(6)),rs.getString(7),rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getLong(11), rs.getString(12), rs.getLong(13),
                        ACMS.convertToBoolean(rs.getInt(14)));
                
                getConnection(item,rs.getLong(15),rs.getLong(16));
               
            }
            
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.REQUEST_SELECT_CHECK).append(". Parameters changeId: ").append(il.getChangeId())
                    .append(" itemName: ").append(il.getItemName()).append(" itemType: ").append(il.getItemType())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        return item;
    }
    
    public Request getSingleRequest(Long id){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        Request item=null;
        try{
            
            ps = conn.prepareStatement(ACMS.REQUEST_SELECT_INDIV_ID);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            while(rs.next()){
                item=new Request(rs.getLong(1),rs.getLong(2),rs.getString(3),rs.getString(4),
                        getLocation(rs.getLong(5)),getLocation(rs.getLong(6)),rs.getString(7),rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getLong(11), rs.getString(12), rs.getLong(13),
                        ACMS.convertToBoolean(rs.getInt(14)));
                getConnection(item,rs.getLong(15),rs.getLong(16));
                
            }
            
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.REQUEST_SELECT_INDIV_ID).append(". Parameters id ").append(id)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        return item;
    }
    
    public ArrayList<Request> getChgRequest(Long changeId){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        ArrayList<Request> items=new ArrayList<Request>();
        try{
            
            ps = conn.prepareStatement(ACMS.REQUEST_SELECT_INDIV_CHGID);
            ps.setLong(1,changeId);
            rs = ps.executeQuery();
            while(rs.next()){
                Request item=new Request(rs.getLong(1),rs.getLong(2),rs.getString(3),rs.getString(4),
                        getLocation(rs.getLong(5)),getLocation(rs.getLong(6)),rs.getString(7),rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getLong(11), rs.getString(12), rs.getLong(13),
                        ACMS.convertToBoolean(rs.getInt(14)));
                getConnection(item,rs.getLong(15),rs.getLong(16));
                items.add(item);
            }
            
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.REQUEST_SELECT_INDIV_CHGID).append(". Parameters changeId ").append(changeId)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
   
        return items;
    }
    
    public ArrayList<Request> getAllRequest(){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        ArrayList<Request> items=new ArrayList<Request>();
        try{
            
            ps = conn.prepareStatement(ACMS.REQUEST_SELECT_INDIV_ALL);
            rs = ps.executeQuery();
            while(rs.next()){
                Request item=new Request(rs.getLong(1),rs.getLong(2),rs.getString(3),rs.getString(4),
                        getLocation(rs.getLong(5)),getLocation(rs.getLong(6)),rs.getString(7),rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getLong(11), rs.getString(12), rs.getLong(13),
                        ACMS.convertToBoolean(rs.getInt(14)));
                getConnection(item,rs.getLong(15),rs.getLong(16));
                items.add(item);
            }
            
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.REQUEST_SELECT_INDIV_ALL)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
        return items;
    }
    
    public ItemLocation getLocation(Long id){
        AccessItemLocation loc=new AccessItemLocation();
        
        return loc.getSingleItemLocation(id);
    }
    
    public void init(){
        try{
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(ACMS.CHGDB);
            conn = ds.getConnection();
            //conn.setAutoCommit(false);
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while attempting to get connection\n")
                    .append(e.getMessage()).toString());
        }
        
    }
    
    public void close_conn(){
        try{
            if(conn != null){ conn.close(); conn=null;}
        }catch(Exception e){
            
        }
    }
    
    public void resetRSPS(PreparedStatement ps, ResultSet rs){
        try{
            if(rs != null){ rs.close(); rs=null;}
        }catch(Exception e){
            log.warning(new StringBuilder()
                    .append("Attempted to close resultset with exception : ")
                    .append(e.getMessage()).toString());
        }
        
        try{
            if(ps != null){ ps.close(); ps=null;}
        }catch(Exception e){
            log.warning(new StringBuilder()
                    .append("Attempted to close preparedstatement with exception : ")
                    .append(e.getMessage()).toString());
        }
    }
}
