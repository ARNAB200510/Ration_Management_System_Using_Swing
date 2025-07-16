public class User{
    private int id;
    private String name;
    private String role;
    private String phone;
    private String email;

    public User(){

    }
    public User(int id, String name, String role, String phone, String email) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.email = email;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}



