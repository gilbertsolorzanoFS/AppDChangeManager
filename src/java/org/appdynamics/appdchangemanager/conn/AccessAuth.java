/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.conn;

import org.appdynamics.appdchangemanager.data.Auth;
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
public class AccessAuth {
    private static final Logger log=Logger.getLogger(AccessAuth.class.getName());
    private Connection conn =null;
    
    public AccessAuth(){}
    
    public void deleteAuth(Auth auth){
        deleteAuth(auth.getId());
    }
    
    public void deleteAuth(Long il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            
            ps = conn.prepareStatement(ACMS.AUTH_DELETE_ROW);
            ps.setLong(1, il);
            ps.execute();
            
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.AUTH_DELETE_ROW).append(". Parameters id")
                    .append(il)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
    }
    
    //"set displayName=?, userName=?, password=?, acctType=? , groupName=? WHERE id=?"
    public boolean updateAuth(Auth il){
        
        PreparedStatement ps=null;
        ResultSet rs = null;
        if(conn == null) init();
        try{
            
            ps = conn.prepareStatement(ACMS.AUTH_UPDATE_ROW);
            ps.setString(1, il.getDisplayName());
            ps.setString(2, il.getUserName());
            ps.setString(3, il.getPassword());
            ps.setInt(4, il.getAcctType());
            ps.setString(5, il.getGroupName());
            ps.setLong(6,il.getId());
            
            ps.execute();
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.AUTH_UPDATE_ROW).append(". Parameters displayName: ")
                    .append(il.getDisplayName()).append(" userName: ").append(il.getUserName())
                    .append(" password: ").append(il.getPassword()).append(" account type: ").append(il.getAcctType())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        return true;
    }
    
    public boolean addAuth(Auth il){
        
        PreparedStatement ps=null;
        ResultSet rs = null;
        Auth user = getSingleAuth(il.getUserName());
        if(user != null){
            return false;
        }
        if(conn == null) init();
        try{
            
            ps = conn.prepareStatement(ACMS.AUTH_INSERT_ROW,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, il.getDisplayName());
            ps.setString(2, il.getUserName());
            ps.setString(3, il.getPassword());
            ps.setInt(4, il.getAcctType());
            ps.setString(5, il.getGroupName());
            
            ps.execute();
            rs=ps.getGeneratedKeys();
            while(rs.next()){
                il.setId(rs.getLong(1));
            }
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.AUTH_INSERT_ROW).append(". Parameters displayName: ")
                    .append(il.getDisplayName()).append(" userName: ").append(il.getUserName())
                    .append(" password: ").append(il.getPassword()).append(" account type: ").append(il.getAcctType())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        return true;
    }
    
    public Auth getSingleAuth(Auth il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        Auth auth=null;
        try{
            
            ps = conn.prepareStatement(ACMS.AUTH_SELECT_INDIV);
            ps.setString(1, il.getUserName());
            rs = ps.executeQuery();
            while(rs.next()){
                auth=new Auth(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6));
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.AUTH_SELECT_INDIV).append(". Parameters displayName ")
                    .append(il.getDisplayName()).append(" userName ").append(il.getUserName())
                    .append(" password ").append(il.getPassword()).append(" account type ").append(il.getAcctType())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
       
        return auth;
    }
    
    public Auth getSingleAuth(String il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        Auth auth=null;
        try{
            
            ps = conn.prepareStatement(ACMS.AUTH_SELECT_INDIV);
            ps.setString(1, il);
            rs = ps.executeQuery();
            while(rs.next()){
                auth=new Auth(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6));
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.AUTH_SELECT_INDIV).append(". Parameters userName ")
                    .append(il)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
       
        return auth;
    }
    
    public Auth getSingleAuth(Long il){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        Auth auth=null;
        try{
            
            ps = conn.prepareStatement(ACMS.AUTH_SELECT_INDIV_ID);
            ps.setLong(1, il);
            rs = ps.executeQuery();
            while(rs.next()){
                auth=new Auth(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getString(6));
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.AUTH_SELECT_INDIV_ID).append(". Parameters id ")
                    .append(il)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
       
        return auth;
    }
    
    public ArrayList<Auth> getAllAuth(){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        ArrayList<Auth> auths=new ArrayList<Auth>();
        
        try{
            
            ps = conn.prepareStatement(ACMS.AUTH_SELECT_ALL);
            rs = ps.executeQuery();
            while(rs.next()){
                Auth auth = new Auth(rs.getLong(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6));
                auths.add(auth);
            }
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.AUTH_SELECT_ALL)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
        return auths;
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
