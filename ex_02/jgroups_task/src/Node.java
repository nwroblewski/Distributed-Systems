import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Node extends ReceiverAdapter{

    //TODO przydalby sie dekorator dla wyswietlania stanu hashmapy, klikania sobie ladnie w get/put itd

    private DistributedMap DMap = new DistributedMap(new HashMap<>());
    JChannel channel;

    private void start() throws Exception{

        channel = new JChannel();
        channel.setReceiver(this);
        channel.connect("TEST_CLUSTER");
        eventLoop();
        channel.close();
    }

    private void eventLoop(){

    }
    public static void main(String[] args) throws Exception{
        System.setProperty("java.net.preferIPv4Stack","true");
        new Node().start();
    }
}
