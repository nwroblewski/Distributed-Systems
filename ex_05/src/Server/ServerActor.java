package Server;

import Server.Search.SearchActor;
import akka.actor.AbstractActor;
import akka.actor.Props;

public class ServerActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class,s -> {
                    if(s.startsWith("s")){
                        context().child("searchActor").get().tell(s,getSender());
                    }
                })
                .matchAny(o -> {
                    System.out.println("test");
                }).build();
    }

    @Override
    public void preStart(){
        context().actorOf(Props.create(SearchActor.class),"searchActor");
    }
}
