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

public class Application {

    private DistributedMap DMap = new DistributedMap(new HashMap<>());
    JChannel channel;

    private void start() throws Exception{
        channel = new JChannel();

        channel.setReceiver(new ReceiverAdapter(){
            @Override
            public void viewAccepted(View view){
                super.viewAccepted(view);
                System.out.println(view.toString());
            }
            public void receive(Message msg){
                System.out.println("received from" + msg.getSrc() + ": " +msg.getObject());
            }
        });
        channel.connect("TEST_CLUSTER");

        TimeUnit.SECONDS.sleep(10);
        for(int i = 0; i < 10; ++i){
            DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.GERMANY);
            TimeUnit.SECONDS.sleep(2);
            String readable = df.format(new Date());
            Message msg = new Message(null,null,readable);
            channel.send(msg);
        }

        channel.close();
    }

    public static void main(String[] args) throws Exception{
        System.setProperty("java.net.preferIPv4Stack","true");
        new Application().start();
    }
}
