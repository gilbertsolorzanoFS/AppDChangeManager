/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.conn;

import org.appdynamics.appdchangemanager.data.Change;
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
public class AccessChanges {
    private static final Logger log=Logger.getLogger(AccessChanges.class.getName());
    private Connection conn =null;
    
    public AccessChanges(){}
    
    public void deleteChange(Long id){
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            deleteRequests(id);
            
            ps = conn.prepareStatement(ACMS.CHANGE_DELETE_ROW);
            ps.setLong(1, id);
            ps.execute();
            
            //conn.commit();
            
            
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CHANGE_DELETE_ROW).append(". Parameters id")
                    .append(id)
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
       
    }
    
    public void deleteChange(Change il){
        deleteChange(il.getId());
    }
    
    public void deleteRequests(Long id){
        AccessRequests ar=new AccessRequests();
        ar.deleteRequestChdId(id);
    }
    
    public void addRequest(ArrayList<Request> reqs){
        AccessRequests ar=new AccessRequests();
        for(Request req:reqs){
            Request tmp=ar.getCheckRequest(req);
            if(tmp==null){
                ar.addRequests(req);
            }else{
                req.setId(tmp.getId());
            }
            
        }
    }
    public void addChange(Change il){
        
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        
        try{
            //requestTime,completedTime,requester,descr,approver,approved,rejected,completed,readyForApproval
            ps = conn.prepareStatement(ACMS.CHANGE_INSERT_ROW,Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, il.getRequestTime());
            ps.setLong(2, il.getCompletedTime());
            ps.setString(3, il.getRequester());
            ps.setString(4, il.getDescr());
            ps.setString(5, il.getApprover());
            ps.setInt(6, ACMS.convertToInt(il.isApproved()));
            ps.setInt(7, ACMS.convertToInt(il.isRejected()));
            ps.setInt(8, ACMS.convertToInt(il.isCompleted()));
            ps.setInt(9, ACMS.convertToInt(il.isReadyForApproval()));
            
            
            ps.execute();
            rs=ps.getGeneratedKeys();
            
            while(rs.next()){
                il.setId(rs.getLong(1));
            }
            
            //conn.commit();
            
            //for(Request req:il.getRequests()) req.setChangeId(il.getId());
            //addRequest(il.getRequests());
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CHANGE_INSERT_ROW).append(". Parameters requestTime: ")
                    .append(il.getRequestTime()).append(", completedTime: ").append(il.getCompletedTime())
                    .append(", requester: ").append(il.getRequester()).append(", descr: ")
                    .append(il.getDescr()).append(" approver ").append(il.getApprover())
                    .append(", approved: ").append(il.isApproved()).append(", rejected: ")
                    .append(il.isRejected()).append(", completed: ").append(il.isCompleted())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
    }
    
    public void addUpdate(Change il){
        Change tmp=null;
        
        if(tmp != null){log.log(Level.WARNING,tmp.toErrorHelp());return;}
        
        if(conn == null) init();
        PreparedStatement ps=null;
        ResultSet rs = null;
        try{
            //requestTime,completedTime,requester,descr,approver,approved,rejected,completed
            ps = conn.prepareStatement(ACMS.CHANGE_UPDATE_ROW);
            ps.setLong(1, il.getRequestTime());
            ps.setLong(2, il.getCompletedTime());
            ps.setString(3, il.getRequester());
            ps.setString(4, il.getDescr());
            ps.setString(5, il.getApprover());
            ps.setInt(6, ACMS.convertToInt(il.isApproved()));
            ps.setInt(7, ACMS.convertToInt(il.isRejected()));
            ps.setInt(8, ACMS.convertToInt(il.isCompleted()));
            ps.setInt(9, ACMS.convertToInt(il.isReadyForApproval()));
            ps.setLong(10, il.getId());
            
            ps.execute();
            //conn.commit();
        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query\n")
                    .append(ACMS.CHANGE_UPDATE_ROW).append(". Parameters requestTime ")
                    .append(il.getRequestTime()).append(" completed time ").append(il.getCompletedTime())
                    .append(" requester ").append(il.getRequester()).append(" descr ")
                    .append(il.getDescr()).append(" approver ").append(il.getApprover())
                    .append(" approved ").append(il.isApproved()).append(" rejected ")
                    .append(il.isRejected()).append(" completed ").append(il.isCompleted())
                    .append(" the id is ").append(il.getId())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
       
    }
    
    public Change getCheckChange(Change il){
        PreparedStatement ps=null;
        ResultSet rs = null;
        Change item = null;
        int count=0;
        // We are going to try 4 time before we give up.
     
        if(conn == null) init();
        try{

            ps = conn.prepareStatement(ACMS.CHANGE_SELECT_CHECK);
            ps.setLong(1,il.getRequestTime());
            ps.setString(2, il.getRequester());
            rs = ps.executeQuery();
            while(rs.next()){
                //id,requestTime,completedTime,requester,descr,approver,approved,rejected,completed,readyForApproval
                //log.info(new StringBuilder().append("Id ").append(rs.getLong(1)).append(" reqTime ").append(rs.getLong(2))
                  //      .append(" requester ").append(rs.getString(4)).append(" descr ").append(rs.getString(5)).toString());

                item = new Change(rs.getLong(1),rs.getLong(2),rs.getLong(3),
                        rs.getString(4),rs.getString(5),rs.getString(6)
                        ,ACMS.convertToBoolean(rs.getInt(7)),ACMS.convertToBoolean(rs.getInt(8))
                        ,ACMS.convertToBoolean(rs.getInt(9)),ACMS.convertToBoolean(rs.getInt(10)));
                //item.setRequests(getRequests(item.getId()));
            }

        }catch(Exception e){
            log.log(Level.SEVERE,new StringBuilder()
                    .append("Exception occurred while executing query number \n")
                    .append(count).append(" ")
                    .append(ACMS.CHANGE_SELECT_CHECK).append(". Parameters requestTime: ").append(il)
                    .append(", requester: ").append(il.getRequester())
                    .append(".\nWith message:\n")
                    .append(e.getMessage()).toString());
        }finally{
            resetRSPS(ps,rs);
            close_conn();
        }
        
        
        return item;
    }
    
    public Change getSingleChange(Long il){
        PreparedStatement ps=null;
        ResultSet rs = null;
        Change item=null;
        int count=0;

            if(conn == null) init();
            try{
                ps = conn.prepareStatement(ACMS.CHANGE_SELECT_INDIV_ID);
                ps.setLong(1,il);
                rs = ps.executeQuery();
                while(rs.next()){

                    item = new Change(rs.getLong(1),rs.getLong(2),rs.getLong(3),
                            rs.getString(4),rs.getString(5),rs.getString(6)
                            ,ACMS.convertToBoolean(rs.getInt(7)),ACMS.convertToBoolean(rs.getInt(8))
                            ,ACMS.convertToBoolean(rs.getInt(9)),ACMS.convertToBoolean(rs.getInt(10)));
                    //item.setRequests(getRequests(item.getId()));

                }

            }catch(Exception e){
                log.log(Level.SEVERE,new StringBuilder()
                        .append("Exception occurred while executing query number \n")
                        .append(count).append(" ")
                        .append(ACMS.CHANGE_SELECT_INDIV_ID).append(". Parameters id ").append(il)
                        .append(".\nWith message:\n")
                        .append(e.getMessage()).toString());
            }finally{
                resetRSPS(ps,rs);
                close_conn();
            }
        
        
        return item;
    }
    
    public ArrayList<Change> getApprovedChange(boolean il){
        PreparedStatement ps=null;
        ResultSet rs = null;
        ArrayList<Change> items=new ArrayList<Change>();
        int count=0;
        
            if(conn == null) init();
            try{

                ps = conn.prepareStatement(ACMS.CHANGE_SELECT_COMPLETED);
                ps.setInt(1, 0);
                rs = ps.executeQuery();
                while(rs.next()){

                    Change item = new Change(rs.getLong(1),rs.getLong(2),rs.getLong(3),
                            rs.getString(4),rs.getString(5),rs.getString(6)
                            ,ACMS.convertToBoolean(rs.getInt(7)),ACMS.convertToBoolean(rs.getInt(8))
                            ,ACMS.convertToBoolean(rs.getInt(9)),ACMS.convertToBoolean(rs.getInt(10)));

                    //item.setRequests(getRequests(item.getId()));
                    if(item.isReadyForApproval() == il)
                        items.add(item);
                }

            }catch(Exception e){
                log.log(Level.SEVERE,new StringBuilder()
                        .append("Exception occurred while executing number \n")
                        .append(count).append(" ")
                        .append(ACMS.CHANGE_SELECT_COMPLETED).append(". Parameters completed ").append(il)
                        .append(".\nWith message:\n")
                        .append(e.getMessage()).toString());
            }finally{
                resetRSPS(ps,rs);
                close_conn();
            }
        
        
        return items;
    }
    
    public ArrayList<Change> getReadyForApprChange(boolean il){
        PreparedStatement ps=null;
        ResultSet rs = null;
        ArrayList<Change> items=new ArrayList<Change>();
        int count=0;
        
        
            if(conn == null) init();
            try{

                ps = conn.prepareStatement(ACMS.CHANGE_SELECT_COMPLETED);
                ps.setInt(1, 0);
                rs = ps.executeQuery();
                while(rs.next()){

                    Change item = new Change(rs.getLong(1),rs.getLong(2),rs.getLong(3),
                            rs.getString(4),rs.getString(5),rs.getString(6)
                            ,ACMS.convertToBoolean(rs.getInt(7)),ACMS.convertToBoolean(rs.getInt(8))
                            ,ACMS.convertToBoolean(rs.getInt(9)),ACMS.convertToBoolean(rs.getInt(10)));

                    //item.setRequests(getRequests(item.getId()));
                    if(item.isReadyForApproval() == il)
                        items.add(item);
                }

            }catch(Exception e){
                log.log(Level.SEVERE,new StringBuilder()
                        .append("Exception occurred while executing number \n")
                        .append(count).append(" ")
                        .append(ACMS.CHANGE_SELECT_COMPLETED).append(". Parameters completed ").append(il)
                        .append(".\nWith message:\n")
                        .append(e.getMessage()).toString());
            }finally{
                resetRSPS(ps,rs);
                close_conn();
            }
        
        
        return items;
    }
    
    public ArrayList<Change> getCompletedChange(boolean il){
        PreparedStatement ps=null;
        ResultSet rs = null;
        ArrayList<Change> items=new ArrayList<Change>();
        int count=0;
        
        
            if(conn == null) init();
            try{

                ps = conn.prepareStatement(ACMS.CHANGE_SELECT_COMPLETED);
                ps.setInt(1, ACMS.convertToInt(il));
                rs = ps.executeQuery();
                while(rs.next()){

                    Change item = new Change(rs.getLong(1),rs.getLong(2),rs.getLong(3),
                            rs.getString(4),rs.getString(5),rs.getString(6)
                            ,ACMS.convertToBoolean(rs.getInt(7)),ACMS.convertToBoolean(rs.getInt(8))
                            ,ACMS.convertToBoolean(rs.getInt(9)),ACMS.convertToBoolean(rs.getInt(10)));

                    //item.setRequests(getRequests(item.getId()));
                    items.add(item);
                }

            }catch(Exception e){
                log.log(Level.SEVERE,new StringBuilder()
                        .append("Exception occurred while executing query number \n")
                        .append(count).append(" ")
                        .append(ACMS.CHANGE_SELECT_COMPLETED).append(". Parameters completed ").append(il)
                        .append(".\nWith message:\n")
                        .append(e.getMessage()).toString());
            }finally{
                resetRSPS(ps,rs);
                close_conn();
            }
        
        
        return items;
    }
    
    public ArrayList<Request> getRequests(Long il){
        AccessRequests ar=new AccessRequests();
        
        return ar.getChgRequest(il);
    }
    
    public ArrayList<Change> getAllChange(){
        PreparedStatement ps=null;
        ResultSet rs = null;
        ArrayList<Change> items=new ArrayList<Change>();
        int count=0;
        
        
            if(conn == null) init();
            try{

                ps = conn.prepareStatement(ACMS.CHANGE_SELECT_ALL);

                rs = ps.executeQuery();
                while(rs.next()){
                    //requestTime,completedTime,requester,descr,approver,approved,rejected,completed
                    Change item = new Change(rs.getLong(1),rs.getLong(2),rs.getLong(3),
                            rs.getString(4),rs.getString(5),rs.getString(6)
                            ,ACMS.convertToBoolean(rs.getInt(7)),ACMS.convertToBoolean(rs.getInt(8))
                            ,ACMS.convertToBoolean(rs.getInt(9)),ACMS.convertToBoolean(rs.getInt(10)));
                    //item.setRequests(getRequests(item.getId()));

                    items.add(item);
                }


            }catch(Exception e){
                log.log(Level.SEVERE,new StringBuilder()
                        .append("Exception occurred while executing query\n")
                        .append(ACMS.CHANGE_SELECT_ALL)
                        .append(".\nWith message:\n")
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
