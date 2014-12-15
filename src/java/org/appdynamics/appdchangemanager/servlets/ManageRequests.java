/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.servlets;

import com.google.gson.Gson;

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

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gilbert.solorzano
 */
@WebServlet(name = "ManageRequests", urlPatterns = {"/ManageRequests"})
public class ManageRequests extends HttpServlet {
    private static final Logger log=Logger.getLogger(ManageRequests.class.getName());
    private Gson gson=new Gson();
    private static final ManageCaches MC=new ManageCaches(); 
    private ManageSessions ms = new ManageSessions();

    
    
    
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. 
    //Click on the + sign on the left to edit the code.">
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
        int errorCode=-1;
        
        if(user == null || role == null){ inError=true; errorCode=1;}
        
        if(ACMS.DL > 0){
            StringBuilder bud = new StringBuilder();
            for(String val: pMap.keySet()) bud
                    .append("Found key ").append(val).append(" - ")
                    .append(pMap.get(val)).append("\n");
            log.info(bud.toString());
        }
        
        /*
         *  Any user
         *  We can get a single request
         *  We can get a request for changeId
         */
        ArrayList<Request> reqs=new ArrayList<Request>();
        
        
        // If we don't get anything we return an empty array.

        if(pMap.containsKey(ACMS.ID)){
            reqs.add(MC.chgCache.getLiveRequest(ACMS.convertToLong(pMap.get(ACMS.ID))));
            //reqs.add(ar.getSingleRequest(Long.getLong(pMap.get(ACMS.ID))));
        }

        if(pMap.containsKey(ACMS.CHANGE_ID)){ 
            log.info(new StringBuilder().append("Change id: ")
                    .append(pMap.get(ACMS.CHANGE_ID)).append(" -- ")
                    .append(ACMS.convertToLong(pMap.get(ACMS.CHANGE_ID))).toString());
            reqs = MC.chgCache.getLiveRequestForChange(ACMS.convertToLong(pMap.get(ACMS.CHANGE_ID)));
            //reqs=ar.getChgRequest(ACMS.convertToLong(pMap.get(ACMS.CHANGE_ID)));
        }
        
        String payload = gson.toJson(reqs);
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
        /*
         * Added a new request:
         * type=add/update/delete
         *  add requires : itemName, itemType, srcItemLocation (srcController, app, tier<?>), destItemLocation(), srcConn, destConn
         *  update options: itemName, itemType, srcItemLocation (), destItemLocation(),srcConn, destConn, id
         *  delete requires: id
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
        
        
        
        StringBuilder bud;
        
        if(ACMS.DL > 0){
            bud = new StringBuilder();
            bud.append("Parameters for request are: ");
            for(String key:pMap.keySet()) bud.append("\nkey-").append(key).append("::").append(pMap.get(key));
            log.info(bud.toString());
        }
        
        String payload="";
        PrintWriter out = response.getWriter();
        
        if(inError || pMap.isEmpty()){
            // We need to spit this into different things
                bud = new StringBuilder();
                bud.append("Incomplete parameters passed:- ");
                for(String key:pMap.keySet()) bud.append(key).append(":").append(pMap.get(key)).append(",");
                log.warning(bud.toString());
                out.print(bud.toString());
                response.sendError(400);
        }else{
            boolean valid=true;
            Request req;
            ItemLocation srcLoc;
            ItemLocation destLoc;
            type=(pMap.containsKey(ACMS.TYPE)) ? pMap.get(ACMS.TYPE):null;
            int typeIndex=-1;
            for(int i=0; i < 3;i++) if(ACMS.REQUEST_POST_OPTS[i].equals(type)) typeIndex=i;
            
            log.info("Working on indexType " + typeIndex);
            
            switch(typeIndex){
                case 0: //add
                    valid=true;
                    //for(int i =0; i < 9; i++) if(!pMap.containsKey(ACMS.R_VARS[i])){valid=false;log.info("We don't have "+ACMS.R_VARS[i]);}
                    log.info("Are we valid " + valid);
                    if(valid){
                        Change _chg = MC.chgCache.getChange(ACMS.convertToLong(pMap.get(ACMS.R_VARS[2])));
                            // I need to get the controller
                        if(_chg != null && (_chg.getRequester().equals(user) || ms.isAdminSession(session.getId(), user))){
                            CnConnection srcConn=MC.chgCache.getCnConnection(ACMS.convertToLong(pMap.get(ACMS.R_VARS[3])));
                            //ac.getCnConnection(ACMS.convertToLong(pMap.get(ACMS.R_VARS[3])));
                            CnConnection destConn=MC.chgCache.getCnConnection(ACMS.convertToLong(pMap.get(ACMS.R_VARS[4])));
                            //ac.getCnConnection(ACMS.convertToLong(pMap.get(ACMS.R_VARS[4])));

                            srcLoc = getLocation(srcConn,pMap,true);

                            destLoc = getLocation(destConn,pMap,false);


                            log.info("create the request.");
                            //(String itemName, String itemType, ItemLocation srcItemLocation,ItemLocation destItemLocation, CnConnection srcConn, CnConnection destConn)
                            req = new Request(ACMS.convertToLong(pMap.get(ACMS.R_VARS[2])),
                                    pMap.get(ACMS.R_VARS[0]),pMap.get(ACMS.R_VARS[1]),srcLoc,destLoc,srcConn,destConn);

                            //ar.addRequests(req);
                            MC.chgCache.addLiveRequest(req);
                            payload=gson.toJson(req);
                        }
                    }
                    
                    break;
                case 1: //update
                    valid =true;
                    req = MC.chgCache.getLiveRequest(ACMS.convertToLong(pMap.get(ACMS.ID)));
                    //ar.getSingleRequest(ACMS.convertToLong(pMap.get(ACMS.ID)));
                    if(req != null){
                        Change _chg = MC.chgCache.getChange(ACMS.convertToLong(pMap.get(ACMS.R_VARS[2])));
                            // I need to get the controller
                        if(_chg != null && (_chg.getRequester().equals(user) || ms.isAdminSession(session.getId(), user))){
                        
                            if(pMap.containsKey(ACMS.R_VARS[2])){
                                //req.setSrcConn(ac.getCnConnection(ACMS.convertToLong(pMap.get(ACMS.R_VARS[2]))));
                                req.setSrcConn(MC.chgCache.getCnConnection(ACMS.convertToLong(pMap.get(ACMS.R_VARS[2]))));
                            }
                            if(pMap.containsKey(ACMS.R_VARS[3])){
                                //req.setDestConn(ac.getCnConnection(ACMS.convertToLong(pMap.get(ACMS.R_VARS[3]))));
                                req.setDestConn(MC.chgCache.getCnConnection(ACMS.convertToLong(pMap.get(ACMS.R_VARS[3]))));
                            }

                            if(pMap.containsKey(pMap.get(ACMS.R_VARS[4])) && pMap.containsKey(pMap.get( ACMS.R_VARS[6]))){
                                /*
                                srcLoc = (pMap.containsKey(ACMS.R_VARS[8])) 
                                    ? ail.getSingleItemLocation(pMap.get(ACMS.R_VARS[4]), ACMS.R_VARS[6], ACMS.R_VARS[8])
                                    : ail.getSingleItemLocation(pMap.get(ACMS.R_VARS[4]), ACMS.R_VARS[6]);
                                */
                                srcLoc = MC.chgCache.getItemLocation(pMap.get(ACMS.R_VARS[4]), pMap.get(ACMS.R_VARS[6]),pMap.get(ACMS.R_VARS[8]));
                                req.setSrcItemLocation(srcLoc);
                            }

                            if(pMap.containsKey(pMap.get(ACMS.R_VARS[5])) && pMap.containsKey(pMap.get( ACMS.R_VARS[7]))){
                                /*
                                destLoc = (pMap.containsKey(ACMS.R_VARS[9])) 
                                    ? ail.getSingleItemLocation(pMap.get(ACMS.R_VARS[5]), ACMS.R_VARS[7], ACMS.R_VARS[9])
                                    : ail.getSingleItemLocation(pMap.get(ACMS.R_VARS[5]), ACMS.R_VARS[7]);
                                */
                                destLoc = MC.chgCache.getItemLocation(pMap.get(ACMS.R_VARS[5]), pMap.get(ACMS.R_VARS[7]),pMap.get(ACMS.R_VARS[9]));
                                req.setDestItemLocation(destLoc);
                            }
                            MC.chgCache.updateLiveRequest(req);
                            //ar.updateRequest(req);
                            payload=gson.toJson(req);
                        }
                    }
                    
                    break;
                case 2: //delete, nothing to return
                    //req = ar.getSingleRequest(ACMS.convertToLong(pMap.get(ACMS.ID)));
                    //if(req != null) ar.deleteRequest(req);
                    Change _chg = MC.chgCache.getChange(ACMS.convertToLong(pMap.get(ACMS.R_VARS[2])));
                    if(_chg != null){ 
                        if (_chg.getRequester().equals(user) || ms.isAdminSession(session.getId(), user))
                            MC.chgCache.deleteLiveRequest(ACMS.convertToLong(pMap.get(ACMS.ID)));
                    }
                    //ar.deleteRequest(ACMS.convertToLong(pMap.get(ACMS.ID)));
                    
                    break;
                default: // don't know what you want ??
                    break;
            }
            
            
            try {
                /* TODO output your page here. You may use following sample code. */
                log.info("Sending response " + payload);
                response.setContentType("application/json");
                out.print(payload);
                out.flush();
            } finally {            
                out.close();
            }
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
    
    private ItemLocation getLocation(CnConnection cn,Map<String,String> map,boolean src){
        
        //Controller contr = acr.getSingleControllers(cn.getControllerId());
        Controller contr = MC.chgCache.getController(cn.getControllerId());
        ItemLocation loc= null;
        if(src){
            loc = MC.chgCache.getItemLocation(contr.getFqdn(), map.get(ACMS.R_VARS[7]),map.get(ACMS.R_VARS[9]));

        }else{
            loc = MC.chgCache.getItemLocation(contr.getFqdn(), map.get(ACMS.R_VARS[8]),map.get(ACMS.R_VARS[10]));
        }
        return loc;
    }
    
    
}
