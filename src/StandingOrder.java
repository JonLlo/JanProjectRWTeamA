public class StandingOrder {
    private String payeeName;
    private double amount;

    public StandingOrder(String payeeName, double amount) {
        this.payeeName = payeeName;
        this.amount = amount;
    }

    public double getAmount() {return amount;}

    public String getPayeeName() {return payeeName;}

    public String toString() {
        return "Standing Order to " + payeeName + " | Amount: " + String.format("£%.2f", amount);
    }
}