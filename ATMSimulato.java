import java.util.Scanner;

class Account {
    private double balance;
    private final int pin;

    public Account(double balance, int pin) {
        this.balance = balance;
        this.pin = pin;
    }

    public boolean verifyPin(int enteredPin) {
        return this.pin == enteredPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class ATM {
    private Account account;
    private boolean sessionActive;

    public ATM(Account account) {
        this.account = account;
        this.sessionActive = false;
    }

    public void startSession() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter PIN: ");
        int enteredPin = sc.nextInt();

        if (account.verifyPin(enteredPin)) {
            System.out.println("Login successful!");
            sessionActive = true;
            showMenu();
        } 
        else {
            System.out.println("Incorrect PIN. Session terminated.");
        }
    }

    private void showMenu() {
        Scanner sc = new Scanner(System.in);

        while (sessionActive) {
            System.out.println("\n--- ATM MENU ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1-> System.out.println("Your balance: ₹" + account.getBalance());
                case 2-> {
                    System.out.print("Enter deposit amount: ");
                    double depAmt = sc.nextDouble();
                    account.deposit(depAmt);
                    System.out.println("Amount deposited successfully.");
                }
                case 3-> {
                    System.out.print("Enter withdrawal amount: ");
                    double wAmt = sc.nextDouble();

                    if (account.withdraw(wAmt)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                }
                case 4-> {
                    System.out.println("Exiting session...");
                    sessionActive = false;
                }
                default->
                    System.out.println("Invalid choice.");
            }
        }
    }
}

public class ATMSimulato {
    public static void main(String[] args) {

        Account userAccount = new Account(5000.0, 1234);

        ATM atm = new ATM(userAccount);

        atm.startSession();
    }
}