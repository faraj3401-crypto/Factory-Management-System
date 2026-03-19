
package item;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
public class Product implements Serializable {
    private int Idproduct;
    private String productname;
    HashMap<Integer,Integer> components;
    public Product(int Idproduct, String productname) {
        this.Idproduct = Idproduct;
        this.productname = productname;
        this.components = new HashMap<>();
    }
        public void addcomponent(int itemnum,int quantity){
        this.components.put(itemnum, quantity);
        }
    public int getIdproduct() {
        return Idproduct;
    }
    public String getproductname() {
        return productname;
    }
    public HashMap<Integer, Integer> getcomponents() {
        return components;
    }
@Override
public boolean equals (Object obj){
     if(this==obj){
        return true;}
    if(obj==null || getClass()!=obj.getClass() ){
       return false;}
       Product product2 = (Product)obj;
       return this.Idproduct==product2.Idproduct;
        } 
public int hashCode(){
return Objects.hash(Idproduct);
}    
    }
    
    

