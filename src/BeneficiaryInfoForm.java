import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class BeneficiaryInfoForm extends JFrame {

    private JTextField cardField, familySizeField, addressField, locationField;
    private JComboBox<String> incomeGroupBox;
    private JButton submitButton;

    private Beneficiary beneficiary;

    public BeneficiaryInfoForm(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;

        setTitle("Complete Profile");
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
                new BeneficiaryDashboard(beneficiary);
            }
        });
        add(backLabel);


        JLabel cardLabel = new JLabel("Card Number:");
        cardLabel.setBounds(40, 60, 200, 30);
        add(cardLabel);
        cardField = new JTextField();
        cardField.setBounds(40, 90, 440, 30);
        add(cardField);

      
        JLabel familyLabel = new JLabel("Family Size:");
        familyLabel.setBounds(40, 130, 200, 30);
        add(familyLabel);
        familySizeField = new JTextField();
        familySizeField.setBounds(40, 160, 440, 30);
        add(familySizeField);

    
        JLabel incomeLabel = new JLabel("Income Group:");
        incomeLabel.setBounds(40, 200, 200, 30);
        add(incomeLabel);
        String[] incomeOptions = {"APL", "BPL"};
        incomeGroupBox = new JComboBox<>(incomeOptions);
        incomeGroupBox.setBounds(40, 230, 440, 30);
        add(incomeGroupBox);

  
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(40, 270, 200, 30);
        add(addressLabel);
        addressField = new JTextField();
        addressField.setBounds(40, 300, 440, 30);
        add(addressField);

    
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setBounds(40, 340, 200, 30);
        add(locationLabel);
        locationField = new JTextField();
        locationField.setBounds(40, 370, 440, 30);
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
            String cardNum = cardField.getText();
            int familySize = Integer.parseInt(familySizeField.getText());
            String incomeGroup = (String) incomeGroupBox.getSelectedItem();
            String address = addressField.getText();
            String location = locationField.getText();

            Connector conn = new Connector();
            String query = "INSERT INTO Beneficiary(id, family_size, income_group, address, location, details_filled) " +
                           "VALUES (?, ?, ?, ?, ?, true)";
            PreparedStatement stmt = conn.connection.prepareStatement(query);
            stmt.setInt(1, beneficiary.getId());
            stmt.setInt(2, familySize);
            stmt.setString(3, incomeGroup);
            stmt.setString(4, address);
            stmt.setString(5, location);
            stmt.executeUpdate();

            stmt.close();
            String cardInsertQuery = "Insert into cardNumbers(card_number,id) values (?,?)";
            stmt = conn.connection.prepareStatement(cardInsertQuery);
            stmt.setString(1, cardNum);
            stmt.setInt(2,beneficiary.getId());
            stmt.executeUpdate();
            conn.connection.close();


            beneficiary.setCardNumber(cardNum);
            beneficiary.setFamilySize(familySize);
            beneficiary.setIncomeGroup(incomeGroup);
            beneficiary.setAddress(address);
            beneficiary.setLocation(location);
            beneficiary.setDetailsFilled(true);

            JOptionPane.showMessageDialog(this, "Details submitted successfully!");
            setVisible(false);
            dispose();
            new BeneficiaryDashboard(beneficiary);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting details. Please try again.");
        }
    }

   
}
