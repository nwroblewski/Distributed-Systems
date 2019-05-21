package Server;

import Client.ClientActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ServerApp {
    public static void main(String[] args) throws Exception{
        File configFile = new File("server.conf");
        Config config = ConfigFactory.parseFile(configFile);

        final ActorSystem actorSystem = ActorSystem.create("server_system", config);
        final ActorRef serverActor = actorSystem.actorOf(Props.create(ServerActor.class),"server_actor");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            String line = br.readLine();
            if(line.equals("q")) break;
            serverActor.tell(line,null);
        }

        actorSystem.terminate();
    }
}
