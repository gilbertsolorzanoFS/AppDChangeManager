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
public class CnConnection {
    private Long id,authId,controllerId;
    private String displayName;

    public CnConnection(){}
    
    public CnConnection( String displayName, Long authId, Long controllerId){
        this.displayName=displayName;
        this.authId=authId;
        this.controllerId=controllerId;
    }
    
    public CnConnection(Long id, String displayName, Long authId, Long controllerId){
        this.id=id;
        this.displayName=displayName;
        this.authId=authId;
        this.controllerId=controllerId;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public Long getControllerId() {
        return controllerId;
    }

    public void setControllerId(Long controllerId) {
        this.controllerId = controllerId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    
    
    @Override
    public String toString(){
        StringBuilder bud = new StringBuilder();
        bud.append(ACMS._V(0,ACMS.CONNECTION));
        bud.append(ACMS._V(1, ACMS.ID,id));
        bud.append(ACMS._V(1,ACMS.DISPLAY_NAME, displayName));
        bud.append(ACMS._V(1, ACMS.CONTROLLER_ID,controllerId));
        bud.append(ACMS._V(1, ACMS.AUTHENTICATION_ID, authId));
        return bud.toString();
    }
    
    public String toErrorHelp(){
        StringBuilder bud = new StringBuilder();
        bud.append("Attempted to add a dup controllerId: ").append(controllerId)
                .append(", authId: ").append(authId);
        return bud.toString();
    }
    
}
