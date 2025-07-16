import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class RationHistoryDialog extends JDialog {

    public RationHistoryDialog(JFrame parent, Beneficiary beneficiary) {
        super(parent, "Ration History", true);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setLayout(null); 
        

        String[] columns = {"Order ID", "Issue Date", "Item", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 550, 330); 
        add(scrollPane);

        try {
            Connector conn = new Connector();
            String query = """
                SELECT o.order_id, o.issue_date, i.item_name, oi.quantity
                FROM orders o
                JOIN order_items oi ON o.order_id = oi.order_id
                JOIN items i ON oi.item_id = i.item_id
                WHERE o.card_number = ?
                ORDER BY o.issue_date DESC
            """;

            PreparedStatement stmt = conn.connection.prepareStatement(query);
            stmt.setString(1, beneficiary.getCardNumber());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                Date issueDate = rs.getDate("issue_date");
                String item = rs.getString("item_name");
                int quantity = rs.getInt("quantity");
                model.addRow(new Object[]{orderId, issueDate, item, quantity});
            }

            rs.close();
            stmt.close();
            conn.connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching ration history.");
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        Beneficiary b = new Beneficiary(1, "Arnav", "Benificiary", "1234567890", "arnav@example.com",
                "XYZ123", 4, "BPL", "Some Address", "City", true);
        new RationHistoryDialog(null, b);
    }
}
