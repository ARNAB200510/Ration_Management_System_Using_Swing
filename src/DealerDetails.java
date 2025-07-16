import javax.swing.*;


public class DealerDetails extends JDialog {

    public DealerDetails(JFrame parent, Dealer dealer) {
        super(parent, "Dealer Details", true);
        setSize(450, 430);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); 

        int x = 30, y = 20, width = 380, height = 25;
        int gap = 35;

        JLabel nameLabel = new JLabel("Name: " + dealer.getName());
        nameLabel.setBounds(x, y, width, height);
        add(nameLabel);

        JLabel cardLabel = new JLabel("Center Name: " + dealer.getCenterName());
        cardLabel.setBounds(x, y += gap, width, height);
        add(cardLabel);

        JLabel familyLabel = new JLabel("Total capacity: " + dealer.getStock_limit());
        familyLabel.setBounds(x, y += gap, width, height);
        add(familyLabel);

        JLabel incomeLabel = new JLabel("Role: " + dealer.getRole());
        incomeLabel.setBounds(x, y += gap, width, height);
        add(incomeLabel);

        JLabel addressLabel = new JLabel("Center Code " + dealer.getCenterCode());
        addressLabel.setBounds(x, y += gap, width, height);
        add(addressLabel);

        JLabel locationLabel = new JLabel("Location: " + dealer.getLocation());
        locationLabel.setBounds(x, y += gap, width, height);
        add(locationLabel);


        setVisible(true);
    }

    
}
