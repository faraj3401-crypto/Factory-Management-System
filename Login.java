
package item;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import java.awt.Image;
import javax.swing.Box;
public class Login extends JFrame{
 private JTextField userfield;
 private JPasswordField passField;
 private JComboBox<String>rolecombo;
 private JButton loginButton;
  private Sharedclass sharedData;
    public Login(Sharedclass sharedData){
        this.sharedData=sharedData;
    setTitle("Factory Managment System Login");
    setSize(400,300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new GridLayout(5,2,10,10));
    add(new JLabel("username"));
    userfield=new JTextField();
    add(userfield);
    add(new JLabel("password"));
    passField=new JPasswordField();
    add(passField);
    add(new JLabel("the role of user"));
    String[]roles={"Manager","supervisor"};
    rolecombo = new JComboBox<>(roles);
    add(rolecombo);
    loginButton = new JButton("login");
    add(new JLabel(""));
    add(loginButton);
    loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
         performLogin();
        }
    } );{
    }
    }
   
private void performLogin() {
    String username = userfield.getText();
    String password = new String(passField.getPassword());
    String selectedRole = (String) rolecombo.getSelectedItem();

    try {
        if (username.equals("Mariam") && password.equals("2025")) {
            
            if (selectedRole.equals("Manager")) {
                // تفعيل واجهة المدير وتمريرها للشاشة
                new ManagerFrame(sharedData).setVisible(true); 
            } else {
          // ابحثي عن هذا الجزء في كلاس Login

                 new SupervisorFrame(sharedData).setVisible(true);
               this.dispose();
            }
        }
            // إغلاق واجهة الدخول لتظهر الواجهة الجديدة مكانها
         else {
            throw new Exception("اسم المستخدم أو كلمة المرور غير صحيحة.");
        }   
            
        }

     catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "فشل تسجيل الدخول: " + ex.getMessage());

        // تسجيل الخطأ في ملف error.txt كما هو مطلوب في المشروع
        try (java.io.FileWriter fw = new java.io.FileWriter("error.txt", true);
             java.io.PrintWriter pw = new java.io.PrintWriter(fw)) {
            
            pw.println("--- سجل خطأ جديد ---");
            pw.println("التاريخ: " + new java.util.Date());
            pw.println("نوع المستخدم: " + selectedRole);
            pw.println("نص الخطأ: " + ex.getMessage());
            pw.println("-----------------------------------");
            
        } catch (java.io.IOException ioEx) {
            System.err.println("فشل الكتابة في ملف الأخطاء: " + ioEx.getMessage());
        }
    }
}}

