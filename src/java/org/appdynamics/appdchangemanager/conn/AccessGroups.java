/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.conn;

import org.appdynamics.appdchangemanager.data.Groups;
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
 * 
 * 
 * 
 */
public class AccessGroups {
    private static final Logger log=Logger.getLogger(CreateTables.class.getName());
    private Connection conn =null;
    

    public AccessGroups(){}
    
    public void deleteGroup(Groups group){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        
        try{
            ps = conn.prepareStatement(ACMS.GROUP_DELETE_INDIV);
            ps.setLong(1, group.getUserId());
            ps.setString(2, group.getGroupName());
            ps.execute();
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.GROUP_DELETE_INDIV).append(". Parameters group name ")
                    .append(group.getGroupName()).append(" userId ").append(group.getUserId())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
    }
    
    public void addGroup(Groups group){  
        Groups tmp = null;
        tmp=getSingleGroup(group.getGroupName(), group.getUserId());
        if(tmp != null){log.log(Level.WARNING,tmp.toErrorHelp());return;}
        
        if(conn == null) init();   
        PreparedStatement ps=null;
        ResultSet rs = null;
        
        try{
            ps = conn.prepareStatement(ACMS.GROUP_INSERT_INDIV,Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, group.getUserId());
            ps.setString(2, group.getGroupName());
            ps.execute();
            rs=ps.getGeneratedKeys();
            while(rs.next()){
                group.setId(rs.getLong(1));
            }
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.GROUP_INSERT_INDIV).append(". Parameters groupName: ")
                    .append(group.getGroupName()).append(", userId: ").append(group.getUserId())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
     

    }
    
    public Groups getSingleGroup(String groupName, Long userId){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        Groups grp =  null;
        try{
            ps = conn.prepareStatement(ACMS.GROUP_SELECT_CHECK);
            ps.setLong(1,userId);
            ps.setString(2, groupName);
            rs = ps.executeQuery();
            
            while(rs.next()){
                grp = new Groups(rs.getLong(1),rs.getLong(3),rs.getString(2));
                
            }
            
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.GROUP_SELECT_CHECK).append(". Parameters groupName: ")
                    .append(groupName).append(" userId ").append(userId).append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
        return grp;
    }
    
    public Groups getSingleGroup(Long id){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        Groups grp = null;
        try{
            ps = conn.prepareStatement(ACMS.GROUP_SELECT_INDIV);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                grp = new Groups(rs.getLong(1),rs.getLong(3),rs.getString(2));
                
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.GROUP_SELECT_INDIV).append(". Parameters id: ")
                    .append(id).append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
        return grp;
    }
    
    public ArrayList<Groups> getAllGroups(){
        if(conn == null) init();
        ArrayList<Groups> grps=new ArrayList<Groups>();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(ACMS.GROUP_SELECT_ALL);
            rs = ps.executeQuery();
            while(rs.next()){
                Groups grp = new Groups(rs.getLong(1),rs.getLong(3),rs.getString(2));
                grps.add(grp);
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.GROUP_SELECT_ALL).append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
      
        return grps;
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
