import com.rabbitmq.client.*;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Admin {
    static Channel logChannel;
    static Channel adminChannel;
    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        logChannel = connection.createChannel();
        logChannel.exchangeDeclare("logExchange", BuiltinExchangeType.FANOUT);

        adminChannel = connection.createChannel();
        adminChannel.exchangeDeclare("adminExchange",BuiltinExchangeType.FANOUT);

        Consumer consumer = new DefaultConsumer(logChannel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received " + msg);
            }
        };

        prepareQueue(consumer);
        CLI();

        logChannel.close();
        connection.close();
    }

    private static void prepareQueue(Consumer consumer) throws IOException{
        logChannel.queueDeclare("adminQ",true,false,false, null);
        logChannel.queueBind("adminQ","logExchange","");
        System.out.println("Connected to admin q.");
        logChannel.basicConsume("adminQ",true, consumer);
    }

    private static void CLI() throws IOException {
        System.out.println("Starting CLI");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            String msg = reader.readLine();
            if(msg.equals("exit")){
                System.out.println("Exiting admin console...");
                break;
            }
            if(msg.equals("send")) {
                String msg2 = reader.readLine();
                adminChannel.basicPublish("adminExchange", "", null, msg2.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
