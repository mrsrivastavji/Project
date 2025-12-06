import java.util.*;

class BankAccount {
    private String accountNumber;
    private String holderName;
    private double balance;
    private int pin;

    public BankAccount(String accountNumber, String holderName, double balance, int pin) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.pin = pin;
    }

    public boolean verifyPin(int enteredPin) {
        return this.pin == enteredPin;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("₹" + amount + " deposited successfully.");
        } else {
            System.out.println("Invalid amount!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("₹" + amount + " withdrawn successfully.");
        } else {
            System.out.println("Insufficient balance or invalid amount!");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

public class BankSystem {
    private static Map<String, BankAccount> accounts = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);

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

    // Create new account
    private static void createAccount() {
        System.out.print("Enter Account Number: ");
        String accNo = sc.next();

        if (accounts.containsKey(accNo)) {
            System.out.println("Account already exists!");
            return;
        }

        System.out.print("Enter Holder Name: ");
        String name = sc.next();

        System.out.print("Enter Initial Deposit: ");
        double amount = sc.nextDouble();

        System.out.print("Set a 4-digit PIN: ");
        int pin = sc.nextInt();

        BankAccount account = new BankAccount(accNo, name, amount, pin);
        accounts.put(accNo, account);

        System.out.println("Account created successfully!");
    }

    // Deposit money
    private static void depositMoney() {
        BankAccount account = findAccount();
        if (account == null) return;

        System.out.print("Enter amount to deposit: ");
        double amt = sc.nextDouble();
        account.deposit(amt);
    }

    // Withdraw money
    private static void withdrawMoney() {
        BankAccount account = findAccount();
        if (account == null) return;

        System.out.print("Enter amount to withdraw: ");
        double amt = sc.nextDouble();
        account.withdraw(amt);
    }

    // View balance
    private static void viewBalance() {
        BankAccount account = findAccount();
        if (account == null) return;

        System.out.println("Your current balance: ₹" + account.getBalance());
    }

    // Helper: verify account + PIN
    private static BankAccount findAccount() {
        System.out.print("Enter Account Number: ");
        String accNo = sc.next();

        BankAccount account = accounts.get(accNo);

        if (account == null) {
            System.out.println("Account not found!");
            return null;
        }

        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();

        if (!account.verifyPin(pin)) {
            System.out.println("Incorrect PIN!");
            return null;
        }

        return account;
    }
}
