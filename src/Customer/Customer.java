package Customer;

public class Customer {

    private int customerId;
    private String name;

    public Customer(int customerId, String name) {
        this.name = name;
        this.customerId = customerId;
    }

    public void introduce(){
        System.out.println("Hello my name is " + this.name);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
