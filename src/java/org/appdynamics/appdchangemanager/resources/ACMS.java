/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.resources;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author gilbert.solorzano
 */
public class ACMS {
    
    public static final String VERSION_V="1.0.0";
    public static final String VERSION="version";
    public static final Long TIMEOUT=1L*1000*60*15;
    public static final Long APPD_TIMEOUT=1L*1000*60*60;
    
    public static final String BASE_DIRECTORY="Base_Directory";
    public static String BASE_DIRECTORY_V="./";
    public static int DL=1;
    public static boolean READY_TO_RUN=false;
    
    public static final String L0="\n";
    public static final String L1="\n";
    public static final String L1_1="\n   ";
    public static final String L2="\n\n";
    public static final String L2_1="\n   ";
    public static final String L3="\n\n\n";
    public static final String VE=" = ";
    public static final String TRUE="true";
    public static final String _="";
    public static final String _P="|";
    public static final String _R=".";
    public static final String[] NA={_};
    
    
    public static final String CHGDB="java:comp/env/jdbc/chgdb";
    public static final String APPD_SESSONID="APPDSESSIONID";
    public static final String CONTROLLER="controller";
    public static final String ID="id";
    public static final String FQDN="fqdn";
    public static final String PORT="port";
    public static final String USESSL="useSSL";
    public static final String ACCOUNT="account";
    public static final String DISPLAY_NAME="displayName";
    public static final String USERNAME="username";
    public static final String PASSWORD="password";
    public static final String ROLENAME="rolename";
    public static final String NAME="name";
    public static final String REQUESTER="requester";
    public static final String REQUEST_TIME="requestTime";
    public static final String ITEM_LOCATION="itermLocation";
    public static final String ITEM_LOCATION_TYPE="itemLocationType";
    public static final String ITEM_TYPE="itemType";
    public static final String ITEM_NAME="itemName";
    public static final String AUTHENTICATION="authentication";
    public static final String AUTHENTICATION_ID="authenticationId";
    public static final String ACCT_TYPE="acctType";
    public static final String CONTROLLER_ID="controllerId";
    public static final String CONNECTION="connnection";
    public static final String SRC_CONNECTION="sourceConnnection";
    public static final String DEST_CONNECTION="destinationConnnection";
    public static final String CHANGE="change";
    public static final String CONFIG="config";
    public static final String VALUE="value";
    public static final String LASTUPDATE="lastUpdate";
    public static final String COMPLETION_TIME="completionTime";
    public static final String APPROVED="approved";
    public static final String APPROVER="approver";
    public static final String REJECTED="rejected";
    public static final String COMPLETED="completed";
    public static final String READY_FOR_APPROVAL="readyForApproval";
    public static final String COMPLETED_BY="completedBy";
    public static final String REQUESTS="requests";
    public static final String REQUEST="request";
    public static final String DESCRIPTION="description";
    public static final String DESCR="descr";
    public static final String APPLICATION="application";
    public static final String TIER="tier";
    public static final String CHANGE_ID="changeId";
    public static final String NEW_VERSION="newVersion";
    public static final String NEW_VERSION_SRC="newVersionSource";
    public static final String NEW_VERSION_LOC="newVersionLocation";
    public static final String SRC_ITEM_LOCATION="srcItemLocation";
    public static final String DEST_ITEM_LOCATION="destItemLocation";
    public static final String ORIG_VERSION="origVersion";
    public static final String ORIG_VERSION_SRC="origVersionSource";
    public static final String EXECUTION_TIME="executionTime";
    public static final String SUCCESS="success";
    public static final String USER_ID="userId";
    public static final String GROUP="group";
    public static final String GROUP_NAME="groupName";
    public static final String GROUP_ADMIN="AppD-Admin";
    public static final String GROUP_USER="AppD-User";
    public static final String GROUP_READ_ONLY="Read-Only";
    public static final String AUTH_ID="auth_id";
    
    public static final String AUTO_DISCOVERY="auto-discovery";
    public static final String CUSTOM_MATCH_POJO="custom-match-pojo";
    public static final String HEALTH_RULE="health-rule";
    
    public static final String[] R_ITEM_TYPE_NAME={AUTO_DISCOVERY,CUSTOM_MATCH_POJO,HEALTH_RULE,"Applications","Tiers"};
    public static final String TYPE="type";
    
    public static final String[] CHANGE_GET_OPTS={"Not-ReadyForApproval","ReadyForApproval","Not-Approved","Approved","Non-Completed","Completed","All","Single"}; //parameter type
    public static final String[] CHANGE_POST_OPTS={"add_chg","update_chg","delete_chg","execute_chg","approve_chg","readyForApproval_chg","reject_chg"};
    public static final String[] REQUEST_POST_OPTS={"add_request","update_request","delete_request"};
    public static final String[] CONFIG_CHGS={"add_auth","add_conn","add_contr","update_auth","update_conn","update_contr","del_auth","del_conn","del_contr","chg_passwd","add_conf","update_conf","del_conf"};
    public static final String[] ITEM_GETS={"connectionId","applicationId","tierId",AUTO_DISCOVERY,CUSTOM_MATCH_POJO,HEALTH_RULE,"applications","tiers","custom-match-servlet"};
    
    public static final String[] R_VARS={ITEM_NAME,ITEM_TYPE,"changeId","srcConnId","destConnId","srcILContr","destILContr","srcILApp","destILApp","srcILTier","destILTier"};
    
    public static final int[] R_ITEM_TYPE={0,1,2};
    
    public static final String HASH1="v2MUKQBoeyWvsUON0fGGTdG6khNBvqOt";
    
    
    public static String _V(int position, String name){
        StringBuilder bud = new StringBuilder();
        bud.append(_L(position)).append(name);
        return bud.toString();
    }
    public static String _V(int position, String name, String value){
        StringBuilder bud = new StringBuilder();
        bud.append(_L(position)).append(name).append(VE).append(value);
        return bud.toString();
    }
    
    public static String _V(int position, String name, boolean value){
        StringBuilder bud = new StringBuilder();
        bud.append(_L(position)).append(name).append(VE).append(value);
        return bud.toString();
    }
    
    public static String _V(int position, String name, Integer value){
        StringBuilder bud = new StringBuilder();
        bud.append(_L(position)).append(name).append(VE).append(value);
        return bud.toString();
    }
    
    public static String _V(int position, String name, Long value){
        StringBuilder bud = new StringBuilder();
        bud.append(_L(position)).append(name).append(VE).append(value);
        return bud.toString();
    }
    
    public static String _V(int position, String name, Double value){
        StringBuilder bud = new StringBuilder();
        bud.append(_L(position)).append(name).append(VE).append(value);
        return bud.toString();
    }
    
    public static String _L(int position){
        switch(position){
            case 0:
                return L0;
            case 1:
                return L1;
            case 2:
                return L1_1;
            case 3:
                return L2;
            case 4:
                return L2_1;
            case 5:
                return L3;   
            default:
                return L0;
                
        }
    }
    
    
    /*
     * 
     *  Create table
     *  
     */
    public static final String CREATE_TABLE="create table ";
    public static final String CHANGE_TABLE="APPD_CHANGES";
    public static final String REQUEST_TABLE="APPD_REQUESTS";
    public static final String AUTH_TABLE="APPD_AUTH";
    public static final String CONTROLLER_TABLE="APPD_CONTROLLERS";
    public static final String GROUP_TABLE="APPD_GROUPS";
    public static final String ITEM_LOCATION_TABLE="APPD_ITEM_LOCATION";
    public static final String CONNECTION_TABLE="APPD_CONNECTION";
    public static final String CONFIG_TABLE="APPD_CONFIG";
    
    public static final String[] TABLES={CHANGE_TABLE,REQUEST_TABLE,AUTH_TABLE,CONTROLLER_TABLE,GROUP_TABLE,ITEM_LOCATION_TABLE,CONNECTION_TABLE,CONFIG_TABLE};
    
    public static final String CREATE_CHANGE=new StringBuilder().append(CREATE_TABLE).append(CHANGE_TABLE)
            .append("( id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), requestTime BIGINT, completedTime BIGINT, requester VARCHAR(128),")
            .append("descr VARCHAR(255), approver varchar(128), approved smallint, rejected smallint, ")
            .append("completed smallint, readyForApproval smallint )").toString();
    
    /**
     * <p> 
     *   CHANGE_TABLE INSERT QUERY 8 Parameters<br>
     *   REQUIRES: requestTime (BIGINT) ,completedTime (BIGINT),<br>
     *   requester (String 128), descr (String 255), <br>
     *   approver (String 128), approved (SMALLINT), <br>
     *   rejected (SMALLINT), completed (SMALLINT)  <br>
     * </p>
     */
    public static final String CHANGE_INSERT_ROW=new StringBuilder().append("INSERT INTO ").append(CHANGE_TABLE)
            .append("(requestTime,completedTime,requester,descr,approver,approved,rejected,completed,readyForApproval) VALUES(?,?,?,?,?,?,?,?,?)").toString();
    
    /**
     * <p>
     *   CHANGE_UPDATE_ROW QUERY 9 Parameters<br>
     *   REQUIRES: requestTime=? (BIGINT), completedTime=? (BIGINT),<br>
     *   requester=? (String 128), descr=? (String 255), <br>
     *   approver=? (String 128), approved=? (SMALLINT), <br>
     *   rejected=? (SMALLINT), completed=? (SMALLINT) <br>
     *   WHERE id=? (BIGINT)<br>
     * </p>
     */
    public static final String CHANGE_UPDATE_ROW=new StringBuilder().append("UPDATE ").append(CHANGE_TABLE)
            .append(" set requestTime=?, completedTime=?, requester=?, descr=?, approver=?, approved=?, rejected=?, completed=?, readyForApproval=? WHERE id=?").toString();
    
    /**
     * <p>
     *   CHANGE_UPDATE_ROW QUERY 1 Parameter<br>
     *   REQUIRES: id=? (BIGINT)<br>
     * </p>
     */
    public static final String CHANGE_DELETE_ROW=new StringBuilder().append("DELETE FROM ").append(CHANGE_TABLE)
            .append(" WHERE id=?").toString();
    
    /**
     * <p>
     *   CHANGE_SELECT_INVID_ID 1 Parameter<br>
     *    REQUIRES: ID (BIGINT)<br>
     *    RETURNS: id (BIGINT),requestTime(BIGINT),completedTime(BIGINT),<br>
     *      requester (String 128),descr (String 255),approver (String 128),<br>
     *      approved (SMALLINT),rejected (SMALLINT),completed (SMALLINT)
     * </p>
     */
    public static final String CHANGE_SELECT_INDIV_ID=new StringBuilder()
            .append("SELECT id,requestTime,completedTime,requester,descr,approver,approved,rejected,completed,readyForApproval from ")
            .append(CHANGE_TABLE).append(" where id=?").toString();
    
    /**
     * <p>
     *   CHANGE_SELECT_INVID_COMPETED 1 Parameter<br>
     *    REQUIRES: COMPLETED (SMALLINT)<br>
     *    RETURNS: id (BIGINT),requestTime(BIGINT),completedTime(BIGINT),<br>
     *      requester (String 128),descr (String 255),approver (String 128),<br>
     *      approved (SMALLINT),rejected (SMALLINT),completed (SMALLINT)
     * </p>
     */
    public static final String CHANGE_SELECT_COMPLETED=new StringBuilder()
            .append("SELECT id,requestTime,completedTime,requester,descr,approver,approved,rejected,completed,readyForApproval from ")
            .append(CHANGE_TABLE).append(" where completed=?").toString();
    
    /**
     * <p>
     *   CHANGE_SELECT_CHECK 2 Parameters<br>
     *    REQUIRES: requestTime(BIGINT), requester (String 128)<br>
     *    RETURNS: id (BIGINT),requestTime(BIGINT),completedTime(BIGINT),<br>
     *      requester (String 128),descr (String 255),approver (String 128),<br>
     *      approved (SMALLINT),rejected (SMALLINT),completed (SMALLINT)
     * </p>
     */
    public static final String CHANGE_SELECT_CHECK=new StringBuilder()
            .append("SELECT id,requestTime,completedTime,requester,descr,approver,approved,rejected,completed,readyForApproval from ")
            .append(CHANGE_TABLE).append(" where requestTime=? AND requester=?").toString();
    
    /**
     * <p>
     *   CHANGE_SELECT_INVID_COMPETED 0 Parameter<br>
     *    RETURNS: id (BIGINT),requestTime(BIGINT),completedTime(BIGINT),<br>
     *      requester (String 128),descr (String 255),approver (String 128),<br>
     *      approved (SMALLINT),rejected (SMALLINT),completed (SMALLINT)
     * </p>
     */
    public static final String CHANGE_SELECT_ALL=new StringBuilder()
            .append("SELECT id,requestTime,completedTime,requester,descr,approver,approved,rejected,completed,readyForApproval from ")
            .append(CHANGE_TABLE).toString();

    
    public static final String CREATE_REQUEST=new StringBuilder().append(CREATE_TABLE).append(REQUEST_TABLE)
            .append("( id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), changeId BIGINT, itemName varchar(128), itemType varchar(128),")
            .append("sourceLocationId BIGINT, destLocationId BIGINT, newVersion varchar(32), origVersion varchar(32),")
            .append("newVersionSrc varchar(255), orgVersionSrc varchar(255), executionTime BIGINT, completedBy varchar(128),")
            .append("completionTime BIGINT, success SMALLINT, srcConn BIGINT, destConn BIGINT )").toString();
    
    /**
     * <p>
     *    REQUEST_INSERT_INDIV_CHGID 14 Parameters<br>
     *    REQUIRES: changeId (BIGINT)<br>
     *    RETURNS: id (BIGINT), changeId (BIGINT), itemName (String 128),<br> 
     *    itemType (String 128), sourceLocation (String 255), destLocation (String 255), <br> 
     *    newVersion (String 32), origVersion (String 32), newVersionSrc (String 255),<br> 
     *    orgVersionSrc (String 255), executionTime (BIGINT), completedBy (String 128),<br>
     *    completionTime (BIGINT), success (SMALLINT)
     * </p>
     */
    public static final String REQUEST_INSERT_INDIV=new StringBuilder()
            .append("INSERT INTO ").append(REQUEST_TABLE)
            .append("(changeId, itemName, itemType, sourceLocationId, destLocationId, newVersion, origVersion,")
            .append(" newVersionSrc, orgVersionSrc, executionTime, completedBy, completionTime, success, srcConn, destConn) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ")
            .toString();
    
    /**
     * <p>
     *    REQUEST_SELECT_INDIV_CHGID 1 Parameter<br>
     *    REQUIRES: changeId (BIGINT)<br>
     *    RETURNS: id (BIGINT), changeId (BIGINT), itemName (String 128),<br> 
     *    itemType (String 128), sourceLocation (String 255), destLocation (String 255), <br> 
     *    newVersion (String 32), origVersion (String 32), newVersionSrc (String 255),<br> 
     *    orgVersionSrc (String 255), executionTime (BIGINT), completedBy (String 128),<br>
     *    completionTime (BIGINT), success (SMALLINT)
     * </p>
     */
    public static final String REQUEST_SELECT_INDIV_CHGID=new StringBuilder()
            .append("SELECT id, changeId, itemName, itemType, sourceLocationId, destLocationId, newVersion, origVersion,")
            .append(" newVersionSrc, orgVersionSrc, executionTime, completedBy, completionTime, success, srcConn, destConn from ")
            .append(REQUEST_TABLE).append(" where changeId=?").toString();
    
    
    /**
     * <p>
     *    REQUEST_SELECT_CHECK 1 Parameter<br>
     *    REQUIRES: changeId (BIGINT)<br>
     *    RETURNS: id (BIGINT), changeId (BIGINT), itemName (String 128),<br> 
     *    itemType (String 128), sourceLocation (String 255), destLocation (String 255), <br> 
     *    newVersion (String 32), origVersion (String 32), newVersionSrc (String 255),<br> 
     *    orgVersionSrc (String 255), executionTime (BIGINT), completedBy (String 128),<br>
     *    completionTime (BIGINT), success (SMALLINT)
     * </p>
     */
    public static final String REQUEST_SELECT_CHECK=new StringBuilder()
            .append("SELECT id, changeId, itemName, itemType, sourceLocationId, destLocationId, newVersion, origVersion,")
            .append(" newVersionSrc, orgVersionSrc, executionTime, completedBy, completionTime, success, srcConn, destConn from ")
            .append(REQUEST_TABLE).append(" where changeId=? AND itemName=? AND itemType=?").toString();
    
    /**
     * <p>
     *    REQUEST_SELECT_INDIV_CHGID 1 Parameter<br>
     *    REQUIRES: changeId (BIGINT)<br>
     *    RETURNS: id (BIGINT), changeId (BIGINT), itemName (String 128),<br> 
     *    itemType (String 128), sourceLocation (String 255), destLocation (String 255), <br> 
     *    newVersion (String 32), origVersion (String 32), newVersionSrc (String 255),<br> 
     *    orgVersionSrc (String 255), executionTime (BIGINT), completedBy (String 128),<br>
     *    completionTime (BIGINT), success (SMALLINT)
     * </p>
     */
    public static final String REQUEST_SELECT_INDIV_ALL=new StringBuilder()
            .append("SELECT id, changeId, itemName, itemType, sourceLocationId, destLocationId, newVersion, origVersion,")
            .append(" newVersionSrc, orgVersionSrc, executionTime, completedBy, completionTime, success, srcConn, destConn from ")
            .append(REQUEST_TABLE).toString();
    
    /**
     * <p>
     *    REQUEST_SELECT_INDIV_ID 1 Parameter<br>
     *    REQUIRES: changeId (BIGINT)<br>
     *    RETURNS: id (BIGINT), changeId (BIGINT), itemName (String 128),<br> 
     *    itemType (String 128), sourceLocation (String 255), destLocation (String 255), <br> 
     *    newVersion (String 32), origVersion (String 32), newVersionSrc (String 255),<br> 
     *    orgVersionSrc (String 255), executionTime (BIGINT), completedBy (String 128),<br>
     *    completionTime (BIGINT), success (SMALLINT)
     * </p>
     */
    public static final String REQUEST_SELECT_INDIV_ID=new StringBuilder()
            .append("SELECT id, changeId, itemName, itemType, sourceLocationId, destLocationId, newVersion, origVersion,")
            .append(" newVersionSrc, orgVersionSrc, executionTime, completedBy, completionTime, success, srcConn, destConn from ")
            .append(REQUEST_TABLE).append(" where id=?").toString();
    
    /**
     * <p>
     *    REQUEST_UPDATE_INDIV_ID 14 Parameters<br>
     *    REQUIRES: changeId (BIGINT), itemName (String 128),<br> 
     *    itemType (String 128), sourceLocation (String 255), destLocation (String 255), <br> 
     *    newVersion (String 32), origVersion (String 32), newVersionSrc (String 255),<br> 
     *    orgVersionSrc (String 255), executionTime (BIGINT), completedBy (String 128),<br>
     *    completionTime (BIGINT), success (SMALLINT), id (BIGINT)<br>
     * </p>
     */
    public static final String REQUEST_UPDATE_INDIV_ID=new StringBuilder()
            .append("UPDATE ").append(REQUEST_TABLE).append(" set changeId=?, itemName=?, itemType=?, sourceLocationId=?, destLocationId=?, newVersion=?, origVersion=?,")
            .append(" newVersionSrc=?, orgVersionSrc=?, executionTime=?, completedBy=?, completionTime=?, success=?, srcConn=?, destConn=? WHERE id=? ")
            .toString();
    
    /**
     * <p>
     *    REQUEST_UPDATE_INDIV_ID 14 Parameters<br>
     *    REQUIRES: changeId (BIGINT), itemName (String 128),<br> 
     *    itemType (String 128), sourceLocationId (BIGINT), sourceConfigLocation (String 255), destLocationId (BIGINT), <br> 
     *    destConfigLocation (String 255),newVersion (String 32), origVersion (String 32), newVersionSrc (String 255),<br> 
     *    orgVersionSrc (String 255), executionTime (BIGINT), completedBy (String 128),<br>
     *    completionTime (BIGINT), success (SMALLINT), id (BIGINT)<br>
     * </p>
     */
    public static final String REQUEST_DELETE_INDIV=new StringBuilder()
            .append("DELETE FROM ").append(REQUEST_TABLE).append(" WHERE changeId=? AND itemName=? AND  itemType=? AND  sourceLocationId=? AND ")
            .append(" destLocationId=? AND  newVersion=? AND origVersion=? AND newVersionSrc=? AND orgVersionSrc=? ")
            .toString();
    
    /**
     * <p>
     *    REQUEST_UPDATE_CHG_ID 1 Parameters<br>
     *    REQUIRES: changeId (BIGINT)
     * </p>
     */
    public static final String REQUEST_DELETE_BY_CHG_ID=new StringBuilder()
            .append("DELETE FROM ").append(REQUEST_TABLE).append(" WHERE changeId=?")
            .toString();
    
    /**
     * <p>
     *    REQUEST_UPDATE_INDIV_ID 1 Parameter<br>
     *    REQUIRES: id (BIGINT)
     * </p>
     */
    public static final String REQUEST_DELETE_INDIV_ID=new StringBuilder()
            .append("DELETE FROM ").append(REQUEST_TABLE).append(" WHERE id=?")
            .toString();
    
    
    public static final String CREATE_AUTH=new StringBuilder().append(CREATE_TABLE).append(AUTH_TABLE)
            .append("( id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), displayName varchar(128), userName varchar(128), password varchar(128), acctType SMALLINT, groupName varchar(128))")
            .toString();
    
    /**
     * <p>
     *    AUTH_INSERT_ROW 4 Parameters<br>
     *    REQUIRES: displayName (String 128),userName (String 128), <br>
     *    password (String 128), acctType (SMALLINT)
     * </p>
     */
    public static final String AUTH_INSERT_ROW=new StringBuilder().append("INSERT INTO ").append(AUTH_TABLE)
            .append("(displayName,userName,password,acctType,groupName) VALUES(?,?,?,?,?)").toString();
    
    /**
     * <p>
     *    AUTH_INSERT_ROW 5 Parameters<br>
     *    REQUIRES: displayName (String 128),userName (String 128), <br>
     *    password (String 128), acctType (SMALLINT), id (BIGINT)<br>
     * </p>
     */
    public static final String AUTH_UPDATE_ROW=new StringBuilder().append("UPDATE ").append(AUTH_TABLE)
            .append(" set displayName=?, userName=?, password=?, acctType=? , groupName=? WHERE id=?").toString();
    
    /**
     * <p>
     *    AUTH_DELETE_ROW 1 Parameter1<br>
     *    REQUIRES: id (BIGINT)<br>
     * </p>
     */
    public static final String AUTH_DELETE_ROW=new StringBuilder().append("DELETE FROM ").append(AUTH_TABLE)
            .append(" WHERE id=?").toString();
    
    /**
     * <p>
     *    AUTH_SELECT_ALL 0 Parameters<br>
     *    RETURNS: id (BIGINT), displayName (String 128), <br>
     *    userName (String 128), password (String 128), acctType (SMALLINT)<br>
     * </p>
     */
    public static final String AUTH_SELECT_ALL=new StringBuilder().append("SELECT id,displayName,userName,password,acctType,groupName from ")
            .append(AUTH_TABLE).toString();
    
    /**
     * <p>
     *    AUTH_SELECT_ALL 1 Parameter<br>
     *    REQUIRES: userName (String 128)<br>
     *    RETURNS: id (BIGINT), displayName (String 128), <br>
     *    userName (String 128), password (String 128), acctType (SMALLINT)<br>
     * </p>
     */
    public static final String AUTH_SELECT_INDIV=new StringBuilder().append("SELECT id,displayName,userName,password,acctType,groupName from ")
            .append(AUTH_TABLE).append(" where userName=?").toString();
    
    
    /**
     * <p>
     *    AUTH_SELECT_ALL 1 Parameter<br>
     *    REQUIRES: userName (String 128)<br>
     *    RETURNS: id (BIGINT), displayName (String 128), <br>
     *    userName (String 128), password (String 128), acctType (SMALLINT)<br>
     * </p>
     */
    public static final String AUTH_SELECT_INDIV_ID=new StringBuilder().append("SELECT id,displayName,userName,password,acctType,groupName from ")
            .append(AUTH_TABLE).append(" where id=?").toString();
    
    
    
    
    public static final String CREATE_GROUPS=new StringBuilder().append(CREATE_TABLE).append(GROUP_TABLE)
            .append("( id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), groupName varchar(128), userId BIGINT)")
            .toString();
    
    
    
    /**
     * <p>
     *    GROUP_INSERT_INDIV 2 Parameters<br>
     *    REQUIRES: userId (BIGINT),groupName (String 128) <br>
     * </p>
     */
    public static final String GROUP_INSERT_INDIV=new StringBuilder().append("INSERT INTO ")
            .append(GROUP_TABLE).append("(userId,groupName) VALUES(?,?)").toString();
    
    /**
     * <p>
     *    GROUP_UPDATE_INDIV 2 Parameters<br>
     *    REQUIRES: groupName (String 128), userId (BIGINT)
     * </p>
     */
    public static final String GROUP_UPDATE_INDIV=new StringBuilder().append("UPDATE ")
            .append(GROUP_TABLE).append(" set groupName=? WHERE userId=?").toString();
    
    
    /**
     * <p>
     *    GROUP_DELETE_INDIV 2 Parameters<br>
     *    REQUIRES: groupName (String 128), userId (BIGINT)
     * </p>
     */
    public static final String GROUP_DELETE_INDIV=new StringBuilder().append("DELETE FROM ")
            .append(GROUP_TABLE).append(" WHERE groupName=? AND userId=?").toString();
    
    /**
     * <p>
     *    GROUP_SELECT_INDIV 2 Parameters<br>
     *    REQUIRES: groupName (String 128), userId (BIGINT)<br>
     *    RETURNS: id (BIGINT), groupName (String 128), userId (BIGINT)<br>
     * </p>
     */
    public static final String GROUP_SELECT_INDIV=new StringBuilder().append("SELECT id,groupName,userId from ")
            .append(GROUP_TABLE).append(" where userId=?").toString();
    
    /**
     * <p>
     *    GROUP_SELECT_CHECK 2 Parameters<br>
     *    REQUIRES: groupName (String 128), userId (BIGINT)<br>
     *    RETURNS: id (BIGINT), groupName (String 128), userId (BIGINT)<br>
     * </p>
     */
    public static final String GROUP_SELECT_CHECK=new StringBuilder().append("SELECT id,groupName,userId from ")
            .append(GROUP_TABLE).append(" where userId=? and groupName=?").toString();
    /**
     * <p>
     *    GROUP_SELECT_INDIV 0 Parameters<br>
     *    RETURNS: id (BIGINT), groupName (String 128), userId (BIGINT)<br>
     * </p>
     */
    public static final String GROUP_SELECT_ALL=new StringBuilder().append("SELECT id,groupName,userId from ")
            .append(GROUP_TABLE).toString();
    
    
    
    public static final String CREATE_CONNECTION=new StringBuilder().append(CREATE_TABLE).append(CONNECTION_TABLE)
            .append("( id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), displayName varchar(128), authId BIGINT, controllerId BIGINT)")
            .toString();
    
    /**
     * <p>
     *    CONNECTION_INSERT_INDIV 3 Parameters<br>
     *    REQUIRES: displayName (String 128), authId (BIGINT), <br>
     *    controllerId (BIGINT)
     * </p>
     */
    public static final String CONNECTION_INSERT_INDIV=new StringBuilder().append("INSERT INTO ")
            .append(CONNECTION_TABLE).append(" (displayName,authId,controllerId) VALUES(?,?,?)").toString();
    
    /**
     * <p>
     *    CONNECTION_SELECT_INDIV 1 Parameter<br>
     *    REQUIRES: id (BIGINT)<br>
     *    RETURNS: id (BIGINT), displayName (String 128),  <br>
     *    authId (BIGINT),controllerId (BIGINT)
     * </p>
     */
    public static final String CONNECTION_SELECT_INDIV=new StringBuilder().append("SELECT id,displayName,authId,controllerId from ")
            .append(CONNECTION_TABLE).append(" where id=?").toString();
    
    /**
     * <p>
     *    CONNECTION_SELECT_CHECK 2 Parameter<br>
     *    REQUIRES: authId (BIGINT), controllerId (BIGINT)<br>
     *    RETURNS: id (BIGINT), displayName (String 128),  <br>
     *    authId (BIGINT),controllerId (BIGINT)
     * </p>
     */
    public static final String CONNECTION_SELECT_CHECK=new StringBuilder().append("SELECT id,displayName,authId,controllerId from ")
            .append(CONNECTION_TABLE).append(" where authId=? and controllerId=?").toString();
    
    
    /**
     * <p>
     *    CONNECTION_SELECT_ALL 0 Parameters<br>
     *    RETURNS: id (BIGINT), displayName (String 128),  <br>
     *    authId (BIGINT),controllerId (BIGINT)
     * </p>
     */
    public static final String CONNECTION_SELECT_ALL=new StringBuilder().append("SELECT id,displayName,authId,controllerId from ")
            .append(CONNECTION_TABLE).toString();
    
    /**
     * <p>
     *    CONNECTION_UPDATE_INDIV 4 Parameter<br>
     *    REQUIRES displayName (String 128), authId (BIGINT),<br>
     *    controllerId (BIGINT), id (BIGINT) <br>
     * </p>
     */
    public static final String CONNECTION_UPDATE_INDIV=new StringBuilder().append("UPDATE ")
            .append(CONNECTION_TABLE).append(" set displayName=?,authId=?,controllerId=? WHERE id=?").toString();
    
    /**
     * <p>
     *    CONNECTION_DELETE_INDIV 4 Parameter<br>
     *    REQUIRES displayName (String 128), authId (BIGINT),<br>
     *    controllerId (BIGINT), id (BIGINT) <br>
     * </p>
     */
    public static final String CONNECTION_DELETE_INDIV_AUTH=new StringBuilder().append("DELETE FROM ")
            .append(CONNECTION_TABLE).append(" WHERE authId=? AND controllerId=? ").toString();
    
    /**
     * <p>
     *    CONNECTION_DELETE_INDIV 4 Parameter<br>
     *    REQUIRES displayName (String 128), authId (BIGINT),<br>
     *    controllerId (BIGINT), id (BIGINT) <br>
     * </p>
     */
    public static final String CONNECTION_DELETE_INDIV_ID=new StringBuilder().append("DELETE FROM ")
            .append(CONNECTION_TABLE).append(" WHERE id=?").toString();
    
    
    
    public static final String CREATE_ITEM_LOCATION=new StringBuilder().append(CREATE_TABLE).append(ITEM_LOCATION_TABLE)
            .append("( id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), controller varchar(128),")
            .append(" application varchar(128), tier varchar(128), locationType SMALLINT)")
            .toString();
    
    /**
     * <p>
     *    ITEM_LOCATION_INSERT_TIER 4 Parameters<br>
     *    REQUIRES: controller (String 128), application (String 128),<br>
     *    tier (String 128), locationType (SMALLINT)
     * </p>
     */
    public static final String ITEM_LOCATION_INSERT_TIER=new StringBuilder().append("INSERT INTO ")
            .append(ITEM_LOCATION_TABLE).append(" (controller,application,tier,locationType) VALUES(?,?,?,?)").toString();
    
    /**
     * <p>
     *    ITEM_LOCATION_INSERT_TIER 3 Parameters<br>
     *    REQUIRES: controller (String 128), application (String 128),<br>
     *    locationType (SMALLINT)
     * </p>
     */
    public static final String ITEM_LOCATION_INSERT_APP=new StringBuilder().append("INSERT INTO ")
            .append(ITEM_LOCATION_TABLE).append(" (controller,application,locationType) VALUES(?,?,?)").toString();
    
    /**
     * <p>
     *    ITEM_LOCATION_DELETE_APP 3 Parameters<br>
     *    REQUIRES: controller (String 128), application (String 128),<br>
     *    locationType (SMALLINT)
     * </p>
     */
    public static final String ITEM_LOCATION_DELETE_APP=new StringBuilder().append("DELETE FROM ")
            .append(ITEM_LOCATION_TABLE).append(" WHERE controller=? AND application=? AND locationType=?").toString();
    
    /**
     * <p>
     *    ITEM_LOCATION_DELETE_APP 3 Parameters<br>
     *    REQUIRES: controller (String 128), application (String 128),<br>
     *    locationType (SMALLINT)
     * </p>
     */
    public static final String ITEM_LOCATION_DELETE_TIER=new StringBuilder().append("DELETE FROM ")
            .append(ITEM_LOCATION_TABLE).append(" WHERE controller=? AND application=? AND tier=? AND locationType=?").toString();
    
    /**
     * <p>
     *    ITEM_LOCATION_SELECT_INDIV_ID 1 Parameter<br>
     *    REQUIRES: id (BIGINT)<br>
     *    RETURNS: id (BIGINT), controller (String 128), application (String 128),<br>
     *    tier (String 128), locationType (SMALLINT)
     * </p>
     */
    public static final String ITEM_LOCATION_SELECT_INDIV_ID=new StringBuilder().append("SELECT id,controller,application,tier,locationType from ")
            .append(ITEM_LOCATION_TABLE).append(" where id=?").toString();
    
    /**
     * <p>
     *    ITEM_LOCATION_SELECT_CHECK_APP 2 Parameters<br>
     *    REQUIRES: id (BIGINT)<br>
     *    RETURNS: id (BIGINT), controller (String 128), application (String 128),<br>
     *    tier (String 128), locationType (SMALLINT)
     * </p>
     */
    public static final String ITEM_LOCATION_SELECT_CHECK_APP=new StringBuilder().append("SELECT id,controller,application,tier,locationType from ")
            .append(ITEM_LOCATION_TABLE).append(" where controller=? AND application=? AND locationType=0").toString();
    
    /**
     * <p>
     *    ITEM_LOCATION_SELECT_CHECK_TIER 3 Parameters<br>
     *    REQUIRES: id (BIGINT)<br>
     *    RETURNS: id (BIGINT), controller (String 128), application (String 128),<br>
     *    tier (String 128), locationType (SMALLINT)
     * </p>
     */
    public static final String ITEM_LOCATION_SELECT_CHECK_TIER=new StringBuilder().append("SELECT id,controller,application,tier,locationType from ")
            .append(ITEM_LOCATION_TABLE).append(" where controller=? AND application=? AND tier=? AND locationType=1").toString();
    /**
     * <p>
     *    ITEM_LOCATION_SELECT_INDIV_APP 3 Parameter<br>
     *    REQUIRES: controller (String 128), application (String 128), locationType (SMALLINT)<br>
     *    RETURNS: id (BIGINT), controller (String 128), application (String 128),<br>
     *    tier (String 128), locationType (SMALLINT)
     * </p>
     */
    public static final String ITEM_LOCATION_SELECT_INDIV_APP=new StringBuilder().append("SELECT id,controller,application,tier,locationType from ")
            .append(ITEM_LOCATION_TABLE).append(" where controller=? AND application=? AND locationType=?").toString();
    
    /**
     * <p>
     *    ITEM_LOCATION_SELECT_INDIV_TIER 4 Parameter<br>
     *    REQUIRES: controller (String 128), application (String 128), tier (String 128), locationType (SMALLINT)<br>
     *    RETURNS: id (BIGINT), controller (String 128), application (String 128),<br>
     *    tier (String 128), locationType (SMALLINT)
     * </p>
     */
    public static final String ITEM_LOCATION_SELECT_INDIV_TIER=new StringBuilder().append("SELECT id,controller,application,tier,locationType from ")
            .append(ITEM_LOCATION_TABLE).append(" where controller=? AND application=? AND tier=? AND locationType=?").toString();
    
    /**
     * <p>
     *    ITEM_LOCATION_SELECT_ALL 0 Parameters<br>
     *    RETURNS: id (BIGINT), controller (String 128), application (String 128),<br>
     *    tier (String 128), locationType (SMALLINT)
     * </p>
     */
    public static final String ITEM_LOCATION_SELECT_ALL=new StringBuilder().append("SELECT id,controller,application,tier,locationType from ")
            .append(ITEM_LOCATION_TABLE).toString();
    
    /**
     * <p>
     *    ITEM_LOCATION_UPDATE 5 Parameters<br>
     *    REQUIRES: controller (String 128), application (String 128),<br>
     *    tier (String 128), locationType (SMALLINT), id (BIGINT)<br>
     * </p>
     */
    public static final String ITEM_LOCATION_UPDATE=new StringBuilder().append("UPDATE ")
            .append(ITEM_LOCATION_TABLE).append(" set controller=?,application=?,tier=?,locationType=? WHERE id=?").toString();
    
    
    
    
    public static final String CREATE_CONTROLLER=new StringBuilder().append(CREATE_TABLE).append(CONTROLLER_TABLE)
            .append("( id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), displayName varchar(128), fqdn varchar(128),")
            .append(" account varchar(128), port varchar(8), useSSL SMALLINT)")
            .toString();
    
    /**
     * <p>
     *    CONTROLLER_INSERT_ROW 5 Parameters<br>
     *    REQUIRES: displayName (String 128), fqdn (String 128),<br>
     *    account (String 128), port (String 8), useSSL (SMALLINT)<br>
     * </p>
     */
    public static final String CONTROLLER_INSERT_ROW=new StringBuilder().append("INSERT INTO ")
            .append(CONTROLLER_TABLE).append(" (displayName,fqdn,account,port,useSSL) VALUES(?,?,?,?,?)").toString();
    
    /**
     * <p>
     *    CONTROLLER_SELECT_INDIV 1 Parameter<br>
     *    REQUIRES: id (BIGINT)<br>
     * </p>
     */
    public static final String CONTROLLER_SELECT_INDIV=new StringBuilder().append("SELECT id,fqdn,displayName,account,port,useSSL from ")
            .append(CONTROLLER_TABLE).append(" where id=?").toString();
    
    /**
     * <p>
     *    CONTROLLER_SELECT_INDIV_CHECK 3 Parameters<br>
     *    REQUIRES: fqdn (String 128), account (String 128),<br> 
     *    port (String 8)<br>
     * </p>
     */
    public static final String CONTROLLER_SELECT_INDIV_CHECK=new StringBuilder().append("SELECT id,fqdn,displayName,account,port,useSSL from ")
            .append(CONTROLLER_TABLE).append(" where fqdn=? AND account=? AND port=?").toString();
    
    /**
     * <p>
     *    CONTROLLER_SELECT_ALL 0 Parameter<br>
     *    REQUIRES: id (BIGINT), displayName (String 128), fqdn (String 128),<br>
     *    account (String 128), port (String 8), useSSL (SMALLINT),<br>
     * </p>
     */
    public static final String CONTROLLER_SELECT_ALL=new StringBuilder().append("SELECT id,fqdn,displayName,account,port,useSSL from ")
            .append(CONTROLLER_TABLE).toString();
    
    /**
     * <p>
     *    CONTROLLER_DELETE_INDIV 1 Parameter<br>
     *    REQUIRES: id (BIGINT)
     * </p>
     */
    public static final String CONTROLLER_DELETE_INDIV=new StringBuilder().append("DELETE FROM ")
            .append(CONTROLLER_TABLE).append(" where id=?").toString();
    
    /**
     * <p>
     *    CONTROLLER_UPDATE_INDIV 6 Parameters<br>
     *    REQUIRES: displayName (String 128), fqdn (String 128),<br>
     *    account (String 128), port (String 8), useSSL (SMALLINT),<br>
     *    id (BIGINT)<br>
     * </p>
     */
    public static final String CONTROLLER_UPDATE_INDIV=new StringBuilder().append("UPDATE ")
            .append(CONTROLLER_TABLE).append(" set displayName=?,fqdn=?,account=?,port=?,useSSL=? WHERE id=?").toString();
    
    
    /*
     * 
     */
    public static final String CREATE_CONFIG=new StringBuilder().append(CREATE_TABLE).append(CONFIG_TABLE)
            .append("( name varchar(128), value varchar(255), lastUpdate BIGINT)")
            .toString();
    
    /**
     * <p>
     *    CONFIG_SELECT_INDIV 1 Parameter<br>
     *    REQUIRES: name (String 128)<br>
     * </p>
     */
    public static final String CONFIG_SELECT_INDIV=new StringBuilder().append("SELECT name,value,lastUpdate from ")
            .append(CONFIG_TABLE).append(" where name=?").toString();
    
    /**
     * <p>
     *    CONFIG_SELECT_ALL 0 Parameter<br>
     *    REQUIRES: name (String 128)<br>
     * </p>
     */
    public static final String CONFIG_SELECT_ALL=new StringBuilder().append("SELECT name,value,lastUpdate from ")
            .append(CONFIG_TABLE).toString();
    
    /**
     * <p>
     *    CONFIG_UPDATE_INDIV 3 Parameter<br>
     *    REQUIRES: value (String 255), lastUpdate BIGINT, name (String 128)<br>
     * </p>
     */
    public static final String CONFIG_UPDATE_INDIV=new StringBuilder().append("UPDATE ")
            .append(CONFIG_TABLE).append(" set value=?,lastUpdate=? where name=?").toString();
    
    /**
     * <p>
     *    CONFIG_UPDATE_INDIV 3 Parameter<br>
     *    REQUIRES: value (String 255), lastUpdate BIGINT, name (String 128)<br>
     * </p>
     */
    public static final String CONFIG_INSERT_INDIV=new StringBuilder().append("INSERT INTO ")
            .append(CONFIG_TABLE).append("(name,value,lastUpdate) VALUES(?,?,?)").toString();
    
    
    /**
     * <p>
     *    CONFIG_DELETE_INDIV 3 Parameter<br>
     *    REQUIRES: value (String 255), lastUpdate BIGINT, name (String 128)<br>
     * </p>
     */
    public static final String CONFIG_DELETE_INDIV=new StringBuilder().append("DELETE FROM ")
            .append(CONFIG_TABLE).append(" where name=?").toString();
    
    
    
    public static String getCreateTableQuery(String table){
        if(table.equals(CHANGE_TABLE)) return CREATE_CHANGE;
        if(table.equals(REQUEST_TABLE)) return CREATE_REQUEST;
        if(table.equals(AUTH_TABLE)) return CREATE_AUTH;
        if(table.equals(GROUP_TABLE)) return CREATE_GROUPS;
        if(table.equals(CONNECTION_TABLE)) return CREATE_CONNECTION;
        if(table.equals(ITEM_LOCATION_TABLE)) return CREATE_ITEM_LOCATION;
        if(table.equals(CONTROLLER_TABLE)) return CREATE_CONTROLLER;
        if(table.equals(CONFIG_TABLE)) return CREATE_CONFIG;
  
        return "";
    }
    
    public static int convertToInt(boolean bol){
        if(bol) return 1;
        return 0;
    }
    
    public static boolean convertToBoolean(int val){
        if(val == 0) return false;
        return true;
    }
    
    public static boolean convertToBoolean(String val){
        if(val!=null && val.equalsIgnoreCase(TRUE)) return true;
        return false;
    }
    
    
    public static long convertToLong(String val){
        int value=0;
        try{
            return new Long(val).longValue();
            
        }catch(Exception e){}
        return value;
    }
    
    public static int convertToInt(String val){
        int value=0;
        try{
            return new Integer(val).intValue();
            
        }catch(Exception e){}
        return value;
    }
    
    
    
    
}
