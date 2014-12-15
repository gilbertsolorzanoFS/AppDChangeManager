/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.action;

import org.appdynamics.appdchangemanager.data.CnConnection;
import org.appdynamics.appdchangemanager.data.ItemLocation;
import org.appdynamics.appdchangemanager.data.ExpiredAppDItems;
import org.appdynamics.appdchangemanager.resources.ACMS;

import org.appdynamics.appdrestapi.RESTAccess;
import org.appdynamics.appdrestapi.data.*;
import org.appdynamics.appdrestapi.exportdata.*;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilbert.solorzano
 */
public class ManageAppDItems {
    private static final Logger log=Logger.getLogger(ManageSessions.class.getName());
    private static ArrayList<ExpiredAppDItems> appDItems=new ArrayList<ExpiredAppDItems>();
    
    public ManageAppDItems(){}
    
    
    public int getNumberOfItems(){
        return appDItems.size();
    }
    
    public synchronized String[] getItemList(CnConnection con, boolean force){
        boolean valid=false;
        String[] items=null;
        String name=con.getDisplayName();
        
        
        boolean fnd=false;
            for(int i=0; i < appDItems.size();i++){
                ExpiredAppDItems item=appDItems.get(i);
                if(item.getName().equals(name)){
                    // We found it, now what ?
                    item.check();
                    if(item.isInvalid() || force){
                        items=getItems(name,ACMS.R_ITEM_TYPE_NAME[3],null,con);
                        appDItems.set(i, new ExpiredAppDItems(name,items));
                        return items;
                    }else{
                        return item.getValues();
                    }

                }
            }
            if(!fnd){
                items=getItems(name,ACMS.R_ITEM_TYPE_NAME[3],null,con);
                appDItems.add(new ExpiredAppDItems(name,items));
                return items;
            }
        
        // We should not reach this
        return items;
    }
    
    /*
     * 
     */
    public synchronized String[] getItemList(String type, ItemLocation loc, CnConnection con, boolean force){
        boolean valid=false;
        String[] items=null;
        String name=null;
        
        if(loc.getLocationType() == 0){
            name=new StringBuilder().append(loc.getController()).append(ACMS._R)
                    .append(type).append(ACMS._R).append(loc.getApplication()).toString();
        }else{
            name=new StringBuilder().append(loc.getController()).append(ACMS._R)
                    .append(type).append(ACMS._R).append(loc.getApplication())
                    .append(ACMS._R).append(loc.getTier()).toString();
        }
        
        boolean fnd=false;
        for(int i=0; i < appDItems.size();i++){
            ExpiredAppDItems item=appDItems.get(i);
            if(item.getName().equals(name)){
                // We found it, now what ?
                item.check();
                if(item.isInvalid() || force){
                    items=getItems(name,type,loc,con);
                    appDItems.set(i, new ExpiredAppDItems(name,items));
                    return items;
                }else{
                    return item.getValues();
                }

            }
        }
        if(!fnd){
            items=getItems(name,type,loc,con);
            appDItems.add(new ExpiredAppDItems(name,items));
            return items;
        }
        
        // We should not reach this
        return items;
    }
    
    private String[] getItems(String name, String type, ItemLocation loc, CnConnection con){
        RESTAccess access = ManageRESTAccess.getRESTAccess(con);
        if(type.equals(ACMS.R_ITEM_TYPE_NAME[0])) return getAutoDiscovery(loc,access);
        if(type.equals(ACMS.R_ITEM_TYPE_NAME[1])) return getCustomMatch(loc,access);
        if(type.equals(ACMS.R_ITEM_TYPE_NAME[2])) return getHealthRules(loc,access);
        if(type.equals(ACMS.R_ITEM_TYPE_NAME[3])) return getApplications(access);
        if(type.equals(ACMS.R_ITEM_TYPE_NAME[4])) return getTiers(loc, access);
        return ACMS.NA;
    }
    
    private String[] getHealthRules(ItemLocation loc, RESTAccess access){
        HealthRules items = access.getRESTHealthRuleObjExportAll(loc.getApplication());
        if(items != null){
            String[] vals=new String[items.getHealthRules().size()];
            for(int i=0; i < items.getHealthRules().size(); i++){
                vals[i]=items.getHealthRules().get(i).getName();
            }
            
            return vals;
        }
        return ACMS.NA;
    }
    
    private String[] getAutoDiscovery(ItemLocation loc, RESTAccess access){
        if(loc.getLocationType()==0){
            AutoDiscoveryConfig items = access.getRESTExportOfAutoObj(loc.getApplication());
            if(items != null){
                //log.info(items.toString());
                String[] vals = new String[items.getAutoDiscovery().size()];
                for(int i =0; i < items.getAutoDiscovery().size(); i++){
                    vals[i]=items.getAutoDiscovery().get(i).getName();
                }
                return vals;
            }
        }else{
             AutoDiscoveryConfig items = access.getRESTExportOfAutoObj(loc.getApplication(),loc.getTier(),null);
             if(items != null){
                 //log.info(items.toString());
                String[] vals = new String[items.getAutoDiscovery().size()];
                for(int i =0; i < items.getAutoDiscovery().size(); i++){
                    vals[i]=items.getAutoDiscovery().get(i).getName();
                }
                return vals;
            }
        }
        return ACMS.NA;
    }
    
    private String[] getCustomMatch(ItemLocation loc, RESTAccess access){
        if(loc.getLocationType()==0){
            
            CustomMatchPoints items = access.getRESTCustomPojoExportAllObj(loc.getApplication());
            if(items != null){
                String[] vals=new String[items.getCustomMatchPoints().size()];
                for(int i =0; i< items.getCustomMatchPoints().size();i++){
                    vals[i]=items.getCustomMatchPoints().get(i).getName();
                }
                return vals;
            }
        }else{
            CustomMatchPoints items = access.getRESTCustomPojoExportAllObj(loc.getApplication(), loc.getTier());
            if(items != null){
                String[] vals=new String[items.getCustomMatchPoints().size()];
                for(int i =0; i< items.getCustomMatchPoints().size();i++){
                    vals[i]=items.getCustomMatchPoints().get(i).getName();
                }
                return vals;
            }
        }
        return ACMS.NA;
    }
    
    private String[] getApplications(RESTAccess access){
        Applications apps = access.getApplications();
        if(apps != null){
            String[] app = new String[apps.getApplications().size()];
            for(int i = 0; i < apps.getApplications().size();i++){
                app[i]= new StringBuilder().append(apps.getApplications().get(i).getId())
                        .append(ACMS._P).append(apps.getApplications().get(i).getName()).toString();
            }
            return app;
        }
        return ACMS.NA;
    }
    
    private String[] getTiers(ItemLocation loc, RESTAccess access){
            Tiers tiers = access.getTiersForApplication(loc.getApplication());
            if(tiers != null){
                String[] tier = new String[tiers.getTiers().size()];
                for(int i=0; i < tiers.getTiers().size();i++) 
                    tier[i]=new StringBuilder().append(tiers.getTiers().get(i).getId())
                            .append(ACMS._P).append(tiers.getTiers().get(i).getName()).toString();
                
                return tier;
            }
            return ACMS.NA;
    }
    
    
}
