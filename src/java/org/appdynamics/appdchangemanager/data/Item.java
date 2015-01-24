/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.data;


import org.appdynamics.appdchangemanager.resources.ACMS;

import javax.xml.bind.annotation.XmlElement;
/**
 *
 * @author gilbert.solorzano
 * 
 * item{
                            srcItemLocation:
                            srcItemFile:
                            destLocation:
                            destExisted:[true|false]
                            destFile:
                         }
 * 
 */
public class Item {
    private String name;
    private String srcItemLocation;
    private String srcItemFile;
    private String destItemLocation;
    private String destItemFile;
    private boolean destItemExisted;

    public Item(){}

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    @XmlElement
    public String getSrcItemLocation() {
        return srcItemLocation;
    }

    public void setSrcItemLocation(String srcLocation) {
        this.srcItemLocation = srcLocation;
    }

    @XmlElement
    public String getSrcItemFile() {
        return srcItemFile;
    }

    public void setSrcItemFile(String srcFile) {
        this.srcItemFile = srcFile;
    }

    @XmlElement
    public String getDestItemLocation() {
        return destItemLocation;
    }

    public void setDestItemLocation(String destLocation) {
        this.destItemLocation = destLocation;
    }

    @XmlElement
    public String getDestItemFile() {
        return destItemFile;
    }

    public void setDestItemFile(String destFile) {
        this.destItemFile = destFile;
    }

    @XmlElement(name=ACMS.DEST_ITEM_EXISTED)
    public boolean isDestItemExisted() {
        return destItemExisted;
    }

    public void setDestItemExisted(boolean destExisted) {
        this.destItemExisted = destExisted;
    }
    
    
    @Override
    public String toString(){
        StringBuilder bud = new StringBuilder();
        bud.append(ACMS.L1).append(ACMS.ITEM);
        bud.append(ACMS.L1_1).append(ACMS.NAME).append(ACMS.VE).append(name);
        bud.append(ACMS.L1_1).append(ACMS.SRC_ITEM_LOCATION).append(ACMS.VE).append(srcItemLocation);
        bud.append(ACMS.L1_1).append(ACMS.SRC_ITEM_FILE).append(ACMS.VE).append(srcItemFile);
        bud.append(ACMS.L1_1).append(ACMS.DEST_ITEM_LOCATION).append(ACMS.VE).append(destItemLocation);
        bud.append(ACMS.L1_1).append(ACMS.DEST_ITEM_FILE).append(ACMS.VE).append(destItemFile);
        bud.append(ACMS.L1_1).append(ACMS.DEST_ITEM_EXISTED).append(ACMS.VE).append(destItemExisted);
        
        return bud.toString();
    }
    
    
}
