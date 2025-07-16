import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Dashboard extends JFrame {
    protected User user;
    JLabel welcomeLabel,logoutLabel;
    JButton profileButton;
    public Dashboard(User user) {
        this.user = user;
        setTitle("Dashboard");
        setSize(540, 650);
        setLocation(400, 70);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);


        welcomeLabel = new JLabel("WELCOME, " + (user.getName().toUpperCase().split(" "))[0]);
        welcomeLabel.setFont(new Font("Monaco", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setBounds(60, 30, 460, 40);
        add(welcomeLabel);

        profileButton = new JButton("View / Update Profile");
        profileButton.setBounds(40, 120, 440, 40);
        styleButton(profileButton);
        add(profileButton);

        logoutLabel = new JLabel("Logout");
        logoutLabel.setFont(new Font("Arial", Font.BOLD, 14));
        logoutLabel.setForeground(Color.BLUE);
        logoutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutLabel.setBounds(460, 10, 60, 20); // Positioned at top-right corner
        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); 
                JOptionPane.showMessageDialog(null, "Logged out successfully.");
                new Login_GUI().setVisible(true);
            }
        });
        add(logoutLabel);

        setVisible(true);
    }

    protected void styleButton(JButton button) {
        button.setBackground(Color.decode("#f2570f"));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Raleway", Font.BOLD, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
    }
}