import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ManageBeneficiary extends JDialog {
    private Dealer dealer;
    private JTextField cardInput;
    private JLabel nameLabel;
    private JLabel resultLabel;
    private JButton viewBtn,  removeBtn;
    private int beneficiaryId = -1;

    public ManageBeneficiary(JFrame parent, Dealer dealer) {
        super(parent, "Search Beneficiary", true);
        this.dealer = dealer;
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setLayout(null);

        JLabel label = new JLabel("Enter Card Number:");
        label.setBounds(30, 30, 150, 25);
        add(label);

        cardInput = new JTextField();
        cardInput.setBounds(180, 30, 200, 25);
        add(cardInput);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(390, 30, 80, 25);
        add(searchBtn);

        nameLabel = new JLabel("");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setBounds(30, 80, 400, 25);
        add(nameLabel);

        resultLabel = new JLabel("");
        resultLabel.setBounds(30, 110, 400, 25);
        add(resultLabel);

        viewBtn = new JButton("View Details");
        viewBtn.setBounds(30, 160, 130, 30);
        viewBtn.setEnabled(false);
        add(viewBtn);

       

        removeBtn = new JButton("Remove User");
        removeBtn.setBounds(330, 160, 130, 30);
        removeBtn.setEnabled(false);
        add(removeBtn);


        searchBtn.addActionListener(e -> searchUser());

        viewBtn.addActionListener(e -> {
            if (beneficiaryId != -1) {
                new BeneficiaryDetailsDialog(parent, beneficiaryId).setVisible(true);
            }
        });


        removeBtn.addActionListener(e -> {
            try {
                Connector conn = new Connector();
                String query = "DELETE FROM customers WHERE beneficiary_id = ?";
                PreparedStatement stmt = conn.connection.prepareStatement(query);
                stmt.setInt(1, beneficiaryId);
                int rows = stmt.executeUpdate();
                stmt.close();
                conn.connection.close();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "User removed successfully.");
                    resetFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to remove user.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error removing user.");
            }
        });

        setVisible(true);
    }

    private void searchUser() {
        String cardNumber = cardInput.getText().trim();
        if (cardNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a card number.");
            return;
        }

        try {
            Connector conn = new Connector();
            String query = """
                SELECT u.name, b.id
                FROM cardnumbers cn
                JOIN beneficiary b ON b.id = cn.id
                JOIN users u ON u.id = b.id
                JOIN customers c ON c.beneficiary_id = b.id
                WHERE cn.card_number = ? AND c.dealer_id = ?
            """;
            PreparedStatement stmt = conn.connection.prepareStatement(query);
            stmt.setString(1, cardNumber);
            stmt.setInt(2, dealer.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nameLabel.setText("Name: " + rs.getString("name"));
                resultLabel.setText("Card Number: " + cardNumber);
                beneficiaryId = rs.getInt("id");
                viewBtn.setEnabled(true);
                removeBtn.setEnabled(true);
            } else {
                resetFields();
                JOptionPane.showMessageDialog(this, "Beneficiary not found or not assigned to you.");
            }

            rs.close();
            stmt.close();
            conn.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching user.");
        }
    }

    private void resetFields() {
        nameLabel.setText("");
        resultLabel.setText("");
        viewBtn.setEnabled(false);
        removeBtn.setEnabled(false);
        beneficiaryId = -1;
    }

    public static void main(String[] args) {
        Dealer dealer = new Dealer();
        dealer.setId(1);
        new ManageBeneficiary(null, dealer);
    }
}
