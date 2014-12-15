/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.data;

import java.util.ArrayList;
import org.appdynamics.appdchangemanager.resources.ACMS;



/**
 *
 * @author gilbert.solorzano
 * 
  create table CHANGE (
     id BIGINT PRIMARY KEY, requestTime BIGINT, completedTime BIGINT, requester VARCHAR(128), descr VARCHAR(255),
     approver varchar(128), approved smallint, rejected smallint, completed smallint
  ); 
 * 
 */
public class Change {
    private Long id=-1L, requestTime=-1L,completedTime=-1L;
    private String requester,descr,approver;
    private boolean approved,rejected,completed,readyForApproval;
    private ArrayList<Request> requests=new ArrayList<Request>();
    
    public Change(){}
    
    public Change(Long requestTime, String requester, String descr){
        this.requestTime=requestTime;
        this.requester=requester;
        this.descr=descr;
    }
    
    public Change(Long id, Long requestTime, Long completedTime, 
            String requester, String descr, String approver, 
            boolean approved, boolean rejected, boolean completed, boolean readyForApproval){
        this.id=id;
        this.requestTime=requestTime;
        this.completedTime=completedTime;
        this.requester=requester;
        this.descr=descr;
        this.approver=approver;
        this.approved=approved;
        this.rejected=rejected;
        this.completed=completed;
        this.readyForApproval=readyForApproval;
    }

    public Change(Long requestTime, Long completedTime, 
            String requester, String descr, String approver, 
            boolean approved, boolean rejected, boolean completed, boolean readyForApproval){

        this.requestTime=requestTime;
        this.completedTime=completedTime;
        this.requester=requester;
        this.descr=descr;
        this.approver=approver;
        this.approved=approved;
        this.rejected=rejected;
        this.completed=completed;
        this.readyForApproval=readyForApproval;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public Long getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Long completedTime) {
        this.completedTime = completedTime;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }

    public boolean isReadyForApproval() {
        return readyForApproval;
    }

    public void setReadyForApproval(boolean readyForApproval) {
        this.readyForApproval = readyForApproval;
    }

    
    
    @Override
    public String toString(){
        StringBuilder bud = new StringBuilder();
        bud.append(ACMS._V(0, ACMS.CHANGE));
        bud.append(ACMS._V(1, ACMS.ID,id));
        bud.append(ACMS._V(1,ACMS.REQUEST_TIME, requestTime));
        bud.append(ACMS._V(1,ACMS.REQUESTER, requester));
        bud.append(ACMS._V(1,ACMS.DESCRIPTION, descr));
        bud.append(ACMS._V(1,ACMS.APPROVER, approver));
        bud.append(ACMS._V(1,ACMS.APPROVED, approved));
        bud.append(ACMS._V(1,ACMS.COMPLETED, completed));
        bud.append(ACMS._V(1,ACMS.COMPLETION_TIME, completedTime));
        bud.append(ACMS._V(1,ACMS.REJECTED, rejected));
        bud.append(ACMS._V(1,ACMS.READY_FOR_APPROVAL, readyForApproval));
        for(Request req: requests)bud.append(req);
        return bud.toString();
    }
    
    public String toErrorHelp(){
        StringBuilder bud = new StringBuilder();
        bud.append("Attempted to add a dup requestTime: ").append(requestTime)
                .append(", requester: ").append(requester);
        return bud.toString();
    }
    
}
