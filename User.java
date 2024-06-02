import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class User {
    private String userId;
    private String userPin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String userId, String userPin, double balance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean validatePin(String pin) {
        return this.userPin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
            return true;
        } else {
            return false;
        }
    }

    public boolean transfer(User recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add("Transferred: $" + amount + " to " + recipient.getUserId());
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }
}

class ATM {
    private HashMap<String, User> users;
    private User currentUser;
    private Scanner scanner;

    public ATM() {
        users = new HashMap<>();
        scanner = new Scanner(System.in);
        // Adding some dummy users
        users.put("user1", new User("user1", "1111", 1000.0));
        users.put("user2", new User("user2", "2222", 1500.0));
    }

    public void start() {
        while (true) {
            System.out.println("Welcome to the ATM");
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter PIN: ");
            String userPin = scanner.nextLine();

            if (authenticateUser(userId, userPin)) {
                showMenu();
            } else {
                System.out.println("Invalid User ID or PIN. Please try again.");
            }
        }
    }

    private boolean authenticateUser(String userId, String userPin) {
        User user = users.get(userId);
        if (user != null && user.validatePin(userPin)) {
            currentUser = user;
            return true;
        } else {
            return false;
        }
    }

    private void showMenu() {
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    showTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (String transaction : currentUser.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (currentUser.withdraw(amount)) {
            System.out.println("Withdrawal successful. Please take your cash.");
        } else {
            System.out.println("Insufficient balance. Transaction failed.");
        }
    }

    private void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = Double.parseDouble(scanner.nextLine());
        currentUser.deposit(amount);
        System.out.println("Deposit successful.");
    }

    private void transfer() {
        System.out.print("Enter recipient User ID: ");
        String recipientId = scanner.nextLine();
        User recipient = users.get(recipientId);

        if (recipient == null) {
            System.out.println("Recipient User ID not found. Transaction failed.");
            return;
        }

        System.out.print("Enter amount to transfer: ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (currentUser.transfer(recipient, amount)) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient balance. Transaction failed.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}


