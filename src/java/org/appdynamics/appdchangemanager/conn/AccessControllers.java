/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.conn;

import org.appdynamics.appdchangemanager.data.Controller;
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
public class AccessControllers {
    private static final Logger log=Logger.getLogger(AccessControllers.class.getName());
    private Connection conn =null;
    
    public void deleteController(Long il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONTROLLER_DELETE_INDIV);
            ps.setLong(1, il);
            ps.execute();
            //conn.commit();
            
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONTROLLER_DELETE_INDIV).append(". Parameters id:")
                    .append(il)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
       
    }
    
    public void addController(Controller il){
        Controller tmp=null;
        
        tmp=getCheckControllers(il.getFqdn(), il.getAccount(), il.getPort());
        if(tmp != null){log.log(Level.INFO,tmp.toErrorHelp());return;}
        
        
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONTROLLER_INSERT_ROW,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, il.getDisplayName());
            ps.setString(2, il.getFqdn());
            ps.setString(3, il.getAccount());
            ps.setString(4, il.getPort());
            ps.setInt(5,ACMS.convertToInt(il.isUseSSL()));
            
            ps.execute();
            rs=ps.getGeneratedKeys();
            while(rs.next()){
                il.setId(rs.getLong(1));
            }
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONTROLLER_INSERT_ROW).append(". Parameters displayName: ")
                    .append(il.getDisplayName()).append(", fqdn: ").append(il.getFqdn())
                    .append(", account: ").append(il.getAccount()).append(", useSSL: ").append(il.isUseSSL())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
    }
    
    public void updateController(Controller il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONTROLLER_UPDATE_INDIV);
            ps.setString(1, il.getDisplayName());
            ps.setString(2, il.getFqdn());
            ps.setString(3, il.getAccount());
            ps.setInt(4,ACMS.convertToInt(il.isUseSSL()));
            ps.setLong(5,il.getId());
            
            ps.execute();
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONTROLLER_UPDATE_INDIV).append(". Parameters displayName ")
                    .append(il.getDisplayName()).append(" fqdn").append(il.getFqdn())
                    .append(" account ").append(il.getAccount()).append(" useSSL ").append(il.isUseSSL())
                    .append(" id ").append(il.getId())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
    }
    
    public ArrayList<Controller> getAllControllers(){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        ArrayList<Controller> items=new ArrayList<Controller>();
        try{
            
            ps = conn.prepareStatement(ACMS.CONTROLLER_SELECT_ALL);
            rs = ps.executeQuery();
            while(rs.next()){
                Controller item=new Controller(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),ACMS.convertToBoolean(rs.getInt(6)));
                items.add(item);
            }
            
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONTROLLER_SELECT_ALL)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
        return items;
    }
    
    public Controller getSingleControllers(Long il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        Controller item=null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONTROLLER_SELECT_INDIV);
            ps.setLong(1,il);
            
            rs = ps.executeQuery();
            while(rs.next()){
                item=new Controller(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),ACMS.convertToBoolean(rs.getInt(6)));
                
            }
            
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONTROLLER_SELECT_INDIV).append(" Parameters id ")
                    .append(il)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
       
        return item;
    }
    
    public Controller getCheckControllers(String fqdn, String account, String port){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        Controller item=null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONTROLLER_SELECT_INDIV_CHECK);
            ps.setString(1,fqdn);
            ps.setString(2,account);
            ps.setString(3, port);
            
            rs = ps.executeQuery();
            while(rs.next()){
                item=new Controller(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),ACMS.convertToBoolean(rs.getInt(6)));
                
            }
            
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONTROLLER_SELECT_INDIV_CHECK).append(" Parameters fqdn ")
                    .append(fqdn).append(" account ").append(account).append(" port ").append(port)
                    .append(".\nWith message:\n")
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
