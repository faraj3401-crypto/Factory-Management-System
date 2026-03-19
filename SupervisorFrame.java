

/* package item;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.*;
import java.util.*;

public class SupervisorFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private JTable inventoryTable, tasksTable;
    private DefaultTableModel inventoryModel, tasksModel;
    private JTextField searchField, taskSearchField;
    private JComboBox<String> categoryFilter, statusFilter;
    private Sharedclass sharedData;

    public SupervisorFrame(Sharedclass sharedData) {
        this.sharedData=sharedData;
        setTitle("نظام مشرف الإنتاج - إدارة شاملة");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // 1. تبويب إدارة المخزون
        setupInventoryTab();

        // 2. تبويب إدارة المهام والإنتاج
        setupTasksTab();

        add(tabbedPane);
        loadData();
    }

    private void setupInventoryTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // لوحة البحث والتصفية (المطلب: بحث حسب الاسم، الفئة، الحالة)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(10);
        categoryFilter = new JComboBox<>(new String[]{"الكل", "مواد خام", "قطع غيار"}); // فئات تجريبية
        statusFilter = new JComboBox<>(new String[]{"الكل", "متاح", "نفذ", "أقل من الحد الأدنى"});
        
        filterPanel.add(new JLabel("بحث بالاسم:")); filterPanel.add(searchField);
        filterPanel.add(new JLabel("الفئة:")); filterPanel.add(categoryFilter);
        filterPanel.add(new JLabel("الحالة:")); filterPanel.add(statusFilter);
        
        JButton btnFilter = new JButton("تطبيق التصفية");
        filterPanel.add(btnFilter);

        // الجدول (المطلب: رقم، اسم، فئة، سعر، كمية، حد أدنى)
        String[] columns = {"ID", "Name", "Category", "Price", "Qty", "Min Limit"};
        inventoryModel = new DefaultTableModel(columns, 0);
        inventoryTable = new JTable(inventoryModel);
        
        // الأزرار (إضافة، تعديل، حذف، حفظ)
        JPanel actionPanel = new JPanel();
        JButton btnAdd = new JButton("إضافة مادة");
        JButton btnDelete = new JButton("حذف");
        JButton btnSave = new JButton("حفظ حالة المخزن (File)");
        actionPanel.add(btnAdd); actionPanel.add(btnDelete); 
        actionPanel.add(btnSave);
 // برمجة زر الإضافة (المطلب: إضافة عناصر للمخزون) 
btnAdd.addActionListener(e -> {
    try {
        // إضافة سطر جديد ببيانات افتراضية للجدول
        inventoryModel.addRow(new Object[]{
            inventoryModel.getRowCount() + 1, // معرف تلقائي [cite: 11]
            "مادة جديدة", 
            "مواد خام", 
            "0", // السعر الابتدائي
            "10", // الكمية الافتراضية
            "5"  // الحد الأدنى الافتراضي
        });
        
        // التمرير التلقائي لآخر سطر تمت إضافته
        inventoryTable.setRowSelectionInterval(inventoryModel.getRowCount() - 1, inventoryModel.getRowCount() - 1);
        
        JOptionPane.showMessageDialog(this, "تم إضافة سطر جديد بنجاح.\nيمكنك الآن الضغط مرتين على أي خلية لتعديل بياناتها.");
    } catch (Exception ex) {
        logError("خطأ أثناء إضافة مادة جديدة: " + ex.getMessage()); // تسجيل الخطأ في ملف error.txt 
    }
});

// برمجة زر الحذف (المطلب: حذف عناصر من المخزون) 
btnDelete.addActionListener(e -> {
    int selectedRow = inventoryTable.getSelectedRow();
    if (selectedRow != -1) {
        inventoryModel.removeRow(selectedRow);
    } else {
        JOptionPane.showMessageDialog(this, "يرجى اختيار مادة من الجدول لحذفها.");
    }
});

        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);
        // 1. تفعيل خاصية البحث والترتيب للجدول (ضروري جداً ليشتغل البحث)
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(inventoryModel);
        inventoryTable.setRowSorter(sorter);
btnFilter.addActionListener(e -> {
    String name = searchField.getText().trim(); // الاسم اللي بدك تبحثي عنه
    
    // إذا المربع فارغ، ارجعي حملي البيانات الأصلية
    if (name.isEmpty()) {
        loadData(); 
        return;
    }

    // ابحثي في الجدول واحذفي أي سطر ما بيشبه الاسم المطلوبة
    for (int i = inventoryModel.getRowCount() - 1; i >= 0; i--) {
        String cellValue = inventoryModel.getValueAt(i, 1).toString(); // عمود الاسم
        
        // إذا الاسم اللي بالجدول ما بيحتوي على النص اللي كتبتيه، احذفي السطر من الشاشة
        if (!cellValue.contains(name)) {
            inventoryModel.removeRow(i);
        }
    }
});
       
        tabbedPane.addTab("إدارة المخزون", panel);

        // برمجة زر الحفظ للملف (المطلب: حفظ حالة المخزن)
        btnSave.addActionListener(e -> saveToFile("inventory.csv", inventoryModel));
        
        // برمجة التصفية (المطلب: أقل من الحد الأدنى)
      
    }

    private void setupTasksTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // لوحة المهام (المطلب: عرض المهام حسب المنتج أو الحالة)
        JPanel taskControl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnMostRequested = new JButton("المنتج الأكثر طلباً");
        JButton btnCompleted = new JButton("عرض المهام المكتملة");
        taskControl.add(btnMostRequested);
        taskControl.add(btnCompleted);
   btnCompleted.addActionListener(e -> {
    boolean found = false;
    int statusColumn = tasksModel.getColumnCount() - 2; // غالباً الحالة هي العمود قبل الأخير

    for (int i = tasksModel.getRowCount() - 1; i >= 0; i--) {
        // نأخذ القيمة ونحولها لنص صغير (lowercase) عشان ما نغلط بالحروف الكبيرة
        String status = tasksModel.getValueAt(i, statusColumn).toString().toLowerCase();
        
        // نبحث عن كلمة Completed أو مكتملة (للاحتياط)
        if (status.contains("completed") || status.contains("done") || status.contains("مكتملة")) {
            found = true;
        } else {
            tasksModel.removeRow(i);
        }
    }

    if (!found) {
        JOptionPane.showMessageDialog(this, "No completed tasks found! / لم يتم العثور على مهام مكتملة");
        loadData(); 
    }
});

            String[] taskCols = {"Line ID", "Product", "Task ID", "Status", "Qty", "Notes", "customer"};
        tasksModel = new DefaultTableModel(taskCols, 0);
        tasksTable = new JTable(tasksModel);

        panel.add(taskControl, BorderLayout.NORTH);
        panel.add(new JScrollPane(tasksTable), BorderLayout.CENTER);
        
      // لوحة الأزرار
        JPanel taskActions = new JPanel();
        JButton btnAddTask = new JButton("إضافة مهمة جديدة");
        JButton btnCancelTask = new JButton("إلغاء مهمة");
        
        taskActions.add(btnAddTask); 
        taskActions.add(btnCancelTask);

        // برمجة زر الإضافة
        btnAddTask.addActionListener(e -> {
            String product = JOptionPane.showInputDialog(this, "أدخل اسم المنتج المطلوب:");
            if (product != null && !product.isEmpty()) {
                // تأكدي أن tasksModel هو نفسه المعرف في أعلى الكلاس
                tasksModel.addRow(new Object[]{
                    "T" + (tasksModel.getRowCount() + 1), 
                    product, 
                    "processing", 
                    "10", 
                    "none", 
                    "customer A"
                });
                // هذا السطر للتأكد من تحديث الواجهة فوراً
                tasksTable.revalidate();
            }
        });

        // برمجة زر الإلغاء
        btnCancelTask.addActionListener(e -> {
            int selectedRow = tasksTable.getSelectedRow();
            if (selectedRow != -1) {
                tasksModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "يرجى اختيار مهمة لحذفها.");
            }
        });

        panel.add(taskActions, BorderLayout.SOUTH);
        tabbedPane.addTab("إدارة المهام والإنتاج", panel);
    }
    

    private void calculateMostRequested() {
        // المطلب: عرض المنتج الأكثر طلباً
        if (tasksModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "لا توجد بيانات مهام حالياً.");
            return;
        }
        // منطق برمجي بسيط للعثور على المنتج الأكثر تكراراً في جدول المهام
        JOptionPane.showMessageDialog(this, "المنتج الأكثر طلباً بناءً على المهام الحالية: (خشب زان)");
    }

  
    private void saveToFile(String fileName, DefaultTableModel model) {
    try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
        for (int i = 0; i < model.getRowCount(); i++) {
            // نجمع بيانات السطر حبة حبة وبينهم فاصلة
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < model.getColumnCount(); j++) {
                line.append(model.getValueAt(i, j));
                if (j < model.getColumnCount() - 1) line.append(","); // نضع فاصلة إلا في آخر عمود
            }
            pw.println(line.toString()); // نحفظ السطر نظيف بدون أقواس
        }
        JOptionPane.showMessageDialog(this, "تم الحفظ بنجاح في " + fileName);
    } catch (IOException e) {
        logError("فشل الحفظ: " + e.getMessage());
    }
}
   

    private void logError(String msg) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("error.txt", true))) {
            pw.println(new Date() + " : " + msg);
        } catch (Exception ignored) {}
    }
    // دالة لتحميل البيانات من الملفين عند فتح البرنامج
private void loadData() {
   /* loadFromFile("inventory.csv", inventoryModel);
    loadFromFile("Production_lines.csv", tasksModel);*/
  /* inventoryModel.setRowCount(0);
   for(Item item:sharedData.getRawmaterial().values()){
   inventoryModel.addRow(new Object[]{
   item.getItemnum(),item.getName(),"Raw Material",item.getPrice(),item.getQuantity(),20
   }
           );
   }
}

// دالة القراءة العامة (تنسق البيانات من CSV إلى الجدول)
private void loadFromFile(String fileName, DefaultTableModel model) {
    File file = new File(fileName);
    if (!file.exists()) return;
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        model.setRowCount(0); 
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                model.addRow(line.split(","));
            }
        }
    } catch (IOException e) {
        logError("خطأ في قراءة " + fileName);
    }
}

}*/
  package item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;

public class SupervisorFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private JTable inventoryTable, tasksTable;
    private DefaultTableModel inventoryModel, tasksModel;
    private JTextField searchField;
    private JComboBox<String> statusFilter;
    private Sharedclass sharedData;

    public SupervisorFrame(Sharedclass sharedData) {
        this.sharedData = sharedData;
        setTitle("نظام مشرف الإنتاج - إدارة شاملة");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // إعداد التبويبات
        setupInventoryTab();
        setupTasksTab();

        add(tabbedPane);
        
        // استدعاء تحميل البيانات فور فتح الواجهة
        loadData(); 
    }

    private void setupInventoryTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        // لوحة البحث والتصفية (حسب متطلبات المشروع)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(10);
        statusFilter = new JComboBox<>(new String[]{"الكل", "متاح", "نفذ", "أقل من الحد الأدنى"});
        JButton btnFilter = new JButton("تطبيق التصفية");
        
        filterPanel.add(new JLabel("بحث بالاسم:")); 
        filterPanel.add(searchField);
        filterPanel.add(new JLabel("الحالة:")); 
        filterPanel.add(statusFilter);
        filterPanel.add(btnFilter);

        // الجدول
        String[] columns = {"ID", "Name", "Category", "Price", "Qty", "Min Limit"};
        inventoryModel = new DefaultTableModel(columns, 0);
        inventoryTable = new JTable(inventoryModel);
        
        // الأزرار
        JPanel actionPanel = new JPanel();
        JButton btnAdd = new JButton("إضافة مادة");
        JButton btnDelete = new JButton("حذف");
        JButton btnSave = new JButton("حفظ حالة المخزن");
        actionPanel.add(btnAdd); actionPanel.add(btnDelete); actionPanel.add(btnSave);

        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);

        // حدث التصفية (المطلب: متاح/نفذ/أقل من الحد)
        btnFilter.addActionListener(e -> applyInventoryFilter());

        // حدث الحفظ
        btnSave.addActionListener(e -> saveToFile("inventory.csv", inventoryModel));

        tabbedPane.addTab("إدارة المخزون", panel);
    }

    private void setupTasksTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // لوحة التحكم بالمهام (المطلب: الأكثر طلباً وتصفية المكتملة)
        JPanel taskControl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnMostRequested = new JButton("المنتج الأكثر طلباً");
        JButton btnCompleted = new JButton("عرض المهام المكتملة فقط");
        taskControl.add(btnMostRequested);
        taskControl.add(btnCompleted);

        String[] taskCols = {"Line ID", "Product", "Task ID", "Status", "Qty", "Notes", "Customer"};
        tasksModel = new DefaultTableModel(taskCols, 0);
        tasksTable = new JTable(tasksModel);

        panel.add(taskControl, BorderLayout.NORTH);
        panel.add(new JScrollPane(tasksTable), BorderLayout.CENTER);
        
        // أزرار المهام
        JPanel taskActions = new JPanel();
        JButton btnAddTask = new JButton("إضافة مهمة جديدة");
        JButton btnCancelTask = new JButton("إلغاء مهمة");
        taskActions.add(btnAddTask); taskActions.add(btnCancelTask);

        panel.add(taskActions, BorderLayout.SOUTH);

        // برمجة زر الأكثر طلباً
        btnMostRequested.addActionListener(e -> calculateMostRequested());

        tabbedPane.addTab("إدارة المهام والإنتاج", panel);
    }
    // دالة تحميل البيانات الشاملة (المخزون + المهام)
    private void loadData() {
        // 1. تحميل المخزون من الـ SharedData
        inventoryModel.setRowCount(0);
        for (Item item : sharedData.getRawmaterial().values()) {
            inventoryModel.addRow(new Object[]{
                item.getItemnum(), item.getName(), "مواد خام", 
                item.getPrice(), item.getQuantity(), item.getMinquantity()
            });
        }

        // 2. تحميل المهام من ملف (لكي لا تظهر فارغة)
        loadFromFile("productlines.csv", tasksModel);
    }

    private void applyInventoryFilter() {
        String selectedStatus = (String) statusFilter.getSelectedItem();
        String searchText = searchField.getText().toLowerCase();
        inventoryModel.setRowCount(0);

        for (Item item : sharedData.getRawmaterial().values()) {
            boolean matchesName = item.getName().toLowerCase().contains(searchText);
            boolean matchesStatus = false;

            if (selectedStatus.equals("الكل")) matchesStatus = true;
            else if (selectedStatus.equals("نفذ") && item.getQuantity() == 0) matchesStatus = true;
            else if (selectedStatus.equals("أقل من الحد الأدنى") && item.getQuantity() < item.getMinquantity()) matchesStatus = true;
            else if (selectedStatus.equals("متاح") && item.getQuantity() > 0) matchesStatus = true;

            if (matchesName && matchesStatus) {
                inventoryModel.addRow(new Object[]{
                    item.getItemnum(), item.getName(), "مواد خام", 
                    item.getPrice(), item.getQuantity(), item.getMinquantity()
                });
            }
        }
    }

    private void calculateMostRequested() {
        if (tasksModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "لا توجد مهام حالياً.");
            return;
        }
        Map<String, Integer> counts = new HashMap<>();
        for (int i = 0; i < tasksModel.getRowCount(); i++) {
            String prod = tasksModel.getValueAt(i, 1).toString();
            counts.put(prod, counts.getOrDefault(prod, 0) + 1);
        }
        String top = Collections.max(counts.entrySet(), Map.Entry.comparingByValue()).getKey();
        JOptionPane.showMessageDialog(this, "المنتج الأكثر طلباً هو: " + top);
    }

    private void loadFromFile(String fileName, DefaultTableModel model) {
        File file = new File(fileName);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            model.setRowCount(0);
            while ((line = br.readLine()) != null) {
                model.addRow(line.split(","));
            }
        } catch (IOException e) {
            System.err.println("خطأ في قراءة " + fileName);}}
  private void saveToFile(String fileName, DefaultTableModel model) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < model.getColumnCount(); j++) {
                    sb.append(model.getValueAt(i, j)).append(j == model.getColumnCount() - 1 ? "" : ",");
                }
                pw.println(sb.toString());
            }
            JOptionPane.showMessageDialog(this, "تم حفظ البيانات بنجاح في " + fileName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "خطأ في الحفظ!");
        }
    }
}
        
    
  

