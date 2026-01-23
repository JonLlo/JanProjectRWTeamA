public class InternationalPayment {

    private double foreignAmount;
    private double exchangeRate;
    private double gbpAmount;

    public InternationalPayment(double foreignAmount, double exchangeRate) {
        this.foreignAmount = foreignAmount;
        this.exchangeRate = exchangeRate;
        this.gbpAmount = foreignAmount * exchangeRate;
    }

    public double getForeignAmount() { return foreignAmount; }
    public double getExchangeRate() { return exchangeRate; }
    public double getGbpAmount() { return gbpAmount; }

    @Override
    public String toString() {
        return "International Payment | Foreign: " + foreignAmount +
                " | Rate: " + exchangeRate +
                " | GBP: £" + String.format("%.2f", gbpAmount);
    }
}
