import java.util.*;

public class Customer {

    private String customerId;
    private String customerName;
    private Map<String, Account> accountMap = new HashMap<>();

    public Customer(String customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public String getId() { return customerId; }
    public String getName() { return customerName; }

    public void addAccount(Account account) {
        accountMap.put(account.getAccountNumber(), account);
    }

    public Account getAccount(String accountNumber) {
        return accountMap.get(accountNumber);
    }

    public Map<String, Account> getAccounts() {
        return accountMap;
    }

    public boolean hasISA() {
        for (Account account : accountMap.values()) {
            if (account instanceof IsaAccount) {
                return true;
            }
        }
        return false;
    }

    public boolean hasBusiness() {
        for (Account account : accountMap.values()) {
            if (account instanceof BusinessAccount) {
                return true;
            }
        }
        return false;
    }

    public void viewAccounts() {
        if (accountMap.isEmpty()) {
            IO.println("No accounts.");
            return;
        }

        for (Account account : accountMap.values()) {
            IO.println(account.toString());
        }
    }
}
