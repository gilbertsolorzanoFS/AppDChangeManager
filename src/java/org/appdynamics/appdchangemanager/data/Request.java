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
 * This class will be used to hold a request
 * 
 * What can we change:
 *    srcConnection
 *    destConnection
 *    srcItemLocation
 *    destItemLocation
 *    itemName
 *    itemType
 *      
 *    For now we are not going to update allow the different destination itemName.
 * 
 */
public class Request {
    private Long id=-1L,changeId=-1L,executionTime=-1L,completionTime=-1L;
    private ItemLocation srcItemLocation,destItemLocation;
    private String itemName,itemType, newVersion,origVersion;
    private String newVersionSrc,origVersionSrc,completedBy;
    private CnConnection srcConn,destConn;
    private boolean success=false;
    
    public Request(){}
    
    public Request(String itemName, String itemType, ItemLocation srcItemLocation, 
            ItemLocation destItemLocation, CnConnection srcConn, CnConnection destConn){
        this.itemName=itemName;
        this.itemType=itemType;
        this.srcItemLocation=srcItemLocation;
        this.destItemLocation=destItemLocation;
        this.srcConn=srcConn;
        this.destConn=destConn;
    }
    
    public Request(Long changeId, String itemName, String itemType, ItemLocation srcItemLocation, 
            ItemLocation destItemLocation, CnConnection srcConn, CnConnection destConn){
        this.changeId=changeId;
        this.itemName=itemName;
        this.itemType=itemType;
        this.srcItemLocation=srcItemLocation;
        this.destItemLocation=destItemLocation;
        this.srcConn=srcConn;
        this.destConn=destConn;
    }
    
    public Request(Long changeId, String itemName, String itemType, ItemLocation srcItemLocation, 
            ItemLocation destItemLocation, String newVersion, String origVersion, String newVersionSrc, String origVersionSrc, 
            Long executionTime, String completedBy, Long completionTime, boolean success ){
        this.changeId=changeId;
        this.itemName=itemName;
        this.itemType=itemType;
        this.srcItemLocation=srcItemLocation;
        this.destItemLocation=destItemLocation;
        this.newVersion=newVersion;
        this.origVersion=origVersion;
        this.newVersionSrc=newVersionSrc;
        this.origVersionSrc=origVersionSrc;
        this.executionTime=executionTime;
        this.completedBy=completedBy;
        this.completionTime=completionTime;
        this.success=success;
    }
    
    public Request(Long id, Long changeId, String itemName, String itemType, ItemLocation srcItemLocation, 
            ItemLocation destItemLocation, String newVersion, String origVersion, String newVersionSrc, String origVersionSrc, 
            Long executionTime, String completedBy, Long completionTime, boolean success ){
        this.id=id;
        this.changeId=changeId;
        this.itemName=itemName;
        this.itemType=itemType;
        this.srcItemLocation=srcItemLocation;
        this.destItemLocation=destItemLocation;
        this.newVersion=newVersion;
        this.origVersion=origVersion;
        this.newVersionSrc=newVersionSrc;
        this.origVersionSrc=origVersionSrc;
        this.executionTime=executionTime;
        this.completedBy=completedBy;
        this.completionTime=completionTime;
        this.success=success;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChangeId() {
        return changeId;
    }

    public void setChangeId(Long changeId) {
        this.changeId = changeId;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public Long getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Long completionTime) {
        this.completionTime = completionTime;
    }

    public ItemLocation getSrcItemLocation() {
        return srcItemLocation;
    }

    public void setSrcItemLocation(ItemLocation srcItemLocation) {
        this.srcItemLocation = srcItemLocation;
    }

    public ItemLocation getDestItemLocation() {
        return destItemLocation;
    }

    public void setDestItemLocation(ItemLocation destItemLocation) {
        this.destItemLocation = destItemLocation;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getOrigVersion() {
        return origVersion;
    }

    public void setOrigVersion(String origVersion) {
        this.origVersion = origVersion;
    }

    public String getNewVersionSrc() {
        return newVersionSrc;
    }

    public void setNewVersionSrc(String newVersionSrc) {
        this.newVersionSrc = newVersionSrc;
    }

    public String getOrigVersionSrc() {
        return origVersionSrc;
    }

    public void setOrigVersionSrc(String origVersionSrc) {
        this.origVersionSrc = origVersionSrc;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public CnConnection getSrcConn() {
        return srcConn;
    }

    public void setSrcConn(CnConnection srcConn) {
        this.srcConn = srcConn;
    }

    public CnConnection getDestConn() {
        return destConn;
    }

    public void setDestConn(CnConnection destConn) {
        this.destConn = destConn;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    
    @Override
    public String toString(){
        StringBuilder bud = new StringBuilder();
        
        bud.append(ACMS._V(0,ACMS.REQUEST));
        bud.append(ACMS._V(1, ACMS.ID,id));
        bud.append(ACMS._V(1, ACMS.CHANGE_ID, changeId));
        bud.append(ACMS._V(1, ACMS.ITEM_NAME, itemName));
        bud.append(ACMS._V(1, ACMS.ITEM_TYPE, itemType));
        bud.append(ACMS._V(2, ACMS.SRC_CONNECTION));bud.append(srcConn);
        
        bud.append(srcItemLocation);
        bud.append(ACMS._V(2, ACMS.DEST_CONNECTION));bud.append(destConn);
        bud.append(destItemLocation);
        bud.append(ACMS._V(1, ACMS.NEW_VERSION, newVersion));
        bud.append(ACMS._V(1, ACMS.ORIG_VERSION, origVersion));
        bud.append(ACMS._V(1, ACMS.NEW_VERSION_SRC, newVersionSrc));
        bud.append(ACMS._V(1, ACMS.ORIG_VERSION_SRC, origVersionSrc));
        bud.append(ACMS._V(1, ACMS.EXECUTION_TIME, executionTime));
        bud.append(ACMS._V(1, ACMS.COMPLETED_BY, completedBy));
        bud.append(ACMS._V(1, ACMS.COMPLETION_TIME, completionTime));
        bud.append(ACMS._V(1, ACMS.SUCCESS, success));
        return bud.toString();
    }
    
    public String toErrorHelp(){
        StringBuilder bud = new StringBuilder();
        bud.append("Attempted to add a dup itemName: ").append(itemName)
                .append(", itemType: ").append(itemType).append(", changeId: ").append(changeId);
        return bud.toString();
    }
    
    
}
