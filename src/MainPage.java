import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

public class MainPage extends JFrame {
    MainPage() {
        setTitle("Homepage");
        setSize(540, 650);
        setLocation(400, 30);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        ImageIcon hIcon = new ImageIcon(ClassLoader.getSystemResource("Icon/homepage.png"));
        setIconImage(hIcon.getImage());

    
        ImageIcon mIcon = new ImageIcon(ClassLoader.getSystemResource("Icon/mainpage.png"));
        Image scaled_mIcon = mIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel tImage = new JLabel(new ImageIcon(scaled_mIcon));
        tImage.setBounds(180, 20, 150, 150);
        add(tImage);

        JPanel welcomeMsg = new JPanel();
        welcomeMsg.setLayout(new BoxLayout(welcomeMsg, BoxLayout.Y_AXIS));
        welcomeMsg.setBounds(70, 200, 400, 100); // Wider bounds for long text
        welcomeMsg.setOpaque(false); // Transparent background

        JLabel line1 = new JLabel("Welcome to");
        JLabel line2 = new JLabel("Ration Management System");

        line1.setForeground(new Color(0xF2570F));
        line2.setForeground(new Color(0xF2570F));

        line1.setFont(new Font("Monaco", Font.BOLD, 32));
        line2.setFont(new Font("Monaco", Font.BOLD, 22));
        line1.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        line2.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        welcomeMsg.add(line1);
        welcomeMsg.add(Box.createVerticalStrut(10)); // space between lines
        welcomeMsg.add(line2);
        add(welcomeMsg);

      
        JLabel credits = new JLabel("credits - Arnsand");
        credits.setBounds(0, 580, 200, 20);
        credits.setForeground(Color.BLACK);
        credits.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(credits);

        JButton loginButton = new JButton("Login/Signup");
        loginButton.setBounds(180, 400, 150, 40);
        loginButton.setBackground(new Color(0xF2570F));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        add(loginButton);
        loginButton.addActionListener( evt ->{
            setVisible(false);
            dispose();
            new Login_GUI().setVisible(true);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainPage();
    }
}