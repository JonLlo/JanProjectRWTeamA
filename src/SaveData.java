/*
saveDataToCSV() needs to call on methods that don't yet exist in 
the skeleton, so this is the simple version. The Customer
section and dummy accounts will need to be deleted when the full 
code is ready.
 */



import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;

public class SaveData {

    private static final String LOG_FILE = "data.csv";
    private static Map<Integer, Customer> customerMap = new HashMap<>();

//dummy accounts
    public static void main() {
        Customer c1 = new Customer(1, "Alice");
        Customer c2 = new Customer(2, "Bob");

        Account a1 = new PersonalAccount(1001, "12-34-56", 500.0);
        Account a2 = new IsaAccount(1002, "65-43-21", 1000.0);

        a1.addDirectDebit(new DirectDebit("Netflix", 9.99));
        a2.addStandingOrder(new StandingOrder("Gym", 25.0));

        c1.addAccount(a1);
        c1.addAccount(a2);

        customerMap.put(c1.getId(), c1);
        customerMap.put(c2.getId(), c2);

        // export it all
        new SaveData().saveDataToCSV();
    }

    private void saveDataToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE))) {

            for (Customer customer : customerMap.values()) {
                writer.println("C," + customer.getId() + "," + customer.getName());

                for (Account account : customer.getAccounts().values()) {

                    String type = "P";
                    if (account instanceof IsaAccount) type = "I";
                    else if (account instanceof BusinessAccount) type = "B";

                    String lastFeeDate = "";
                    if (account instanceof BusinessAccount) {
                        LocalDate d = ((BusinessAccount) account).getLastFeeAppliedDate();
                        if (d != null) lastFeeDate = d.toString();
                    }

                    // dummy account to test
                    writer.println("A," + customer.getId() + "," + type + "," +
                            account.getAccountNumber() + "," +
                            account.sortCode + "," +
                            String.format("%.2f", account.balance) + "," + lastFeeDate);

                    // direct debit
                    for (DirectDebit dd : account.getDirectDebits()) {
                        writer.println("D," + customer.getId() + "," +
                                account.getAccountNumber() + "," +
                                dd.getPayee() + "," +
                                String.format("%.2f", dd.getAmount()));
                    }

                    // standing orders
                    for (StandingOrder so : account.getStandingOrders()) {
                        writer.println("S," + customer.getId() + "," +
                                account.getAccountNumber() + "," +
                                so.getPayee() + "," +
                                String.format("%.2f", so.getAmount()));
                    }
                }
            }

            IO.println("CSV data saved.");

        } catch (Exception e) {
            IO.println("Error saving CSV: " + e.getMessage());
        }
    }

 // to test
    static class IO {
        static void println(String s) { System.out.println(s); }
    }

    static class Customer {
        private int id;
        private String name;
        private Map<Integer, Account> accounts = new HashMap<>();

        public Customer(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public Map<Integer, Account> getAccounts() { return accounts; }

        public void addAccount(Account a) { accounts.put(a.getAccountNumber(), a); }
    }

    static class Account {
        int accountNumber;
        String sortCode;
        double balance;
        private List<DirectDebit> directDebits = new ArrayList<>();
        private List<StandingOrder> standingOrders = new ArrayList<>();

        public Account(int accountNumber, String sortCode, double balance) {
            this.accountNumber = accountNumber;
            this.sortCode = sortCode;
            this.balance = balance;
        }

        public int getAccountNumber() { return accountNumber; }
        public List<DirectDebit> getDirectDebits() { return directDebits; }
        public List<StandingOrder> getStandingOrders() { return standingOrders; }

        public void addDirectDebit(DirectDebit dd) { directDebits.add(dd); }
        public void addStandingOrder(StandingOrder so) { standingOrders.add(so); }
    }

    static class PersonalAccount extends Account {
        public PersonalAccount(int accountNumber, String sortCode, double balance) {
            super(accountNumber, sortCode, balance);
        }
    }

    static class IsaAccount extends Account {
        public IsaAccount(int accountNumber, String sortCode, double balance) {
            super(accountNumber, sortCode, balance);
        }
    }

    static class BusinessAccount extends Account {
        private LocalDate lastFeeAppliedDate;

        public BusinessAccount(int accountNumber, String sortCode, double balance, LocalDate lastFee) {
            super(accountNumber, sortCode, balance);
            this.lastFeeAppliedDate = lastFee;
        }

        public LocalDate getLastFeeAppliedDate() { return lastFeeAppliedDate; }
    }

    static class DirectDebit {
        private String payee;
        private double amount;
        public DirectDebit(String payee, double amount) { this.payee = payee; this.amount = amount; }
        public String getPayee() { return payee; }
        public double getAmount() { return amount; }
    }

    static class StandingOrder {
        private String payee;
        private double amount;
        public StandingOrder(String payee, double amount) { this.payee = payee; this.amount = amount; }
        public String getPayee() { return payee; }
        public double getAmount() { return amount; }
    }
}

