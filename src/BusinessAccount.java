import java.time.LocalDate;

public class BusinessAccount extends Account {

    private final double ANNUAL_FEE = 120.00;
    private String businessType;
    private LocalDate lastFeeAppliedDate;

    public BusinessAccount(String businessType) {
        this.businessType = businessType;
    }

    public BusinessAccount() {

        overdraftEnabled = true;
        overdraftLimit = 500.00;
        this.accountType = "Business";

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
    }

}
