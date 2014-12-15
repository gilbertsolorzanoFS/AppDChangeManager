/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.servlets;

import org.appdynamics.appdchangemanager.data.*;
import org.appdynamics.appdchangemanager.conn.*;
import org.appdynamics.appdchangemanager.action.*;
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

/**
 *
 * @author gilbert.solorzano
 */
@WebServlet(name = "ManageItems", urlPatterns = {"/ManageItems"})
public class ManageItems extends HttpServlet {
    private static final Logger log=Logger.getLogger(ManageItems.class.getName());
    private static final ManageCaches MC=new ManageCaches();  
    private ManageAppDItems mad=new ManageAppDItems();
    
    private long serialVersionUUID = 1L;
    private Gson gson=new Gson();
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
        if(! ACMS.READY_TO_RUN){ ManageSetup.init();}
        
        Map<String,String> cookMap = ServletUtil.simpleCookies(request.getCookies());
        Map<String,String> pMap = ServletUtil.parameterMap(request.getParameterMap());
        
        int typeIndex=0;
        Long idNum=0L;

        /*
         * every type has to have a 
         * contrId
         * appId
         * tierId
         */
        // type=getApp,contrId
        PrintWriter out = response.getWriter();
        
        boolean inError=false;
        String type = (pMap.containsKey(ACMS.TYPE)) ? pMap.get(ACMS.TYPE):null;
        String id = (pMap.containsKey(ACMS.ITEM_GETS[0])) ? pMap.get(ACMS.ITEM_GETS[0]):null;
        
        if(ACMS.DL > 0){
            StringBuilder bud = new StringBuilder();
            for(String val: pMap.keySet()) bud
                    .append("Found key ").append(val).append(" - ")
                    .append(pMap.get(val)).append("\n");
            log.info(bud.toString());
        }
        
        if(type != null){ for(int i=0; i < ACMS.ITEM_GETS.length;i++) if(ACMS.ITEM_GETS[i].equals(type)) typeIndex=i; }
        
        if(type == null || id == null) inError=true;
        if(!inError){
            switch(typeIndex){
                case 3:
                    inError=!ServletUtil.getAppDAutoParams(pMap);
                    break;
                case 4:
                    inError=!ServletUtil.getAppDCustomPojoParams(pMap);
                    break;
                case 5:
                    inError=!ServletUtil.getAppDHealthRulesParams(pMap);
                    break;
                case 6:
                    inError=!ServletUtil.getAppDAppsParams(pMap);
                    break;
                case 7:
                    inError=!ServletUtil.getAppDTiersParams(pMap);
                    break;
            }
        }
        
        try {
            /* TODO output your page here. You may use following sample code. */
            if(inError){
                StringBuilder bud = new StringBuilder();
                bud.append("Incomplete parameters passed:- ");
                for(String key:pMap.keySet()) bud.append(key).append(":").append(pMap.get(key)).append(",");
                log.warning(bud.toString());
                out.print(bud.toString());
                response.sendError(400);
            }else{
                
                log.log(Level.INFO, new StringBuilder().append("ManageItem index is ").append(typeIndex).toString());
                CnConnection conn = MC.chgCache.getCnConnection(ACMS.convertToLong(id));//ac.getCnConnection(ACMS.convertToLong(id));
                Controller contr = MC.chgCache.getController(conn.getControllerId());//acr.getSingleControllers(conn.getControllerId());
                String application;
                String tier;
                ItemLocation itemLoc;

                String[] retVal;
                
                String payload=null;
                switch(typeIndex){
                    case 3: //auto
                         application = (pMap.containsKey(ACMS.ITEM_GETS[1])) ? pMap.get(ACMS.ITEM_GETS[1]):null;
                         tier = (pMap.containsKey(ACMS.ITEM_GETS[2])) ? pMap.get(ACMS.ITEM_GETS[2]):null;
                         if(tier != null){
                             itemLoc = MC.chgCache.getItemLocation(contr.getFqdn(), application, tier);
                             
                             //itemLoc = ail.getSingleItemLocationOrAdd(contr.getFqdn(), application, tier);
                         }else{
                             itemLoc = MC.chgCache.getItemLocation(contr.getFqdn(), application, null);
                             //itemLoc = ail.getSingleItemLocationOrAdd(contr.getFqdn(), application);
                         }
                         retVal=mad.getItemList(ACMS.R_ITEM_TYPE_NAME[0], itemLoc, conn, false);
                         payload=gson.toJson(retVal);
                         break;
                    case 4: //custom-match
                        application = (pMap.containsKey(ACMS.ITEM_GETS[1])) ? pMap.get(ACMS.ITEM_GETS[1]):null;
                        tier = (pMap.containsKey(ACMS.ITEM_GETS[2])) ? pMap.get(ACMS.ITEM_GETS[2]):null;
                        if(tier != null){
                             itemLoc = MC.chgCache.getItemLocation(contr.getFqdn(), application, tier);
                             
                             //itemLoc = ail.getSingleItemLocationOrAdd(contr.getFqdn(), application, tier);
                         }else{
                             itemLoc = MC.chgCache.getItemLocation(contr.getFqdn(), application, null);
                             //itemLoc = ail.getSingleItemLocationOrAdd(contr.getFqdn(), application);
                         }
                        retVal=mad.getItemList(ACMS.R_ITEM_TYPE_NAME[1], itemLoc, conn, false);
                        payload=gson.toJson(retVal);
                        break;
                    case 5: //health-rule
                        application = (pMap.containsKey(ACMS.ITEM_GETS[1])) ? pMap.get(ACMS.ITEM_GETS[1]):null;
                        itemLoc = MC.chgCache.getItemLocation(contr.getFqdn(), application, null);
                        //itemLoc = ail.getSingleItemLocationOrAdd(contr.getFqdn(), application);
                        retVal=mad.getItemList(ACMS.R_ITEM_TYPE_NAME[2], itemLoc, conn, false);
                        payload=gson.toJson(retVal);
                        //R_ITEM_TYPE_NAME[]
                        break;
                    case 6: //applications
                        
                        retVal = mad.getItemList(conn, false);
                        payload=gson.toJson(retVal);
                        break;
                    case 7: // tiers requires (application)
                        application = (pMap.containsKey(ACMS.ITEM_GETS[1])) ? pMap.get(ACMS.ITEM_GETS[1]):null;
                        //itemLoc = ail.getSingleItemLocationOrAdd(contr.getFqdn(), application);
                        itemLoc = MC.chgCache.getItemLocation(contr.getFqdn(), application, null);
                        retVal=mad.getItemList(ACMS.R_ITEM_TYPE_NAME[4], itemLoc, conn, false);
                        payload=gson.toJson(retVal);
                        break;
                    default:
                         break;
                }
                response.setContentType("application/json");
                out.print(payload);
            }
            out.flush();
        } finally {            
            out.close();
        }
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
        processRequest(request, response);
        /*

        */
        
        
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "This will provide access to AppDynamics objects.";
    }// </editor-fold>
}
