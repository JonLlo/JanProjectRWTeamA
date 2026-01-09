import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

public class Logger {
    //needed main to run it separately
    // change main/log when all the files are done
    private static final String LOG_FILE = "bank_log.txt";
    public static void main(String[] args) {
        log("Logger is running");
    }

    public static void log(String message) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());
        System.out.println(time + " - " + message);
    }
}

