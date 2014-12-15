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
public class Controller {
    private Long id;
    private String fqdn,port,displayName,account;
    private boolean useSSL;
    
    public Controller(){}
    
    public Controller(String fqdn, String displayName, String account, String port, boolean useSSL){
       
        this.fqdn=fqdn;
        this.displayName=displayName;
        this.account=account;
        this.port=port;
        this.useSSL=useSSL;
    }
    
    public Controller(Long id, String fqdn, String displayName, String account, String port, boolean useSSL){
        this.id=id;
        this.fqdn=fqdn;
        this.displayName=displayName;
        this.account=account;
        this.port=port;
        this.useSSL=useSSL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFqdn() {
        return fqdn;
    }

    public void setFqdn(String fqdn) {
        this.fqdn = fqdn;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }
    
    @Override
    public String toString(){
        StringBuilder bud = new StringBuilder();
        bud.append(ACMS._V(0,ACMS.CONTROLLER));
        bud.append(ACMS._V(1,ACMS.ID, id));
        bud.append(ACMS._V(1, ACMS.DISPLAY_NAME, displayName));
        bud.append(ACMS._V(1, ACMS.FQDN, fqdn));
        bud.append(ACMS._V(1, ACMS.PORT, port));
        bud.append(ACMS._V(1, ACMS.ACCOUNT, account));
        bud.append(ACMS._V(1, ACMS.USESSL, useSSL));
        return bud.toString();
    }
    
    public String toErrorHelp(){
        StringBuilder bud = new StringBuilder();
        bud.append("Attempted to add a dup fqdn: ").append(fqdn)
                .append(", port: ").append(port)
                .append(", account: ").append(account);
        return bud.toString();
    }
    
}
