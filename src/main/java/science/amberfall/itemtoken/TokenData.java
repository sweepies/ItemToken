package science.amberfall.itemtoken;

import java.util.HashMap;

public class TokenData {
    public String item;
    public String amount;
    public String token;
    public int timestamp;
    public HashMap<String, Object> createdBy;
    public boolean used;

    public String getToken() {
        return token;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public HashMap<String, Object> getCreatedBy() {
        return createdBy;
    }

    public boolean isUsed() {
        return used;
    }


    public String getItem() {
        return item;
    }

    public String getAmount() {
        return amount;
    }
}
