package com.jaysn.bkc;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {

    public static Economy econ = null;
    public static EconomyResponse r;

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
}
