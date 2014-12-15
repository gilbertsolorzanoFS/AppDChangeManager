/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.action;

import org.appdynamics.appdchangemanager.conn.CreateTables;
import org.appdynamics.appdchangemanager.conn.ChkTables;
import org.appdynamics.appdchangemanager.resources.ACMS;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author gilbert.solorzano
 */
public class ManageSetup {
    private static final Logger log = Logger.getLogger(ManageSetup.class.getName());
    
    public synchronized static void init(){
        if(!ACMS.READY_TO_RUN){
            //Double check to make sure we are still not ready to run:
            // Setup the DB Connection
            // Setup the DB if it does not exist
            log.log(Level.INFO, "Starting setup");
            CreateTables crt = new CreateTables();


            try{
                crt.init();
                // This is only dev not for production!!!
                //ChkTables chk=new ChkTables();
                
                ACMS.READY_TO_RUN=true;
            }catch(Exception e){
                log.log(Level.SEVERE, new StringBuilder()
                        .append("Exception occurred while attempting to create tables, with exception ")
                        .append(e.getMessage()).toString());
            }

        }
    }
}
