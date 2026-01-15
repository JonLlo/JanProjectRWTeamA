import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    protected String accountNumber;
    protected String sortCode;
    protected double balance;

    protected boolean overdraftEnabled = false;
    protected double overdraftLimit = 0.0;

    protected List<DirectDebit> directDebits = new ArrayList<>();
    protected List<StandingOrder> standingOrders = new ArrayList<>();

    public void addDirectDebit(DirectDebit dd) {
        directDebits.add(dd);
        balance -= dd.getAmount();
    }

    public void addStandingOrder(StandingOrder so) {
        standingOrders.add(so);
        balance -= so.getAmount();
    }

    public void loadDirectDebit(DirectDebit dd) {
        directDebits.add(dd);
    }

    public void loadStandingOrder(StandingOrder so) {
        standingOrders.add(so);
    }

    public void viewDirectDebits() {
        if (directDebits.isEmpty()) {
            IO.println("No Direct Debits.");
            return;
        }
        for (DirectDebit dd : directDebits) {
            IO.println(dd);
        }
    }

    public void viewStandingOrders() {
        if (standingOrders.isEmpty()) {
            IO.println("No Standing Orders.");
            return;
        }
        for (StandingOrder so : standingOrders) {
            IO.println(so);
        }
    }

    public List<DirectDebit> getDirectDebits() { return directDebits; }
    public List<StandingOrder> getStandingOrders() { return standingOrders; }

    public void assignIdentifiers() {
        accountNumber = AccountNumberGenerator.generate();
        sortCode = getSortCodeForType();
    }

    protected abstract String getSortCodeForType();

    public String getAccountNumber() { return accountNumber; }

    public void deposit(double amount) {
        if (amount <= 0) return;
        balance += amount;
        IO.println("Balance: £" + String.format("%.2f", balance));
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            IO.println("Invalid amount.");
            return;
        }

        double available = balance + (overdraftEnabled ? overdraftLimit : 0);

        if (amount > available) {
            IO.println("Insufficient funds.");
            return;
        }

        balance -= amount;

        if (balance < 0) {
            IO.println("Overdraft used.");
        }

        IO.println("New balance: £" + String.format("%.2f", balance));
    }

    public void applyAnnualInterest() {}
    public void applyAnnualFee() {}

    @Override
    public String toString() {
        return "Account: " + accountNumber +
                " | Sort: " + sortCode +
                " | Balance: £" + String.format("%.2f", balance);
    }
}

