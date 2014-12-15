/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.servlets;

import org.appdynamics.appdchangemanager.resources.ACMS;
import org.appdynamics.appdchangemanager.action.CheckAuth;
import org.appdynamics.appdchangemanager.action.ManageSetup;
import org.appdynamics.appdchangemanager.action.ManageSessions;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilbert.solorzano
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private static final Logger log=Logger.getLogger(LoginServlet.class.getName());
    private static final long serialVersionUUID = 1L;
    private static final ManageSessions manageSessions=new ManageSessions();

    
    
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
        
        String user = request.getParameter(ACMS.USERNAME);
        String pwd = request.getParameter(ACMS.PASSWORD);
        PrintWriter out=null;
        
        if(ACMS.DL > 0) log.info(new StringBuilder().append("Attempting to login ").append(user).append(" ").append(pwd).toString());
        
        
        try {
            if(CheckAuth.checkUserPass(user, pwd)){
                
                HttpSession session = request.getSession();
                session.setAttribute(ACMS.USERNAME, user);
                
                String roleName=CheckAuth.getUserGroup(CheckAuth.getAuth(user).getId());
                session.setAttribute(ACMS.ROLENAME, roleName);
                session.setMaxInactiveInterval(30*60);
                
                Cookie userName = new Cookie(ACMS.USERNAME,user);
                userName.setMaxAge(30*60);                               
                response.addCookie(userName);
                
                Cookie roleNameC = new Cookie(ACMS.ROLENAME,roleName);
                userName.setMaxAge(30*60);
                response.addCookie(roleNameC);
                
                
                
                boolean bol = manageSessions.manageSession(session.getId(),user, roleName,0);
                
                
                if(bol && ACMS.GROUP_ADMIN.equals(roleName)){
                    response.sendRedirect("admin/index.jsp");
                }else{
                    if(bol){ response.sendRedirect("user/index.jsp");}
                    else{response.sendRedirect("/index.jsp");}
                }
                
                
            }else{
                if(ACMS.DL > 0)log.info("Authentication unsuccessful");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
                response.sendRedirect("/index.jsp");
                
            }
        } finally {            
            if(out != null) out.close();
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
        return "Short description";
    }// </editor-fold>
}
