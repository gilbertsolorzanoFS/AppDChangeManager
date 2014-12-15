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
public class Groups {
    private Long id,userId;
    private String groupName;
    
    public Groups(){}
    
    public Groups(Long id, Long userId, String groupName){
        this.id=id;
        this.userId=userId;
        this.groupName=groupName;
    }
    
    public Groups(Long userId, String groupName){
        this.userId=userId;
        this.groupName=groupName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    @Override
    public String toString(){
        StringBuilder bud = new StringBuilder();
        bud.append(ACMS._V(0,ACMS.GROUP));
        bud.append(ACMS._V(1, ACMS.ID,id));
        bud.append(ACMS._V(1, ACMS.GROUP_NAME,groupName));
        bud.append(ACMS._V(1, ACMS.USER_ID,userId));
        return bud.toString();
    }
    
    public String toErrorHelp(){
        StringBuilder bud = new StringBuilder();
        bud.append("Attempted to add a dup groupName: ").append(groupName)
                .append(", userId: ").append(userId);
        return bud.toString();
    }
    
}
