package Server;

import akka.actor.AbstractActor;

public class ServerActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(o -> {
                    System.out.println("duperka");
                }).build();
    }
}
