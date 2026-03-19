
package item;
import java.util.Date;
public class Main {
public static void main(String args[]){

    Sharedclass share = new Sharedclass();
    try {
        share.readfromfile("inventory.csv");
        
        // لا تحذفي هذا الجزء، هو الذي يفتح شاشة الدخول بأمان
        java.awt.EventQueue.invokeLater(() -> {
            new Login(share).setVisible(true);
        });
        
    } catch (Exception e) {
        e.printStackTrace();
    }
    
}}
      /*  try {
            // 1. إنشاء مواد خام (Item)
            // الاسم، الرقم، الكمية الكلية، السعر، الحد الأدنى، الفئة
            Item wood = new Item("Wood", 101, 100, 50.0, 10, "Raw Material");
            Item nail = new Item("Nail", 102, 500, 1.0, 50, "Hardware");

            // 2. إنشاء منتج (Product) وتحديد مكوناته
            Product chair = new Product(1, "Wooden Chair");
            chair.addcomponent(101, 4);  // الكرسي يحتاج 4 قطع خشب
            chair.addcomponent(102, 10); // الكرسي يحتاج 10 براغي

            System.out.println("--- بداية التجربة ---");
            System.out.println("المخزن قبل الحجز: Wood=" + wood.getQuantity() + ", Nails=" + nail.getQuantity());

            // 3. تجربة الحجز (Reserve)
            System.out.println("\nجاري حجز مواد لـ 5 كراسي...");
            int chairsToMake = 5;
            wood.reserve(4 * chairsToMake); // حجز 20 خشبة
            nail.reserve(10 * chairsToMake); // حجز 50 برغي

            System.out.println("المتاح للحجز الآن في الخشب: " + (wood.getQuantity() - wood.getReservedquantity()));

            // 4. تجربة السحب (Withdraw)
            System.out.println("\nتم الانتهاء من التصنيع، جاري سحب المواد...");
            wood.withdraw(20);
            nail.withdraw(50);

            System.out.println("المخزن النهائي: Wood=" + wood.getQuantity() + ", Nails=" + nail.getQuantity());
            System.out.println("الحالة الحالية للخشب: " + wood.getStatus());

            // 5. تجربة خطأ (حجز كمية أكبر من الموجود)
            System.out.println("\nتجربة حجز كمية خيالية (يجب أن تظهر رسالة خطأ):");
            wood.reserve(1000); 

        } catch (Exception e) {
            System.err.println("تم اكتشاف خطأ منطقي: " + e.getMessage());
        }

        // 1. إنشاء المخزن المركزي
       Sharedclass inventory = new Sharedclass();

        // 2. إضافة مواد خام للمخزن (مثلاً: خشب وبراغي)
        // Item(name, id, quantity, price, minquantity, category)
        Item wood = new Item("Wood", 101, 50, 10.0, 5, "Material");
        Item screws = new Item("Screws", 102, 100, 0.5, 10, "Material");
        
        inventory.addrawmaterial(wood);
        inventory.addrawmaterial(screws);

        // 3. إنشاء منتج (مثلاً: طاولة) وتحديد مكوناتها
        Product table = new Product(1, "Office Table");
        table.addcomponent(101, 2); // الطاولة الواحدة تحتاج قطعتين خشب
        table.addcomponent(102, 10); // الطاولة الواحدة تحتاج 10 براغي

        // 4. إنشاء خط إنتاج
        Productline line1 = new Productline(501, "Line A",  inventory);

        // 5. إنشاء مهمة (إنتاج 5 طاولات) وإضافتها للخط
        Task task1 = new Task(201, table, new Date(), "Client X", 5);
        line1.addTask(task1);

        // 6. تشغيل خط الإنتاج كخيط (Thread)
        Thread lineThread = new Thread(line1);
        lineThread.start();

        // 7. مراقبة التحديثات (اختياري فقط للعرض في الكونسول)
        try {
            while (lineThread.isAlive()) {
                System.out.println("حالة المهمة: " + task1.getComplationpercentage() + "%");
                Thread.sleep(2000); // تفقد الحالة كل ثانيتين
                if (task1.getComplationpercentage() == 100) break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 8. فحص المخزن بعد الانتهاء
        System.out.println("الكمية المتبقية من الخشب: " + wood.getQuantity());
        System.out.println("كمية الطاولات في مخزن المنتجات النهائية: " + inventory.getFinalproduct().get(1));
    }
}*/
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
    










