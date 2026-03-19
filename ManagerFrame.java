
package item;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;

public class ManagerFrame extends JFrame {
    private JTable linesTable;
    private DefaultTableModel model;
    private JTextField linenamefield, capicityfield, performancefield, notefield;
    private final String FILE_NAME = "production_lines.csv";
      private Sharedclass sharedData;

    public ManagerFrame(Sharedclass sharedData) {
        this.sharedData=sharedData;
        setTitle("Manager Dashboard - لوحة المدير");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
this.sharedData=sharedData;
        // العنوان العلوي
        JLabel header = new JLabel("Production Lines Management", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // الجدول (إضافة الأعمدة المطلوبة في المشروع)
        String[] columns = {"ID", "Line Name", "Capacity", "Status", "Performance (%)", "Notes"};
        model = new DefaultTableModel(columns, 0);
        linesTable = new JTable(model);
        add(new JScrollPane(linesTable), BorderLayout.CENTER);

        // لوحة الإدخال (ترتيب منظم)
        JPanel inputPanel = new JPanel(new GridLayout(3, 4, 10, 10)); // 3 صفوف و 4 أعمدة
        inputPanel.setBorder(BorderFactory.createTitledBorder("Control & Management"));

        linenamefield = new JTextField();
        capicityfield = new JTextField();
        performancefield = new JTextField();
        notefield = new JTextField();
        
        JButton btnAdd = new JButton("Add New Line");
        JButton btnUpdate = new JButton("Update Selected");

        // الصف الأول: إضافة خط جديد
        inputPanel.add(new JLabel("Line Name:"));
        inputPanel.add(linenamefield);
        inputPanel.add(new JLabel("Capacity:"));
        inputPanel.add(capicityfield);

        // الصف الثاني: التقييم والملاحظات
        inputPanel.add(new JLabel("Performance (%):"));
        inputPanel.add(performancefield);
        inputPanel.add(new JLabel("Notes:"));
        inputPanel.add(notefield);

        // الصف الثالث: الأزرار
        inputPanel.add(new JLabel("")); // خلية فارغة للتنسيق
        inputPanel.add(btnAdd);
        inputPanel.add(btnUpdate);
        inputPanel.add(new JLabel("")); 

        add(inputPanel, BorderLayout.SOUTH);

        // تحميل البيانات عند الفتح
        loadLinesFromFile();

        // حدث إضافة خط جديد
        btnAdd.addActionListener(e -> addProductionLine());

        // حدث تحديث الحالة والتقييم
        btnUpdate.addActionListener(e -> updateLineStatus());
    }

    private void addProductionLine() {
        try {
            String name = linenamefield.getText();
            int capacity = Integer.parseInt(capicityfield.getText());
            if (capacity <= 0) throw new Exception("Capacity must be positive!"); 

            Object[] row = {model.getRowCount() + 1, name, capacity, "Active", "0%", ""};
            model.addRow(row);
            saveAllToFile(); // حفظ الكل لضمان التزامن 

            linenamefield.setText("");
            capicityfield.setText("");
            JOptionPane.showMessageDialog(this, "Line Added Successfully!");
        } catch (Exception ex) {
            logError("Manager Input Error: " + ex.getMessage()); 
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateLineStatus() {
        int row = linesTable.getSelectedRow();
        if (row != -1) {
            String currentStatus = model.getValueAt(row, 3).toString();
            String newStatus = currentStatus.equals("Active") ? "Maintenance" : "Active";
            
            model.setValueAt(newStatus, row, 3);
            model.setValueAt(performancefield.getText() + "%", row, 4); 
            model.setValueAt(notefield.getText(), row, 5); 
            
            saveAllToFile();
            JOptionPane.showMessageDialog(this, "Line Updated Successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a line first!");
        }
    }

    private void saveAllToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < model.getColumnCount(); j++) {
                    sb.append(model.getValueAt(i, j)).append(j == model.getColumnCount() - 1 ? "" : ",");
                }
                pw.println(sb.toString());
            }
        } catch (IOException e) {
            logError("File Write Error: " + e.getMessage()); 
        }
    }


    private void loadLinesFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                model.addRow(line.split(","));
            }
        } catch (IOException e) {
            logError("File Read Error: " + e.getMessage()); 
        }
    }

    private void logError(String message) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("error.txt", true))) { // تغيير الاسم ليتطابق مع طلب المشروع 
            pw.println(new java.util.Date() + " : " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}