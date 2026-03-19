
package item;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.Date;
public class Productline implements Runnable  {
    private int lineId;
    private String nameline;
    private String status="Idle";
    private List<Task>tasks;
    boolean working=true;
    Sharedclass share;
    Task currenttask;
    public Productline(int lineId, String nameline, Sharedclass share) {
        this.lineId = lineId;
        this.nameline = nameline;
        this.share = share;
      this.tasks=new ArrayList<>();
    }
    @Override
   public void run(){
   while(working){
   if(!tasks.isEmpty()){
         Task currenttask = tasks.get(0); 
       try{       
   Map<Integer,Integer>requirments=currenttask.getRequiredProduct().getcomponents();         
  for(Integer itemnum :requirments.keySet()){
      int quantityneeded=requirments.get(itemnum);
  int totalNeeded = quantityneeded * currenttask.getRequiredamount();
  share.withdrawItem(itemnum, totalNeeded); 
       }
   currenttask=tasks.remove(0);
      this.status="in progress";
      currenttask.setStartDate(new Date());
       for(int i=0;i<=100;i+=20){
       Thread.sleep(1000);
       currenttask.setcomplationpercentage(i); // تحديث النسب
       }
      
           this.status="Finished";
       share.addfinalproduct(currenttask.getRequiredProduct().getIdproduct(),currenttask.getRequiredamount());
       }
      catch(MinException e){
          System.out.println(e.getMessage());
      }
       catch(InterruptedException e){
           working=false;
           System.out.println("Thread interrupted");
       }
     this.status="Idle";
     currenttask=null;
   }
   else{
       
   try{
        if (!this.status.equals("IDLE")) {
            System.out.println(">>> Line " + lineId + " finished all tasks and is now IDLE.");
            this.status = "IDLE";
        }
        Thread.sleep(3000); // ينتظر 3 ثواني بهدوء قبل الفحص مرة أخرى
    } catch (InterruptedException e) {
        working = false;
    }
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
   

  
   
   
   
   

