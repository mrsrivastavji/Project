// import java.util.ArrayList;
// import java.util.Scanner;
import java.util.*;

class MenuItem {
    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " - Rs. " + price;
    }
}

class OrderItem {
    private MenuItem item;
    private int quantity;

    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public double getTotal() {
        return item.getPrice() * quantity;
    }

    public String getName() { return item.getName(); }
    public int getQuantity() { return quantity; }
    public double getPrice() { return item.getPrice(); }
}

public class RestaurantBillingSystem {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<MenuItem> menu = new ArrayList<>();
    static ArrayList<OrderItem> cart = new ArrayList<>();
    static final double GST = 0.05; // 5%

    public static void main(String[] args) {

        menu.add(new MenuItem("Pizza", 250));
        menu.add(new MenuItem("Burger", 120));
        menu.add(new MenuItem("Pasta", 180));

        int choice;
        do {
            System.out.println("\n===== RESTAURANT BILLING SYSTEM =====");
            System.out.println("1. View Menu");
            System.out.println("2. Add Menu Item");
            System.out.println("3. Remove Menu Item");
            System.out.println("4. Take Order");
            System.out.println("5. Generate Bill");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1-> viewMenu();
                case 2-> addMenuItem();
                case 3-> removeMenuItem();
                case 4-> takeOrder();
                case 5-> generateBill();
                case 6-> System.out.println("Thank you!"); 
                default-> System.out.println("Invalid choice!");
            }
        } while (choice != 6);
    }

    
    public static void viewMenu() {
        System.out.println("\n----- MENU -----");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i+1) + ". " + menu.get(i));
        }
    }

    public static void addMenuItem() {
        sc.nextLine();
        System.out.print("Enter item name: ");
        String name = sc.nextLine();
        System.out.print("Enter item price: ");
        double price = sc.nextDouble();

        menu.add(new MenuItem(name, price));
        System.out.println("Item added successfully!");
    }

    public static void removeMenuItem() {
        viewMenu();
        System.out.print("Enter item number to remove: ");
        int index = sc.nextInt();

        if (index > 0 && index <= menu.size()) {
            menu.remove(index - 1);
            System.out.println("Item removed!");
        } else {
            System.out.println("Invalid item number!");
        }
    }

    public static void takeOrder() {
        viewMenu();
        System.out.print("Enter item number: ");
        int index = sc.nextInt();

        if (index < 1 || index > menu.size()) {
            System.out.println("Invalid item!");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        cart.add(new OrderItem(menu.get(index - 1), qty));
        System.out.println("Item added to cart!");
    }

    public static void generateBill() {
        if (cart.isEmpty()) {
            System.out.println("No items ordered!");
            return;
        }

        System.out.println("\n========== BILL RECEIPT ==========");
        double subtotal = 0;

        for (OrderItem oi : cart) {
            System.out.printf("%-15s Qty: %d  Price: %.2f  Total: %.2f\n",
                    oi.getName(), oi.getQuantity(), oi.getPrice(), oi.getTotal());
            subtotal += oi.getTotal();
        }

        double gstAmount = subtotal * GST;
        double grandTotal = subtotal + gstAmount;

        System.out.println("-----------------------------------");
        System.out.printf("Subtotal: Rs %.2f\n", subtotal);
        System.out.printf("GST (5%%): Rs %.2f\n", gstAmount);
        System.out.printf("Grand Total: Rs %.2f\n", grandTotal);
        System.out.println("===================================");

        cart.clear(); 
    }
}
