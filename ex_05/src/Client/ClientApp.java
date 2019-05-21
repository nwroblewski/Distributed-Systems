package Client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ClientApp {

    public static void main(String[] args) throws Exception{
        File configFile = new File("client.conf");
        Config config = ConfigFactory.parseFile(configFile);

        final ActorSystem actorSystem = ActorSystem.create("client_system", config);
        final ActorRef clientActor = actorSystem.actorOf(Props.create(ClientActor.class),"client_actors");
        System.out.println("Supported operations: 'o [title]' 's [title]' 'st [title]' 'q' ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            String line = br.readLine();
            if(line.equals("q")) break;
            clientActor.tell(line,null);
        }

        actorSystem.terminate();
    }
}
