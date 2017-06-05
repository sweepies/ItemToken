package science.amberfall.itemtoken;

import com.earth2me.essentials.Essentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public final class ItemToken extends JavaPlugin implements Listener {

    Essentials ess;

    private static ItemToken plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        if (Bukkit.getServer().getPluginManager().getPlugin("Essentials") != null) {
            this.ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        } else {
            getLogger().severe("This plugin depends on Essentials to run, which was not found.");
            disableMe();
        }

        getLogger().info(getDataFolder().getAbsolutePath() + "/data");
        File dataDir = new File(getDataFolder().getAbsolutePath() + "/data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }

    @Override
    public void onDisable() {}

    // Print a message and disable the plugin
    private void disableMe() {
        getLogger().severe("Disabling plugin.");
        Bukkit.getPluginManager().disablePlugin(this);
    }

    private String md5(byte[] b) {
        // Get md5 digest of b
        try {
            return DigestUtils.md5Hex(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof ConsoleCommandSender)) {
            Player player = (Player) sender;
            if (label.toLowerCase().matches("(itemtoken|it)")) {
                if (args.length > 0) {
                    if (args[0].toLowerCase().matches("(create|c)")) {
                        if (sender.hasPermission("itemtoken.create")) {

                            if (args.length >= 2) {

                                String item = args[1];

                                try {

                                    if (args.length >= 3) {

                                        String token = args[2];

                                        if (args.length >= 4) {

                                            Integer amount = Integer.parseInt(args[3]);

                                            ItemStack stack = ess.getItemDb().get(item, amount);

                                            if (stack == null) {
                                                sender.sendMessage("Item not found: " + item);
                                                return true;
                                            }

                                            String fileName = md5(token.getBytes());

                                            File file = new File(getDataFolder() + "/data/" + fileName + ".json");
                                            if (file.exists()) {
                                                sender.sendMessage(ChatColor.RED + "Specified token already exists.");
                                                return true;
                                            } else {
                                                HashMap<String, Object> data = new HashMap<>();
                                                HashMap<String, String> createdBy = new HashMap<>();
                                                createdBy.put("userName", player.getName());
                                                createdBy.put("uuid", player.getUniqueId().toString());
                                                createdBy.put("ipAddr", player.getAddress().getHostString());
                                                data.put("item", item);
                                                data.put("amount", amount.toString());
                                                data.put("token", token);
                                                data.put("timestamp", (int) System.currentTimeMillis());
                                                data.put("createdBy", createdBy);
                                                data.put("used", false);
                                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                                String json = gson.toJson(data);
                                                FileUtils.writeStringToFile(file, json, "UTF-8");
                                                sender.sendMessage(ChatColor.GOLD + "Token '" + token + "' created for item " + stack.getType().name() + " x " + amount);
                                            }
                                        } else {
                                            sender.sendMessage(ChatColor.RED + "Missing argument. Usage: /itemtoken create <item> <token> <amount>");
                                            return true;
                                        }
                                    } else {
                                        sender.sendMessage(ChatColor.RED + "Missing argument. Usage: /itemtoken create <item> <token> <amount>");
                                        return true;
                                    }

                                } catch (Exception e) {
                                    sender.sendMessage("Item not found: " + item);
                                    e.printStackTrace();
                                    return true;
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "Missing argument. Usage: /itemtoken create <item> <token> <amount>");
                                return true;
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "You do not have access to this command.");
                        }
                    } else if (args[0].toLowerCase().matches("(get|g)")) {
                        if (sender.hasPermission("itemtoken.get")) {
                            if (args.length >= 2) {
                                String token = args[1];
                                String fileName = md5(token.getBytes());
                                File file = new File(getDataFolder() + "/data/" + fileName + ".json");
                                if (!file.exists()) {
                                    sender.sendMessage(ChatColor.RED + "Invalid token: " + token);
                                } else {
                                    try {

                                        String jsonIn = FileUtils.readFileToString(file, "UTF-8");
                                        Gson gsonIn = new Gson();
                                        TokenData tokenData = gsonIn.fromJson(jsonIn, TokenData.class);

                                        if (!tokenData.isUsed()) {
                                            ItemStack item = ess.getItemDb().get(tokenData.getItem(), Integer.parseInt(tokenData.getAmount()));
                                            player.getInventory().addItem(item);
                                            sender.sendMessage(ChatColor.GOLD + "Received " + item.getType().name() + " x " + tokenData.getAmount());
                                            HashMap<String, Object> data = new HashMap<>();
                                            data.put("item", tokenData.getItem());
                                            data.put("amount", tokenData.getAmount());
                                            data.put("token", tokenData.getToken());
                                            data.put("timestamp", tokenData.getTimestamp());
                                            data.put("createdBy", tokenData.getCreatedBy());
                                            data.put("used", true);
                                            Gson gsonOut = new GsonBuilder().setPrettyPrinting().create();
                                            String jsonOut = gsonOut.toJson(data);
                                            FileUtils.writeStringToFile(file, jsonOut, "UTF-8");
                                        } else {
                                            sender.sendMessage(ChatColor.RED + "Invalid token: " + token);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "Missing argument. Usage: /itemtoken get <token>");
                            }
                        }
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + " ---- " + ChatColor.GOLD + "ItemToken Help" + ChatColor.YELLOW + " -- " + ChatColor.GOLD + "Page " + ChatColor.RED + "1" + ChatColor.GOLD + "/" + ChatColor.RED + "1" + ChatColor.YELLOW + " ----");
                        sender.sendMessage(ChatColor.GOLD + "/itemtoken create <item> <token> <amount>" + ChatColor.WHITE + ": Create a token for a stack of items.");
                        sender.sendMessage(ChatColor.GOLD + "/itemtoken get <token>" + ChatColor.WHITE + ": Get the stack of items from a token.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Unknown argument. See /itemtoken help for more info.");
                }

            }
        } else {
            getLogger().warning("That command cannot be run by the console.");
            return true;
        }
        return true;
    }
}
