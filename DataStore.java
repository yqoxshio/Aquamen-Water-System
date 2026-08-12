package aquamen.data;

import aquamen.model.Client;
import aquamen.model.Order;
import aquamen.model.Sale;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class DataStore {
    private static final DataStore INSTANCE = new DataStore();

    private final List<Client> clients = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    private final List<Sale> sales = new ArrayList<>();
    private final Map<LocalDate, DailyRecord> dailyRecords = new HashMap<>();

    private final AtomicInteger clientIdCounter = new AtomicInteger(1001);
    private final AtomicInteger orderIdCounter = new AtomicInteger(2001);
    private final AtomicInteger saleIdCounter = new AtomicInteger(3001);

    private DataStore() {
        // Sample clients
        addClient(new Client("C1001", "Juan Dela Cruz", "123 Mabini St, Manila", "09171234567",
                5, "2026-08-07", "Call before delivery", "Pending"));
        addClient(new Client("C1002", "Maria Santos", "45 Rizal Ave, Quezon City", "09281234567",
                10, "2026-08-07", "", "Pending"));
        addClient(new Client("C1003", "Pedro Reyes", "78 Bonifacio St, Makati", "09391234567",
                3, "2026-08-08", "Leave at gate", "Pending"));

        orders.add(new Order("O2001", "Juan Dela Cruz", "123 Mabini St, Manila", 5, "Pending"));
        orders.add(new Order("O2002", "Maria Santos", "45 Rizal Ave, Quezon City", 10, "Pending"));
        orders.add(new Order("O2003", "Pedro Reyes", "78 Bonifacio St, Makati", 3, "Pending"));

        // Sample historical records so the calendar has data to show
        dailyRecords.put(LocalDate.now().minusDays(2),
                new DailyRecord(LocalDate.now().minusDays(2), 3200, 80, 900));
        dailyRecords.put(LocalDate.now().minusDays(1),
                new DailyRecord(LocalDate.now().minusDays(1), 4100, 102, 1100));
        dailyRecords.put(LocalDate.now(),
                new DailyRecord(LocalDate.now(), 0, 0, 0));
    }

    public static DataStore getInstance() {
        return INSTANCE;
    }

    // ===== Clients =====
    public void addClient(Client client) {
        if (client.getId() == null || client.getId().isBlank()) {
            client.setId("C" + clientIdCounter.getAndIncrement());
        }
        clients.add(client);
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clients);
    }

    public Optional<Client> findClientById(String id) {
        return clients.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public Optional<Client> findClientByName(String name) {
        return clients.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name.trim()))
                .findFirst();
    }

    public boolean updateClient(Client updated) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId().equals(updated.getId())) {
                clients.set(i, updated);
                return true;
            }
        }
        return false;
    }

    public boolean deleteClient(String id) {
        return clients.removeIf(c -> c.getId().equals(id));
    }

    // ===== Orders =====
    public List<Order> getPendingOrders() {
        List<Order> pending = new ArrayList<>();
        for (Order o : orders) {
            if ("Pending".equalsIgnoreCase(o.getStatus())) {
                pending.add(o);
            }
        }
        return pending;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public void addOrder(Order order) {
        if (order.getId() == null || order.getId().isBlank()) {
            order.setId("O" + orderIdCounter.getAndIncrement());
        }
        orders.add(order);
    }

    public boolean updateOrderStatus(String id, String status) {
        for (Order o : orders) {
            if (o.getId().equals(id)) {
                o.setStatus(status);
                return true;
            }
        }
        return false;
    }

    // ===== Sales =====
    public void addSale(String clientName, int gallons) {
        int amount = gallons * 40;
        LocalDate today = LocalDate.now();
        Sale sale = new Sale("S" + saleIdCounter.getAndIncrement(), clientName, gallons, amount, today);
        sales.add(sale);

        // Update today's daily record
        DailyRecord record = dailyRecords.computeIfAbsent(today,
                d -> new DailyRecord(d, 0, 0, 0));
        record.setSales(record.getSales() + amount);
        record.setGallons(record.getGallons() + gallons);
    }

    public List<Sale> getAllSales() {
        return new ArrayList<>(sales);
    }

    // ===== Daily / Historical records =====
    public DailyRecord getRecordForDate(LocalDate date) {
        return dailyRecords.getOrDefault(date, new DailyRecord(date, 0, 0, 0));
    }

    public int getTotalSalesToday() {
        return getRecordForDate(LocalDate.now()).getSales();
    }

    public int getTotalGallonsToday() {
        return getRecordForDate(LocalDate.now()).getGallons();
    }

    public int getTotalExpenses() {
        return getRecordForDate(LocalDate.now()).getExpenses();
    }

    public void recordFinancials(int sales, int gallons, int expenses) {
        LocalDate today = LocalDate.now();
        dailyRecords.put(today, new DailyRecord(today, sales, gallons, expenses));
    }

    public void recordFinancialsForDate(LocalDate date, int sales, int gallons, int expenses) {
        dailyRecords.put(date, new DailyRecord(date, sales, gallons, expenses));
    }
}
