import java.sql.*;
import java.util.*;

class BankAccount {
    private final String accountNumber;
    private final String holderName;
    private double balance;
    private final int pin;

    public BankAccount(String accountNumber, String holderName, double balance, int pin) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.pin = pin;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    public int getPin() { return pin; }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

public class BankSystem {

    private static final String URL = "jdbc:mysql://localhost:3306/bankdb";
    private static final String USER = "root";
    private static final String PASS = "21Shiv@m04";

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n==== BANK ACCOUNT MANAGEMENT ====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Balance");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> depositMoney();
                case 3 -> withdrawMoney();
                case 4 -> viewBalance();
                case 5 -> {
                    System.out.println("Thank you! Exiting system...");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // --------------------------------------------------------
    // DATABASE HANDLING METHODS
    // --------------------------------------------------------

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    private static BankAccount getAccount(String accNo) {
        try (Connection con = connect()) {
            String sql = "SELECT * FROM accounts WHERE accountNumber=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, accNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new BankAccount(
                    rs.getString("accountNumber"),
                    rs.getString("holderName"),
                    rs.getDouble("balance"),
                    rs.getInt("pin")
                );
            }

        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
        }
        return null;
    }

    private static void updateBalance(String accNo, double newBalance) {
        try (Connection con = connect()) {
            String sql = "UPDATE accounts SET balance=? WHERE accountNumber=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, newBalance);
            ps.setString(2, accNo);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
        }
    }

    // --------------------------------------------------------
    // MAIN FEATURES
    // --------------------------------------------------------

    // Create new account
    private static void createAccount() {

        System.out.print("Enter Account Number: ");
        String accNo = sc.next();

        // check database
        if (getAccount(accNo) != null) {
            System.out.println("Account already exists!");
            return;
        }

        System.out.print("Enter Holder Name: ");
        String name = sc.next();

        System.out.print("Enter Initial Deposit: ");
        double amount = sc.nextDouble();

        System.out.print("Set a 4-digit PIN: ");
        int pin = sc.nextInt();

        try (Connection con = connect()) {
            String sql = "INSERT INTO accounts VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, accNo);
            ps.setString(2, name);
            ps.setDouble(3, amount);
            ps.setInt(4, pin);

            ps.executeUpdate();
            System.out.println("Account created successfully!");

        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
        }
    }

    // Deposit money
    private static void depositMoney() {
        BankAccount account = verifyAccount();
        if (account == null) return;

        System.out.print("Enter amount to deposit: ");
        double amt = sc.nextDouble();

        if (amt <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        double newBal = account.getBalance() + amt;
        updateBalance(account.getAccountNumber(), newBal);

        System.out.println("₹" + amt + " deposited successfully.");
    }

    // Withdraw money
    private static void withdrawMoney() {
        BankAccount account = verifyAccount();
        if (account == null) return;

        System.out.print("Enter amount to withdraw: ");
        double amt = sc.nextDouble();

        if (amt <= 0 || amt > account.getBalance()) {
            System.out.println("Insufficient balance or invalid amount!");
            return;
        }

        double newBal = account.getBalance() - amt;
        updateBalance(account.getAccountNumber(), newBal);

        System.out.println("₹" + amt + " withdrawn successfully.");
    }

    // View balance
    private static void viewBalance() {
        BankAccount account = verifyAccount();
        if (account == null) return;

        System.out.println("Your current balance: ₹" + account.getBalance());
    }

    // Verify account + PIN
    private static BankAccount verifyAccount() {
        System.out.print("Enter Account Number: ");
        String accNo = sc.next();

        BankAccount account = getAccount(accNo);

        if (account == null) {
            System.out.println("Account not found!");
            return null;
        }

        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();

        if (pin != account.getPin()) {
            System.out.println("Incorrect PIN!");
            return null;
        }

        return account;
    }
}