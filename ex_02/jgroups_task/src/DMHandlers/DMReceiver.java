package DMHandlers;

import org.jgroups.*;
import org.jgroups.util.Util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.jgroups.util.Util.*;

public class DMReceiver extends ReceiverAdapter {

    final private HashMap<String,Integer> storage;
    final private JChannel channel;


    public DMReceiver(HashMap<String,Integer> storage, JChannel channel){
        this.storage = storage;
        this.channel = channel;
    }

    @Override
    public void receive(Message msg){
        try {
            DMMessage message = (DMMessage) objectFromByteBuffer(msg.getBuffer());
            processMessage(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getState(OutputStream output) throws Exception{
        System.out.println("getstate");
        synchronized(storage){
            objectToStream(storage,new DataOutputStream(output));
        }
    }

    @Override
    public void setState(InputStream input) throws Exception{
        System.out.println("setstate");
        Map<String, Integer> tmp;
        tmp = (Map<String, Integer>) Util.objectFromStream(new DataInputStream(input));
        synchronized (storage) {
            storage.clear();
            storage.putAll(tmp);
        }
    }

    @Override
    public void viewAccepted(View view){
        super.viewAccepted(view);

        System.out.println("View: " + view.toString());

        if(view instanceof MergeView){
            MergeView mView = (MergeView) view;
            MHandler handler = new MHandler(mView,channel);
            new Thread(handler).run();
        }
    }
    private void processMessage(DMMessage msg){
        switch(msg.type){
            case "PUT":
                storage.put(msg.key,msg.value);
                break;
            case "REMOVE":
                storage.remove(msg.key);
                break;
        }
    }

}
