import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BusinessAccount extends Account {

    // --- Existing fields ---
    private boolean chequeBookIssued = false;
    private boolean loanActive = false;
    private double loanAmount = 0.0;

    private final double ANNUAL_FEE = 120.00;
    private LocalDate lastFeeAppliedDate;

    // --- International trading ---
    private final double INTERNATIONAL_FEE_RATE = 0.02; // 2% fee
    private List<InternationalPayment> internationalPayments = new ArrayList<>();

    public BusinessAccount() {
        this.accountType = "Business";
        overdraftEnabled = true;
        overdraftLimit = 500.00;
    }

    // --- Cheque book ---
    public void requestChequeBook() {
        chequeBookIssued = true;
        System.out.println("Cheque book issued successfully.");
    }

    public boolean hasChequeBook() {
        return chequeBookIssued;
    }

    public void setChequeBookIssued(boolean issued) {
        this.chequeBookIssued = issued;
    }

    // --- Loan methods ---
    public void requestLoan(double amount) {
        if (amount <= 0) {
            System.out.println("Loan amount must be greater than 0.");
            return;
        }

        if (loanActive) {
            loanAmount += amount;
            balance += amount;
            System.out.println("Additional loan of £" + amount +
                    " added. Total loan: £" + loanAmount);
        } else {
            loanActive = true;
            loanAmount = amount;
            balance += amount;
            System.out.println("Loan of £" + amount + " approved and added to balance.");
        }
    }

    public boolean hasLoan() {
        return loanActive;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanActive(boolean active) {
        this.loanActive = active;
    }

    public void setLoanAmount(double amount) {
        this.loanAmount = amount;
    }

    // --- International payment ---
    public void internationalPayment(double foreignAmount, double exchangeRate) {

        if (foreignAmount <= 0 || exchangeRate <= 0) {
            System.out.println("Invalid international payment values.");
            return;
        }

        double gbpAmount = foreignAmount * exchangeRate;
        double fee = gbpAmount * INTERNATIONAL_FEE_RATE;
        double total = gbpAmount + fee;

        withdraw(total);

        internationalPayments.add(
                new InternationalPayment(foreignAmount, exchangeRate)
        );

        System.out.println("International payment processed. Fee: £" +
                String.format("%.2f", fee));
    }

    // Used ONLY when loading from CSV (no balance change)
    public void loadInternationalPayment(InternationalPayment ip) {
        internationalPayments.add(ip);
    }

    public List<InternationalPayment> getInternationalPayments() {
        return internationalPayments;
    }

    // --- Annual fee ---
    @Override
    public void applyAnnualFee() {
        LocalDate today = LocalDate.now();

        if (lastFeeAppliedDate == null ||
                today.getYear() > lastFeeAppliedDate.getYear()) {

            balance -= ANNUAL_FEE;
            lastFeeAppliedDate = today;

            IO.println("Business annual fee applied: " +
                    String.format("£%.2f", ANNUAL_FEE));
        }
    }

    public LocalDate getLastFeeAppliedDate() {
        return lastFeeAppliedDate;
    }

    public void setLastFeeAppliedDate(LocalDate date) {
        this.lastFeeAppliedDate = date;
    }

    // --- Sort code ---
    @Override
    protected String getSortCodeForType() {
        return "60-70-70";
    }
}
