
package item;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.util.Scanner;
import java.io.*;
public class Sharedclass implements Serializable{
    
    private Map<Integer,Item>rawmaterial;/*المخزون المركزي للمواد الخام */
    private Map<Integer,Integer>finalproduct;/*قائمة المنتجات النهائية الجاهزة يتم اضافتها بعد اكتمال  المهمة*/

    public Sharedclass() {
        this.rawmaterial = new HashMap<>();
        this.finalproduct = new HashMap<>();
    }
   public void addrawmaterial(Item item){
   this.rawmaterial.put(item.getItemnum(), item);
   } 
   public void addfinalproduct(int Idproduct,int quantity){/*الكمية اللي صنعناها  */
   synchronized (finalproduct){
    if(finalproduct.containsKey(Idproduct)){
    int currentquantity=finalproduct.get(Idproduct);
    int newquantity = currentquantity+quantity;
    finalproduct.put(Idproduct, newquantity);
    }
    else{
    finalproduct.put(Idproduct, quantity);
    }    
   }       
   }
   public  synchronized void withdrawItem(int itemnum, int amount) throws MinException {
    Item item = rawmaterial.get(itemnum);
    
    if (item == null) {
        throw new MinException(" the item which num is  " + itemnum + " is not found!");
    }

    if (item.getQuantity() < amount) {
        throw new MinException("the available quantity is not sufficient" + item.getQuantity() + " and we need " + amount);
    }
    item.setQuantity(item.getQuantity() - amount);
}
   public void readfromfile(String filename) throws FileNotFoundException{
   File file  =new File(filename);
   Scanner scanner  =new Scanner(file);
   int count =0;/*عداد الاسطر لمتابعة ارقام الاسطر التي نقرأها*/
   while(scanner.hasNextLine()){/*حلقة القراءة مادامت اداة الدخال تجد سطر اخر لتقرأه في الملف استمر في الدوران وتنفيذ الكود داخل الحلقة */
   String line = scanner.nextLine();/*تقرا السطر الحالي بالكامل من الملف وتضعه في متغير*/
   count++;
   String []data=line.split(",");
   if(data.length>=6){
   try{
   int id=Integer.parseInt(data[0].trim());
   String name=data[1].trim();
   int quantity=Integer.parseInt(data[2].trim());
   double price = Double.parseDouble(data[3].trim());      // قراءة السعر
        int minquantity = Integer.parseInt(data[4].trim());        // قراءة الحد الأدنى
        String category = data[5].trim();
  int reservedquantity = Integer.parseInt(data[6].trim()); 
   Item newItem = new Item(name, id, quantity, price, minquantity, category);
        this.addrawmaterial(newItem);/*ناخذ هذه الكائن الجاهز ونرسله للدالة لتضعه في ال Map  */
   }
   catch(NumberFormatException e){
       System.out.println(e.getMessage());
   }
   }
   else{
       System.out.println("error reading line"+count);
   }
   
   }
  scanner.close();
   }
  public void writetofile(String filename) throws IOException{
  FileWriter writer = new FileWriter(filename);
  for(Integer itemId : rawmaterial.keySet()){
  Item item=rawmaterial.get(itemId);
  writer.write(item.getItemnum()+","+item.getName()+","+item.getQuantity()+"n/");
  
  }
  for(Integer Idproduct : finalproduct.keySet()){
  Integer quantity = finalproduct.get(Idproduct);
  writer.write("product"+Idproduct+"quantity"+quantity);
  }
  writer.close();
  }        

    public Map<Integer, Item> getRawmaterial() {
        return rawmaterial;
    }

    public Map<Integer, Integer> getFinalproduct() {
        return finalproduct;
    }

 
}
