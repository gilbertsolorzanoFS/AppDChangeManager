/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.conn;

import org.appdynamics.appdchangemanager.data.ItemLocation;
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
public class AccessItemLocation {
    private static final Logger log=Logger.getLogger(AccessItemLocation.class.getName());
    private Connection conn =null;
    
    public AccessItemLocation(){}
    
    /*
     * item location type 0 is app, 1 tier, 2 node (for later)
     */
    public void deleteItemLocation(ItemLocation il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        String query=ACMS.ITEM_LOCATION_DELETE_APP;
        if(il.getLocationType()==1)query=ACMS.ITEM_LOCATION_DELETE_TIER;
        try{
            
            ps = conn.prepareStatement(query);
            ps.setString(1, il.getController());
            ps.setString(2, il.getApplication());
            if(il.getLocationType() == 1){
                ps.setString(3, il.getTier());
                ps.setInt(4, il.getLocationType());
            }else{
                ps.setInt(3, il.getLocationType());
            }
            
            ps.execute();
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(query).append(". Parameters controller ")
                    .append(il.getController()).append(" application ").append(il.getApplication())
                    .append(" tier ").append(il.getTier()).append(" type ").append(il.getLocationType())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
    }
    
   public void updateItemLocation(ItemLocation il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;

        try{
            
            ps = conn.prepareStatement(ACMS.ITEM_LOCATION_UPDATE);
            ps.setString(1, il.getController());
            ps.setString(2, il.getApplication());
            ps.setString(3, il.getTier());
            ps.setInt(4, il.getLocationType());
         
            ps.execute();
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.ITEM_LOCATION_UPDATE).append(". Parameters controller ")
                    .append(il.getController()).append(" application ").append(il.getApplication())
                    .append(" tier ").append(il.getTier()).append(" type ").append(il.getLocationType())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
    }
    
    public void addItemLocation(ItemLocation il){
        ItemLocation tmp= null;
        if(il.getLocationType()==0){
            tmp=getSingleItemLocation(il.getController(), il.getApplication());
        }else{
            tmp=getSingleItemLocation(il.getController(), il.getApplication(), il.getTier());
        }
        
        if(tmp != null){log.log(Level.WARNING,tmp.toErrorHelp());return;}
        
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.ITEM_LOCATION_INSERT_TIER,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, il.getController());
            ps.setString(2, il.getApplication());
            ps.setString(3, il.getTier());
            ps.setInt(4, il.getLocationType());
            
            
            ps.execute();
            rs=ps.getGeneratedKeys();
            while(rs.next()){
                il.setId(rs.getLong(1));
            }
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.ITEM_LOCATION_INSERT_TIER).append(". Parameters controller ")
                    .append(il.getController()).append(" application ").append(il.getApplication())
                    .append(" tier ").append(il.getTier()).append(" type ").append(il.getLocationType())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
       
    }
    
    public ItemLocation getSingleItemLocation(Long id){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        ItemLocation item=null;
        try{
            
            ps = conn.prepareStatement(ACMS.ITEM_LOCATION_SELECT_INDIV_ID);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            while(rs.next()){
                item=new ItemLocation(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));
               
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.ITEM_LOCATION_SELECT_INDIV_ID).append(". Parameter id ")
                    .append(id).append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
       
        return item;
    }
    
    public ItemLocation getSingleItemLocationOrAdd(String controller, String application){
        ItemLocation item = getSingleItemLocation(controller,application);
        if(item == null){ // it does not exist
            item=new ItemLocation(controller,application);
            addItemLocation(item);
        }
        return item;
    }
    
    public ItemLocation getSingleItemLocationOrAdd(String controller, String application, String tier){
        ItemLocation item = getSingleItemLocation(controller,application,tier);
        if(item == null){ // it does not exist
            item=new ItemLocation(controller,application,tier);
            addItemLocation(item);
        }
        return item;
    }
    
    public ItemLocation getSingleItemLocation(String controller, String application){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        ItemLocation item=null;
        try{
            
            ps = conn.prepareStatement(ACMS.ITEM_LOCATION_SELECT_INDIV_APP);
            ps.setString(1, controller);
            ps.setString(2, application);
            ps.setInt(3, 0);
            rs = ps.executeQuery();
            while(rs.next()){
                item=new ItemLocation(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));
                return item;
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.ITEM_LOCATION_SELECT_INDIV_APP).append(". Parameters controller ")
                    .append(controller).append(" application ").append(application)
                    .append(" type ").append(0)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
        return item;
    }
    
    public ItemLocation getSingleItemLocation(String controller, String application, String tier){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        ItemLocation item=null;
        try{
            
            ps = conn.prepareStatement(ACMS.ITEM_LOCATION_SELECT_INDIV_TIER);
            ps.setString(1, controller);
            ps.setString(2, application);
            ps.setString(3, tier);
            ps.setInt(4, 1);
            rs = ps.executeQuery();
            
            while(rs.next()){
                item=new ItemLocation(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));
                
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.ITEM_LOCATION_SELECT_INDIV_TIER).append(". Parameters controller ")
                    .append(controller).append(" application ").append(application)
                    .append(" tier ").append(tier).append(" type ").append(1)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
        return item;
    }
    
    
    
    public ArrayList<ItemLocation> getAllItemLocation(){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        ArrayList<ItemLocation> items= new ArrayList<ItemLocation>();
        try{
            
            ps = conn.prepareStatement(ACMS.ITEM_LOCATION_SELECT_ALL);
            rs = ps.executeQuery();
            
            while(rs.next()){
                ItemLocation item=new ItemLocation(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));
                items.add(item);
            }
            
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.ITEM_LOCATION_SELECT_ALL).append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
        return items;
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
