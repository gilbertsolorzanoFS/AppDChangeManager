/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.conn;


import org.appdynamics.appdchangemanager.data.Config;
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
public class AccessConfig {
    private static final Logger log=Logger.getLogger(AccessConfig.class.getName());
    
    private Connection conn =null;
    
    public void deleteConfig(String il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONFIG_DELETE_INDIV);
            ps.setString(1, il);
            ps.execute();
            
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.AUTH_DELETE_ROW).append(". Parameters name:")
                    .append(il)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
    }
    
    public void deleteConfig(Config il){
        deleteConfig(il.getName());
    }
    
    public void addConfig(Config il){
        addConfig(il.getName(),il.getValue());
    }
    
    public void addConfig(String name, String value){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONFIG_INSERT_INDIV);
            ps.setString(1, name);
            ps.setString(2, value);
            ps.setLong(3,java.util.Calendar.getInstance().getTimeInMillis());

            ps.execute();
            
            //conn.commit();

        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.AUTH_INSERT_ROW).append(". Parameters name: ")
                    .append(name).append(", value: ").append(value)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
    }
    
    public void updateConfig(Config il){
        updateConfig(il.getName(),il.getValue());
    }
    
    public void updateConfig(String name, String value){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONFIG_UPDATE_INDIV);
            ps.setString(1, value);
            ps.setLong(2,java.util.Calendar.getInstance().getTimeInMillis());
            ps.setString(3, name);

            ps.execute();
            
            //conn.commit();

        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONFIG_UPDATE_INDIV).append(". Parameters name: ")
                    .append(name).append(", value: ").append(value)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
    }
    
    public Config getSingleValue(Config il){
        return getSingleValue(il.getName());
    }
    
    public Config getSingleValue(String il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        Config value=null;
        try{
            
            ps = conn.prepareStatement(ACMS.CONFIG_SELECT_INDIV);
            ps.setString(1, il);
            rs = ps.executeQuery();
            while(rs.next()){
                value=new Config(rs.getString(1),rs.getString(2),rs.getLong(3));
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONFIG_SELECT_INDIV).append(". Parameters name ")
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
       
        return value;
    }
    
    public ArrayList<Config> getAllValues(){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        ArrayList<Config> values=new ArrayList<Config>();
        try{
            
            ps = conn.prepareStatement(ACMS.CONFIG_SELECT_ALL);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Config value=new Config(rs.getString(1),rs.getString(2),rs.getLong(3));
                values.add(value);
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CONFIG_SELECT_INDIV).append(". Parameters name ")
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
       
        return values;
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
