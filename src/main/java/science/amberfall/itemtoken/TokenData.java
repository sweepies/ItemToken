package science.amberfall.itemtoken;

import java.util.HashMap;

public class TokenData {
    private String item;
    private String amount;
    private String token;
    private int createdAt;
    private HashMap<String, Object> createdBy;
    private boolean used;
    private int usedAt;
    private HashMap<String, Object> usedBy;

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
