package DMHandlers;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MergeView;
import org.jgroups.View;

import java.util.LinkedList;
import java.util.List;

public class MHandler implements Runnable{

    final private MergeView view;
    final private JChannel channel;

    public MHandler(MergeView view, JChannel channel){
        this.view = view;
        this.channel = channel;
    }

    @Override
    public void run() {

        List<View> subgroups = new LinkedList<>(view.getSubgroups());
        subgroups.sort((gr1, gr2) -> {
            if (gr1.size() == gr2.size()) {
                return gr1.compareTo(gr2);
            } else {
                return gr1.size() - gr2.size();
            }
        });
        View main = subgroups.get(0);
        Address localAddr = channel.getAddress();

        try {
            if (!main.getMembers().contains(localAddr)) {
                System.err.println("Resetting node after partitions merge");
                channel.getState(null, 0);
            } else {
                System.err.println("Node is in the main partition after merge");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
