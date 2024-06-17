import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class LoginForm extends JFrame {
    final private Font mainFont = new Font("Segoe print", Font.BOLD, 18);
    JTextField tfEmail;
    JPasswordField pfPassword;
    public void initialize(){
        //Form Panel
        JLabel lbLoginForm = new JLabel("Login Form", SwingConstants.CENTER);
        lbLoginForm.setFont(mainFont);


        JLabel lbEmail = new JLabel("Email");
        lbEmail.setFont(mainFont);

        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(mainFont);

        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.add(lbLoginForm);
        formPanel.add(lbEmail);
        formPanel.add(tfEmail);
        formPanel.add(lbPassword);
        formPanel.add(pfPassword);


        //Buttons Panel
        JButton btnSignin = new JButton("Sign in");
        btnSignin.setFont(mainFont);
        btnSignin.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());
                
                User user = getAuthenticatedUser(email, password);

                if(user!=null){
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.initialize(user);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(LoginForm.this, 
                        "Email or Password is Invalid",
                        "Try Again",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        JButton btnCancel = new JButton("New Registration");
        btnCancel.setFont(mainFont);
        btnCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                RegistrationFrame rf = new RegistrationFrame();
                rf.initialize();
                dispose();
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonsPanel.add(btnSignin);
        buttonsPanel.add(btnCancel);



        //Initialize the frame
        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);




        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setMinimumSize(new Dimension(350, 450));
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private User getAuthenticatedUser(String email, String password){
        User user = null;
        final String db_url = "jdbc:mysql://localhost:3306/world";
        final String userkey = "root";
        final String passkey = "Password576#";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(db_url, userkey, passkey);
            String sql = "Select * from logininfo where Email=? and Password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                user = new User();
                user.name = rs.getString("Name");
                user.email = rs.getString("Email");
                user.phone = rs.getString("Phone");
                user.address = rs.getString("Address");
                user.password = rs.getString("Password");
            }

            ps.close();
            conn.close();

        }catch(Exception e){
            System.out.println(e);
        }

        return user;
    }
    public static void main(String[] args){
        LoginForm lf = new LoginForm();
        lf.initialize();
    }
}