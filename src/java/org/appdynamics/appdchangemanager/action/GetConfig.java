/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.action;

import org.appdynamics.appdchangemanager.data.*;
import org.appdynamics.appdchangemanager.conn.*;

/**
 *
 * @author gilbert.solorzano
 */
public class GetConfig {
    
    public GetConfig(){}
    
    public ConfigPayload getAllConfig(){
        ConfigPayload cfg = new ConfigPayload();
        AccessAuth aa=new AccessAuth();
        AccessControllers ac=new AccessControllers();
        AccessConnection acc=new AccessConnection();
        AccessChanges ach=new AccessChanges();
        AccessGroups ag=new AccessGroups();
        AccessConfig acf = new AccessConfig();
        
        cfg.setAuths(aa.getAllAuth());
        cfg.setControllers(ac.getAllControllers());

        cfg.setConns(acc.getAllCnConnection());
        cfg.setConfigs(acf.getAllValues());
        
        return cfg;
    }
    
    
}
