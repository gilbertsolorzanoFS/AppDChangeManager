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
public class Config {
    private String name,value;
    private Long lastUpdate=-1L;
    
    public Config(){}
    
    public Config(String name, String value){
        this.name=name;
        this.value=value;
    }
    
    public Config(String name, String value, Long lastUpdate){
        this.name=name;
        this.value=value;
        this.lastUpdate=lastUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    
    
    @Override
    public String toString(){
        StringBuilder bud = new StringBuilder();
        bud.append(ACMS._V(0,ACMS.CONFIG));
        bud.append(ACMS._V(1, ACMS.NAME,name));
        bud.append(ACMS._V(1,ACMS.VALUE, value));
        bud.append(ACMS._V(1, ACMS.LASTUPDATE,lastUpdate));
        return bud.toString();
    }
    
    public String toErrorHelp(){
        StringBuilder bud = new StringBuilder();
        bud.append("Attempted to add a dup name: ").append(name)
                .append(", value: ").append(value).append(", lastUpdate ").append(lastUpdate);
        return bud.toString();
    }
    
}
