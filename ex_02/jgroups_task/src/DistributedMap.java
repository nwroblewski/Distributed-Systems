import org.jgroups.util.Util;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class DistributedMap implements SimpleStringMap{

    private HashMap<Integer,String> storage;


    public DistributedMap(HashMap<Integer,String> storage){
        this.storage = storage;
    }

    @Override
    public boolean containsKey(String key) {
        return false;
    }

    @Override
    public Integer get(String key) {
        return null;
    }

    @Override
    public void put(String key, Integer value) {

    }

    @Override
    public Integer remove(String key) {
        return null;
    }

    private void getState(OutputStream output) throws Exception {
        synchronized(storage){
            Util.objectToStream(storage, new DataOutputStream(output));
        }
    }

    private void setState(InputStream input) throws Exception{

    }
}
