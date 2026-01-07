
//Note here we have the "Main" file. This will run the whole system.

public class SimpleBank {

    //we use string[] args because we are working with the command line.
    public static void main(String[] args) {
        BankSystem bankSystem = new BankSystem();
        bankSystem.start();
    }
}

