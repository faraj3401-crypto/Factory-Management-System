
package item;
import java.io.Serializable;
import java.util.Date;
public class Task implements Serializable{
 private int taskId;
 private Product requiredProduct;
 private String status;
 private float complationpercentage;
private Productline assignedline;
private Date startDate;
private Date endDate;
private String client;
private int requiredamount;
 public Task(int taskId, Product requiredProduct, Date endDate, String client, int requiredamount) {
        this.taskId = taskId;
        this.requiredProduct = requiredProduct;
        this.status = "pending";
        this.complationpercentage = 0.0f;
        this.assignedline = null;
        this.startDate = null;
        this.endDate = endDate;
        this.client = client;
        this.requiredamount = requiredamount;
    }
    public int getTaskId() {
        return taskId;
    }

    public Product getRequiredProduct() {
        return requiredProduct;
    }

    public String getStatus() {
        return status;
    }

    public float getComplationpercentage() {
        return complationpercentage;
    }

    public Productline getAssignedline() {
        return assignedline;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getClient() {
        return client;
    }
public void setStatus(String status){
this.status=status;
}

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getRequiredamount() {
        return requiredamount;
    }
public void setcomplationpercentage(float complationpercentage){/*دالة تحديث نسبة الانتاج*/
if(complationpercentage>=0&&complationpercentage<=100){
this.complationpercentage=complationpercentage;
}
}
public void assignedline(Productline line){/*دالة التعيين لخط الانتاج*/
this.assignedline=line;
this.status="In progress";
if(this.startDate==null){
this.startDate=new Date();
}
}
    @Override
    public String toString() {
        return "Task{" + "taskId=" + taskId + ", requiredProduct=" + requiredProduct + ", status=" + status + ", complationpercentage=" + complationpercentage + ", assignedline=" + assignedline + ", startDate=" + startDate + ", endDate=" + endDate + ", client=" + client + '}';
    }

}
