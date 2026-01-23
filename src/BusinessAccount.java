import java.time.LocalDate;

public class BusinessAccount extends Account {

    private static final double ANNUAL_FEE = 120.00;
    private static final double INTERNATIONAL_FEE_RATE = 0.02; // 2%

    private String businessType;
    private LocalDate lastFeeAppliedDate;

    public BusinessAccount(String businessType) {
        this.businessType = businessType;
        overdraftEnabled = true;
        overdraftLimit = 500.00;
    }

    public BusinessAccount() {
        overdraftEnabled = true;
        overdraftLimit = 500.00;
    }

    @Override
    protected String getSortCodeForType() {
        return "60-70-70";
    }

    // ===== ANNUAL FEE =====
    @Override
    public void applyAnnualFee() {
        LocalDate today = LocalDate.now();

        if (lastFeeAppliedDate == null ||
                today.getYear() > lastFeeAppliedDate.getYear()) {

            balance -= ANNUAL_FEE;
            lastFeeAppliedDate = today;

            IO.println("Business annual fee applied: £" +
                    String.format("%.2f", ANNUAL_FEE));
        }
    }

    public LocalDate getLastFeeAppliedDate() {
        return lastFeeAppliedDate;
    }

    public void setLastFeeAppliedDate(LocalDate date) {
        this.lastFeeAppliedDate = date;
    }

    public void internationalPayment(double foreignAmount, double exchangeRate) {

        if (foreignAmount <= 0 || exchangeRate <= 0) {
            IO.println("Invalid international transaction.");
            return;
        }

        double gbpAmount = foreignAmount * exchangeRate;
        double fee = gbpAmount * INTERNATIONAL_FEE_RATE;
        double totalCost = gbpAmount + fee;

        if (totalCost > balance + (overdraftEnabled ? overdraftLimit : 0)) {
            IO.println("Insufficient funds for international transaction.");
            return;
        }

        balance -= totalCost;

        IO.println("International payment successful.");
        IO.println("Converted amount: £" + String.format("%.2f", gbpAmount));
        IO.println("Transaction fee: £" + String.format("%.2f", fee));
        IO.println("New balance: £" + String.format("%.2f", balance));
    }
}
