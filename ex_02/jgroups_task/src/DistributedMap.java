import DMHandlers.DMMessage;
import DMHandlers.DMReceiver;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.ProtocolStack;
import org.jgroups.util.Util;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.HashMap;

import static org.jgroups.util.Util.objectToByteBuffer;

public class DistributedMap implements SimpleStringMap{
    private final String CLUSTER = "HASHMAP_CLUSTER";

    private HashMap<String,Integer> storage = new HashMap<>();

    private JChannel channel;


    public DistributedMap() throws Exception{
        start();
    }

    @Override
    public boolean containsKey(String key) {
        return storage.containsKey(key);
    }

    @Override
    public Integer get(String key) {
        return storage.get(key);
    }

    @Override
    public void put(String key, Integer value) {
        DMMessage msg = DMMessage.makePutDMMessage(key,value);
        sendMessage(msg);
    }

    @Override
    public Integer remove(String key) {
        Integer removed = storage.get(key);
        DMMessage msg = DMMessage.makeRemoveDMMessage(key);
        sendMessage(msg);
        return removed;
    }

    public void close(){
        channel.close();
    }

    private void start() throws Exception{
        System.setProperty("java.net.preferIPv4Stack","true");
        channel = new JChannel(false);

        ReceiverAdapter receiver = new DMReceiver(storage,channel);

        UDP udp = new UDP();
        udp.setValue("mcast_group_addr", InetAddress.getByName("230.100.200.1"));

        ProtocolStack stack=new ProtocolStack();
        channel.setProtocolStack(stack);
        stack.addProtocol(udp)
                .addProtocol(new PING())
                .addProtocol(new MERGE3())
                .addProtocol(new FD_SOCK())
                .addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
                .addProtocol(new VERIFY_SUSPECT())
                .addProtocol(new BARRIER())
                .addProtocol(new NAKACK2())
                .addProtocol(new UNICAST3())
                .addProtocol(new STABLE())
                .addProtocol(new GMS())
                .addProtocol(new UFC())
                .addProtocol(new MFC())
                .addProtocol(new FRAG2())
                .addProtocol(new STATE())
                .addProtocol(new FLUSH());

        stack.init();

        channel.setReceiver(receiver);

        channel.connect(CLUSTER);
    }

    private void sendMessage(DMMessage msg){
        try{
            byte[] buffer = objectToByteBuffer(msg);
            Message message = new Message(null,buffer);
            channel.send(message);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public String toString(){
        StringBuilder stringMap = new StringBuilder();
        for (String key : storage.keySet()) {
            stringMap.append(key + " : ").append(storage.get(key)).append("\n");
        }

        return stringMap.toString();
    }
}
