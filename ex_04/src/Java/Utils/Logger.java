package Java.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private String className;

    public Logger(String className){
        this.className = className;
    }

    public void log(String message){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("[" + dateFormat.format(date) + "]" + "[" + this.className + "]" + message);
    }
}
