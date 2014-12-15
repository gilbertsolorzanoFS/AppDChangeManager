/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.conn;

import org.appdynamics.appdchangemanager.resources.ACMS;
import org.appdynamics.appdchangemanager.data.CnConnection;
import org.appdynamics.appdchangemanager.data.Auth;
import org.appdynamics.appdchangemanager.data.Controller;

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
public class AccessConnection {
    private static final Logger log=Logger.getLogger(AccessConnection.class.getName());
    private Connection conn =null;
    
    public AccessConnection(){}
    
    public void deleteCnConnection(Long il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONNECTION_DELETE_INDIV_ID);
            ps.setLong(1, il);
            ps.execute();
            //conn.commit();
            
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONNECTION_DELETE_INDIV_ID).append(". Parameters id")
                    .append(il)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
    }
    
    public void deleteCnConnection(CnConnection il){
        deleteCnConnection(il.getId());
    }
    
    
    public void addCnConnection(CnConnection il){
        CnConnection tmp=getCheckCnConnection(il.getAuthId(),il.getControllerId());
        
        if(tmp != null){log.log(Level.WARNING,tmp.toErrorHelp());return;}
        
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONNECTION_INSERT_INDIV,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, il.getDisplayName());
            ps.setLong(2, il.getAuthId());
            ps.setLong(3, il.getControllerId());
            
            ps.execute();
            rs=ps.getGeneratedKeys();
            while(rs.next()){
                il.setId(rs.getLong(1));
            }
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONNECTION_INSERT_INDIV).append(". Parameters controller id ")
                    .append(il.getControllerId()).append(" auth id ").append(il.getAuthId())
                    .append(" displayName ").append(il.getDisplayName())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
       
    }
    
    public void updateCnConnection(CnConnection il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONNECTION_UPDATE_INDIV);
            ps.setString(1, il.getDisplayName());
            ps.setLong(2, il.getAuthId());
            ps.setLong(3, il.getControllerId());
            ps.setLong(4, il.getId());
            
            ps.execute();
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONNECTION_INSERT_INDIV).append(". Parameters controller id ")
                    .append(il.getControllerId()).append(" auth id ").append(il.getAuthId())
                    .append(" displayName ").append(il.getDisplayName())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
    }
    
    public ArrayList<CnConnection> getAllCnConnection(){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        CnConnection item=null;
        ArrayList<CnConnection> items=new ArrayList<CnConnection>();
        
        try{
            
            ps = conn.prepareStatement(ACMS.CONNECTION_SELECT_ALL);
            rs = ps.executeQuery();
            while(rs.next()){
                item=new CnConnection(rs.getLong(1),rs.getString(2),rs.getLong(3),rs.getLong(4));
                items.add(item);
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONNECTION_SELECT_ALL)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
        return items;
    }
    
    public CnConnection getCnConnection(Long id){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        CnConnection item=null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONNECTION_SELECT_INDIV);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            while(rs.next()){
                item=new CnConnection(rs.getLong(1),rs.getString(2),rs.getLong(3),rs.getLong(4));
                
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONNECTION_SELECT_INDIV).append(". Parameter id ")
                    .append(id).append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
        return item;
    }
    
    public CnConnection getCheckCnConnection(Long authId, Long controllerId){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        CnConnection item=null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONNECTION_SELECT_CHECK);
            ps.setLong(1,authId);
            ps.setLong(2,controllerId);
            rs = ps.executeQuery();
            while(rs.next()){
                item=new CnConnection(rs.getLong(1),rs.getString(2),rs.getLong(3),rs.getLong(4));
                
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONNECTION_SELECT_CHECK).append(". Parameter auth id ")
                    .append(authId).append(" controller id ").append(controllerId).append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        return item;
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
