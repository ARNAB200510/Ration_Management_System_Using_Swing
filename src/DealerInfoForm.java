import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import javax.swing.*;

public class DealerInfoForm extends JFrame {

    private JTextField centerNameField, limitField, centerCodeField, locationField;
    private JButton submitButton;

    private Dealer dealer;

    public DealerInfoForm(Dealer dealer) {
        this.dealer = dealer;

        setTitle("Validate Profile");
        setSize(540, 650);
        setLocation(400, 30);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        JLabel backLabel = new JLabel("Back");
        backLabel.setFont(new Font("Arial", Font.BOLD, 14));
        backLabel.setForeground(Color.BLUE);
        backLabel.setBounds(10, 10, 100, 30);
        backLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setVisible(false);
                dispose();
                new DealerDashboard(dealer);
            }
        });
        add(backLabel);


        JLabel cardLabel = new JLabel("Center Name:");
        cardLabel.setBounds(40, 60, 200, 30);
        add(cardLabel);
        centerNameField = new JTextField();
        centerNameField.setBounds(40, 90, 440, 30);
        add(centerNameField);

      
        JLabel familyLabel = new JLabel("Total capacity(in tons):");
        familyLabel.setBounds(40, 130, 200, 30);
        add(familyLabel);
        limitField = new JTextField();
        limitField.setBounds(40, 160, 440, 30);
        add(limitField);

  
        JLabel centerCodLabel = new JLabel("Center code:");
        centerCodLabel.setBounds(40, 230, 200, 30);
        add(centerCodLabel);
        centerCodeField = new JTextField();
        centerCodeField.setBounds(40, 260, 440, 30);
        add(centerCodeField);

    
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setBounds(40, 330, 200, 30);
        add(locationLabel);
        locationField = new JTextField();
        locationField.setBounds(40, 360, 440, 30);
        add(locationField);

        submitButton = new JButton("Submit");
        submitButton.setBounds(40, 440, 440, 40);
        submitButton.setFont(new Font("Raleway", Font.BOLD, 20));
        submitButton.setBackground(Color.decode("#f2570f"));
        submitButton.setForeground(Color.BLACK);
        submitButton.addActionListener(this::submitActionPerformed);
        add(submitButton);

        setVisible(true);
    }

    private void submitActionPerformed(ActionEvent evt) {
        try {
            String centerNam = centerNameField.getText();
            int totalCap = Integer.parseInt(limitField.getText());
            String centerCod = centerCodeField.getText();
            String location = locationField.getText();

            Connector conn = new Connector();
            String query = "INSERT INTO Dealers(id, center_name, stock_limit_tons, center_code, location, is_validated) " +
                           "VALUES (?, ?, ?, ?, ?,true)";
            PreparedStatement stmt = conn.connection.prepareStatement(query);
            stmt.setInt(1, dealer.getId());
            stmt.setString(2, centerNam);
            stmt.setInt(3, totalCap);
            stmt.setString(4, centerCod);
            stmt.setString(5, location);
            stmt.executeUpdate();

            stmt.close();
            conn.connection.close();

            // Update local object state
            dealer.setCenterName(centerNam);
            dealer.setStock_limit(totalCap);
            dealer.setCenterCode(centerCod);
            dealer.setLocation(location);
            dealer.setValidated(true);

            JOptionPane.showMessageDialog(this, "Details submitted successfully!");
            setVisible(false);
            dispose();
            new DealerDashboard(dealer);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting details. Please try again.");
        }
    }

    public static void main() {
        new DealerInfoForm(new Dealer(1, "Rahul Sharma", "Dealer", "9876543210", "rahul@example.com", null, null, null, 0, false));
    }

}
