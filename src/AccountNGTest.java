import java.util.*;
// I will change the name to AccountNumberGenerator in a bit
// I gave it a different class name to test it separate from the other files
public class AccountNGTest {

    private static Set<String> usedAccountNumbers = new HashSet<>();
    private static Random random = new Random();

    // Called when loading accounts from file
    public static void registerExisting(String accountNumber) {
        usedAccountNumbers.add(accountNumber);
    }

    public static String generate() {

        // Try a reasonable number of attempts
        for (int attempt = 0; attempt < 1000; attempt++) {

            String generatedNumber = String.format("%08d", random.nextInt(100_000_000));

            if (!usedAccountNumbers.contains(generatedNumber)) {
                usedAccountNumbers.add(generatedNumber);
                return generatedNumber;
            }
        }

        // If you cannot generate a random number
        throw new IllegalStateException(
                "Unable to generate a unique account number"
        );
    }

    // Allows this class to run on its own
    public static void main(String[] args) {

        // Simulate loading existing account numbers
        registerExisting("00001234");
        registerExisting("12345678");

        // Generate and print some new account numbers
        for (int i = 0; i < 5; i++) {
            System.out.println(generate());
        }
    }
}
