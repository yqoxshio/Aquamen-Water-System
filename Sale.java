package aquamen.model;

import java.time.LocalDate;

public class Sale {
    private String id;
    private String clientName;
    private int gallons;
    private int amount; // pesos
    private LocalDate date;

    public Sale(String id, String clientName, int gallons, int amount, LocalDate date) {
        this.id = id;
        this.clientName = clientName;
        this.gallons = gallons;
        this.amount = amount;
        this.date = date;
    }

    public String getId() { return id; }
    public String getClientName() { return clientName; }
    public int getGallons() { return gallons; }
    public int getAmount() { return amount; }
    public LocalDate getDate() { return date; }
}
