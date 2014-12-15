/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.servlets;

import org.appdynamics.appdchangemanager.data.*;
import org.appdynamics.appdchangemanager.conn.*;
import org.appdynamics.appdchangemanager.action.Crypto;
import org.appdynamics.appdchangemanager.action.GetConfig;
import org.appdynamics.appdchangemanager.action.ManageSetup;
import org.appdynamics.appdchangemanager.resources.ACMS;
import org.appdynamics.appdchangemanager.util.ServletUtil;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.appdynamics.appdchangemanager.action.ManageSessions;



/**
 *
 * @author gilbert.solorzano
 */
@WebServlet(name = "ConfigServlet", urlPatterns = {"/ConfigServlet"})
public class ConfigServlet extends HttpServlet {
    private static final Logger log=Logger.getLogger(ConfigServlet.class.getName());
    private ManageSessions ms = new ManageSessions();
    private static final AccessAuth aa=new AccessAuth();
    private static final AccessConnection ac=new AccessConnection();
    private static final AccessControllers acr=new AccessControllers();
    private static final AccessConfig acg = new AccessConfig();
    private static final long serialVersionUUID = 1L;
    private static final Gson gson=new Gson();

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * We can allow the retreival 
         */
        if(! ACMS.READY_TO_RUN){ ManageSetup.init();}
        Map<String,String> pMap = ServletUtil.parameterMap(request.getParameterMap());
        Map<String,String> cookMap = ServletUtil.simpleCookies(request.getCookies());
        
        
        String type = (pMap.containsKey(ACMS.TYPE)) ? pMap.get(ACMS.TYPE):null;
        String id = (pMap.containsKey(ACMS.ID)) ? pMap.get(ACMS.ID):null;
   
        String user = (cookMap.containsKey(ACMS.USERNAME)) ? cookMap.get(ACMS.USERNAME):null; 
        String role = (cookMap.containsKey(ACMS.ROLENAME)) ? cookMap.get(ACMS.ROLENAME):null;           
        HttpSession session = request.getSession();
        
        boolean inError=false;
        
        if(user == null || role == null) inError=true;
        
        
        if(ACMS.DL > 0){
            StringBuilder bud = new StringBuilder();
            for(String val: pMap.keySet()) bud
                    .append("Found key ").append(val).append(" - ")
                    .append(pMap.get(val)).append("\n");
            log.info(bud.toString());
        }

        PrintWriter out = response.getWriter();
        
        try {
            /* TODO output your page here. You may use following sample code. */
            response.setContentType("application/json");
            
            GetConfig cfg = new GetConfig();
            log.log(Level.INFO, "Sending out all of the configs.");
            
            String payload = gson.toJson(cfg.getAllConfig());
            out.print(payload);
            out.flush();
        } finally {            
            out.close();
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        if(! ACMS.READY_TO_RUN){ ManageSetup.init();}
        Map<String,String> pMap = ServletUtil.parameterMap(request.getParameterMap());
        Map<String,String> cookMap = ServletUtil.simpleCookies(request.getCookies());
        
        
        String type = (pMap.containsKey(ACMS.TYPE)) ? pMap.get(ACMS.TYPE):null;
        String id = (pMap.containsKey(ACMS.ID)) ? pMap.get(ACMS.ID):null;
   
        String user = (cookMap.containsKey(ACMS.USERNAME)) ? cookMap.get(ACMS.USERNAME):null; 
        String role = (cookMap.containsKey(ACMS.ROLENAME)) ? cookMap.get(ACMS.ROLENAME):null;           
        HttpSession session = request.getSession();
        
        boolean inError=false;
        
        if(user == null || role == null) inError=true;
        
        if(ACMS.DL > 0){
            StringBuilder bud = new StringBuilder();
            for(String val: pMap.keySet()) bud
                    .append("Found key ").append(val).append(" - ")
                    .append(pMap.get(val)).append("\n");
            log.info(bud.toString());
        }
        
        int action=-1;
        action=findAction(pMap.get(ACMS.TYPE));
        
        if(ACMS.DL > 0)
            log.info(new StringBuilder().append("Action to take is ")
                    .append(action).toString());
        
        String payload="";
        
        switch(action){
            case 0: //add_auth    
                if(ServletUtil.chkAddAuthParams(pMap) && ms.isAdminSession(session.getId(), user)){
                   
                        String pwd=Crypto.crypt(pMap.get(ACMS.PASSWORD));
                        Auth auth=new Auth(pMap.get(ACMS.DISPLAY_NAME),pMap.get(ACMS.USERNAME)
                                ,pwd,ACMS.convertToInt(pMap.get(ACMS.ACCT_TYPE)),
                                pMap.get(ACMS.ROLENAME));
                        aa.addAuth(auth);
                        payload=gson.toJson(auth);
                    
                }else{
                    // Return an error:
                    log.warning("Attempt to create auth failed because of incorrect parameters or lack of permissions.");
                }
                break;
            case 1: //add_conn
                if(ServletUtil.chkAddConnParams(pMap) && ms.isAdminSession(session.getId(), user)){
                    CnConnection conn = new CnConnection(
                        pMap.get(ACMS.DISPLAY_NAME),
                        ACMS.convertToLong(pMap.get(ACMS.AUTHENTICATION_ID)),
                        ACMS.convertToLong(pMap.get(ACMS.CONTROLLER_ID))
                        );
                    ac.addCnConnection(conn);
                    payload=gson.toJson(conn);
                }else{
                    log.warning("Attempt to create connection failed because of incorrect parameters.");
                }
                break;
            case 2: //add_contr
                if(ServletUtil.chkAddContrParams(pMap) && ms.isAdminSession(session.getId(), user)){
                    Controller contr=new Controller(
                            pMap.get(ACMS.FQDN),
                            pMap.get(ACMS.DISPLAY_NAME),
                            pMap.get(ACMS.ACCOUNT),
                            pMap.get(ACMS.PORT),
                            ACMS.convertToBoolean(pMap.get(ACMS.USESSL))
                            );
                    
                    acr.addController(contr);
                    payload=gson.toJson(contr);
                }else{
                    log.warning("Attempt to create connection failed because of incorrect parameters.");
                }
                break;
            case 3: //update_auth
                if(ServletUtil.chkUpdateAuthParams(pMap) && ms.isAdminSession(session.getId(), user)){
                    Auth auth = aa.getSingleAuth(pMap.get(ACMS.ID));
                    if(pMap.containsKey(ACMS.DISPLAY_NAME)) 
                        auth.setDisplayName(pMap.get(ACMS.DISPLAY_NAME));
                    if(pMap.containsKey(ACMS.PASSWORD))
                        auth.setPassword(Crypto.crypt(pMap.get(ACMS.PASSWORD)));
                    if(pMap.containsKey(ACMS.ROLENAME))
                        auth.setGroupName(pMap.get(ACMS.ROLENAME));
                    
                    aa.updateAuth(auth);
                    // No need to return anything the table will get updated automatically
                }else{
                    log.warning("Attempt to update authentication failed.");
                }
                break;
            case 4: //update_conn
                if(ServletUtil.chkUpdateConnParams(pMap) && ms.isAdminSession(session.getId(), user)){
                    CnConnection conn=ac.getCnConnection(ACMS.convertToLong(pMap.get(ACMS.ID)));
                    if(pMap.containsKey(ACMS.DISPLAY_NAME))
                        conn.setDisplayName(pMap.get(ACMS.DISPLAY_NAME));
                    if(pMap.containsKey(ACMS.AUTHENTICATION_ID))
                        conn.setAuthId(ACMS.convertToLong(pMap.get(ACMS.AUTHENTICATION_ID)));
                    if(pMap.containsKey(ACMS.CONTROLLER_ID))
                        conn.setControllerId(ACMS.convertToLong(pMap.get(ACMS.CONTROLLER_ID)));
                    
                    ac.updateCnConnection(conn);
                }else{
                    log.warning("Attempt to update connection failed.");
                }
                break;
            case 5: //update_contr we can change the displayName, port, useSSL, account
                if(ServletUtil.chkUpdateContrParams(pMap) && ms.isAdminSession(session.getId(), user)){
                    Controller contr=acr.getSingleControllers(ACMS.convertToLong(pMap.get(ACMS.ID)));
                    if(pMap.containsKey(ACMS.DISPLAY_NAME)) 
                        contr.setDisplayName(pMap.get(ACMS.DISPLAY_NAME));
                    if(pMap.containsKey(ACMS.FQDN))
                        contr.setFqdn(pMap.get(ACMS.FQDN));
                    if(pMap.containsKey(ACMS.ACCOUNT))
                        contr.setAccount(pMap.get(ACMS.ACCOUNT));
                    if(pMap.containsKey(ACMS.PORT))
                        contr.setPort(pMap.get(ACMS.PORT));
                    if(pMap.containsKey(ACMS.USESSL))
                        contr.setUseSSL(ACMS.convertToBoolean(pMap.get(ACMS.USESSL)));
                    acr.updateController(contr);
                    
                }else{
                    log.warning("Attempt to update controller failed.");
                }
                break;
            case 6: //del_auth, no need to return anything
                if(ServletUtil.chkDeleteContrParams(pMap) && ms.isAdminSession(session.getId(), user)){
                    aa.deleteAuth(ACMS.convertToLong(pMap.get(ACMS.ID)));
                }else{
                    log.warning("Attempt to delete controller failed because of incorrect parameters.");
                }
                break;
            case 7: //del_conn
                if(ServletUtil.chkDeleteContrParams(pMap) && ms.isAdminSession(session.getId(), user)){
                    ac.deleteCnConnection(ACMS.convertToLong(pMap.get(ACMS.ID)));
                }else{
                    log.warning("Attempt to delete controller failed because of incorrect parameters.");
                }
                break;
            case 8: //del_contr, no need to return anything
                if(ServletUtil.chkDeleteContrParams(pMap) && ms.isAdminSession(session.getId(), user)){
                    acr.deleteController(ACMS.convertToLong(pMap.get(ACMS.ID)));
                }else{
                    log.warning("Attempt to delete controller failed because of incorrect parameters.");
                }
                break;
            case 9: //chg_passwd, no need to return anything
                if(ServletUtil.chkChangePasswdParams(pMap)){
                    log.info("We need to figure this out.");
                    String pwd=Crypto.crypt(pMap.get(ACMS.PASSWORD));
                    Auth auth = aa.getSingleAuth(ACMS.convertToLong(pMap.get(ACMS.ID)));
                    if( ms.isAdminSession(session.getId(), user) || auth.getUserName().equals(user)){
                        log.info("Updating the password.");
                        auth.setPassword(pwd);
                        aa.updateAuth(auth);
                    }
                    payload="";
                    
                }else{
                    log.warning("Attempt to change password failed because of incorrect parameters.");
                }
                break;
            case 10: //add_conf, no need to return anything
                if(ServletUtil.chkAddConfParams(pMap)){
                    Config conf=new Config(pMap.get(ACMS.DISPLAY_NAME),pMap.get(ACMS.VALUE));
                    acg.addConfig(conf);
                    payload=gson.toJson(conf);
                    
                }else{
                    log.warning("Attempt to add configuration failed because of incorrect parameters.");
                }
                break;
            case 11: //update_conf, no need to return anything
                if(ServletUtil.chkUpdateConfParams(pMap) && ms.isAdminSession(session.getId(), user)){
                    Config conf=acg.getSingleValue(pMap.get(ACMS.DISPLAY_NAME));
                    conf.setValue(pMap.get(ACMS.VALUE));
                    acg.updateConfig(conf);
                    
                }else{
                    log.warning("Attempt to update configuration failed because of incorrect parameters.");
                }
                break;
            case 12: //del_conf, no need to return anything
                if(ServletUtil.chkDeleteConfParams(pMap) && ms.isAdminSession(session.getId(), user)){
                    acg.deleteConfig(pMap.get(ACMS.DISPLAY_NAME));
                    
                }else{
                    log.warning("Attempt to delete configuration failed because of incorrect parameters.");
                }
                break;
            default: //We did not find it, so what was it ??
                break;
        }
        
        PrintWriter out = response.getWriter();
        
        try {
            /* TODO output your page here. You may use following sample code. */
            response.setContentType("application/json");
            out.print(payload);
            out.flush();
        } finally {            
            out.close();
        }
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    public int findAction(String action){
        for(int i=0; i < ACMS.CONFIG_CHGS.length;i++) 
            if(ACMS.CONFIG_CHGS[i].equals(action)) return i;
        return -1;
    }
}
