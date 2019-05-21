package Client;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

public class ClientActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    System.out.println(getSelf().path());
                    if(s.startsWith("s")){
                    }
                }).build();
    }
}
