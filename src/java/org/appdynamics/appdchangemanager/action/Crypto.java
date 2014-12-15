/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.action;

import org.appdynamics.crypto.CryptoTool;
import org.appdynamics.crypto.StringLogger;

/**
 *
 * @author gilbert.solorzano
 */
public class Crypto {
    private static final StringLogger sl = CryptoTool.getStringLogger();
    
    public static String decrypt(String str1){
        try{
            
            return sl.toLower1(sl.format1(str1));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static String crypt(String str1){
        try{
            return sl.toUpper1(str1);     
        }catch(Exception e){e.printStackTrace();}
        return null;
    }
}
