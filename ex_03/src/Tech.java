import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Tech {
    static Channel logChannel;
    static Channel techChannel;
    static Channel adminChannel;
    static ArrayList<String> examination = new ArrayList<>();
    static String EXCHANGE_NAME = "techExchange";

    public static void main(String[] args) throws Exception{

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        examination.add(reader.readLine());
        examination.add(reader.readLine());


        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        techChannel = connection.createChannel();
        techChannel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        logChannel = connection.createChannel();
        logChannel.exchangeDeclare("logExchange",BuiltinExchangeType.FANOUT);

        adminChannel = connection.createChannel();
        adminChannel.exchangeDeclare("adminExchange",BuiltinExchangeType.FANOUT);
        String adminQName = adminChannel.queueDeclare().getQueue();
        adminChannel.queueBind(adminQName,"adminExchange","");

        Consumer consumer = new DefaultConsumer(techChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println(properties.getReplyTo() + " sent " + msg);
                msg += " done";
                techChannel.basicPublish(EXCHANGE_NAME,properties.getReplyTo(),null,msg.getBytes());
                logChannel.basicPublish("logExchange","",null,msg.getBytes());
            }
        };

        Consumer adminConsumer = new DefaultConsumer(adminChannel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("ADMIN: " + msg);
            }
        };

        adminChannel.basicConsume(adminQName,true, adminConsumer);

        if(examination.contains("knee")){
            techChannel.queueDeclare("kneeTechQ",true,false,false,null);
            techChannel.queueBind("kneeTechQ",EXCHANGE_NAME,"tech.knee");
            System.out.println("Connected to knee queue.");
            techChannel.basicConsume("kneeTechQ",true,consumer);
        }

        if(examination.contains("hip")){
            techChannel.queueDeclare("hipTechQ",true,false,false,null);
            techChannel.queueBind("hipTechQ",EXCHANGE_NAME,"tech.hip");
            System.out.println("Connected to hip queue.");
            techChannel.basicConsume("hipTechQ",true,consumer);
        }

        if(examination.contains("elbow")){
            techChannel.queueDeclare("elbowTechQ",true,false,false,null);
            techChannel.queueBind("elbowTechQ",EXCHANGE_NAME,"tech.elbow");
            System.out.println("Connected to elbow queue.");
            techChannel.basicConsume("elbowTechQ",true,consumer);
        }
    }
}
