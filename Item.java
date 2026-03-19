
package item;
import java.io.Serializable;
import java.util.Objects;
public class Item implements Serializable {
private String name;
private int itemnum;
private int quantity;
private double price;
private int minquantity;
private String category;
private int reservedquantity;
    public Item(String name, int itemnum, int quantity, double price, int minquantity, String category) {
        this.name = name;
        this.itemnum = itemnum;
        this.quantity = quantity;
        this.price = price;
        this.minquantity = minquantity;
        this.category = category;
        this.reservedquantity=0;
    }
    public String getName() {
        return name;
    }
    public int getItemnum() {
        return itemnum;
    }
    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getMinquantity() {
        return minquantity;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public void setMinquantity(int minquantity) {
        this.minquantity = minquantity;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public int getReservedquantity() {
        return reservedquantity;
    }
   
    public  synchronized void reserve(int amount)throws MinException{
    if(amount<=0){
    throw new MinException("The quantity you need to reserve cannot be negative ");
    }
   if(amount>(this.quantity-this.reservedquantity)){
   throw new MinException("The Inventory doesnot have the quantity you require the available quanity"+(this.quantity-this.reservedquantity));
   } 
    this.reservedquantity+=amount;
    }
    
    public  synchronized void withdraw(int amount)throws MinException{
    if(amount <=0){
    throw new MinException("the quantity cant not be negative");
    }
    if(this.reservedquantity<amount){
  throw new MinException("you cant withdraw amount bigger than you have reserved");
 }
int remainingamount=this.quantity-amount;
if(remainingamount<this.minquantity){
    System.out.println("Warning: Item" + name + " has reached the minimum level!");

}
 this.quantity-=amount; 
 this.reservedquantity-=amount;
    } 
    public synchronized void cancelreservation(int amount) throws MinException{
    if(amount>this.reservedquantity){
    throw new MinException("the quantity you want to cancel bigger than the quantity you have reserved");
    }
    this.reservedquantity-=amount;
        System.out.println("you have canceled"+amount+"successfully");
    }  
// --- دالة الإيداع (مهمة عند إضافة مواد جديدة أو إلغاء مهمة) ---
    public synchronized void deposit(int amount) {
        if (amount > 0) {
            this.quantity += amount;
        }
    }
    public String getStatus() {
        int available=this.quantity-this.reservedquantity;
        if (quantity == 0) 
            return "outof stack";
        if (available < minquantity) 
            return "less than minimum";
        return "available";
    }
@Override
public boolean equals (Object obj){
     if(this==obj){
        return true;}
    if(obj==null || getClass()!=obj.getClass() ){
       return false;}
       Item item = (Item)obj;
       return this.itemnum==item.itemnum;
        } 
public int hashCode(){
return Objects.hash(itemnum);
}
    @Override
    public String toString() {
        return "Item{" + "name=" + name + ", itemnum=" + itemnum + ", quantity=" + quantity + ", price=" + price + ", minquantity=" + minquantity + ", category=" + category + '}';
    }  
}
