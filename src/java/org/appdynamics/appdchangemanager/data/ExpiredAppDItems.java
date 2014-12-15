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
public class ExpiredAppDItems {
    private String name;//
    private String[] values;
    private Long lastUpdate;
    private boolean invalid;
    
    public ExpiredAppDItems(){}
    
    public ExpiredAppDItems(String name,String[] values){
        this.name=name;
        this.values=values;
        update();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public void check(){
        if(java.util.Calendar.getInstance().getTimeInMillis()-lastUpdate > ACMS.APPD_TIMEOUT) invalid=true;
    }
    
 
    public final void update(){
        lastUpdate=java.util.Calendar.getInstance().getTimeInMillis();
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }
 
    
}
