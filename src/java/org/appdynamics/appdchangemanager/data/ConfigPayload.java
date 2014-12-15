/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.data;


import org.appdynamics.appdchangemanager.resources.ACMS;


import java.util.ArrayList;

/**
 *
 * @author gilbert.solorzano
 */
public class ConfigPayload {
    private ArrayList<Auth> auths=new ArrayList<Auth>();

    private ArrayList<Controller> controllers=new ArrayList<Controller>();

    private ArrayList<CnConnection> conns= new ArrayList<CnConnection>();
    private ArrayList<Config> configs = new ArrayList<Config>();
    
    public ConfigPayload(){}

    public ArrayList<Auth> getAuths() {
        return auths;
    }

    public void setAuths(ArrayList<Auth> auths) {
        this.auths = auths;
    }



    public ArrayList<Controller> getControllers() {
        return controllers;
    }

    public void setControllers(ArrayList<Controller> controllers) {
        this.controllers = controllers;
    }


    public ArrayList<CnConnection> getConns() {
        return conns;
    }

    public void setConns(ArrayList<CnConnection> conns) {
        this.conns = conns;
    }

    public ArrayList<Config> getConfigs() {
        return configs;
    }

    public void setConfigs(ArrayList<Config> configs) {
        this.configs = configs;
    }
    
    
    @Override
    public String toString(){
        StringBuilder bud = new StringBuilder();
        bud.append(ACMS.L0).append(ACMS.CONFIG);
        for(Config cfg:configs) bud.append(cfg.toString());
        for(Auth auth:auths) bud.append(auth.toString());
        for(Controller controller:controllers)bud.append(controller.toString());
        for(CnConnection con:conns) bud.append(con.toString());

        return bud.toString();
    }
    
}
