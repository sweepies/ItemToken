package science.amberfall.itemtoken;

import java.util.HashMap;

public class TokenData {
    public String item;
    public String amount;
    public String token;
    public int createdAt;
    public HashMap<String, Object> createdBy;
    public boolean used;
    public int usedAt;
    public HashMap<String, Object> usedBy;

    public String getToken() {
        return token;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public HashMap<String, Object> getCreatedBy() {
        return createdBy;
    }

    public boolean isUsed() {
        return used;
    }

    public int getUsedAt() {
        return usedAt;
    }

    public HashMap<String, Object> getUsedByBy() {
        return usedBy;
    }

    public String getItem() {
        return item;
    }

    public String getAmount() {
        return amount;
    }
}
