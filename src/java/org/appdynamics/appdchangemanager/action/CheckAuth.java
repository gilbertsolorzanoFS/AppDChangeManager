/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.action;

import org.appdynamics.appdchangemanager.conn.AccessAuth;
import org.appdynamics.appdchangemanager.conn.AccessGroups;
import org.appdynamics.appdchangemanager.data.Auth;
import org.appdynamics.appdchangemanager.data.Groups;
import org.appdynamics.appdchangemanager.resources.ACMS;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilbert.solorzano
 */
public class CheckAuth {
    private static final Logger log=Logger.getLogger(CheckAuth.class.getName());
    
    public static boolean checkUserPass(String user, String password){
        log.info(new StringBuilder().append("User: ").append(user).append(" - ").append(password).toString());
        boolean valid=false;
        if(user == null || password == null) return valid;
        
        Auth userAuth=getAuth(user);
        
        // We did not find the user
        if(userAuth == null  ) return valid;
        log.info(new StringBuilder().append("Found: ").append(Crypto.decrypt(userAuth.getPassword())).toString());
        //We found the 
        if(Crypto.decrypt(userAuth.getPassword()).equals(password)) valid=true;
        
        return valid;
    }
    

    public static Auth getAuth(String user){
        AccessAuth auth=new AccessAuth();
        Auth userAuth=auth.getSingleAuth(user);
        
        return userAuth;
    }
    
    public static String getUserGroup(Long userId){
        AccessAuth auth=new AccessAuth();
        Auth userAuth=auth.getSingleAuth(userId);
        
        if(userAuth != null){
            return userAuth.getGroupName();
        }
        
        return ACMS.GROUP_READ_ONLY;
    }
    
}
