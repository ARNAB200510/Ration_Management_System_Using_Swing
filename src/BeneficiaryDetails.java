import javax.swing.*;
import java.sql.*;

public class BeneficiaryDetails extends JDialog {

    public BeneficiaryDetails(JFrame parent, Beneficiary beneficiary) {
        super(parent, "Beneficiary Details", true);
        setSize(450, 430);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setLayout(null); 

        int x = 30, y = 20, width = 380, height = 25;
        int gap = 35;

        JLabel nameLabel = new JLabel("Name: " + beneficiary.getName());
        nameLabel.setBounds(x, y, width, height);
        add(nameLabel);

        JLabel cardLabel = new JLabel("Card Number: " + beneficiary.getCardNumber());
        cardLabel.setBounds(x, y += gap, width, height);
        add(cardLabel);

        JLabel familyLabel = new JLabel("Family Size: " + beneficiary.getFamilySize());
        familyLabel.setBounds(x, y += gap, width, height);
        add(familyLabel);

        JLabel incomeLabel = new JLabel("Income Group: " + beneficiary.getIncomeGroup());
        incomeLabel.setBounds(x, y += gap, width, height);
        add(incomeLabel);

        JLabel addressLabel = new JLabel("Address: " + beneficiary.getAddress());
        addressLabel.setBounds(x, y += gap, width, height);
        add(addressLabel);

        JLabel locationLabel = new JLabel("Location: " + beneficiary.getLocation());
        locationLabel.setBounds(x, y += gap, width, height);
        add(locationLabel);

        // Fetch dealer details
        String centerName = "null";
        String centerCode = "null";

        try {
            Connector conn = new Connector();
            String query = "SELECT d.center_name, d.center_code " +
                           "FROM customer c JOIN dealer d ON c.dealer_id = d.id " +
                           "WHERE c.id = ?";
            PreparedStatement stmt = conn.connection.prepareStatement(query);
            stmt.setInt(1, beneficiary.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                centerName = rs.getString("center_name");
                centerCode = rs.getString("center_code");
            }

            rs.close();
            stmt.close();
            conn.connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel centerNameLabel = new JLabel("Dealer Center Name: " + centerName);
        centerNameLabel.setBounds(x, y += gap, width, height);
        add(centerNameLabel);

        JLabel centerCodeLabel = new JLabel("Dealer Center Code: " + centerCode);
        centerCodeLabel.setBounds(x, y += gap, width, height);
        add(centerCodeLabel);

       

        setVisible(true);
    }

    
}
