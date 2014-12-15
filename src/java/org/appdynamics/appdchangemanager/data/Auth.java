/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.data;

import org.appdynamics.appdchangemanager.resources.ACMS;

/**
 *
 * @author gilbert.solorzano
 * 
 *  create table AUTH (id BIGINT PRIMARY KEY, displayName VARCHAR(128), userName VARCHAR(128), password VARCHAR(128), acctType SMALLINT);
 * 
 * acctType 0 is for controller;
 * acctType 1 is for siteAccounts
 */
public class Auth {
    private Long id;
    private String displayName,userName,password,groupName;
    private int acctType;
    
    public Auth(){}
    
    public Auth(Long id, String displayName, String userName, String password, int acctType, String groupName){
        this.id=id;
        this.displayName=displayName;
        this.userName=userName;
        this.password=password;
        this.acctType=acctType;
        this.groupName=groupName;
    }
    
    public Auth(String displayName, String userName, String password, int acctType, String groupName){
    
        this.displayName=displayName;
        this.userName=userName;
        this.password=password;
        this.acctType=acctType;
        this.groupName=groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    

    public int getAcctType() {
        return acctType;
    }

    public void setAcctType(int acctType) {
        this.acctType = acctType;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    @Override
    public String toString(){
        StringBuilder bud = new StringBuilder();
        bud.append(ACMS._V(0, ACMS.AUTHENTICATION));
        bud.append(ACMS._V(1,ACMS.ID, id));
        bud.append(ACMS._V(1,ACMS.DISPLAY_NAME, displayName));
        bud.append(ACMS._V(1,ACMS.USERNAME, userName));
        bud.append(ACMS._V(1,ACMS.PASSWORD, password));
        bud.append(ACMS._V(1,ACMS.ACCT_TYPE,acctType));
        return bud.toString();
    }
    
    public String toErrorHelp(){
        StringBuilder bud = new StringBuilder();
        bud.append("Attempted to add a dup userName: ").append(userName)
                .append(", acctType: ").append(acctType);
       
        return bud.toString();
    }
    
}
