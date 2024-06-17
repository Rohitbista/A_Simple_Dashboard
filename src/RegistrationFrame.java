import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegistrationFrame extends JFrame{
    final private Font mainFont = new Font("Segoe print", Font.BOLD, 14);
    JTextField tfName, tfPhone, tfEmail, tfAddress;
    JPasswordField pfPassword, pfRewrite_password;

    public void initialize(){

        //Form Panel
        //JLabel lbRegistrationForm = new JLabel("Registration Form", SwingConstants.CENTER);
        //lbRegistrationForm.setFont(mainFont);

        JLabel lbName = new JLabel("Name");
        lbName.setFont(mainFont);

        tfName = new JTextField();
        tfName.setFont(mainFont);

        JLabel lbPhone = new JLabel("Phone number");
        lbPhone.setFont(mainFont);

        tfPhone = new JTextField();
        tfPhone.setFont(mainFont);

        JLabel lbEmail = new JLabel("Email");
        lbEmail.setFont(mainFont);

        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel lbAddress = new JLabel("Address");
        lbAddress.setFont(mainFont);

        tfAddress = new JTextField();
        tfAddress.setFont(mainFont);

        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(mainFont);

        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);

        JLabel lbRewrite_password = new JLabel("Rewrite Password");
        lbRewrite_password.setFont(mainFont);

        pfRewrite_password = new JPasswordField();
        pfRewrite_password.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(12, 1, 2, 2));
        //formPanel.add(lbRegistrationForm);
        formPanel.add(lbName);
        formPanel.add(tfName);
        formPanel.add(lbPhone);
        formPanel.add(tfPhone);
        formPanel.add(lbEmail);
        formPanel.add(tfEmail);
        formPanel.add(lbAddress);
        formPanel.add(tfAddress);
        formPanel.add(lbPassword);
        formPanel.add(pfPassword);
        formPanel.add(lbRewrite_password);
        formPanel.add(pfRewrite_password);

        //Button Panel
        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(mainFont);
        btnRegister.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String pp = String.valueOf(pfPassword.getPassword());
                String Rp = String.valueOf(pfRewrite_password.getPassword());
                if(pp.equals(Rp)){
                    //dispose();
                    String name = tfName.getText();
                    String phone = tfPhone.getText();
                    String address = tfAddress.getText();
                    String email = tfEmail.getText();
                    try{
                        final String db_url = "jdbc:mysql://localhost:3306/world";
                        final String userkey = "root";
                        final String passkey = "Password576#";
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection(db_url, userkey, passkey);
                        Statement stmt = conn.createStatement();
                        String s1 = "insert into logininfo values(\""+email+"\",\""+pp+"\",\""+phone+"\",\""+name+"\",\""+address+"\")";
                        System.out.println(s1);
                        int b = stmt.executeUpdate(s1);
                        dispose();
                        if(b==0){
                            System.out.println("Insertion not successful");
                        }else{
                            System.out.println("Values Added");
                        }
                        conn.close();
                    }catch(Exception f){
                        System.out.println(f);
                    }
                }else{
                    pfPassword.setText("");
                    pfRewrite_password.setText("");
                }
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 1, 2, 2));
        buttonsPanel.add(btnRegister);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(128, 128, 255));
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        
        add(mainPanel);


        setTitle("Registration Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1100, 650);
        setMinimumSize(new Dimension(300, 400));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
