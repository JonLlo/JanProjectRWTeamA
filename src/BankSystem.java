import java.io.FileWriter;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.io.PrintWriter;
import java.util.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;


public class BankSystem {

   //for help menu:


    enum HelpContext {
        MAIN_MENU,
        CUSTOMER_MENU,
        DEPOSIT,
        WITHDRAW,
        OPEN_ACCOUNT,
        PAYMENTS,
        CHEQUEBOOK
    }

    private HelpContext helpContext = HelpContext.MAIN_MENU;
    //


    private Map<String, Customer> customerMap = new HashMap<>();
    private Scanner inputScanner = new Scanner(System.in);
    private Customer loggedInCustomer;
    private static final Set<String> ALLOWED_BUSINESS_TYPES =
            Set.of("SOLE_TRADER", "LIMITED");
    private static final String DATA_FILE_NAME = "bank_data.csv";

    public BankSystem() {
        loadDataFromCSV();

        for (Customer customer : customerMap.values()) {
            for (Account account : customer.getAccounts().values()) {
                account.applyAnnualInterest();
                account.applyAnnualFee();
            }
        }
    }
    public void start() {
        helpContext = HelpContext.MAIN_MENU;
        boolean running = true;
        while (running) {
            IO.println("=== MAIN MENU ===");
            IO.println("Please select an option:");
            IO.println("1. Authenticate Customer");
            IO.println("2. Create New Customer");
            IO.println("3. Help");
            IO.println("4. Exit");

            IO.println("Tip: You can type help at any time for assistance.");

            IO.print("Enter Option number: ");

            String menuChoice = helpOnInput();

            switch (menuChoice) {
                case "1": authenticateCustomer(); break;
                case "2": createCustomer(); break;
                case "3": showMainHelp(); break;
                case "4": IO.println("saveDataToCSV()"); running = false; break;
                //default: IO.println("Invalid choice 2.");
            }

        }
    }
    private void authenticateCustomer() {
        //Jonny
        helpContext = HelpContext.CUSTOMER_MENU;
        IO.println("Please enter your customer ID:");
        String customerId = helpOnInput();
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
        IO.print("Enter Customer ID: ");
        String customerId = helpOnInput();

        if (customerMap.containsKey(customerId)) {
            IO.println("Customer ID already exists.");
            return;
        }

        IO.print("Enter Customer Name: ");
        String customerName = helpOnInput();

        customerMap.put(customerId, new Customer(customerId, customerName));
        Logger.log("NEW CUSTOMER CREATED: " + customerId);
        IO.println("Customer created successfully.");
        saveDataToCSV();
    }
    private String helpOnInput() {
        while (true) {
            String input = inputScanner.nextLine().trim();

            if (input.equalsIgnoreCase("help")) {
                // Show the correct help screen
                switch (helpContext) {
                    case MAIN_MENU -> showMainHelp();
                    case CUSTOMER_MENU -> showCustomerHelp();
                    case DEPOSIT -> showDepositHelp();
                    case WITHDRAW -> showWithdrawalHelp();
                    case CHEQUEBOOK -> showChequeBookHelp();
                    case OPEN_ACCOUNT -> showOpenAccountHelp();
                    case PAYMENTS -> showPaymentsHelp();
                }

                // Pause **only after showing help**


                // Loop back to ask for input again

            }



            // Normal input — return immediately
            return input;
        }
    }


    private void showMainHelp() {
        IO.println("\n=== HELP: MAIN MENU ===");
        IO.println("1. Authenticate Customer - Log in using a valid Customer ID.");
        IO.println("2. Create Customer      - Add a new customer with unique ID.");
        IO.println("3. Help                 - Show instructions and rules.");
        IO.println("4. Exit                 - Save all data and quit the program.");
        IO.println("\nNotes:");
        IO.println("- Customer IDs must be unique.");
        IO.println("- Data is saved automatically on exit.");

        IO.println("\nPress Enter to return...");
        inputScanner.nextLine();



    }
    private void showPaymentsHelp() {
        IO.println("\n=== HELP: DIRECT DEBITS & STANDING ORDERS ===");
        IO.println("- Add/View Direct Debits: only positive amounts, requires a name.");
        IO.println("- Add/View Standing Orders: only positive amounts, requires a name.");

        IO.println("\nPress Enter to return...");
        inputScanner.nextLine();

    }
    private void showOpenAccountHelp() {
        IO.println("\n=== HELP: OPEN ACCOUNT ===");
        IO.println("- Personal Account: min £1, multiple allowed, requires ID validation.");
        IO.println("- ISA Account: only 1 per customer, interest at 2.75% APR.");
        IO.println("- Business Account: only 1 per customer, type must be SOLE_TRADER or LIMITED, £120 annual fee.");

        IO.println("\nPress Enter to return...");
        inputScanner.nextLine();


    }
    private void showChequeBookHelp() {
        IO.println("\n=== HELP: CHEQUE BOOKS AND LOANS (BUSINESS ACCOUNTS ONLY) ===");
        IO.println("- Only business accounts are eligible for a cheque books or loans.");
        IO.println("- Each business account can receive only one cheque book.");
        IO.println("- To request a cheque book or loan, enter the account number of the eligible business account when prompted.");

        IO.println("\nPress Enter to return...");
        inputScanner.nextLine();
    }

    private void showDepositHelp() {
        IO.println("\n=== HELP: DEPOSIT ===");
        IO.println("- Select an account by account number.");
        IO.println("- Deposit amount must be greater than £0.");
        IO.println("- Only numeric values are accepted.");

        IO.println("\nPress Enter to return...");
        inputScanner.nextLine();


    }
    private void showWithdrawalHelp() {
        IO.println("\n=== HELP: WITHDRAW ===");
        IO.println("- Withdrawal amount must be greater than £0.");
        IO.println("- Amount cannot exceed available balance.");

        IO.println("\nPress Enter to return...");
        inputScanner.nextLine();



    }
    // added saveDataToCSV and loadDataFromCSV() to this section
    private void saveDataToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE_NAME))) {

            for (Customer customer : customerMap.values()) {

                // CUSTOMER
                writer.println("C," + customer.getId() + "," + customer.getName());

                for (Account account : customer.getAccounts().values()) {

                    // ACCOUNT TYPE
                    String type = "P";
                    if (account instanceof IsaAccount) {
                        type = "I";
                    } else if (account instanceof BusinessAccount) {
                        type = "B";
                    }

                    // LAST BUSINESS FEE DATE
                    String lastFeeDate = "";
                    if (account instanceof BusinessAccount) {
                        LocalDate d = ((BusinessAccount) account).getLastFeeAppliedDate();
                        if (d != null) {
                            lastFeeDate = d.toString();
                        }
                    }

                    // ACCOUNT LINE
                    if (account instanceof BusinessAccount ba) {
                        writer.println("A," +
                                customer.getId() + "," +
                                type + "," +
                                account.getAccountNumber() + "," +
                                account.sortCode + "," +
                                String.format("%.2f", account.balance) + "," +
                                lastFeeDate + "," +
                                ba.hasLoan() + "," +
                                ba.getLoanAmount() + "," +
                                ba.hasChequeBook());
                    } else {
                        writer.println("A," +
                                customer.getId() + "," +
                                type + "," +
                                account.getAccountNumber() + "," +
                                account.sortCode + "," +
                                String.format("%.2f", account.balance) + "," +
                                lastFeeDate);
                    }

                    // DIRECT DEBITS
                    for (DirectDebit dd : account.getDirectDebits()) {
                        writer.println("D," +
                                customer.getId() + "," +
                                account.getAccountNumber() + "," +
                                dd.getPayee() + "," +
                                String.format("%.2f", dd.getAmount()));
                    }

                    // STANDING ORDERS
                    for (StandingOrder so : account.getStandingOrders()) {
                        writer.println("S," +
                                customer.getId() + "," +
                                account.getAccountNumber() + "," +
                                so.getPayee() + "," +
                                String.format("%.2f", so.getAmount()));
                    }

                    // INTERNATIONAL PAYMENTS (BUSINESS ONLY)
                    if (account instanceof BusinessAccount ba) {
                        for (InternationalPayment ip : ba.getInternationalPayments()) {
                            writer.println("IP," +
                                    customer.getId() + "," +
                                    account.getAccountNumber() + "," +
                                    String.format("%.2f", ip.getForeignAmount()) + "," +
                                    String.format("%.4f", ip.getExchangeRate()));
                        }
                    }
                }
            }

            IO.println("CSV data saved.");

        } catch (Exception e) {
            IO.println("Error saving CSV.");
        }
    }

    private void loadDataFromCSV() {
        File file = new File(BankSystem.DATA_FILE_NAME);
        if (!file.exists()) return;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) continue;
                String[] p = line.split(",");

                switch (p[0]) {
                    case "C":
                        customerMap.put(p[1], new Customer(p[1], p[2]));
                        break;
                    case "A": {
                        Customer customer = customerMap.get(p[1]);
                        if (customer == null) break;
                        Account account = null;
                        String lastFeeDate = p.length > 6 ? p[6] : "";
                        switch (p[2]) {
                            case "P":
                                account = new PersonalAccount();
                                break;
                            case "I":
                                account = new IsaAccount();
                                break;
                            case "B":
                                account = new BusinessAccount();
                                if (!lastFeeDate.isEmpty()) {
                                    ((BusinessAccount) account).setLastFeeAppliedDate(LocalDate.parse(lastFeeDate));
                                }
                                // NEW: load loan and cheque book info if present
                                if (p.length > 8) { // loan info
                                    ((BusinessAccount) account).setLoanActive(Boolean.parseBoolean(p[7]));
                                    ((BusinessAccount) account).setLoanAmount(Double.parseDouble(p[8]));
                                }
                                if (p.length > 9) { // cheque book
                                    ((BusinessAccount) account).setChequeBookIssued(Boolean.parseBoolean(p[9]));
                                }
                        }

                        if (account == null) break;
                        account.accountNumber = p[3];
                        account.sortCode = p[4];
                        account.balance = Double.parseDouble(p[5]);
                        AccountNumberGenerator.registerExisting(p[3]);
                        customer.addAccount(account);
                        break;
                    }
                    case "D": {
                        Customer customer = customerMap.get(p[1]);
                        if (customer == null) break;
                        Account account = customer.getAccount(p[2]);
                        if (account instanceof PersonalAccount) {
                            account.loadDirectDebit(
                                    new DirectDebit(p[3], Double.parseDouble(p[4])));
                        }
                        break;
                    }
                    case "S": {
                        Customer customer = customerMap.get(p[1]);
                        if (customer == null) break;
                        Account account = customer.getAccount(p[2]);
                        if (account instanceof PersonalAccount) {
                            account.loadStandingOrder(
                                    new StandingOrder(p[3], Double.parseDouble(p[4])));
                        }
                        break;
                    }
                    case "IP": {
                        Customer customer = customerMap.get(p[1]);
                        if (customer == null) break;

                        Account account = customer.getAccount(p[2]);

                        if (account instanceof BusinessAccount businessAccount) {
                            double foreignAmount = Double.parseDouble(p[3]);
                            double exchangeRate = Double.parseDouble(p[4]);

                            businessAccount.loadInternationalPayment(
                                    new InternationalPayment(foreignAmount, exchangeRate)
                            );
                        }
                        break;
                    }

                }
            }
            IO.println("CSV data loaded.");
        } catch (Exception e) {
            IO.println("Error loading CSV.");
        }
    }
    private void customerMenu() {
        helpContext = HelpContext.CUSTOMER_MENU;
        boolean stayInCustomerMenu = true;
        while (stayInCustomerMenu) {
            IO.println("\n=== CUSTOMER MENU ===");
            IO.println("1. View Accounts");
            IO.println("2. Open Account");
            IO.println("3. Deposit");
            IO.println("4. Withdraw");
            IO.println("5. Direct Debit / Standing Order (Personal Accounts only)");
            IO.println("6. Loans / Cheque Books (Business Accounts only)");
            IO.println("7. International Payment (Business)");
            IO.println("8. Help");
            IO.println("9. Switch Customer");


            String choice = helpOnInput();

            switch (choice) {

                //done but need to uncomment functions once they are made.
                //below are all functions we need to define later on
                case "1": helpContext = HelpContext.OPEN_ACCOUNT;
                          loggedInCustomer.viewAccounts(); break;


                //"ViewAccounts" method needs to be defined in customer class.
                case "2": openNewAccount(); break;
                case "3":  performDeposit(); break;
                case "4":  performWithdrawal(); break;
                case "5": managePayments(); break;
                case "6":
                    IO.println("Do you want to take out a loan or a cheque book?");
                    IO.println("1. Loan");
                    IO.println("2. Cheque Book");
                    IO.print("Enter choice (1 or 2): ");
                    String loanChoice = this.helpOnInput();

                    switch (loanChoice) {
                        case "1":
                            manageLoan();       // Call your loan handling method
                            break;
                        case "2":
                            manageChequeBooks(); // Call your cheque book handling method
                            break;
                        default:
                            IO.println("Invalid choice. Returning to customer menu.");
                    }
                    break;
                case "7": performInternationalPayment(); break;
                case "8": showCustomerHelp(); break;
                case "9": stayInCustomerMenu = false; break;
            }
        }
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
        IO.println("5. Direct Debit/Standing Order            - Created standing order or direct debit. Personal accounts only");
        IO.println("6. Cheque Books            - Issue Cheque Books. Business accounts only");
        IO.println("7. Help            - Show this help message.");
        IO.println("8. Switch Customer - Log out and authenticate a different customer.");
        IO.println("\nNotes:");
        IO.println("- All transactions are logged with timestamps.");
        IO.println("- Account numbers are unique and required for selecting accounts.");

        IO.println("\nPress Enter to return...");
        inputScanner.nextLine();

    }
    private void openNewAccount() {
        helpContext = HelpContext.OPEN_ACCOUNT;  // before opening account
        IO.println("1. Personal  2. ISA  3. Business");
        String accountChoice = this.inputScanner.nextLine();
        Account newAccount = null;
        if (accountChoice.equals("1")) {
            IO.println("To open a Personal Account, you must have:");

            IO.print("Do you have a Photo ID (passport or driving licence)? (y/n): ");
            String photoId = helpOnInput();
            if (!photoId.equalsIgnoreCase("y")) {
                IO.println("Cannot open Personal Account without Photo ID. Account creation cancelled.");
                return;
            }

            IO.print("Do you have an Address ID (utility bill or council tax letter)? (y/n): ");
            String addressId = helpOnInput();
            if (!addressId.equalsIgnoreCase("y")) {
                IO.println("Cannot open Personal Account without Address ID. Account creation cancelled.");
                return;
            }

            // Ask for opening balance ≥ £1
            double openingBalance = 0.0;
            while (openingBalance < 1.0) {
                IO.print("Enter opening balance (£, minimum £1): ");
                try {
                    openingBalance = Double.parseDouble(helpOnInput());
                } catch (NumberFormatException e) {
                    IO.println("Invalid input. Please enter a number.");
                }
                if (openingBalance < 1.0) {
                    IO.println("Opening balance must be at least £1.");
                }
            }

            // Create the account
            newAccount = new PersonalAccount();
            newAccount.balance = openingBalance;
        }
        else if (accountChoice.equals("2")) {
            if (this.loggedInCustomer.hasISA()) {
                IO.println("ISA already exists.");
                return;
            }

            newAccount = new IsaAccount(); }
        else {
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

            newAccount = new BusinessAccount();
        }

        newAccount.assignIdentifiers();
        this.loggedInCustomer.addAccount(newAccount);
        Logger.log("ACCOUNT CREATED: " + newAccount.getAccountNumber());
        IO.println("Account created: " + newAccount.getAccountNumber());
        this.saveDataToCSV();
    }

    private void performInternationalPayment() {

        IO.print("Enter business account number: ");
        String accountNumber = inputScanner.nextLine();

        Account account = loggedInCustomer.getAccount(accountNumber);

        if (!(account instanceof BusinessAccount businessAccount)) {
            IO.println("International payments are only available for Business Accounts.");
            return;
        }

        IO.print("Enter foreign amount: ");
        double foreignAmount;
        try {
            foreignAmount = Double.parseDouble(inputScanner.nextLine());
        } catch (NumberFormatException e) {
            IO.println("Invalid amount.");
            return;
        }

        IO.print("Enter exchange rate to GBP: ");
        double exchangeRate;
        try {
            exchangeRate = Double.parseDouble(inputScanner.nextLine());
        } catch (NumberFormatException e) {
            IO.println("Invalid exchange rate.");
            return;
        }

        businessAccount.internationalPayment(foreignAmount, exchangeRate);

        saveDataToCSV(); // persist balance change
    }


    private void performDeposit () {
        /*
         - here we define a function to put down a deposit.
         - Must call methods that will be in the account class,
           namely putting down a deposit on a particular account
         - Note diff types of account (ISA, business)
         - called when user types "3" when on customer menu */


        helpContext = HelpContext.DEPOSIT;

        if (this.loggedInCustomer.getAccounts().isEmpty()) {
            IO.println("No accounts available.");
        } else {
            IO.println("Available accounts:");

            for (Account account : this.loggedInCustomer.getAccounts().values()) {
                IO.println(account.toString());
            }

            IO.print("Enter account number to deposit into: ");
            String enteredAccountNumber = this.helpOnInput();
            Account selectedAccount = this.loggedInCustomer.getAccount(enteredAccountNumber);
            if (selectedAccount == null) {
                IO.println("Account not found.");
            } else {

                double amount = 0;
                while (amount <= 0) {
                    IO.print("Enter deposit amount: (£): ");

                    try {
                        amount = Double.parseDouble(helpOnInput());
                        if (amount <= 0) {
                            IO.println("Error: Amount must be greater than zero.");
                        }
                    } catch (NumberFormatException e) {
                        IO.println("Error: Please enter a valid amount.");
                    }
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
        helpContext = HelpContext.WITHDRAW;

        if (this.loggedInCustomer.getAccounts().isEmpty()) {
            IO.println("No accounts available.");
        } else {
            IO.println("Available accounts:");

            for (Account account : this.loggedInCustomer.getAccounts().values()) {
                IO.println(account.toString());
            }

            IO.print("Enter account number to withdraw from: ");
            String enteredAccountNumber = this.helpOnInput();
            Account selectedAccount = this.loggedInCustomer.getAccount(enteredAccountNumber);
            if (selectedAccount == null) {
                IO.println("Account not found.");
            } else {
                IO.print("Enter withdrawal amount: ");

                double amount;
                try {
                    amount = Double.parseDouble(this.helpOnInput());
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
        helpContext = HelpContext.PAYMENTS;      // before managing payments



        if (this.loggedInCustomer.getAccounts().isEmpty()) {
            IO.println("No accounts available.");
        } else {
            IO.println("Available accounts:");

            for (Account account : this.loggedInCustomer.getAccounts().values()) {
                IO.println(account.toString());
            }
            IO.print("Enter account number to withdraw from (personal accounts only): ");

            String accountNumber = helpOnInput();

            Account userAccount = loggedInCustomer.getAccount(accountNumber);

            /* Check if account exists */
            /* If account is not in personal accounts, exit function*/
            if (!(userAccount instanceof PersonalAccount personalAccount)) {
                IO.println("Account not found.");
                return;
            }

            IO.println("\n=== DIRECT DEBITS & STANDING ORDERS ===");
            IO.println("1. Add Direct Debit");
            IO.println("2. View Direct Debits");
            IO.println("3. Add Standing Order");
            IO.println("4. View Standing Orders");
            IO.println("5. Back");

            switch (helpOnInput()) {
                case "1": addDirectDebit(personalAccount); break;
                case "2": personalAccount.viewDirectDebits(); break;
                case "3": addStandingOrder(personalAccount); break;
                case "4": personalAccount.viewStandingOrders(); break;
                case "5": return;
                default: IO.println("Invalid choice 1.");
            }
        }}
    private void manageChequeBooks() {
        helpContext = HelpContext.CHEQUEBOOK;

        if (this.loggedInCustomer.getAccounts().isEmpty()) {
            IO.println("No accounts available.");
            return;
        }

        // List all accounts
        IO.println("Available accounts:");
        for (Account account : this.loggedInCustomer.getAccounts().values()) {
            IO.println(account.toString());
        }

        // Ask teller which account to use
        IO.print("Enter account number to issue a cheque book (Business accounts only): ");
        String enteredAccountNumber = this.helpOnInput();

        Account selectedAccount = this.loggedInCustomer.getAccount(enteredAccountNumber);

        if (selectedAccount == null) {
            IO.println("Account not found.");
            return;
        }

        // Check if it’s a BusinessAccount
        if (!(selectedAccount instanceof BusinessAccount)) {
            IO.println("Cheque books can only be issued for business accounts.");
            return;
        }

        BusinessAccount businessAccount = (BusinessAccount) selectedAccount;

        // Check if cheque book already issued
        if (businessAccount.hasChequeBook()) {
            IO.println("Cheque book has already been issued for this account.");
        } else {
            businessAccount.requestChequeBook();
            Logger.log("CHEQUE BOOK ISSUED for " + businessAccount.getAccountNumber());

            this.saveDataToCSV(); // save changes
        }


    }
    private void manageLoan() {

        if (this.loggedInCustomer.getAccounts().isEmpty()) {
            IO.println("No accounts available.");
            return;
        }

        // List all accounts
        IO.println("Available accounts:");
        for (Account account : this.loggedInCustomer.getAccounts().values()) {
            IO.println(account.toString());
        }

        // Ask teller which account to use
        IO.print("Enter account number to request a loan (Business accounts only): ");
        String enteredAccountNumber = this.helpOnInput();

        Account selectedAccount = this.loggedInCustomer.getAccount(enteredAccountNumber);

        if (selectedAccount == null) {
            IO.println("Account not found.");
            return;
        }

        // Check if it’s a BusinessAccount
        if (!(selectedAccount instanceof BusinessAccount)) {
            IO.println("Loans can only be requested for business accounts.");
            return;
        }

        BusinessAccount businessAccount = (BusinessAccount) selectedAccount;

        // Check if a loan is already active
        if (businessAccount.hasLoan()) {
            IO.println("Note: A loan of £" + businessAccount.getLoanAmount() + " is already active for this account.");
        }

        // Ask teller for loan amount
        IO.print("Enter loan amount: ");
        double amount;
        try {
            amount = Double.parseDouble(this.helpOnInput());
        } catch (NumberFormatException e) {
            IO.println("Invalid amount entered.");
            return;
        }

        if (amount <= 0) {
            IO.println("Loan amount must be greater than 0.");
            return;
        }

        // Request the loan
        businessAccount.requestLoan(amount);

        Logger.log("LOAN REQUEST £" + amount + " for " + businessAccount.getAccountNumber());
        this.saveDataToCSV(); // save changes
    }


    private void addDirectDebit (PersonalAccount account){
        /* Requires input validation */
        String debitName = "";
        //Input Validation
        while (debitName.trim().isEmpty()) {
            IO.print("Enter Debit name: ");
            debitName = helpOnInput();

            if (debitName.trim().isEmpty()) {
                IO.println("Debit name cannot be empty.");
            }
        }

        double debitAmount = 0;
        while (debitAmount <= 0) {
            IO.print("Enter Debit amount (£): ");

            try {
                debitAmount = Double.parseDouble(helpOnInput());
                if (debitAmount <= 0) {
                    IO.println("Error: Debit amount must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                IO.println("Error: Please enter a valid amount.");
            }
        }

        // Are you sure?
        IO.print("You are about to add a Direct Debit of " + debitAmount + " to " + debitName + ". Are you sure? (y/n): ");
        String confirmation = helpOnInput();

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
        //Input Validation
        while (orderName.trim().isEmpty()) {
            IO.print("Enter Standing Order name: ");
            orderName = helpOnInput();

            if (orderName.trim().isEmpty()) {
                IO.println("Standing Order name cannot be empty.");
            }
        }

        double orderAmount = 0;
        while (orderAmount <= 0) {
            IO.print("Enter Standing Order amount (£): ");
            try {
                orderAmount = Double.parseDouble(helpOnInput());
                if (orderAmount <= 0) {
                    IO.println("Error: Amount must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                IO.println("Error: Please enter a valid amount.");
            }
        }
        // Are you sure?
        IO.print("You are about to add a Standing Order of " + orderAmount + " to " + orderName + ". Are you sure? (y/n): ");
        String confirmation = helpOnInput();

        if (!confirmation.equalsIgnoreCase("y")) {
            IO.println("Standing Order cancelled.");
            return;
        }

        account.addStandingOrder(new StandingOrder(orderName, orderAmount));
        saveDataToCSV();
        Logger.log("Standing Order added: " + orderName + " £" + orderAmount);
    }


}