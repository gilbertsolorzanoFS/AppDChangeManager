/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.servlets;

import org.appdynamics.appdchangemanager.data.Change;
import org.appdynamics.appdchangemanager.conn.AccessChanges;
import org.appdynamics.appdchangemanager.action.ManageSetup;
import org.appdynamics.appdchangemanager.action.ManageSessions;
import org.appdynamics.appdchangemanager.resources.ACMS;
import org.appdynamics.appdchangemanager.util.ServletUtil;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Map;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.appdynamics.appdchangemanager.action.ManageCaches;
import org.appdynamics.appdchangemanager.action.ProcessChange;

/**
 *
 * @author gilbert.solorzano
 */
@WebServlet(name = "ManageChanges", urlPatterns = {"/ManageChanges"})
public class ManageChanges extends HttpServlet {
private static final Logger log=Logger.getLogger(ManageChanges.class.getName());
private static final Gson gson=new Gson();
private static final ManageCaches MC=new ManageCaches(); 
private static final ManageSessions ms = new ManageSessions();
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
     * 
     * default it to get non-completed
     * id=XX (for single request)
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
        
        
        int typeIndex=0;
        Long idNum=0L;
        
        if(type != null){ 
            for(int i=0; i < ACMS.CHANGE_GET_OPTS.length;i++) if(ACMS.CHANGE_GET_OPTS[i].equals(type)) typeIndex=i; 
        }
        
        
         
        ArrayList<Change> chgs=new ArrayList<Change>();
        
        if(typeIndex==3 && id != null){ idNum=new Long(id);} 
        log.info(new StringBuilder().append("The index is ").append(typeIndex).toString());
        
        switch(typeIndex){
            case 0: 
                chgs = MC.chgCache.getChanges(0,false);
                //chgs = ac.getReadyForApprChange(false);
                break;
            case 1:
                chgs = MC.chgCache.getChanges(0,true);
                //chgs = ac.getReadyForApprChange(true);
                break;
            case 2:
                chgs = MC.chgCache.getChanges(1,false);
                //chgs = ac.getApprovedChange(false);
                break;
            case 3:
                chgs = MC.chgCache.getChanges(1,true);
                //chgs = ac.getApprovedChange(true);
                break;
            case 4:
                chgs = MC.chgCache.getLiveChanges();
                //chgs = ac.getCompletedChange(false);
                break;
            case 5:
                chgs = MC.chgCache.getCompletedChanges();
                //chgs = ac.getCompletedChange(true);
                break;
            case 6:
                chgs = MC.chgCache.getCompletedChanges();
                chgs.addAll(MC.chgCache.getLiveChanges());
                //chgs = ac.getAllChange();
                break;
            case 7:   
                chgs.add(MC.chgCache.getChange(idNum));
                //chgs.add(ac.getSingleChange(idNum));
                break;
                
            default:
                break;
        }
        
        String payload = gson.toJson(chgs);
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
     *  This is how we are going to post changes : 
     * type/add: Change(Long requestTime, String requester, String descr)
     * type/update: Change(Long id, Long completedTime, String descr, 
     *      String approver, boolean approved, boolean rejected
     * 
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
        // processRequest(request, response);
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
        
        if(ServletUtil.chkBaseCookies(cookMap)){ 
            log.info(new StringBuilder().append("The username is ")
                    .append(cookMap.get(ACMS.USERNAME)).append(" rolename ")
                    .append(cookMap.get(ACMS.ROLENAME)).toString());
        }else{
            log.info("Base cookies not present.");
        }
        
        /*
         *  Check the security and return if nothing is found
         */
        
        int typeIndex=0;
        Long idNum=0L;
        
        if(type != null){ for(int i=0; i < ACMS.CHANGE_POST_OPTS.length;i++) if(ACMS.CHANGE_POST_OPTS[i].equals(type)) typeIndex=i; }
        
        
        if(ACMS.DL > 0) for(String key: pMap.keySet())log.info("The key is " + key + " : "+ pMap.get(key));
        
        Change chg=null;
        String descr=null;
        String readyForApproval=null;
        String payload="";
        
        log.info("Posting a change! " + typeIndex);
        switch(typeIndex){
            case 0: // Add a chnage
                // Add a change, can be execute by any user
                descr = (pMap.containsKey(ACMS.DESCR)) ? pMap.get(ACMS.DESCR):"Description::";
                chg=new Change(Calendar.getInstance().getTimeInMillis(),user,descr);
                MC.chgCache.addLiveChange(chg);
                //ac.addChange(chg);
                payload=gson.toJson(chg);
                break;
            case 1:// Update can be executed by requester or admin       
                if(id != null) idNum=new Long(id);
                chg = MC.chgCache.getChange(idNum);
                //chg = ac.getSingleChange(idNum);
                
                if(chg != null){ // or its an admin
                    if(chg.getRequester().equals(user) || ms.isAdminSession(session.getId(), user)){
                        // We now execute the update, what is updatable ?
                        descr = (pMap.containsKey(ACMS.DESCR)) ? pMap.get(ACMS.DESCR):null;
                        if(descr != null){ chg.setDescr(descr);}
                        readyForApproval = (pMap.containsKey(ACMS.READY_FOR_APPROVAL)) ? pMap.get(ACMS.READY_FOR_APPROVAL):null;
                        if(readyForApproval != null) {chg.setReadyForApproval(ACMS.convertToBoolean(readyForApproval));}
                        MC.chgCache.updateLiveChange(chg);
                        //ac.addUpdate(chg);
                        payload=gson.toJson(chg);
                    }
                }
                break;
            case 2:// Delete can be executed by requester or admin
                
                if(id != null) idNum=new Long(id);
                chg = MC.chgCache.getChange(idNum);
                //chg = ac.getSingleChange(idNum);
                
                if(chg != null){ // or its an admin
                    if(chg.getRequester().equals(user) || ms.isAdminSession(session.getId(), user)){
                        MC.chgCache.deleteLiveChange(chg);
                        //ac.deleteChange(idNum);
                    }
                }
                
                break;
            case 3:
                // Only admin executes the action
                if(ms.isAdminSession(session.getId(), user)){
                    if(id != null) idNum=new Long(id);
                    chg = MC.chgCache.getChange(idNum);
                    int retVal = ProcessChange.executeChange(chg);
                    if(retVal == 0){
                        chg.setCompletedTime(java.util.Calendar.getInstance().getTimeInMillis());
                        chg.setCompleted(true);
                        MC.chgCache.updateLiveChange(chg);
                        payload=gson.toJson(chg);
                    }
                }
                break;
            case 4:
                // Only admin approves the actions
                if(ms.isAdminSession(session.getId(), user)){
                    
                    if(id != null) idNum=new Long(id);
                    chg = MC.chgCache.getChange(idNum);
                    
                    if(chg != null){ 
                        chg.setApproved(true);
                        chg.setApprover(user);
                        MC.chgCache.updateLiveChange(chg);
                        payload=gson.toJson(chg);
                    }
                }
                
                break;
            case 5: //ready for approval
                
                    if(id != null) idNum=new Long(id);
                    chg = MC.chgCache.getChange(idNum);
                    
                    if(chg != null && (chg.getRequester().equals(user) || ms.isAdminSession(session.getId(), user) )){ 
                        chg.setReadyForApproval(true);
                        MC.chgCache.updateLiveChange(chg);
                        payload=gson.toJson(chg);
                    }
                
                break;
            case 6: //refuse, remove ready for approval
                if(ms.isAdminSession(session.getId(), user)){
                    
                    if(id != null) idNum=new Long(id);
                    chg = chg = MC.chgCache.getChange(idNum);
                    
                    if(chg != null){ 
                        chg.setReadyForApproval(false);
                        chg.setApprover("");
                        chg.setDescr(new StringBuilder().append("Rejected by: ")
                                .append(user).append(" on ")
                                .append(java.util.Calendar.getInstance().getTime())
                                .append(" -- ").append(chg.getDescr()).toString());
                        MC.chgCache.updateLiveChange(chg);
                        payload=gson.toJson(chg);
                    }
                }
                break;
            default:
                // Do nothing
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
}
