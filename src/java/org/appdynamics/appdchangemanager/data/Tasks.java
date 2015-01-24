/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.appdynamics.appdchangemanager.data;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 *
 * @author gilbert.solorzano
 */
@XmlSeeAlso(Task.class)
public class Tasks {
    private ArrayList<Task> tasks=new ArrayList<Task>();
    
    public Tasks(){}

    @XmlElement
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    
    
}
