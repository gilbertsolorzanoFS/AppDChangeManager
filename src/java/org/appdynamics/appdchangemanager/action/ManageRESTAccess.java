/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.action;

import org.appdynamics.appdchangemanager.data.CnConnection;
import org.appdynamics.appdchangemanager.conn.AccessRest;
import org.appdynamics.appdrestapi.RESTAccess;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;

/**
 *
 * @author gilbert.solorzano
 */
public class ManageRESTAccess {
    private static HashMap<String,RESTAccess> rests=new HashMap<String,RESTAccess>();
    private static final Logger log=Logger.getLogger(ManageRESTAccess.class.getName());
    
    public static RESTAccess getRESTAccess(CnConnection conn){
        if(rests.containsKey(conn.getDisplayName())) return rests.get(conn.getDisplayName());
        return newRESTAccess(conn);
    }
    
    private static synchronized RESTAccess newRESTAccess(CnConnection conn){
            
        if(rests.containsKey(conn.getDisplayName())) return rests.get(conn.getDisplayName());

        log.log(Level.INFO, new StringBuilder().append("Added RESTAccess for ")
                .append(conn.getDisplayName()).append(".").toString());
        
        rests.put(conn.getDisplayName(), AccessRest.getRestAccess(conn));
        
        return rests.get(conn.getDisplayName());
        
    }
    
}
