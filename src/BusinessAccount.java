import java.time.LocalDate;

public class BusinessAccount extends Account {
    private boolean chequeBookIssued = false; // Tracks cheque book issuance
    private boolean loanActive = false;        // Track if a loan is active
    private double loanAmount = 0.0;           // Amount of loan granted

    public BusinessAccount() {
        this.accountType = "Business";
        overdraftEnabled = true;
        overdraftLimit = 500.00;

    }
    public void requestChequeBook() {
            chequeBookIssued = true;
            System.out.println("Cheque book issued successfully.");
    }


    public boolean hasChequeBook() {
        return chequeBookIssued;
    }

    private final double ANNUAL_FEE = 120.00;
    private String businessType;
    private LocalDate lastFeeAppliedDate;



    // --- Loan methods ---

    public void setLoanActive(boolean active) {
        this.loanActive = active;
    }

    public void setLoanAmount(double amount) {
        this.loanAmount = amount;
    }

    public void setChequeBookIssued(boolean issued) {
        this.chequeBookIssued = issued;
    }

    public void requestLoan(double amount) {
        if (loanActive) {
            // simply increase existing loan
            loanAmount += amount;
            balance += amount;
            System.out.println("Additional loan of £" + amount + " added. Total loan: £" + loanAmount);
        } else if (amount <= 0) {
            System.out.println("Loan amount must be greater than 0.");
        } else {
            System.out.println("Loan of £" + amount + " approved and added to balance.");
            loanActive = true;
            loanAmount = amount;
            balance += amount;  // simple simulation: add loan to balance
        }
    }

    public boolean hasLoan() {
        return loanActive;
    }

    public double getLoanAmount() {
        return loanAmount;
    }


    protected String getSortCodeForType() {
        return "60-70-70";
    }

    @Override
    public void applyAnnualFee() {
        LocalDate today = LocalDate.now();

        // Apply fee if never applied or last applied before this year
        if (lastFeeAppliedDate == null || today.getYear() > lastFeeAppliedDate.getYear()) {
            balance -= ANNUAL_FEE;
            lastFeeAppliedDate = today;
            IO.println("Business annual fee applied: " + String.format("£%.2f", ANNUAL_FEE));
        }
    }

    public LocalDate getLastFeeAppliedDate() {
        return lastFeeAppliedDate;
    }

    public void setLastFeeAppliedDate(LocalDate date) {
        this.lastFeeAppliedDate = date;





    }}
