package science.amberfall.itemtoken;


import org.bukkit.inventory.ItemStack;

public class GetItemsReturnData {
    private String message;
    private ItemStack items;

    GetItemsReturnData(String message, ItemStack items) {
        this.message = message;
        this.items = items;
    }

    public String getMessage() {
        return message;
    }

    public ItemStack getItems() {
        return items;
    }
}
