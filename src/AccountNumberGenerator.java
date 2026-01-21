import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/*
Note: the simple ver of this worked, but please check again when everything
else is added - Christina
 */

public class AccountNumberGenerator {

    private static final Set<String> usedAccountNumbers = new HashSet<>();
    private static final Random random = new Random();
    private static final int MAX_ATTEMPTS = 1000;

    // when accounts are loaded (storage)
    public static void registerExisting(String accountNumber) {
        usedAccountNumbers.add(accountNumber);
    }

    public static String generate() {

        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            String candidate = String.format(
                    "%08d",
                    random.nextInt(100_000_000)
            );

            if (usedAccountNumbers.add(candidate)) {
                return candidate;
            }

            attempts++;
        }

        throw new IllegalStateException("Unable to generate a unique account number");
    }
}
