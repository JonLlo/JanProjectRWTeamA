public class DirectDebit {

    private String payeeName;
    private double amount;

    public DirectDebit(String payeeName, double amount) {
        this.payeeName = payeeName;
        this.amount = amount;
    }

    public double getAmount() {return amount;}

    public String getPayee() {return payeeName;}

    public String toString() {
        return "Direct Debit to " + payeeName + " | Amount: " + String.format("£%.2f", amount);}
}