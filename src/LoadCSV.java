import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



//make class private
// missing Customer class
public class LoadCSV {

    //This is to test it
    private static final String DATA_FILE_NAME = "practice.csv";

    // "customers" add later-
    private Map<String, Customer> customerMap = new HashMap<>();

    // the test!
    public void loadDataFromCSV() {
        File file = new File(DATA_FILE_NAME);
        if (!file.exists()) {
            IO.println("Sorry! This CSV file doesn't exist.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) continue;

                String[] p = line.split(",");

                // CUSTOMER
                if (p[0].equals("C")) {
                    customerMap.put(p[1], new Customer(p[1], p[2]));
                }

                // ACCOUNT
                else if (p[0].equals("A")) {
                    Customer customer = customerMap.get(p[1]);
                    if (customer == null) continue;

                    Account account = null;
                    String lastFeeDate = p.length > 6 ? p[6] : "";

                    if (p[2].equals("P")) account = new PersonalAccount();
                    else if (p[2].equals("I")) account = new IsaAccount();
                    else if (p[2].equals("B")) {
                        account = new BusinessAccount();
                        if (!lastFeeDate.isEmpty()) {
                            ((BusinessAccount) account).setLastFeeAppliedDate(LocalDate.parse(lastFeeDate));
                        }
                    }

                    if (account == null) continue;

                    account.accountNumber = p[3];
                    account.sortCode = p[4];
                    account.balance = Double.parseDouble(p[5]);

                    AccountNumberGenerator.registerExisting(p[3]);
                    customer.addAccount(account);
                }

                // DIRECT DEBIT
                else if (p[0].equals("D")) {
                    Customer customer = customerMap.get(p[1]);
                    if (customer == null) continue;

                    Account account = customer.getAccount(p[2]);
                    if (account instanceof PersonalAccount) {
                        account.loadDirectDebit(new DirectDebit(p[3], Double.parseDouble(p[4])));
                    }
                }

                // STANDING ORDER
                else if (p[0].equals("S")) {
                    Customer customer = customerMap.get(p[1]);
                    if (customer == null) continue;

                    Account account = customer.getAccount(p[2]);
                    if (account instanceof PersonalAccount) {
                        account.loadStandingOrder(new StandingOrder(p[3], Double.parseDouble(p[4])));
                    }
                }
            }

            IO.println("CSV data loaded.");

        } catch (Exception e) {
            IO.println("Error loading CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // runs just this part
    public static void main() {
        LoadCSV loader = new LoadCSV();
        loader.loadDataFromCSV();
    }
}

//Wouldn't run without a customer class - I think someone else is doing this part
// Everything below is just so it runs in terminal
class Customer {
    String id, name;
    Map<String, Account> accounts = new HashMap<>();

    Customer(String id, String name) { this.id = id; this.name = name; }

    void addAccount(Account account) { accounts.put(account.accountNumber, account); }
    Account getAccount(String accountNumber) { return accounts.get(accountNumber); }
}

abstract class Account {
    String accountNumber, sortCode;
    double balance;
    void loadDirectDebit(DirectDebit dd) {}
    void loadStandingOrder(StandingOrder so) {}
}

class PersonalAccount extends Account {}
class IsaAccount extends Account {}
class BusinessAccount extends Account {
    LocalDate lastFeeAppliedDate;
    void setLastFeeAppliedDate(LocalDate date) { lastFeeAppliedDate = date; }
}

class DirectDebit { DirectDebit(String n, double a) {} }
class StandingOrder { StandingOrder(String n, double a) {} }

class AccountNumberGenerator { static void registerExisting(String accNum) {} }

class IO { static void println(String msg) { System.out.println(msg); } }
