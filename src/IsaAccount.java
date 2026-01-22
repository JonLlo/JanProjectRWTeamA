public class IsaAccount extends Account {
    private final double INTEREST_RATE = 0.0275;


    protected String getSortCodeForType() {
        return "60-60-70";
    }

    @Override
    public void applyAnnualInterest() {
        if (balance <= 0) return;

        double interest = balance * INTEREST_RATE;
        balance += interest;
        IO.println("ISA interest applied: £" + String.format("%.2f", interest));
    }
}
