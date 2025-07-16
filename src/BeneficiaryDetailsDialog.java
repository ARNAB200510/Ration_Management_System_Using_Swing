import javax.swing.*;
import java.sql.*;

public class BeneficiaryDetailsDialog extends JDialog {

    public BeneficiaryDetailsDialog(JFrame parent, int beneficiaryId) {
        super(parent, "Beneficiary Details", true);
        setSize(450, 430);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setLayout(null); 

        int x = 30, y = 20, width = 380, height = 25;
        int gap = 35;

        String name = "N/A", cardNumber = "N/A", incomeGroup = "N/A", address = "N/A", location = "N/A";
        int familySize = 0;
        String centerName = "N/A", centerCode = "N/A";

        try {
            Connector conn = new Connector();
            String query = """
                SELECT u.name, cn.card_number, b.family_size, b.income_group, b.address, b.location,
                       d.center_name, d.center_code
                FROM beneficiary b
                JOIN users u ON u.id = b.id
                JOIN cardnumbers cn ON cn.id = b.id
                LEFT JOIN customers c ON c.beneficiary_id = b.id
                LEFT JOIN dealers d ON d.id = c.dealer_id
                WHERE b.id = ?
            """;

            PreparedStatement stmt = conn.connection.prepareStatement(query);
            stmt.setInt(1, beneficiaryId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                name = rs.getString("name");
                cardNumber = rs.getString("card_number");
                familySize = rs.getInt("family_size");
                incomeGroup = rs.getString("income_group");
                address = rs.getString("address");
                location = rs.getString("location");
                centerName = rs.getString("center_name");
                centerCode = rs.getString("center_code");
            }

            rs.close();
            stmt.close();
            conn.connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        addLabel("Name: " + name, x, y);            y += gap;
        addLabel("Card Number: " + cardNumber, x, y); y += gap;
        addLabel("Family Size: " + familySize, x, y); y += gap;
        addLabel("Income Group: " + incomeGroup, x, y); y += gap;
        addLabel("Address: " + address, x, y);        y += gap;
        addLabel("Location: " + location, x, y);      y += gap;
        addLabel("Dealer Center Name: " + centerName, x, y); y += gap;
        addLabel("Dealer Center Code: " + centerCode, x, y);

        setVisible(true);
    }

    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 380, 25);
        add(label);
    }
}
