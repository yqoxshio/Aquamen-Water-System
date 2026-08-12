package aquamen.model;

public class Order {
    private String id;
    private String clientName;
    private String address;
    private int gallonsOrdered;
    private String status; // Pending, Delivered

    public Order(String id, String clientName, String address, int gallonsOrdered, String status) {
        this.id = id;
        this.clientName = clientName;
        this.address = address;
        this.gallonsOrdered = gallonsOrdered;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getGallonsOrdered() { return gallonsOrdered; }
    public void setGallonsOrdered(int gallonsOrdered) { this.gallonsOrdered = gallonsOrdered; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
