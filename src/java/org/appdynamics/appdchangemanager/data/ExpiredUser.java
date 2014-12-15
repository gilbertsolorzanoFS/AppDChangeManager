/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.data;

import org.appdynamics.appdchangemanager.resources.ACMS;

/**
 *
 * @author gilbert.solorzano
 */
public class ExpiredUser {
    private String userName;
    private String session;
    private String group;
    private Long lastActivity;
    private boolean invalid=false;
    
    public ExpiredUser(){}
    
    /**
     * <p>
     *  This is going to create a session for a standard user.
     * </p>
     * @param session
     * @param userName 
     */
    public ExpiredUser(String session,String userName){
        this.session=session;
        this.userName=userName;
        this.group=ACMS.GROUP_USER;
        update();
    }
    
    public ExpiredUser(String session,String userName, String group){
        this.session=session;
        this.userName=userName;
        this.group=group;
        update();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Long lastActivity) {
        this.lastActivity = lastActivity;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    
    
    
    public boolean sameSession(String sessionChk, String userNameChk){
        boolean valid=false;        
        if(this.session.equals(sessionChk) && this.userName.equals(userNameChk)) valid=true;
        
        return valid;
    }
    
    public void check(){
        if(java.util.Calendar.getInstance().getTimeInMillis()-lastActivity > ACMS.TIMEOUT) invalid=true;
    }
    
    public void update(){
        lastActivity=java.util.Calendar.getInstance().getTimeInMillis();
    }
    
}
