import com.rabbitmq.client.*;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Doctor {
    static String docName;
    static Channel techChannel;
    static Channel logChannel;
    static Channel adminChannel;
    static String EXCHANGE_NAME = "techExchange";
    public static void main(String[] args) throws Exception{

        System.out.println("Enter doctor name");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        docName = reader.readLine();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        techChannel = connection.createChannel();
        techChannel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String qName = techChannel.queueDeclare().getQueue();
        techChannel.queueBind(qName,EXCHANGE_NAME,createTopicName(docName));

        logChannel = connection.createChannel();
        logChannel.exchangeDeclare("logExchange",BuiltinExchangeType.FANOUT);

        adminChannel = connection.createChannel();
        adminChannel.exchangeDeclare("adminExchange",BuiltinExchangeType.FANOUT);
        String adminQName = adminChannel.queueDeclare().getQueue();
        adminChannel.queueBind(adminQName,"adminExchange","");

        Consumer consumer = new DefaultConsumer(techChannel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received " + msg);
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
        techChannel.basicConsume(qName,true,consumer);

        CLI(reader);

        techChannel.close();
        connection.close();
    }

    private static String createTopicName(String docName){
        return "doctor." + docName;
    }

    private static void CLI(BufferedReader reader) throws IOException {
        System.out.println("Command Line Interface start");

        String examination;
        String name;
        String msg;

        while(true){
                System.out.println("Enter the type.");
                examination = reader.readLine();

                if(examination.equals("exit")){
                    break;
                }

                System.out.println("Enter your name");
                name = reader.readLine();
                msg = examination + " " + name;
                techChannel.basicPublish(
                        EXCHANGE_NAME,
                        "tech." + examination,
                            new AMQP.BasicProperties.Builder().replyTo(createTopicName(docName)).build(),
                        msg.getBytes(StandardCharsets.UTF_8));
                logChannel.basicPublish("logExchange","",null,msg.getBytes(StandardCharsets.UTF_8));
        }
    }
}

