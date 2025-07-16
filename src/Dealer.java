public class Dealer extends User{
    private String centerName;
    private String centerCode;
    private String location;
    private int stock_limit;
    private boolean is_validated;

    public Dealer(){

    }
    public Dealer(int id, String name, String role, String phone, String email,
                  String centerName, String centerCode, String location,
                  int stock_limit, boolean is_validated) {
        super(id, name, role, phone, email);
        this.centerName = centerName;
        this.centerCode = centerCode;
        this.location = location;
        this.stock_limit = stock_limit;
        this.is_validated = is_validated;
    }
    public Dealer(User user){
        super(user.getId(),user.getName(),user.getRole(),user.getPhone(),user.getEmail());

    }
    public String getCenterName() {
    return centerName;
    }

public void setCenterName(String centerName) {
    this.centerName = centerName;
}

public String getCenterCode() {
    return centerCode;
}

public void setCenterCode(String centerCode) {
    this.centerCode = centerCode;
}

public String getLocation() {
    return location;
}

public void setLocation(String location) {
    this.location = location;
}

public int getStock_limit() {
    return stock_limit;
}

public void setStock_limit(int stock_limit) {
    this.stock_limit = stock_limit;
}

public boolean isValidated() {
    return is_validated;
}

public void setValidated(boolean is_validated) {
    this.is_validated = is_validated;
}
}
