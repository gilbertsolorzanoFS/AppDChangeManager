/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.conn;

import org.appdynamics.appdchangemanager.action.Crypto;
import org.appdynamics.appdchangemanager.data.CnConnection;
import org.appdynamics.appdchangemanager.data.Auth;
import org.appdynamics.appdchangemanager.data.Controller;

import org.appdynamics.appdrestapi.RESTAccess;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilbert.solorzano
 */
public class AccessRest {
    private static final Logger log=Logger.getLogger(AccessRest.class.getName());
    
    //This is going to return a REST Access when requested
    public static RESTAccess getRestAccess(CnConnection connection){
        RESTAccess access=null;
        AccessAuth aa=new AccessAuth();
        AccessControllers ac=new AccessControllers();
        
        Auth auth = aa.getSingleAuth(connection.getAuthId());
        Controller controller = ac.getSingleControllers(connection.getControllerId());
        
        String pwd = Crypto.decrypt(auth.getPassword());
        StringBuilder bud = new StringBuilder();
        bud.append("FQDN:").append(controller.getFqdn()).append(",PORT:").append(controller.getPort()).append(",isUseSSL: ").append(controller.isUseSSL());
        bud.append(",username:").append(auth.getUserName()).append(", pwd:").append(pwd).append(",account ").append(controller.getAccount());
        log.info(bud.toString());
        access = new RESTAccess(controller.getFqdn(), controller.getPort(), 
                controller.isUseSSL(), auth.getUserName(), pwd, 
                controller.getAccount());
        
        
        return access;
    }
}
