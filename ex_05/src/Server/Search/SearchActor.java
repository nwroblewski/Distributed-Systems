package Server.Search;

import akka.actor.AbstractActor;

public class SearchActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class,s -> {
                    if(s.startsWith("s")){
                        getSender().tell("response not found yet",null);
                    }
                })
                .matchAny(o -> {
                    System.out.println("test");
                }).build();
    }
    @Override
    public void preStart(){

    }
}
