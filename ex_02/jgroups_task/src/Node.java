import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;

import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.LogManager;

public class Node extends ReceiverAdapter{


    public static void main(String[] args) throws Exception{

        LogManager.getLogManager().reset();
        DistributedMap map = new DistributedMap();
        Scanner scanner = new Scanner(System.in);

        while(true){
            try{
                String op = scanner.next();
                String key;
                Integer value;

                switch(op.toUpperCase()){
                    case "PUT":
                        System.out.println("provide key");
                        key = scanner.next();
                        System.out.println("provide value");
                        value = scanner.nextInt();
                        map.put(key,value);
                        break;
                    case "REMOVE":
                        System.out.println("provide key");
                        key = scanner.next();
                        System.out.println(map.remove(key));
                        break;
                    case "GET":
                        System.out.println("provide key");
                        key = scanner.next();
                        System.out.println(map.get(key));
                        break;
                    case "PRINT":
                        System.out.println(map);
                        break;
                    case "ISIN":
                        System.out.println("provide key");
                        key = scanner.next();
                        System.out.println(map.containsKey(key));
                        break;
                    case "EXIT":
                        map.close();
                        return;
                        default:
                            System.out.println("Invalid operation. Valid operations are:" +
                                    "put, remove, get, print, isin, exit");

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
