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
                case "3": showMainHelp(); break;
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
            IO.println("Customer ID does not exist.");

            Logger.log("Failed authentication: " + customerId);  //Will only work once logger class is created

        }
        else {
            this.loggedInCustomer = this.customerMap.get(customerId); //will only work once customer class is created
            Logger.log("Success: " + customerId); //Will only work once logger class is created
            this.customerMenu();


        }




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

    }
    private void showMainHelp() {
       /* Here we want to write a function to show main help. This is called when someone
        types '3' on the start menu. It also, as per the brief, needs to be called anytime someone types 'help'
        ANYWHERE in the application*/

        IO.println("\n=== HELP: MAIN MENU ===");
        IO.println("1. Authenticate Customer - Log in using a valid Customer ID.");
        IO.println("2. Create Customer      - Add a new customer with unique ID.");
        IO.println("3. Help                 - Show instructions and rules.");
        IO.println("4. Exit                 - Save all data and quit the program.");
        IO.println("\nNotes:");
        IO.println("- Customer IDs must be unique.");
        IO.println("- Data is saved automatically on exit.");

    }
    private String helpOnInput() {
        //makes it so any time in the application if someone types help, the help menu comes up.
        String input = inputScanner.nextLine();
        if (input.equalsIgnoreCase("help")) {
            showMainHelp();  // show the main help
            return helpOnInput(); // recursively ask again
        }
        return input;
/*
            //We need to replace every instance of: "scanner" with String xyz = helpOnInput()
*/
    }
    private void showCustomerHelp() {
        IO.println("\n=== HELP: CUSTOMER MENU ===");
        IO.println("1. View Accounts   - List all accounts for this customer.");
        IO.println("2. Open Account    - Create a new account. Rules:");
        IO.println("    • Personal Account: Minimum £1 opening balance, multiple allowed.");
        IO.println("    • ISA Account: Only 1 per customer.");
        IO.println("    • Business Account: Only 1 per customer, restricted types.");
        IO.println("3. Deposit         - Add funds to a selected account. Amount must be > 0.");
        IO.println("4. Withdraw        - Remove funds from a selected account. Cannot exceed balance.");
        IO.println("5. Help            - Show this help message.");
        IO.println("6. Switch Customer - Log out and authenticate a different customer.");
        IO.println("\nNotes:");
        IO.println("- All transactions are logged with timestamps.");
        IO.println("- Account numbers are unique and required for selecting accounts.");
    }
    private void saveDataToCSV() {
        /*Here we want to write a function that saves all the data to the CSV (essentially the hashmap but in plain text).
        And we want to run this just before the program is exited (when someone types '4')
         */}
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

                //done but need to uncomment functions once they are made.
                //below are all functions we need to define later on
                case "1": //loggedInCustomer.viewAccounts() //break;
                    //"ViewAccounts" method needs to be defined in customer class.
                case "2": //openNewAccount(); break;
                case "3":   performDeposit(); break;
                case "4":   performWithdrawal(); break;
                case "5": //managePayments(); break;
                case "6": showCustomerHelp(); break;
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

        /*
         * Stuff that needs to exist for openNewAccount() to work:
         *
         * 1) Account classes:
         *    - A base Account class (doesn’t have to be abstract, but could be).
         *    - PersonalAccount, IsaAccount, and BusinessAccount all extend Account.
         *    - BusinessAccount needs a constructor that takes a String for the business type.
         *
         * 2) Customer stuff:
         *    - Customer has a way to keep track of accounts (like a Map<String, Account>).
         *    - Customer needs hasISA() and hasBusiness() to check if those accounts exist.
         *    - Customer needs addAccount(...) to actually store new accounts.
         *
         * 3) Constants:
         *    - ALLOWED_BUSINESS_TYPES should exist as a Set<String> with "SOLE_TRADER" and "LIMITED".
         *
         * 4) IO helper:
         *    - IO.println() and IO.print() for console messages.
         *
         */

        IO.println("1. Personal  2. ISA  3. Business");
        String accountChoice = this.inputScanner.nextLine();
        Account newAccount = null;
        if (accountChoice.equals("1")) {
            newAccount = new PersonalAccount();
            newAccount.balance = (double) 1.0F;
        } else if (accountChoice.equals("2")) {
            if (this.loggedInCustomer.hasISA()) {
                IO.println("ISA already exists.");
                return;
            }

            newAccount = new IsaAccount();
        } else {
            if (!accountChoice.equals("3")) {
                IO.println("Invalid account type.");
                return;
            }

            if (this.loggedInCustomer.hasBusiness()) {
                IO.println("Business account already exists.");
                return;
            }

            IO.println("Enter business type (SOLE_TRADER / LIMITED): ");
            String businessType = this.inputScanner.nextLine().toUpperCase();
            if (!ALLOWED_BUSINESS_TYPES.contains(businessType)) {
                IO.println("Business type not supported.");
                IO.println("Allowed types: SOLE_TRADER, LIMITED");
                return;
            }

            newAccount = new BusinessAccount(businessType);


        } }
    private void performDeposit () {
        /*
         - here we define a function to put down a deposit.
         - Must call methods that will be in the account class,
           namely putting down a deposit on a particular account
         - Note diff types of account (ISA, business)
         - called when user types "3" when on customer menu */
        if (this.loggedInCustomer.getAccounts().isEmpty()) {
            IO.println("No accounts available.");
        } else {
            IO.println("Available accounts:");

            for (Account account : this.loggedInCustomer.getAccounts().values()) {
                IO.println(account.toString());
            }

            IO.print("Enter account number to deposit into: ");
            String enteredAccountNumber = this.inputScanner.nextLine();
            Account selectedAccount = this.loggedInCustomer.getAccount(enteredAccountNumber);
            if (selectedAccount == null) {
                IO.println("Account not found.");
            } else {
                IO.print("Enter deposit amount: ");

                double amount;
                try {
                    amount = Double.parseDouble(this.inputScanner.nextLine());
                } catch (NumberFormatException var6) {
                    IO.println("Invalid amount.");
                    return;
                }

                selectedAccount.deposit(amount);
                IO.println("Deposit successful.");
                Object[] var10001 = new Object[]{selectedAccount.balance};
                IO.println("Updated balance: " + String.format("£%.2f", var10001));
                this.saveDataToCSV();
                Logger.log("DEPOSIT £" + amount + " INTO " + selectedAccount.getAccountNumber());
            }
        }
    }
    private void performWithdrawal () {
        /*
         - Similar process to the 'performDeposit' function
         - called when user types "4" when on customer menu */

        if (this.loggedInCustomer.getAccounts().isEmpty()) {
            IO.println("No accounts available.");
        } else {
            IO.println("Available accounts:");

            for (Account account : this.loggedInCustomer.getAccounts().values()) {
                IO.println(account.toString());
            }

            IO.print("Enter account number to withdraw from: ");
            String enteredAccountNumber = this.inputScanner.nextLine();
            Account selectedAccount = this.loggedInCustomer.getAccount(enteredAccountNumber);
            if (selectedAccount == null) {
                IO.println("Account not found.");
            } else {
                IO.print("Enter withdrawal amount: ");

                double amount;
                try {
                    amount = Double.parseDouble(this.inputScanner.nextLine());
                } catch (NumberFormatException var6) {
                    IO.println("Invalid amount.");
                    return;
                }

                selectedAccount.withdraw(amount);
                Object[] var10001 = new Object[]{selectedAccount.balance};
                IO.println("Updated balance: " + String.format("£%.2f", var10001));
                this.saveDataToCSV();
                Logger.log("WITHDRAW £" + amount + " FROM " + selectedAccount.getAccountNumber());
            }
        }
    }
    private void managePayments () {
     /*
         - here we define a function to manage payments.
         - will be dependent on an account class and a customer class.
         - Note diff types of account (personal, ISA, business)
         - called when user types "2" when on customer menu

         */
        IO.print("Enter account number: ");
        String accountNumber = inputScanner.nextLine();
        /* Get the account number */

        /* Check if account exists */
        /* If account is not in personal accounts, exit function*/


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
            case "5":
                return;
            default:
                IO.println("Invalid choice.");
        }
    }
    private void addDirectDebit (PersonalAccount account){
        /* Requires input validation */
        String debitName = "";

        while (debitName.trim().isEmpty()) {
            IO.print("Enter Debit name: ");
            debitName = inputScanner.nextLine();

            if (debitName.trim().isEmpty()) {
                IO.println("Debit name cannot be empty.");
            }
        }

        double debitAmount = 0;
        while (debitAmount <= 0) {
            IO.print("Enter Debit amount (£): ");

            try {
                debitAmount = Double.parseDouble(inputScanner.nextLine());
                if (debitAmount <= 0) {
                    IO.println("Error: Debit amount must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                IO.println("Error: Please enter a valid amount.");
            }
        }

        /* Are you sure? */
        IO.print("You are about to add a Direct Debit of " + debitAmount + " to " + debitName + ". Are you sure? (y/n): ");
        String confirmation = inputScanner.nextLine();

        if (!confirmation.equalsIgnoreCase("y")) {
            IO.println("Direct Debit cancelled.");
            return;
        }

        account.addDirectDebit(new DirectDebit(debitName, debitAmount));
        saveDataToCSV();
        Logger.log("Direct Debit added: " + debitName + " £" + debitAmount);
    }
    private void addStandingOrder (PersonalAccount account){
        /* Requires input validation */
        String orderName = "";
        while (orderName.trim().isEmpty()) {
            IO.print("Enter Standing Order name: ");
            orderName = inputScanner.nextLine();
            if (orderName.trim().isEmpty()) {
                IO.println("Standing Order name cannot be empty.");
            }
        }

        double orderAmount = 0;
        while (orderAmount <= 0) {
            IO.print("Enter Standing Order amount (£): ");
            try {
                orderAmount = Double.parseDouble(inputScanner.nextLine());
                if (orderAmount <= 0) {
                    IO.println("Error: Amount must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                IO.println("Error: Please enter a valid amount.");
            }
        }
        /* Are you sure? */
        IO.print("You are about to add a Standing Order of " + orderAmount + " to " + orderName + ". Are you sure? (y/n): ");
        String confirmation = inputScanner.nextLine();

        if (!confirmation.equalsIgnoreCase("y")) {
            IO.println("Standing Order cancelled.");
            return;
        }

        account.addStandingOrder(new StandingOrder(orderName, orderAmount));
        saveDataToCSV();
        Logger.log("Standing Order added: " + orderName + " £" + orderAmount);
    }


}