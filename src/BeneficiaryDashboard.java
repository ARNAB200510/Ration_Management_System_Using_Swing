import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class BeneficiaryDashboard extends Dashboard {

    public BeneficiaryDashboard(Beneficiary beneficiary) {
        super(beneficiary); 
        this.user = beneficiary;

    
        JLabel infoLabel = new JLabel("Note:Profiles must be updated to proceed further");
        infoLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        infoLabel.setForeground(java.awt.Color.RED);
        infoLabel.setBounds(40, 80, 400, 20);
        add(infoLabel);

        int yStart = 240; 
        int gap = 140;
        
        profileButton.addActionListener(e  -> {
            if(!beneficiary.isDetailsFilled()){
            this.setVisible(false);
            dispose();
                new BeneficiaryInfoForm(beneficiary).setVisible(true);
            }
            else{
                new BeneficiaryDetails(this,beneficiary).setVisible(true);;
            }
        });
        JButton historyButton = new JButton("View Ration History");
        historyButton.setBounds(40, yStart, 440, 40);
        styleButton(historyButton);
        historyButton.addActionListener(e -> {
           new RationHistoryDialog(this,beneficiary).setVisible(true);
           
        });
        add(historyButton);

       

        JButton requestButton = new JButton("Request for Dealer Change");
        requestButton.setBounds(40, yStart +   gap, 440, 40);
        styleButton(requestButton);
        requestButton.addActionListener(e -> {
            try{
                int currentId = -100;
                boolean idFound = false;
                Connector conn = new Connector();
                String validationQuery = "select details_filled from beneficiary where id = ?";
                PreparedStatement valStmt = conn.connection.prepareStatement(validationQuery);
                ResultSet rs0 = valStmt.executeQuery();
                beneficiary.setDetailsFilled(rs0.getBoolean("details_filled"));
                String statement = "Select dealer_id from customers Where beneficiary_id = ?";
                PreparedStatement stmt = conn.connection.prepareStatement(statement);
                stmt.setInt(1,beneficiary.getId());
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    idFound = true;
                    currentId = rs.getInt("dealer_id");
                }
                rs.close();
                statement = "Select cap.dealer_id from capacity cap where capacity_left >= ? AND dealer_id !=? AND cap.location =? order by capacity_left ASC";
                stmt = conn.connection.prepareStatement(statement);
                int currentReq = beneficiary.getFamilySize() * 10;
                stmt.setInt(1, currentReq);
                stmt.setInt(2,currentId);
                stmt.setString(3,beneficiary.getLocation());
                rs = stmt.executeQuery();
                
                if(rs.next()){
                    int newDealerId = rs.getInt("dealer_id");
                    if(idFound){
                        String update = "UPDATE customers SET dealer_id = ? WHERE beneficiary_id = ?";
                        PreparedStatement updateStmt = conn.connection.prepareStatement(update);
                        updateStmt.setInt(1, newDealerId);
                        updateStmt.setInt(2, beneficiary.getId());
                        updateStmt.executeUpdate();
                        updateStmt.close();
                        rs.close();
                        stmt.close();
                        JOptionPane.showMessageDialog(null, "Dealer changed successfully!");
                    }
                    else{
                        String insert = "INSERT INTO customers (beneficiary_id, dealer_id) VALUES (?, ?)";
                        PreparedStatement insertStmt = conn.connection.prepareStatement(insert);
                        insertStmt.setInt(1, beneficiary.getId());
                        insertStmt.setInt(2, newDealerId);
                        insertStmt.executeUpdate();
                        insertStmt.close();
                        rs.close();
                        stmt.close();
                        JOptionPane.showMessageDialog(null, "Dealer assigned successfully!");

                    }

                }
                else{
                    JOptionPane.showMessageDialog(null, "No dealer available");
                    rs.close();
                    stmt.close();
                }
            }catch(Exception exc){
                JOptionPane.showMessageDialog(null, "Error!Plz try again");
            }
            

        });
        add(requestButton);
        if(!beneficiary.isDetailsFilled()){
            requestButton.setEnabled(false);
            historyButton.setEnabled(false);
            requestButton.setBackground(Color.DARK_GRAY);
            historyButton.setBackground(Color.DARK_GRAY);

        }
        else{
            requestButton.setEnabled(true);
            historyButton.setEnabled(true);
            Color buttonColor = Color.decode("#f2570f");
            requestButton.setBackground(buttonColor);
            historyButton.setBackground(buttonColor);

        }
        setVisible(true);
    }

    
    public static void main(String[] args) {
    
    }
}    