/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.action;

import org.appdynamics.appdchangemanager.data.ExpiredUser;
import org.appdynamics.appdchangemanager.resources.ACMS;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilbert.solorzano
 */
public class ManageSessions {
    private static final Logger log=Logger.getLogger(ManageSessions.class.getName());
    private static ArrayList<ExpiredUser> sessionsActive=new ArrayList<ExpiredUser>();
    
    public ManageSessions(){}
    
    
    public int getNumberOfSessions(){
        return sessionsActive.size();
    }
    
    /**
     * If the type == 0 then we are just adding 
     * 
     * @param session
     * @param userName
     * @param group
     * @param type
     * @return 
     */
    public synchronized boolean manageSession(String session, String userName, String group, int type){
        boolean valid=false;
        
        if(ACMS.DL > 0) log.log(Level.INFO,new StringBuilder().append("Session ").append(session).append(" userName ")
                .append(userName).append(" group ").append(group).append(" type ").append(type).toString());
        
        switch(type){
            case 0: // add
                    addSession(session,userName,group);
                    valid=true;
                break;
            case 1: // check
                    valid=checkSession(session,userName,group);
                break;
            case 2: // remove 
                    deleteSession(session,userName,group);
                break;
        }

        
        return valid;
    }
    
    /*
     *  This is going to be considered a safe operation so we don't have to worry much.
     */
    public synchronized boolean isAdminSession(String session, String userName){
        boolean valid=false;
        
        if(ACMS.DL > 0)log.info(new StringBuilder().append("Session ").append(session)
                .append(" userName ").append(userName).toString());
        
        for(int i=0; i < sessionsActive.size();i++){
            if(sessionsActive.get(i).sameSession(session, userName) 
                    && sessionsActive.get(i).getGroup().equals(ACMS.GROUP_ADMIN)) valid=true;
        }
        
        return valid;
    }
    
    private void addSession(String session, String userName, String group){
        log.log(Level.INFO, new StringBuilder().append("Adding session for ")
                .append(userName).append(" with session ").append(session)
                .append(".").toString());
        
        sessionsActive.add(new ExpiredUser(session,userName,group));
    }
    
    private boolean checkSession(String session,String userName, String group){
        boolean valid=false;
        Iterator<ExpiredUser> users=sessionsActive.iterator();
        while(users.hasNext()){
            ExpiredUser user = users.next();
            user.check();
            if(user.isInvalid()){ 
                users.remove();
                log.log(Level.INFO, new StringBuilder().append("Removing session for ")
                .append(userName).append(" with session ").append(session)
                .append(".").toString());
            }
            else{
                if(user.sameSession(session, userName)) valid=true;
            }
        }
        
        return valid;
    }
    
    private void deleteSession(String session,String userName, String group){

        Iterator<ExpiredUser> users=sessionsActive.iterator();
        while(users.hasNext()){
            ExpiredUser user = users.next();
                if(user.sameSession(session, userName)) users.remove();
            
        }

    }
    
}
