/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.conn;

import org.appdynamics.appdchangemanager.data.Auth;
import org.appdynamics.appdchangemanager.data.Groups;
import org.appdynamics.appdchangemanager.data.Config;
import org.appdynamics.appdchangemanager.resources.ACMS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
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
public class CreateTables {
    private static final Logger log=Logger.getLogger(CreateTables.class.getName());
    private ArrayList<String> tablesFound=new ArrayList<String>(),coreTables;
    
    public CreateTables(){
        coreTables=new ArrayList<String>();
        for(String val:ACMS.TABLES)coreTables.add(val);
    }
    
    public void init() throws Exception{
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup(ACMS.CHGDB);
        Connection conn = ds.getConnection();
        DatabaseMetaData dbMeta = conn.getMetaData();
        ResultSet rs = dbMeta.getTables(null, "APPD", null, null);
        
        boolean notFnd=true;
        while(rs.next()){
            notFnd=false;
            log.log(Level.INFO, "Found the following " + rs.getString(2) + " " + rs.getString(3));
            tablesFound.add(rs.getString(3));
        }
        if(chkTables()) setup(conn);
        rs.close();
        conn.close();
        
    }
    
    public boolean chkTables(){
        boolean runCreate=false;
        Iterator<String> core = coreTables.iterator();
        while(core.hasNext()){
            String val = core.next();
            if(tablesFound.contains(val)) core.remove();
        }
        if(coreTables.size() > 0) runCreate=true;
       
        return runCreate;
        
    }
    
    public void setup(Connection conn) throws Exception{
        createTables(conn);

        
        if(!tablesFound.contains(ACMS.AUTH_TABLE)){
            AccessAuth aa=new AccessAuth();
            AccessGroups ag=new AccessGroups();
            Auth auth=new Auth("Site Admin", "appd", ACMS.HASH1, 1, ACMS.GROUP_ADMIN);
            
            aa.addAuth(auth);
            
            /*
            Groups groups=new Groups(auth.getId(), ACMS.GROUP_ADMIN);
            ag.addGroup(groups);
            */
            
        }
        if(!tablesFound.contains(ACMS.CONFIG_TABLE)){
            
            Config configValue=new Config(ACMS.VERSION,ACMS.VERSION_V);
            AccessConfig ac=new AccessConfig();
            
            ac.addConfig(configValue);
            Config configValue1=new Config(ACMS.BASE_DIRECTORY,ACMS.BASE_DIRECTORY_V);
            ac.addConfig(configValue1);
        }

        
    }
    
    public void createTables(Connection conn) throws Exception{
        PreparedStatement ps=null;
        for(int i =0; i < coreTables.size(); i++){
            log.log(Level.INFO,new StringBuilder().append("Creating table ")
                    .append(coreTables.get(i)).append(" with query ")
                    .append(ACMS.getCreateTableQuery(coreTables.get(i))).toString());
            ps = conn.prepareStatement(ACMS.getCreateTableQuery(coreTables.get(i)));
            ps.execute();
        }
        ps.close();
    }
    
    
}
