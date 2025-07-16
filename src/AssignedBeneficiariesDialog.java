import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class AssignedBeneficiariesDialog extends JDialog {

    public AssignedBeneficiariesDialog(JFrame parent, Dealer dealer) {
        super(parent, "Assigned Beneficiaries", true);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(null);

        JLabel heading = new JLabel("Assigned Beneficiaries");
        heading.setBounds(20, 10, 300, 25);
        heading.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        add(heading);

        String[] columns = {"Name", "Card Number"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 50, 540, 270);
        add(scrollPane);

        try {
            Connector conn = new Connector();
            String query = """
                SELECT u.name, cn.card_number
                FROM customers c
                JOIN beneficiary b ON b.id = c.beneficiary_id
                JOIN users u ON u.id = b.id
                JOIN cardnumbers cn ON cn.id = b.id
                WHERE c.dealer_id = ?
            """;
            PreparedStatement stmt = conn.connection.prepareStatement(query);
            stmt.setInt(1, dealer.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String cardNumber = rs.getString("card_number");
                model.addRow(new Object[]{name, cardNumber});
            }

            rs.close();
            stmt.close();
            conn.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching beneficiaries.");
        }

        JButton closeBtn = new JButton("Close");
        closeBtn.setBounds(460, 330, 100, 30);
        closeBtn.addActionListener(e -> dispose());
        add(closeBtn);

        setVisible(true);
    }
    public static void main(String[] args) {
    // Create a sample Dealer object (replace with actual values if needed)
    Dealer dealer = new Dealer();
    dealer.setId(1); // must match a valid dealer_id in your database
    dealer.setName("Demo Dealer");
    dealer.setPhone("9876543210");
    dealer.setEmail("dealer@example.com");
    dealer.setLocation("Guwahati");
    dealer.setValidated(true);
    new AssignedBeneficiariesDialog(null, dealer);
}

}
