
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class BankSystem {
    // private Customer loggedInCustomer;  //Here we must define a 'customer' class.
    // private Map<String, Customer> customerMap = new HashMap<>(); //hash map to log users. Also relies on a 'customer' class.
    private Scanner inputScanner = new Scanner(System.in); //Scanner for "start()" method
    /*The following constructor "public BankSystem()" will run whenever a new object of the class BankSystem is created:
    i.e. when "BankSystem bank = new BankSystem();" is called */
    public BankSystem() {
         //IO.println("\n=== Bank System has been called! ===");

    }
    public void start() {
        boolean running = true;
        while (running) {
        IO.println("========================================\n" +
                "       ACME BANK System\n" +
                "========================================");
        IO.println("Please select an option:");
        IO.println("1. Authenticate Customer");
        IO.println("2. Create New Customer");
        IO.println("3. Help");
        IO.println("4. Exit");

        IO.println("Tip: You can type help at any time for assistance.");

        IO.print("Enter Option number: ");

        String menuChoice = inputScanner.nextLine();

        switch (menuChoice) {
            case "1": authenticateCustomer(); break;
            case "2": createCustomer(); break;
            case "3": IO.println("showMainHelp()"); break;
            case "4": IO.println("saveDataToCSV()"); running = false; break;
            default: IO.println("Invalid choice.");
        }








    }
}
    private void authenticateCustomer() {

        //Jonny
        IO.println("Please enter your customer ID");
        String customerID = inputScanner.nextLine();
        if (!this.customerMap.containsKey(customerId)) {
            IO.println("Customer ID does not exist.")
            //Logger.log("Failed authentication: " + customerId) Wait for logger class to be created to uncomment this

        }
        else {
            //Not quite sure which of the below two is needed.
            this.loggedInCustomer = this.customerMap.get(customerId)
            //this.loggedInCustomer = (Customer)this.customerMap.get(customerId);
            //Logger.log("Success: " + customerId) Wait for logger class to be created to uncomment this

            this.customerMenu();


        }


        //if hashmap contains customerID {


    }
        /* Here we want to write a function to authenticate a customer. This is called when someone
            types '1' on the start menu */
        /* We will then want to call a function "CustomerMenu()" which will take user to the customer menu */





        //some code then:
        //CustomerMenu()

    }
    private void createCustomer() {
        /* Here we want to write a function to create a customer. This is called when someone
        types '2' on the start menu */

        IO.print("Enter Customer ID: ");
        String customerId = inputScanner.nextLine();

        if (customerMap.containsKey(customerId)) {
        if (customerMap.containsKey(customerId)) {
            IO.println("Customer ID already exists.");
            return;
        }

        IO.print("Enter Customer Name: ");
        String customerName = inputScanner.nextLine();

        customerMap.put(customerId, new Customer(customerId, customerName));
        Logger.log("NEW CUSTOMER CREATED: " + customerId);

        IO.println("Customer created successfully.");
        saveDataToCSV();
    }
    private void showMainHelp() {
       /* Here we want to write a function to create a customer. This is called when someone
        types '3' on the start menu. It also, as per the brief, needs to be called anytime someone types 'help'
        ANYWHERE in the application*/
    }
    private void saveDataToCSV() {
        /*Here we want to write a function that saves all the data to the CSV (essentially the hashmap but in plain text).
        And we want to run this just before the program is exited (when someone types '4')
         */

    }

    private void customerMenu() {
        boolean stayInCustomerMenu = true;
        while (stayInCustomerMenu) {
            IO.println("\n=== CUSTOMER MENU ===");
            IO.println("1. View Accounts");
            IO.println("2. Open Account");
            IO.println("3. Deposit");
            IO.println("4. Withdraw");
            IO.println("5. Direct Debit / Standing Order");
            IO.println("6. Help");
            IO.println("7. Switch Customer");


            String choice = inputScanner.nextLine();

            switch (choice) {
                //below are all functions we need to define later on
                case "1": //loggedInCustomer.viewAccounts() //break;
                    //"ViewAccounts" method needs to be defined in customer class.
                case "2": //openNewAccount(); break;
                case "3": //performDeposit(); break;
                case "4": //performWithdrawal(); break;
                case "5": //managePayments(); break;
                case "6": //showCustomerHelp(); break;
                case "7": stayInCustomerMenu = false; break;
                default: IO.println("Invalid choice.");
            }
        }
    }
    private void openNewAccount() {
        /*
         - here we define a function to open a new account.
         - will be dependent on an account class and a customer class.
         - Note diff types of account (ISA, business)
         - called when user types "2" when on customer menu

         */
    }
    private void performDeposit() {
        /*
         - here we define a function to put down a deposit.
         - Must call methods that will be in the account class,
           namely putting down a deposit on a particular account
         - Note diff types of account (ISA, business)
         - called when user types "3" when on customer menu */
    }
    private void performWithdrawal() {
        /*
         - Similar process to the 'performDeposit' function
         - called when user types "4" when on customer menu */
    }
    private void managePayments() {
     /*
         - here we define a function to manage payments.
         - will be dependent on an account class and a customer class.
         - Note diff types of account (personal, ISA, business)
         - called when user types "2" when on customer menu

         */
        IO.print("Enter account number: ");
        /* now write some code to check that account is a PERSONAL Account,
         on system and if so print the below:
         */



        IO.println("\n=== DIRECT DEBITS & STANDING ORDERS ===");
        IO.println("1. Add Direct Debit");
        IO.println("2. View Direct Debits");
        IO.println("3. Add Standing Order");
        IO.println("4. View Standing Orders");
        IO.println("5. Back");

        switch (inputScanner.nextLine()) {
            //case "1": addDirectDebit(personalAccount); break;
            //case "2": personalAccount.viewDirectDebits(); break;
            //case "3": addStandingOrder(personalAccount);
            //case "4": personalAccount.viewStandingOrders(); break;
            //note viewDirectDebits() and viewStandingOrders() will rely on the account class.
            //addStandingOrder() and addDirectDebit() will be defined on this document next.
            case "5": return;
            default: IO.println("Invalid choice.");
        }
  /*
        private void addDirectDebit(PersonalAccount account) {
            here we will want to add a direct debit to a given personalAccount}
   */

     /*
    private void addStandingOrder(PersonalAccount account) {
           here we will want to add a standing order to a given personalAccount
    }
    */


    }





}