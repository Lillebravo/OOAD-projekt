package Customer;

public class Customer {

    private int customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;

    // For creating new customers
    public Customer(String name, String email, String phone, String address, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    // For listing customers
    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public int getCustomerId() {
        return customerId;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "-------------------------------------\n" +
                "Customer Id:" + customerId + "\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "-------------------------------------";
    }

}

