package DMHandlers;

import java.io.Serializable;

public class DMMessage implements Serializable {
    final public String type;
    final public String key;
    final public Integer value;

    private DMMessage(String type, String key, Integer value){
        this.type = type;
        this.key = key;
        this.value = value;
    }
    @Override
    public String toString(){
        return "Message:\ntype : " + type + "\nkey: " + key + "\nvalue: " + value;
    }

    public static DMMessage makePutDMMessage(String key, Integer value){
        return new DMMessage("PUT",key,value);

    }

    public static DMMessage makeRemoveDMMessage(String key){
        return new DMMessage("REMOVE",key,null);
    }
}