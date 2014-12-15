/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.action;

import org.appdynamics.appdchangemanager.conn.AccessChanges;
import org.appdynamics.appdchangemanager.data.Change;
import org.appdynamics.appdchangemanager.data.Request;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilbert.solorzano
 * 
 * This is going to execute the change once it is approved.
 * 
 * Step 1. Create Access object for src
 * Step 2. Save source (object to save the source)
 * Step 3. Execute on destination
 * Step 5. Send event
 * 
 */
public class ProcessChange {    
    private static final Logger log=Logger.getLogger(ProcessChange.class.getName());


    /**
     * <p>
     * This is going to execute on a change
     * </p>
     * @param il
     * @return 
     */
    public static int executeChange(Change il){
        int retValue=0;
        StringBuilder eventPost=new StringBuilder();
        log.info("Processing change");
        eventPost.append("Change id: ").append(il.getId()).append(" -- ");
        
        // If we don't have any entries just stop.
        if(il.getRequests().isEmpty()) return 3;
        
        for(Request req: il.getRequests()){ 
            try{
                
                ManageItemRequests.processRequest(req); 
                eventPost.append(req.getItemType()).append(":").append(req.getItemName()).append(" -- ");
            }catch(Exception e){
                log.log(Level.SEVERE,new StringBuilder().append("Exception occurred with Request ")
                        .append(req.toString()).toString());
            }
        }
        
        // This is the readme.
        StringBuilder readMsg=new StringBuilder();
        readMsg.append("Processed changes for change object ").append(il.toString());
        
        ManageChgFiles.writeFile(il.getId(), readMsg.toString());
        
        ManageItemRequests.postChangeCompletion(il.getRequests().get(0), 
                new StringBuilder().append(eventPost.toString()).append(".").toString(), 
                new StringBuilder().append("Requested: ").append(il.getRequester())
                .append(" approver ").append(il.getApprover()).append(".").toString());
        
        
        return retValue;
    }
    
}
