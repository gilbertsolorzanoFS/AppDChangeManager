/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gilbert.solorzano
 */
@XmlSeeAlso(Items.class)
public class Task {
    private String type;
    private String descr;
    private String match;
    private Items items;
    private boolean successful;
    
    public Task(){}

    @XmlTransient
    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    @XmlElement
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @XmlElement
    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    @XmlElement
    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

}
