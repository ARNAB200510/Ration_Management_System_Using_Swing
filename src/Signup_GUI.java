import java.util.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Signup_GUI extends JFrame {
    JLabel label1, label2, label3, label4, label5, label6,label7,label8;

    JTextField textMail, textName, textPhone;
    JPasswordField passwordField, confirmPasswordField;
    JButton signupButton;
    JComboBox<String> roleDropdown;


    HashMap<JLabel, Pair<Boolean, JLabel>> messageMap;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    Signup_GUI() {
        setTitle("Sign Up");
        setSize(540, 650);
        setLocation(400, 30);
        setBackground(new Color(245, 245, 220));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        messageMap = new HashMap<>();
        
        
        ImageIcon mIcon = new ImageIcon(ClassLoader.getSystemResource("Icon/homepage.png"));
        Image scaled_mIcon = mIcon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        JLabel tImage = new JLabel(new ImageIcon(scaled_mIcon));
        tImage.setBounds(230, 5, 70, 50);
        add(tImage);

        label1 = new JLabel("Name:");
        label1.setFont(new Font("Raleway", Font.PLAIN, 18));
        label1.setForeground(Color.BLACK);
        label1.setBounds(40, 90, 200, 30);
        add(label1);
       

        textName = new JTextField(20);
        textName.setBounds(40, 120, 440, 30);
        textName.setFont(new Font("Arial", Font.PLAIN, 14));
        Pattern nameVerifier = Pattern.compile("^[a-zA-Z ]+$");
        add(textName);
        label2 = new JLabel("Email:");
        label2.setFont(new Font("Raleway", Font.PLAIN, 18));
        label2.setForeground(Color.BLACK);
        label2.setBounds(40, 170, 200, 30);
        add(label2);
        label8 = new JLabel("Username already exists!");
        label8.setForeground(Color.red);
        label8.setFont(new Font("Raleway", Font.PLAIN, 14));
        label8.setBounds(label2.getX() + label2.getWidth() + 5, label2.getY(), 280, 20);
        label8.setVisible(false);
        add(label8);
        textMail = new JTextField(30);
        textMail.setBounds(40, 200, 440, 30);
        textMail.setFont(new Font("Arial", Font.PLAIN, 14));
        Pattern mailVerifier = Pattern.compile("^[A-Z0-9a-z*#+-_/{|}!%&()=]+@[A-Z0-9a-z-]+(\\.[A-Z0-9a-z-]+)+$");
        add(textMail);
        label3 = new JLabel("Phone:");
        label3.setFont(new Font("Raleway", Font.PLAIN, 18));
        label3.setForeground(Color.BLACK);
        label3.setBounds(40, 250, 200, 30);
        add(label3);
        textPhone = new JTextField(20);
        textPhone.setBounds(40, 280, 440, 30);
        textPhone.setFont(new Font("Arial", Font.PLAIN, 14));
        Pattern phoneVerifier = Pattern.compile("^[0-9]{10}$");
        add(textPhone);

        String[] roles = {"Admin","Dealer","Benificiary"};
        roleDropdown = new JComboBox<>(roles);
        roleDropdown.setBounds(40,330,440,30);
        add(roleDropdown);

        label5 = new JLabel("Password: ");
        label5.setFont(new Font("Raleway", Font.PLAIN, 18));
        label5.setForeground(Color.BLACK);
        label5.setBounds(40, 380, 200, 30);
        add(label5);
        passwordField = new JPasswordField();
        passwordField.setBounds(40, 410, 440, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        Pattern passwordVerifier = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\W).{10,}$");
        add(passwordField);

        label6 = new JLabel("Confirm Password: ");
        label6.setFont(new Font("Raleway", Font.PLAIN, 18));
        label6.setForeground(Color.BLACK);
        label6.setBounds(40, 460, 200, 30);
        add(label6);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(40, 490, 440, 30);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(confirmPasswordField);

       

        signupButton = new JButton("Sign up");
        Color signupButtonColor = Color.decode("#f2570f");
        signupButton.setBackground(signupButtonColor);
        signupButton.setForeground(Color.BLACK);
        signupButton.setFont(new Font("Raleway", Font.BOLD, 20));
        signupButton.setBounds(40, 550, 440, 40);
        signupButton.addActionListener(evt -> signupButtonActionPerformed(evt));
        add(signupButton);

        messageMap.put(label6, new Pair(false, new JLabel("Passwords don't match")));
        messageMap.put(label5, new Pair(false, new JLabel("Valid:10 characters,uppercase + lowercase + digit + special ")));
        messageMap.put(label3, new Pair(false, new JLabel("Enter a valid phone number")));
        messageMap.put(label2, new Pair(false, new JLabel("Enter a valid email")));
        messageMap.put(label1, new Pair(false, new JLabel("Invalid name")));
        
        for (JLabel label : messageMap.keySet()) {

            JLabel errorText = messageMap.get(label).getY();
            errorText.setForeground(Color.red);
            errorText.setFont(new Font("Raleway", Font.PLAIN, 14));
            errorText.setBounds(label.getX() + label.getWidth() + 5, label.getY(), 280, 20);
            errorText.setVisible(false);
            add(errorText);
        }

        ValidationSystemListener.labelFieldMap = messageMap;
        textName.getDocument().addDocumentListener(new ValidationSystemListener(label1, textName, nameVerifier));
        textMail.getDocument().addDocumentListener(new ValidationSystemListener(label2, textMail, mailVerifier));
        textPhone.getDocument().addDocumentListener(new ValidationSystemListener(label3, textPhone, phoneVerifier));
        passwordField.getDocument().addDocumentListener(new ValidationSystemListener(label5, passwordField, passwordVerifier));
        setVisible(true);
       
    }

 

    public void signupButtonActionPerformed(ActionEvent evt) {
        
        for (JLabel label : messageMap.keySet()) {
            if(label ==  label6){
                continue;
            }
            
            Pair<Boolean, JLabel> pair = messageMap.get(label);
       
            if (!pair.getX()) {
               
                pair.getY().setVisible(true);
                return;
            } else {
                pair.getY().setVisible(false);
                
            }
        }
        if (new String(passwordField.getPassword()).equals(new String(confirmPasswordField.getPassword()))
                && new String(passwordField.getPassword()).length() > 0) {
            messageMap.get(label6).setOne(true);
            messageMap.get(label6).getY().setVisible(false);
            
        }
        else {
            messageMap.get(label6).setOne(false);
            messageMap.get(label6).getY().setVisible(true);
            return;
            
        }
        
        try {
        
            String name = textName.getText();
            String email = textMail.getText();
            String phone = textPhone.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleDropdown.getSelectedItem(); 
            label8.setVisible(false);
           
            Connector conn = new Connector();
            String insertUserDet = "Insert into users(name,phone,password,user_type) values(?,?,?,?)";
            PreparedStatement stmtUserDet = conn.connection.prepareStatement(insertUserDet,Statement.RETURN_GENERATED_KEYS);
            stmtUserDet.setString(1,name);
            stmtUserDet.setString(2,phone);
            stmtUserDet.setString(3,password);
            stmtUserDet.setString(4,role);
            int affectedRows = stmtUserDet.executeUpdate();
            int id;
            if (affectedRows > 0){
                ResultSet rs1 = stmtUserDet.getGeneratedKeys();
                rs1.next();
                id = rs1.getInt(1);
                rs1.close();
                System.out.println("User data entered!");
            }
            else{
                throw new SQLException("Value not inserted into table");
            }
            
            stmtUserDet.close();
            String insertEmailDet = "Insert into emails(email,id) values(?,?)";
            PreparedStatement stmtEmailDet = conn.connection.prepareStatement(insertEmailDet);
            stmtEmailDet.setString(1, email);
            stmtEmailDet.setInt(2, id);
            stmtEmailDet.executeUpdate();
            stmtEmailDet.close();

            this.setVisible(false);
            dispose();
            if(role.equalsIgnoreCase("Benificiary")){
                new BeneficiaryDashboard(new Beneficiary(new User(id,name,role,phone,email))).setVisible(true);
            }
            else if(role.equalsIgnoreCase("Dealer")){
               new DealerDashboard(new Dealer(new User(id,name,role,phone,email))).setVisible(true);
            }
        
        }catch(SQLIntegrityConstraintViolationException exc){
            System.out.println("Error in database entry");
            label8.setVisible(true);
            return;
        }
        catch(SQLException exc){
            System.out.println("Error in database entry");
            exc.printStackTrace();
            return;
        }
        catch(Exception exc){
            System.out.println("Error!");
            exc.printStackTrace();
            return;
        }
        
        
    }

    public static void main(String[] args) {
        new Signup_GUI();

    }
}

class Pair<K, T> {
    private K X;
    private T Y;

    Pair() {}

    Pair(K X, T Y) {
        this.X = X;
        this.Y = Y;
    }

    public K getX() {
        return X;
    }

    public T getY() {
        return Y;
    }

    public void setOne(K X) {
        this.X = X;
    }

    public void setTwo(T Y) {
        this.Y = Y;
    } 
}