/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.action;

import org.appdynamics.appdchangemanager.resources.ACMS;

import java.io.BufferedWriter;
import java.io.File; 
import java.io.FileWriter;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilbert.solorzano
 * 
 * This is going to take a request item and save the source and destination as
 * xml files. The name of the files will be created using information in the object.
 * 
 * This needs to be able to create files, does it need access to REST ?(no for now)
 * <BaseDirectory>/
 * 
 * The fileName. how are getting this SRC_<change_id>_<request_id>.xml
 */
public class ManageChgFiles {
    private static final Logger log=Logger.getLogger(ManageChgFiles.class.getName());
    
    /**
     * <p>
     *  This will write out the README file
     * </p>
     * @param changeId
     * @param fileContent
     * @return 
     */
    public static int writeFile(Long changeId, String fileContent){
        int retValue=0;
        //retValue = createChgDirectory(changeId);

        String baseDir1=new StringBuilder().append(ACMS.BASE_DIRECTORY_V).append("APPD/").toString();
        
        File dir = new File(baseDir1);
        if(!dir.exists()) dir.mkdir();
        
        String baseDir=new StringBuilder().append(baseDir1).append("CHG_").append(changeId).toString();
        
        File dir1 = new File(baseDir);
        if(!dir1.exists()) dir1.mkdir();
        
        String fileName = new StringBuilder().append("/")
                .append("README.txt").toString();
        
        log.log(Level.INFO, new StringBuilder().append("Writting the following README file ")
                .append(fileName).append(".").toString());
        
        retValue=writeFile(baseDir,fileName,fileContent);
        
        return retValue;
    }
    
    /*
     * This is the main one used.
     */
    public static int writeFile(String type, Long changeId, Long requestId, String fileContent){
        int retValue=0;
        //retValue = createChgDirectory(changeId);
        String baseDir1=new StringBuilder().append(ACMS.BASE_DIRECTORY_V).append("APPD/").toString();
        
        File dir = new File(baseDir1);
        if(!dir.exists()) dir.mkdir();
        
        String baseDir=new StringBuilder().append(baseDir1).append("CHG_").append(changeId).toString();
        
        File dir1 = new File(baseDir);
        if(!dir1.exists()) dir1.mkdir();

        /*
        String fileName = new StringBuilder().append(ACMS.BASE_DIRECTORY_V)
                .append("CHG_").append(changeId).append("/").append(type).append("_")
                .append(requestId).append(".xml").toString();
        */
        String fileName = new StringBuilder().append("/")
                .append(type).append("_").append(requestId).append(".xml").toString();
        
        log.log(Level.INFO, new StringBuilder().append("Writting the following backup file ")
                .append(fileName).append(".").toString());
        
        retValue=writeFile(baseDir,fileName,fileContent);
        
        return retValue;
    }
    
    public static int writeFile(Long app, String controller, String fileContent){
        int retValue=0;
        //retValue=createAppDirectory(app,controller);
        String baseDir1=new StringBuilder().append(ACMS.BASE_DIRECTORY_V).append("APPD/").append(controller).append("/").toString();
        
        File dir = new File(baseDir1);
        if(!dir.exists()) dir.mkdir();
        
        String baseDir=new StringBuilder().append(baseDir1).append("AppExport_").append(app).append("/").toString();
        
        File dir1 = new File(baseDir);
        if(!dir1.exists()) dir1.mkdir();
        
        String fileName=new StringBuilder().append("/").append("AppExport_").append(app).append("/").toString();
        
        log.log(Level.INFO, new StringBuilder().append("Writting the following AppExport file ")
                .append(fileName).append(".").toString());
        
        retValue=writeFile(baseDir,fileName,fileContent);
        
        return retValue;
    }
    
    private static int writeFile(String basePath,String fileName, String fileContent){
        int retValue=0;
        BufferedWriter outF=null;
        try{
            File base=new File(basePath);
            StringBuilder bud = new StringBuilder();
               bud.append(base.getAbsolutePath()).append(fileName);
            fileName=bud.toString();
            File file = new File(fileName);
            outF=new BufferedWriter(new FileWriter(file));
            outF.write(fileContent);
            outF.flush();
            outF.close();
        }catch(Exception e){
            log.severe(new StringBuilder()
                    .append("Exception occurred while attempting to write file: ")
                    .append(fileName).append("\n. With content : ").append(fileContent)
                    .append(".\n With exception: ").append(e.getMessage()).toString());
            retValue=1;
        }finally{
            try{
                if(outF != null) outF.close();
            }catch(Exception e){}
        }
        
        return retValue;
    }
    
    private static int writeFile(String fileName, String fileContent){
        int retValue=0;
        BufferedWriter outF=null;
        try{
            File file = new File(fileName);
            outF=new BufferedWriter(new FileWriter(file));
            outF.write(fileContent);
            outF.flush();
            outF.close();
        }catch(Exception e){
            log.severe(new StringBuilder()
                    .append("Exception occurred while attempting to write file: ")
                    .append(fileName).append("\n. With content : ").append(fileContent)
                    .append(".\n With exception: ").append(e.getMessage()).toString());
            retValue=1;
        }finally{
            try{
                if(outF != null) outF.close();
            }catch(Exception e){}
        }
        
        return retValue;
    }
    
    private static int createChgDirectory(Long changeId){
        int retValue=0;
        String baseDir1=new StringBuilder().append(ACMS.BASE_DIRECTORY_V).append("APPD/").toString();
        
        File dir = new File(baseDir1);
        if(!dir.exists()) dir.mkdir();
        
        String baseDir=new StringBuilder().append(baseDir1).append("CHG_").append(changeId).toString();
        
        File dir1 = new File(baseDir);
        if(!dir1.exists()) dir1.mkdir();
        
        return retValue;
    }
    
    private static int createAppDirectory(Long app, String controller){
        int retValue=0;
        String baseDir1=new StringBuilder().append(ACMS.BASE_DIRECTORY_V).append("APPD/").append(controller).append("/").toString();
        
        File dir = new File(baseDir1);
        if(!dir.exists()) dir.mkdir();
        
        String baseDir=new StringBuilder().append(baseDir1).append("AppExport_").append(app).append("/").toString();
        
        File dir1 = new File(baseDir);
        if(!dir1.exists()) dir1.mkdir();
        
        return retValue;
    }
}
