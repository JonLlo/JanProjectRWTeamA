import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public static void main(String[] args) {
        log("Logger is running");
    }

    public static void log(String message) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());
        System.out.println(time + " - " + message);
    }
}

