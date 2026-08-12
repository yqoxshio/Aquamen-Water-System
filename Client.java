package aquamen.model;

public class Client {
    private String id;
    private String name;
    private String address;
    private String contactNumber;
    private int gallonsOrdered;
    private String deliveryDate;
    private String remarks;
    private String status; // Pending, Delivered, Cancelled

    public Client(String id, String name, String address, String contactNumber,
                  int gallonsOrdered, String deliveryDate, String remarks, String status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.gallonsOrdered = gallonsOrdered;
        this.deliveryDate = deliveryDate;
        this.remarks = remarks;
        this.status = status;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public int getGallonsOrdered() { return gallonsOrdered; }
    public void setGallonsOrdered(int gallonsOrdered) { this.gallonsOrdered = gallonsOrdered; }

    public String getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(String deliveryDate) { this.deliveryDate = deliveryDate; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
