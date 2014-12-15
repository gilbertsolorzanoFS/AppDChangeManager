/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.conn;

import org.appdynamics.appdchangemanager.action.Crypto;
import org.appdynamics.appdchangemanager.data.*;
import org.appdynamics.appdchangemanager.resources.ACMS;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilbert.solorzano
 */
public class ChkTables {
    private static final Logger log=Logger.getLogger(CreateTables.class.getName());
    
    
    public ChkTables(){init();}
    
    public void init(){
        Auth auth1=new Auth("admin1", "admin1", Crypto.crypt("admin1-password"), 0,ACMS.GROUP_ADMIN);
        Auth auth2=new Auth("admin2", "admin2", Crypto.crypt("admin2-password"), 0,ACMS.GROUP_ADMIN);
        Auth auth3=new Auth("user3", "admin3", Crypto.crypt("admin3-password"), 1, ACMS.GROUP_USER);
        
        log.log(Level.INFO,"Creating auths");
        AccessAuth ac=new AccessAuth();
        ac.addAuth(auth1);
        ac.addAuth(auth2);
        ac.addAuth(auth3);
        
        Controller cnt1=new Controller("controller1.acme.com", "controller1", "acme1", "8993", true);
        Controller cnt2=new Controller("controller2.acme.com", "controller2", "acme2", "8993", true);
        Controller cnt3=new Controller("controller3.acme.com", "controller3", "acme3", "8993", true);
        
        AccessControllers cc=new AccessControllers();
        cc.addController(cnt1);
        cc.addController(cnt2);
        cc.addController(cnt3);
        
        Groups grp1=new Groups(auth2.getId(),ACMS.GROUP_ADMIN);
        Groups grp2=new Groups(auth1.getId(),ACMS.GROUP_ADMIN);
        Groups grp3=new Groups(auth3.getId(),ACMS.GROUP_USER);
        
        AccessGroups gc=new AccessGroups();
        gc.addGroup(grp1);
        gc.addGroup(grp2);
        gc.addGroup(grp3);
        
        
        ItemLocation item1=new ItemLocation("controller1","Application 1",0);
        ItemLocation item2=new ItemLocation("controller2","Application 1","tier1",1);
        ItemLocation item3=new ItemLocation("controller3","Application 2","tier1",1);
        
        AccessItemLocation ic=new AccessItemLocation();
        ic.addItemLocation(item1);
        ic.addItemLocation(item2);
        ic.addItemLocation(item3);
        
        
        AccessConnection cc1=new AccessConnection();
        
        //displayName,authId,controllerId
        CnConnection cnn1=new CnConnection("ControllerAdmin", auth1.getId(), cnt1.getId());
        CnConnection cnn2=new CnConnection("WebUser1", auth2.getId(), cnt2.getId());
        CnConnection cnn3=new CnConnection("WebUser4", auth3.getId(), cnt3.getId());
        
        cc1.addCnConnection(cnn1);
        cc1.addCnConnection(cnn2);
        
        //String itemName, String itemType, ItemLocation srcItemLocation,ItemLocation destItemLocation, CnConnection srcConn, CnConnection destConn
        Request req1=new Request("pojo1","custom-match-rule",item1,item2,cnn1,cnn2);
        Request req2=new Request("Bt01","custom-match-rule",item1,item3,cnn1,cnn1);
        Request req3=new Request("Check Me","health-rule",item1,item2,cnn3,cnn2);
        Request req4=new Request("Check that","health-rule",item3,item2,cnn2,cnn2);
        Request req5=new Request("List Prices","custom-match-rule",item3,item1,cnn3,cnn1);
        Request req6=new Request("Servlet 1","custom-match-rule",item1,item2,cnn2,cnn2);
        
        log.log(Level.INFO,"Creating Requests");
        
        AccessRequests rr=new AccessRequests();
        rr.addRequests(req1);
        rr.addRequests(req2);
        rr.addRequests(req3);
        rr.addRequests(req4);
        rr.addRequests(req5);
        rr.addRequests(req6);
        
        ArrayList<Request> reqs1=new ArrayList<Request>();
        reqs1.add(req1);
        reqs1.add(req2);
        reqs1.add(req3);
        
        ArrayList<Request> reqs2=new ArrayList<Request>();
        reqs2.add(req4);
        reqs2.add(req5);
        reqs2.add(req6);
        
        log.log(Level.INFO,"Creating Changes");
        //Long requestTime, String requester, String descr
        Change chg1=new Change(1000L,"Dave","This is a change");
        Change chg2=new Change(1001L,"Joe","This is another change");
        chg1.setRequests(reqs1);
        chg2.setRequests(reqs2);
        
        AccessChanges ac1=new AccessChanges();
        log.log(Level.INFO,"Saving change 1");
        ac1.addChange(chg1);
        log.log(Level.INFO,"Saving change 2");
        ac1.addChange(chg2);
        
        
        log.log(Level.INFO,"Done with Tasks");
    }
    
}
