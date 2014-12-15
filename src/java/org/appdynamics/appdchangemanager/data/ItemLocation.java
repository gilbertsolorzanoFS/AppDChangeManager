/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.data;

import org.appdynamics.appdchangemanager.resources.ACMS;

/**
 *
 * @author gilbert.solorzano
 */
public class ItemLocation {
    private Long id;
    private String controller,application,tier;
    private int locationType;
    
    public ItemLocation(){}
    
    public ItemLocation(Long id, String controller, String application, int locationType){
        this.id=id;
        this.controller=controller;
        this.application=application;
        this.locationType=locationType;
    }
    
    public ItemLocation(Long id, String controller, String application, String tier, int locationType){
        this.id=id;
        this.controller=controller;
        this.application=application;
        this.tier=tier;
        this.locationType=locationType;
    }
    
    public ItemLocation(String controller, String application, int locationType){
        this.controller=controller;
        this.application=application;
        this.locationType=locationType;
    }
    
    public ItemLocation(String controller, String application, String tier, int locationType){
        this.controller=controller;
        this.application=application;
        this.tier=tier;
        this.locationType=locationType;
    }

    
    public ItemLocation(String controller, String application){
        this.controller=controller;
        this.application=application;
        this.locationType=0;
    }
    
    public ItemLocation(String controller, String application, String tier){
        this.controller=controller;
        this.application=application;
        this.tier=tier;
        this.locationType=1;
    }
    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    @Override
    public String toString(){
        StringBuilder bud = new StringBuilder();
        bud.append(ACMS._V(0, ACMS.ITEM_LOCATION));
        bud.append(ACMS._V(1,ACMS.CONTROLLER, controller));
        bud.append(ACMS._V(1,ACMS.APPLICATION, application));
        bud.append(ACMS._V(1,ACMS.TIER, tier));
        bud.append(ACMS._V(1, ACMS.ITEM_LOCATION_TYPE, locationType));
        return bud.toString();
    }
    
    public String toErrorHelp(){
        StringBuilder bud = new StringBuilder();
        bud.append("Attempted to add a dup controller: ").append(controller)
                .append(", application: ").append(application)
                .append(", tier: ").append(tier).append(", locationType: ").append(locationType);
        return bud.toString();
    }
}
