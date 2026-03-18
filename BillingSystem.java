import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Model Class: Represents an inventory item
class Item {
    private int id;
    private String name;
    private double price;
    private int stock;

    public Item(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }

    // Display item in formatted structure
    public void display() {
        System.out.printf("%-10d %-20s %-10.2f %-10d\n", id, name, price, stock);
    }
}



// Manager Class: Handles inventory & billing
class InventoryManager {
    private ArrayList<Item> items = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    // Add new item to inventory
    public void addItem() {
        System.out.print("Enter Item ID: ");
        int id = getIntInput();

        System.out.print("Enter Item Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Price: ");
        double price = getDoubleInput();

        System.out.print("Enter Stock Quantity: ");
        int stock = getIntInput();

        items.add(new Item(id, name, price, stock));
        System.out.println("Item added successfully!");
    }

    // Update stock or price
    public void updateItem() {
        System.out.print("Enter Item ID to update: ");
        int id = getIntInput();

        Item item = searchItem(id);
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }

        System.out.println("1. Update Price");
        System.out.println("2. Update Stock");
        System.out.print("Choose an option: ");
        int choice = getIntInput();

        switch (choice) {
            case 1:
                System.out.print("Enter new price: ");
                double newPrice = getDoubleInput();
                item.setPrice(newPrice);
                System.out.println("Price updated successfully!");
                break;

            case 2:
                System.out.print("Enter new stock quantity: ");
                int newStock = getIntInput();
                item.setStock(newStock);
                System.out.println("Stock updated successfully!");
                break;

            default:
                System.out.println("Invalid option!");
        }
    }

    // View inventory list
    public void viewInventory() {
        if (items.isEmpty()) {
            System.out.println("No items in inventory.");
            return;
        }

        System.out.println("\n---------------------- INVENTORY ----------------------");
        System.out.printf("%-10s %-20s %-10s %-10s\n", "ID", "NAME", "PRICE", "STOCK");
        System.out.println("--------------------------------------------------------");

        for (Item item : items) {
            item.display();
        }
        System.out.println("--------------------------------------------------------\n");
    }

    // Billing System: Generate a bill
    public void generateBill() {
        HashMap<Item, Integer> cart = new HashMap<>();

        while (true) {
            System.out.print("Enter Item ID to purchase (0 to finish): ");
            int id = getIntInput();

            if (id == 0) break;

            Item item = searchItem(id);
            if (item == null) {
                System.out.println("Item not found.");
                continue;
            }

            System.out.print("Enter quantity: ");
            int qty = getIntInput();

            if (qty > item.getStock()) {
                System.out.println("Not enough stock!");
                continue;
            }

            cart.put(item, cart.getOrDefault(item, 0) + qty);

            item.setStock(item.getStock() - qty); // reduce stock
            System.out.println("Item added to cart!");
        }

        if (cart.isEmpty()) {
            System.out.println("No items purchased.");
            return;
        }

        System.out.println("\n-------------------------- BILL --------------------------");
        System.out.printf("%-20s %-10s %-10s %-10s\n", "ITEM", "QTY", "PRICE", "TOTAL");
        System.out.println("-----------------------------------------------------------");

        double grandTotal = 0;

        for (Item item : cart.keySet()) {
            int qty = cart.get(item);
            double price = item.getPrice();
            double total = qty * price;

            grandTotal += total;

            System.out.printf("%-20s %-10d %-10.2f %-10.2f\n",
                    item.getName(), qty, price, total);
        }

        double tax = grandTotal * 0.18;  // 18% GST
        double finalTotal = grandTotal + tax;

        System.out.println("-----------------------------------------------------------");
        System.out.printf("Subtotal: %.2f\n", grandTotal);
        System.out.printf("GST (18%%): %.2f\n", tax);
        System.out.printf("Final Total: %.2f\n", finalTotal);
        System.out.println("-----------------------------------------------------------");
        System.out.println("Thank you for shopping!");
    }

    // Search item by ID
    private Item searchItem(int id) {
        for (Item item : items) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }

    // Input validation for integers
    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Invalid input! Enter a number: ");
            }
        }
    }

    // Input validation for doubles
    private double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Invalid input! Enter a valid number: ");
            }
        }
    }

    // Menu system
    public void showMenu() {
        while (true) {
            System.out.println("\n====== BILLING & INVENTORY SYSTEM ======");
            System.out.println("1. Add Item");
            System.out.println("2. Update Item");
            System.out.println("3. View Inventory");
            System.out.println("4. Generate Bill");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1: addItem(); break;
                case 2: updateItem(); break;
                case 3: viewInventory(); break;
                case 4: generateBill(); break;
                case 5:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}



// Main Class
public class BillingSystem {
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
        manager.showMenu();
    }
}
