/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package item;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Queue;
public class mari {





public class Productline implements Runnable  {
    private int lineId;
    private String nameline;
    private String status;
    private List<Task>tasks;
    boolean working=true;
    Sharedclass share;
    Task currenttask;

    public Productline(int lineId, String nameline, String status) {
        this.lineId = lineId;
        this.nameline = nameline;
        this.status = status;
        this.tasks = new LinkedList<>();
    }
    @Override
   public void run(){
   while(working){
   if(!tasks.isEmpty()){
   Task currenttask=tasks.remove(0);
  /* currenttask.assignedline(this);/*ربط المهمة بهذا الخط*/
   this.status="Active";
       System.out.println("line"+lineId+"started working");
       try{
       for(int i=0;i<=100;i+=20){
       Thread.sleep(1000);
       currenttask.getComplationpercentage();
       
       }
           currenttask.setStatus("completed");
       }
       catch(InterruptedException e){
       e.getMessage();
       }
       this.status="Idle";
       currenttask=null;
       }
   
   else{
   try{
   Thread.sleep(100);
   }
   catch(InterruptedException e){
       System.out.println(e.getMessage());
   
   }
       System.out.println("line"+lineId+"stopped");
   }
   }
   }
      public void addTask(Task task){
   tasks.add(task);
       System.out.println("task"+task.getTaskId()+"added to the list of tasks");
   }
  public void stopworking(){
  this.working=false;
  } 
   }    
}
