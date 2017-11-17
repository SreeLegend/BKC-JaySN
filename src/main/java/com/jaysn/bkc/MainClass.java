package com.jaysn.bkc;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {

    public static Economy econ = null;
    public static EconomyResponse r;
    boolean autorespawn;
    boolean joinmessage;
    String killmessage;
    Double killmoney;

    public Mechanics mechs;


    @Override
    public void onEnable() {
        loadConfig();
        instancesClasses();

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        getLogger().info("BetterKilLCustomization is up and running smooth!");
        getLogger().info("| Version 1.0 Enabled  |");
        getLogger().info("|- Code by JaySN  |");
        getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        getServer().getPluginManager().registerEvents(new Mechanics(), this);

    }

    @Override
    public void onDisable(){
        saveConfig();
        getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        getLogger().info("BetterKilLCustomization is down !");
        getLogger().info("| Version 1.0 Disabled  |");
        getLogger().info("|- Code by JaySN  |");
        getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        getConfig().set("AllowJoinMessage", joinmessage);
        getConfig().set("AllowCustomRespawn", autorespawn);
        getConfig().set("KillMessage", killmessage);
        getConfig().set("KillMoney", killmoney);
        saveConfig();
    }

    public void instancesClasses(){
        mechs = new Mechanics();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("setlobbyspawn") && sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("bkc.admin")) {
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "You do not have the permission!");

            } else {
                getConfig().set("LobbySpawn.world", player.getWorld().getName());
                getConfig().set("LobbySpawn.x", player.getLocation().getX());
                getConfig().set("LobbySpawn.y", player.getLocation().getY());
                getConfig().set("LobbySpawn.z", player.getLocation().getZ());
                getConfig().set("LobbySpawn.yaw", player.getLocation().getYaw());
                getConfig().set("LobbySpawn.pitch", player.getLocation().getPitch());
                player.sendMessage(ChatColor.GREEN + "LobbySpawn has been set!");
            }
            return true;
        }
        return false;
    }
}
