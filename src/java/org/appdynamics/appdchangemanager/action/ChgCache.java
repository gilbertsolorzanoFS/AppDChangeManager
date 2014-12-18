/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.action;

import org.appdynamics.appdchangemanager.conn.*;
import org.appdynamics.appdchangemanager.data.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilbert.solorzano
 * 
 * This is going to be the cache for :
 * CnConnection
 * Controllers
 * ItemLocations
 * 
 * These should not have many items
 * 
 */
public class ChgCache {
    private static final Logger log=Logger.getLogger(ChgCache.class.getName());
    private static final HashMap<String,ItemLocation> locations=new HashMap<String,ItemLocation>();
    private static final HashMap<Long,Controller> controllers = new HashMap<Long,Controller>();
    private static final HashMap<Long,CnConnection> connections = new HashMap<Long,CnConnection>();
    private static final HashMap<Long,Auth> controllerAuths = new HashMap<Long,Auth>();
    private static final HashMap<Long,Request> liveRequests = new HashMap<Long,Request>();
    private static final HashMap<Long,Change> liveChanges = new HashMap<Long,Change>();

    
    private static final AccessAuth auth=new AccessAuth();
    private static final AccessConnection conn=new AccessConnection();
    private static final AccessControllers contr=new AccessControllers();
    private static final AccessItemLocation loc=new AccessItemLocation();
    private static final AccessRequests req = new AccessRequests();
    private static final AccessChanges chg = new AccessChanges();
    
    private static boolean setup=false;
    
    public ChgCache(){
        if(!setup){
            init();
        }
    }
    
    
    private static synchronized void init(){
        if(setup) return;
        
        log.info("Loading auths cache ...");
        
        ArrayList<Auth> auths = auth.getAllAuth();
        for(Auth val:auths) controllerAuths.put(val.getId(), val);
        auths=null;
        
        log.info("Loading location cache ...");
        
        ArrayList<ItemLocation> locs = loc.getAllItemLocation();
        for(ItemLocation val:locs){
            StringBuilder name=new StringBuilder();
            name.append(val.getController()).append(".").append(val.getApplication());
            if(val.getLocationType() == 1){
                name.append(".").append(val.getTier());    
            }
             locations.put(name.toString(), val);
        }
        locs=null;
        
        log.info("Loading connection cache ...");
        ArrayList<CnConnection> conns=conn.getAllCnConnection();
        for(CnConnection val: conns) connections.put(val.getId(), val);
        conns=null;
        
        log.info("Loading contr cache ...");
        ArrayList<Controller> _contr = contr.getAllControllers();
        for(Controller val:_contr) controllers.put(val.getId(), val);
        _contr =null;
        
        log.info("Loading change cache ...");
        ArrayList<Change> _allChg=chg.getAllChange();
        for(Change val:_allChg){
            if(!val.isCompleted()){
                liveChanges.put(val.getId(), val);
            }
        }
        _allChg=null;
        
        log.info("Loading request cache ...");
        ArrayList<Request> _allReq=req.getAllRequest();
        for(Request val:_allReq){
            if(liveChanges.containsKey(val.getChangeId())){
                liveRequests.put(val.getId(), val);
            }
        }
        _allReq=null;
        
        log.info("Completed loading cache.");
        setup=true;
    }
    
    public static synchronized CnConnection getCnConnection(Long id){
        if(connections.containsKey(id)) return connections.get(id);
        
        // We don't have it, check the database
        CnConnection cn=conn.getCnConnection(id);
        connections.put(id, cn);
        return cn;
    }
    
    public static synchronized CnConnection updateConnection(CnConnection cn){
        conn.updateCnConnection(cn);
        connections.put(cn.getId(),cn);
        return cn;
    }
    
    public static synchronized void deleteConnection(CnConnection cn){
        conn.deleteCnConnection(cn);
        connections.remove(cn.getId());
        
    }
    
    public static synchronized Controller getController(Long id){
        if(controllers.containsKey(id)) return controllers.get(id);
        
        Controller cn=contr.getSingleControllers(id);
        controllers.put(id,cn);
        return cn;
    } 
    
    public static synchronized Controller updateController(Controller cn){
        contr.updateController(cn);
        controllers.put(cn.getId(), cn);
        return cn;
    }
    
    public static synchronized void deleteController(Controller cn){
        contr.deleteController(cn.getId());
        controllers.remove(cn.getId());
    }
    
    public static synchronized ItemLocation getItemLocation(String controller,String application, String tier){
        // Fqdn, application
        ItemLocation item=null;
        StringBuilder name=new StringBuilder();
        name.append(controller).append(".").append(application);
        if(tier != null){
            name.append(".").append(tier);    
        }
        
        // We found, great
        if(locations.containsKey(name.toString())) return locations.get(name.toString());
        
        if(tier != null){
            item=loc.getSingleItemLocationOrAdd(controller, application, tier);
        }else{
            item=loc.getSingleItemLocationOrAdd(controller,application);
        }
        
        locations.put(name.toString(),item);
        
        return item;
    }
    
    public static synchronized ItemLocation updateItemLocation(String controller, String application, String tier){
        ItemLocation item=null;
        StringBuilder name=new StringBuilder();
        name.append(controller).append(".").append(application);
        if(tier != null){
            name.append(".").append(name);    
        }
        
        //if(locations.containsKey(name.toString())) return locations.get(name.toString());
        
        if(tier != null){
            //item=loc(controller, application, tier);
        }else{
            item=loc.getSingleItemLocationOrAdd(controller,application);
        }
        
        locations.put(name.toString(),item);
        
        return item;
    }
    
    public static synchronized Request getLiveRequest(Long id){
        
        if(liveRequests.containsKey(id)) return liveRequests.get(id);
        
        Request _req = req.getSingleRequest(id);
        liveRequests.put(id, _req);
        return _req;
    }
    
    public static synchronized ArrayList<Request> getLiveRequestForChange(Long id){
        ArrayList<Request> reqs = new ArrayList<Request>();
        for(Request _req: liveRequests.values()) if(_req.getChangeId() == id) reqs.add(_req);
        
        if(reqs.isEmpty()) return req.getChgRequest(id);
        
        return reqs;
    }
    
    public static synchronized Request addLiveRequest(Request _req){
        req.addRequests(_req);
        if(_req.getId() != -1L){
            liveRequests.put(_req.getId(),_req);
        }
        return _req;
    }
    
    public static synchronized Request updateLiveRequest(Request _req){
        liveRequests.put(_req.getId(),_req);
        req.updateRequest(_req);
        return _req;
    }
    
    
    
    public static synchronized void deleteLiveRequest(Request _req){
        liveRequests.remove(_req.getId());
        req.deleteRequest(_req.getId());
    }
    
    public static synchronized void deleteLiveRequestFromChg(Long id){
        java.util.Iterator<Request> _reqIt = liveRequests.values().iterator();
        while(_reqIt.hasNext()){
            Request _req=_reqIt.next();
            if(_req.getChangeId()==id){
                req.deleteRequest(_req.getId());
                _reqIt.remove();
            }
        }
    }
    
    public static synchronized void deleteLiveRequest(Long id){
        liveRequests.remove(id);
        req.deleteRequest(id);
    }
    
    public static synchronized ArrayList<Change> getLiveChanges(){
        ArrayList<Change> chgs = new ArrayList<Change>();
        chgs.addAll(liveChanges.values());
        return chgs;
    }
    
    public static synchronized Change addLiveChange(Change _chg){
        chg.addChange(_chg);
        if(_chg.getId() != -1L) liveChanges.put(_chg.getId(), _chg);
        return _chg;
    }
    
    public static synchronized Change updateLiveChange(Change _chg){
        chg.addUpdate(_chg);
        if(_chg.isCompleted()){
            liveChanges.remove(_chg.getId());
        }else{
            liveChanges.put(_chg.getId(), _chg);
        }
        return _chg;
    }
    
    // type 0 * readyForApproval (true,false)
    // type 1 * approved (true,false);
    public static synchronized ArrayList<Change> getChanges(int type, boolean on){
        ArrayList<Change> chgs=new ArrayList<Change>();
        switch(type){
            case 0:
                    for(Change _chg:liveChanges.values()) if(_chg.isReadyForApproval() == on) chgs.add(_chg);
                    break;
            case 1:
                    for(Change _chg:liveChanges.values()) if(_chg.isApproved() == on) chgs.add(_chg);
                    break;
            default:
                break;
        }
        return chgs;
        
    }
    
    public static synchronized Change getChange(Long id){
        Change _chg=liveChanges.get(id);
        if(_chg == null) _chg=chg.getSingleChange(id);
        
        return _chg;
    }
    
    public static synchronized void deleteLiveChange(Change _chg){
        deleteLiveRequestFromChg(_chg.getId());
        
        chg.deleteChange(_chg);
        liveChanges.remove(_chg.getId());
    }
    
    public static synchronized void deleteLiveChange(Long id){
        Change _chg = liveChanges.get(id);
        deleteLiveRequestFromChg(id);
        chg.deleteChange(_chg);
        liveChanges.remove(_chg.getId());
    }
    
    public static synchronized ArrayList<Change> getCompletedChanges(){
        return chg.getCompletedChange(true);
    }
    
    
}
