

public class Beneficiary extends User{
    private String cardNumber = "";
    private int familySize = 0;
    private String incomeGroup = "";
    private String address = "";
    private String location = "";
    private boolean detailsFilled = false;
    
    public Beneficiary(int id, String name, String role, String phone, String email,String cardNumber, int familySize, String incomeGroup,
                       String address, String location, boolean detailsFilled){
                        
        super(id,name,role,phone,email);
        this.cardNumber = cardNumber;
        this.familySize = familySize;
        this.incomeGroup = incomeGroup;
        this.address = address;
        this.location = location;
        this.detailsFilled = detailsFilled;

    }
    public Beneficiary(User user){
        super(user.getId(),user.getName(),user.getRole(),user.getPhone(),user.getEmail());

    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getFamilySize() {
        return familySize;
    }

    public String getIncomeGroup() {
        return incomeGroup;
    }

    public String getAddress() {
        return address;
    }

    public String getLocation() {
        return location;
    }

    public boolean isDetailsFilled() {
        return detailsFilled;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setFamilySize(int familySize) {
        this.familySize = familySize;
    }

    public void setIncomeGroup(String incomeGroup) {
        this.incomeGroup = incomeGroup;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDetailsFilled(boolean detailsFilled) {
        this.detailsFilled = detailsFilled;
    }

}