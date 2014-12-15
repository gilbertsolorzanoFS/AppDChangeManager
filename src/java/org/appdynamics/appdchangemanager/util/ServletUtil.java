/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.util;

import org.appdynamics.appdchangemanager.resources.ACMS;


import javax.servlet.http.Cookie;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author gilbert.solorzano
 */
public class ServletUtil {
    
    public static Map<String,String> simpleCookies(Cookie[] cookies){
        Map<String,String> map= new HashMap<String,String>();
        for(Cookie cookie:cookies) map.put(cookie.getName(), cookie.getValue());
        return map;
    }
    
    public static Map<String,String> parameterMap(Map<String,String[]> map){
        Map<String,String> pMap = new HashMap<String,String>();
        
        java.util.Iterator<String> keys=map.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            if(map.get(key) != null && map.get(key).length > 0) pMap.put(key, map.get(key)[0]);
        }
        
        return pMap;
    }
    
    public static boolean chkBaseCookies(Map<String,String> map){
        boolean valid=false;
        
        if(map.containsKey(ACMS.USERNAME) && map.containsKey(ACMS.ROLENAME) )valid=true;
        
        return valid;
    }
    
    //type=add_auth&displayName="+dN+"&uName="+userName+"&uPass="+pass+"
    //&acctType="+iType+"&roleName="+role;
    public static boolean chkAddAuthParams(Map<String,String> map){
        boolean valid=false;
        boolean cont=false;
        
        if( map.containsKey(ACMS.DISPLAY_NAME) 
                && map.get(ACMS.DISPLAY_NAME) != null){ cont=true;}else{cont=false;}
        
        if(cont && map.containsKey(ACMS.USERNAME) 
                && map.get(ACMS.USERNAME) != null){ cont=true;}else{cont=false;}
        
        if(cont && map.containsKey(ACMS.PASSWORD) 
                && map.get(ACMS.PASSWORD) != null){ cont=true;}else{cont=false;}
        
        if(cont && map.containsKey(ACMS.ACCT_TYPE) 
                && map.get(ACMS.ACCT_TYPE) != null){ cont=true;}else{cont=false;}
        
        if(cont && map.containsKey(ACMS.ROLENAME) 
                && map.get(ACMS.ROLENAME) != null){ cont=true;}else{cont=false;}
        
        if(cont) valid=true;
        
        return valid;
    }
    
    //type=add_conn&controller="+contr_id+"&auth_id="+auth_id+"&displayName="+dN;
    public static boolean chkAddConnParams(Map<String,String> map){
        boolean valid=false;
        boolean cont=false;
        
        if(map.containsKey(ACMS.AUTHENTICATION_ID) 
                && map.get(ACMS.AUTHENTICATION_ID) != null){ cont=true;}else{cont=false;}
        
        if(cont && map.containsKey(ACMS.CONTROLLER_ID) 
                && map.get(ACMS.CONTROLLER_ID) != null){ cont=true;}else{cont=false;}
        
        if(cont && map.containsKey(ACMS.DISPLAY_NAME) 
                && map.get(ACMS.DISPLAY_NAME) != null){ cont=true;}else{cont=false;}
        
        if(cont) valid=true;
        
        return valid;
    }
    
    public static boolean chkAddConfParams(Map<String,String> map){
        boolean valid=false;
        if(map.containsKey(ACMS.DISPLAY_NAME) && map.containsKey(ACMS.VALUE)
                && map.get(ACMS.DISPLAY_NAME) != null 
                && map.get(ACMS.VALUE) != null) valid=true;
        
        return valid;
    }
    
    // "/AppDChangeManager/ConfigServlet?type=add_contr&fqdn="+fqdn+"
    //&port="+port+"&displayName="+dN+"&useSSL="+useSSL+"&account="+account;
    public static boolean chkAddContrParams(Map<String,String> map){
        boolean valid=false;
        boolean cont=false;
        
        if(map.containsKey(ACMS.DISPLAY_NAME) 
                && map.get(ACMS.DISPLAY_NAME) != null){ cont=true;}else{cont=false;}
        
        if(cont && map.containsKey(ACMS.FQDN) 
                && map.get(ACMS.FQDN) != null){ cont=true;}else{cont=false;}
        
        if(cont && map.containsKey(ACMS.PORT) 
                && map.get(ACMS.PORT) != null){ cont=true;}else{cont=false;}
        
        if(cont && map.containsKey(ACMS.ACCOUNT) 
                && map.get(ACMS.FQDN) != null){ cont=true;}else{cont=false;}
        
        if(cont && map.containsKey(ACMS.USESSL) 
                && map.get(ACMS.USESSL) != null){ cont=true;}else{cont=false;}
        
        if(cont) valid=true;
        
        return valid;
    }
    
    //type=updatw_auth&id="+id"&displayName="+dN+"&uName="+userName+"&uPass="+pass+"
    //&acctType="+iType+"&roleName="+role;
    public static boolean chkUpdateAuthParams(Map<String,String> map){
        boolean valid=false;
        try{
            Long val = new Long(map.get(ACMS.ID));
            valid=true;
        }catch(Exception e){  }
        return valid;
    }
    
    
    public static boolean chkUpdateConnParams(Map<String,String> map){
        boolean valid=false;
        try{
            Long val = new Long(map.get(ACMS.ID));
            valid=true;
        }catch(Exception e){  }
        return valid;
    }
    
    public static boolean chkUpdateConfParams(Map<String,String> map){
        boolean valid=false;
        if(map.containsKey(ACMS.DISPLAY_NAME) && map.containsKey(ACMS.VALUE)
                && map.get(ACMS.DISPLAY_NAME) != null 
                && map.get(ACMS.VALUE) != null) valid=true;
        
        return valid;
    }
    
    public static boolean chkUpdateContrParams(Map<String,String> map){
        boolean valid=false;
        try{
            Long val = new Long(map.get(ACMS.ID));
            valid=true;
        }catch(Exception e){  }
        return valid;
    }
    
    public static boolean chkDeleteAuthParams(Map<String,String> map){
        boolean valid=false;
        try{
            Long val = new Long(map.get(ACMS.ID));
            valid=true;
        }catch(Exception e){  }
        return valid;
    }
    
    
    public static boolean chkDeleteConnParams(Map<String,String> map){
        boolean valid=false;
        try{
            Long val = new Long(map.get(ACMS.ID));
            valid=true;
        }catch(Exception e){  }
        return valid;
    }
    
    public static boolean chkDeleteConfParams(Map<String,String> map){
        boolean valid=false;
        if(map.containsKey(ACMS.DISPLAY_NAME) && map.get(ACMS.DISPLAY_NAME) != null)
            valid=true;
        return valid;
    }
    
    public static boolean chkDeleteContrParams(Map<String,String> map){
        boolean valid=false;
        try{
            Long val = new Long(map.get(ACMS.ID));
            valid=true;
        }catch(Exception e){  }
        return valid;
    }
    
    public static boolean chkChangePasswdParams(Map<String,String> map){
        boolean valid=false;
        try{
            // We need the user id and the new password
            Long val = new Long(map.get(ACMS.ID));
            if(map.containsKey(ACMS.PASSWORD) && map.get(ACMS.PASSWORD) != null)
                valid=true;
        }catch(Exception e){  }
        return valid;
    }
    
    public static boolean getAppDAppsParams(Map<String,String> map){
        boolean valid=false;
        try{
            // We need the user id and the new password
            Long val = new Long(map.get(ACMS.ITEM_GETS[0]));
            valid=true;
        }catch(Exception e){  }
        return valid;
    }
    
    public static boolean getAppDTiersParams(Map<String,String> map){
        boolean valid=false;
        boolean cont=false;
        
        Long val = new Long(map.get(ACMS.ITEM_GETS[0]));
        if(val > 0)
            cont=true;
        
        if( cont && map.containsKey(ACMS.ITEM_GETS[1]) 
                && map.get(ACMS.ITEM_GETS[1]) != null){ cont=true;}else{cont=false;}
        
        
        if(cont) valid=true;
        
        return valid;
    }
    
    public static boolean getAppDAutoParams(Map<String,String> map){
        boolean valid=false;
        boolean cont=false;
        
        Long val = new Long(map.get(ACMS.ITEM_GETS[0]));
        if(val > 0)
            cont=true;
        
        if( cont && map.containsKey(ACMS.ITEM_GETS[1]) 
                && map.get(ACMS.ITEM_GETS[1]) != null){ cont=true;}else{cont=false;}
        
        
        if(cont) valid=true;
        
        return valid;
    }
   
    public static boolean getAppDCustomPojoParams(Map<String,String> map){
        boolean valid=false;
        boolean cont=false;
        
        Long val = new Long(map.get(ACMS.ITEM_GETS[0]));
        if(val > 0)
            cont=true;
        
        if( cont && map.containsKey(ACMS.ITEM_GETS[1]) 
                && map.get(ACMS.ITEM_GETS[1]) != null){ cont=true;}else{cont=false;}
        
        
        if(cont) valid=true;
        
        return valid;
    }
    
    public static boolean getAppDHealthRulesParams(Map<String,String> map){
        boolean valid=false;
        boolean cont=false;
        
        Long val = new Long(map.get(ACMS.ITEM_GETS[0]));
        if(val > 0)
            cont=true;
        
        if( cont && map.containsKey(ACMS.ITEM_GETS[1]) 
                && map.get(ACMS.ITEM_GETS[1]) != null){ cont=true;}else{cont=false;}
        
        
        if(cont) valid=true;
        
        return valid;
    }
}
