package aquamen.data;

import java.time.LocalDate;

/**
 * Holds the financial summary for one specific date.
 */
public class DailyRecord {
    private final LocalDate date;
    private int sales;
    private int gallons;
    private int expenses;

    public DailyRecord(LocalDate date, int sales, int gallons, int expenses) {
        this.date = date;
        this.sales = sales;
        this.gallons = gallons;
        this.expenses = expenses;
    }

    public LocalDate getDate() { return date; }
    public int getSales() { return sales; }
    public int getGallons() { return gallons; }
    public int getExpenses() { return expenses; }
    public int getNetProfit() { return sales - expenses; }

    public void setSales(int sales) { this.sales = sales; }
    public void setGallons(int gallons) { this.gallons = gallons; }
    public void setExpenses(int expenses) { this.expenses = expenses; }
}
