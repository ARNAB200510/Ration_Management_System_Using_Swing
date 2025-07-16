import javax.swing.*;
import java.sql.Statement;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DealerDashboard extends Dashboard {

    public DealerDashboard(Dealer dealer) {
        super(dealer);
        this.user = dealer;
        

        JLabel infoLabel = new JLabel("Note: Please ensure your profile is validated.");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoLabel.setForeground(Color.RED);
        infoLabel.setBounds(40, 80, 400, 20);
        add(infoLabel);

        int yStart = 240;
        int gap = 100;

      
        profileButton.addActionListener(e -> {
            if (!dealer.isValidated()) {
                this.setVisible(false);
                dispose();
                new DealerInfoForm(dealer).setVisible(true);
            } else {
                new DealerDetails(this, dealer).setVisible(true);
            }
        });

        
        JButton viewBeneficiaries = new JButton("View Assigned Beneficiaries");
        viewBeneficiaries.setBounds(40, yStart, 440, 40);
        styleButton(viewBeneficiaries);
        viewBeneficiaries.addActionListener(e -> {
            new AssignedBeneficiariesDialog(this, dealer).setVisible(true);
        });
        add(viewBeneficiaries);

        JButton issueRation = new JButton("Manage beneficaries");
        issueRation.setBounds(40, yStart + gap, 440, 40);
        styleButton(issueRation);
        issueRation.addActionListener(e -> {
            new  ManageBeneficiary(this, dealer).setVisible(true);
        });
        add(issueRation);

        JButton historyButton = new JButton("Issue Ration");
        historyButton.setBounds(40, yStart + 2 * gap, 440, 40);
        styleButton(historyButton);
        historyButton.addActionListener(e -> {
            String cardNumber = JOptionPane.showInputDialog(this, "Enter Beneficiary Card Number:");

            if (cardNumber == null || cardNumber.trim().isEmpty()) {
             JOptionPane.showMessageDialog(this, "Card number is required.");
            return;
             }

            try {
                Connector conn = new Connector();

                String validationQuery = "select is_validated from dealers where id = ?";
                PreparedStatement valStmt = conn.connection.prepareStatement(validationQuery);
                ResultSet rs0 = valStmt.executeQuery();
                dealer.setValidated(rs0.getBoolean("is_validated"));
                PreparedStatement ps = conn.connection.prepareStatement(
                        "SELECT b.id, b.family_size FROM beneficiary b " +
                        "JOIN cardnumbers cn ON cn.id = b.id WHERE cn.card_number = ?"
                        );
                ps.setString(1, cardNumber);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Card number not found.");
                    return;
                }

                int beneficiaryId = rs.getInt("id");
                int familySize = rs.getInt("family_size");
                rs.close();
                ps.close();

        
                String insertstmt ="INSERT INTO orders(card_number, dealer_id, issue_date) VALUES (?, ?, CURDATE())";
                ps = conn.connection.prepareStatement(insertstmt,Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, cardNumber);
                ps.setInt(2, dealer.getId());
                ps.executeUpdate();
                ResultSet rsOrder = ps.getGeneratedKeys();
                rsOrder.next();
                int orderId = rsOrder.getInt(1);
                rsOrder.close();
                ps.close();

                
                ps = conn.connection.prepareStatement(
                "SELECT item_id, quantity FROM stock WHERE dealer_id = ?"
                );
                ps.setInt(1, dealer.getId());
                rs = ps.executeQuery();

                while (rs.next()) {
                    int itemId = rs.getInt("item_id");
                    int available = rs.getInt("quantity");
                    int qty = familySize * 10;

                if (available >= qty) {
                
                    PreparedStatement psInsert = conn.connection.prepareStatement(
                    "INSERT INTO order_items(order_id, item_id, quantity) VALUES (?, ?, ?)"
                    );
                    psInsert.setInt(1, orderId);
                    psInsert.setInt(2, itemId);
                    psInsert.setInt(3, qty);
                    psInsert.executeUpdate();
                    psInsert.close();

                
                    PreparedStatement psUpdate = conn.connection.prepareStatement(
                        "UPDATE stock SET quantity = quantity - ? WHERE dealer_id = ? AND item_id = ?"
                    );
                    psUpdate.setInt(1, qty);
                    psUpdate.setInt(2, dealer.getId());
                    psUpdate.setInt(3, itemId);
                    psUpdate.executeUpdate();
                    psUpdate.close();
            }
        }

        rs.close();
        ps.close();
        conn.connection.close();

        JOptionPane.showMessageDialog(this, "Ration issued successfully to: " + cardNumber);

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error issuing ration.");
    }
        });
        add(historyButton);

        if (!dealer.isValidated()) {
            for (Component c : getContentPane().getComponents()) {
                if (c instanceof JButton && c != profileButton) {
                    c.setEnabled(false);
                    c.setBackground(Color.DARK_GRAY);
                }
            }
        }

        setVisible(true);
    }

  
}
