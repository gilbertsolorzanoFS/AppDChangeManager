/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.action;

import org.appdynamics.appdchangemanager.data.ItemLocation;
import org.appdynamics.appdchangemanager.data.CnConnection;
import org.appdynamics.appdchangemanager.data.Request;
import org.appdynamics.appdchangemanager.resources.ACMS;

import org.appdynamics.appdrestapi.RESTAccess;
import org.appdynamics.appdrestapi.util.PostEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilbert.solorzano
 */
public class ManageItemRequests {
    private static final Logger log=Logger.getLogger(ManageItemRequests.class.getName());
    
    /**
     * <p>
     * This is going to start the process of the request first we are going to 
     * start with the saving the source and destination, the posting to the destination
     * </p>
     * @param request
     * @throws Exception 
     */
    public static void processRequest(Request request)throws Exception{
        //process the request
        log.info("Getting access. ");
        RESTAccess access=ManageRESTAccess.getRESTAccess(request.getSrcConn());
        String srcXml = getItem(request,request.getSrcItemLocation(),access);
        log.info("Retreived source, getting dest access.");
        access=ManageRESTAccess.getRESTAccess(request.getDestConn());
        
        String destXml = getItem(request,request.getDestItemLocation(),access);
        
        log.info("Retreived dest, posting the item.");
        postItem(request,request.getDestItemLocation(),srcXml,access);
        
        log.info("Writing the source file: " + srcXml);
        log.info("Writing the dest file: " + destXml);
        ManageChgFiles.writeFile("SRC", request.getChangeId(), request.getId(), srcXml);
        ManageChgFiles.writeFile("DEST", request.getChangeId(), request.getId(), destXml);
        log.info("Comparing files.");
        destXml = getItem(request,request.getDestItemLocation(),access);
        if(!destXml.equals(srcXml))throw new Exception(new StringBuilder()
                .append("After processing request: ").append(request.toErrorHelp())
                .append(". The source and destination did not match.").toString());
        //process the dest
    }
    
    
    
    public static String getItem(Request request, ItemLocation loc, RESTAccess access)throws Exception{
        //item location
        String xml=null;
        int itemT=0;
        for(int i = 0; i < ACMS.R_ITEM_TYPE_NAME.length; i++){
            if(ACMS.R_ITEM_TYPE_NAME[i].equals(request.getItemType()))itemT=i;
        }
        
        switch(itemT){
            case 0:
                xml=getAuto(request,loc,access);
                break;
            case 1:
                xml=getCustomPojo(request,loc,access);
                break;
            case 2:
                xml=getHealthRule(request,loc,access);
                break;
            default:
                break;
        }
        
        return xml;
    }
    
    public static void postItem(Request request, ItemLocation loc, String src, RESTAccess access)throws Exception{
        //item location & source text.
        //item location

        int itemT=0;
        for(int i = 0; i < ACMS.R_ITEM_TYPE_NAME.length; i++){
            if(ACMS.R_ITEM_TYPE_NAME[i].equals(request.getItemType()))itemT=i;
        }
        
        switch(itemT){
            case 0:
                postAuto(request,loc,src,access);
                break;
            case 1:
                postCustomPojo(request,loc,src,access);
                break;
            case 2:
                postHealthRule(request,loc,src,access);
                break;
            default:
                break;
        }
        

    }
    
    public static void postAuto(Request request, ItemLocation loc, String xml, RESTAccess access){
        if(loc.getLocationType() == 0){
            //case 1
            access.postRESTAutoSingle(loc.getApplication(), request.getItemName(), xml);

        }else{
            access.postRESTAutoSingle(loc.getApplication(), loc.getTier(), request.getItemName(), xml);
        }
    }
    
    public static String getAuto(Request request, ItemLocation loc, RESTAccess access){
        String xml=null;
        if(loc.getLocationType() == 0){
            //case 1
            xml=access.getRESTExportOfAuto(1, loc.getApplication(), request.getItemName());
        }else{
            xml=access.getRESTExportOfAuto(loc.getApplication(),loc.getTier(), request.getItemName());
        }
        
        return xml;
    }
    
    public static void postCustomPojo(Request request, ItemLocation loc, String xml, RESTAccess access){
        if(loc.getLocationType() == 0){
            //case 1
            access.postRESTCustomPojo(loc.getApplication(), request.getItemName(), xml);

        }else{
            access.postRESTCustomPojo(loc.getApplication(), loc.getTier(), request.getItemName(), xml);
        }
    }
    
    public static String getCustomPojo(Request request, ItemLocation loc, RESTAccess access){
        String xml=null;
        if(loc.getLocationType() == 0){
            //case 1
            xml=access.getRESTCustomPojoExport(loc.getApplication(), request.getItemName());
        }else{
            xml=access.getRESTCustomPojoExport(loc.getApplication(),loc.getTier(), request.getItemName());
        }
        
        return xml;
    }
    
    public static void postHealthRule(Request request, ItemLocation loc, String xml, RESTAccess access){
        access.postRESTHealthRule(loc.getApplication(), request.getItemName(), xml);
    }
    
    public static String getHealthRule(Request request, ItemLocation loc, RESTAccess access){
        String xml=null;

        // Only Health Rule level
        xml=access.getRESTHealthRuleExportSingle(loc.getApplication(),request.getItemName());

        
        return xml;
    }
    
    public static void postChangeCompletion(Request request, String summary, String content){
        RESTAccess access=ManageRESTAccess.getRESTAccess(request.getDestConn());
        PostEvent event = new PostEvent(summary,content);
        access.postRESTCustomEvent(request.getDestItemLocation().getApplication(), event);
    }
    
}
