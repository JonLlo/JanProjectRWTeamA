import java.util.Scanner;

public class BankSystem {

    private Scanner inputScanner = new Scanner(System.in);

    public BankSystem() {
        System.out.println("\n=== Bank System Initialized ===");
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("========================================");
            System.out.println("       ACME BANK System");
            System.out.println("========================================");
            System.out.println("Please select an option:");
            System.out.println("1. Authenticate Customer");
            System.out.println("2. Create New Customer");
            System.out.println("3. Help");
            System.out.println("4. Exit");
            System.out.print("Enter Option number: ");

            String menuChoice = inputScanner.nextLine();

            switch (menuChoice) {
                case "1":
                    authenticateCustomer();
                    break;
                case "2":
                    createCustomer();
                    break;
                case "3":
                    showMainHelp();
                    break;
                case "4":
                    System.out.println("Exiting the system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void authenticateCustomer() {
        System.out.println("authenticateCustomer() called");
        // Stub: would normally check login
        customerMenu(); // demo navigation
    }

    private void createCustomer() {
        System.out.println("createCustomer() called");
        // Stub: normally get input and create Customer object
        System.out.print("Enter Customer ID: ");
        String customerId = inputScanner.nextLine();
        System.out.print("Enter Customer Name: ");
        String customerName = inputScanner.nextLine();

        Logger.log("NEW CUSTOMER CREATED: " + customerId);
        System.out.println("Customer created successfully (stub).");
    }

    private void showMainHelp() {
        System.out.println("showMainHelp() called");
        System.out.println("This is a stub help menu for ACME BANK System.");
    }

    private void customerMenu() {
        boolean stayInCustomerMenu = true;
        while (stayInCustomerMenu) {
            System.out.println("\n=== CUSTOMER MENU ===");
            System.out.println("1. View Accounts");
            System.out.println("2. Open Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Direct Debit / Standing Order");
            System.out.println("6. Help");
            System.out.println("7. Switch Customer");
            System.out.print("Enter Option number: ");

            String choice = inputScanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("View Accounts stub");
                    break;
                case "2":
                    System.out.println("Open Account stub");
                    break;
                case "3":
                    System.out.println("Deposit stub");
                    break;
                case "4":
                    System.out.println("Withdraw stub");
                    break;
                case "5":
                    System.out.println("Manage Payments stub");
                    break;
                case "6":
                    System.out.println("Customer Help stub");
                    break;
                case "7":
                    System.out.println("Returning to main menu...");
                    stayInCustomerMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Stubs for future functionality
    private void openNewAccount() {}
    private void performDeposit() {}
    private void performWithdrawal() {}
    private void managePayments() {}

    public static void main(String[] args) {
        BankSystem bankSystem = new BankSystem();
        bankSystem.start();
    }
}
