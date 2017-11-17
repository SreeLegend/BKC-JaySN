package com.jaysn.bkc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Mechanics implements Listener{

    private MainClass plugin = MainClass.getPlugin(MainClass.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player p = event.getPlayer();

        if (plugin.getConfig().contains("AllowJoinMessage")){
            if (plugin.getConfig().getBoolean("AllowJoinMessage")){
                p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "BetterKillCustomization Version 1.0 - JaySN");
            }
        }

    }

    @EventHandler
    public void killBalance(EntityDamageEvent event){
        if (event.getEntity() instanceof Player && event.getFinalDamage() >= ((Player) event.getEntity()).getHealth()){
            Player player = (Player) event.getEntity();

            if (plugin.getConfig().getBoolean("AllowCustomRespawn")) {
                if (plugin.getConfig().contains("LobbySpawn")) {

                    World world = Bukkit.getWorld(plugin.getConfig().getString("LobbySpawn.world"));
                    double x = plugin.getConfig().getDouble("LobbySpawn.x");
                    double y = plugin.getConfig().getDouble("LobbySpawn.y");
                    double z = plugin.getConfig().getDouble("LobbySpawn.z");
                    double yaw = plugin.getConfig().getDouble("LobbySpawn.yaw");
                    double pitch = plugin.getConfig().getDouble("LobbySpawn.pitch");
                    Location lobbyspawn = new Location(world, x, y, z, (float) yaw, (float) pitch);
                    event.setCancelled(true);
                    ((Player) event.getEntity()).setHealth(20);
                    event.getEntity().teleport(lobbyspawn);
                } else {

                    player.sendMessage(ChatColor.RED + "&c You did not set the lobbyspawn");
                }
            }
            Player killed = (Player) event.getEntity();
            Player killer = killed.getKiller();

            try {
                MainClass.econ.depositPlayer(((Player) event.getEntity()).getKiller(), 10);

            }catch(NoSuchMethodError e) {

                MainClass.econ.depositPlayer(killed.getName(), 10);
            }

            if(MainClass.r.transactionSuccess()) {
                killer.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("KillMessage")));
            }

        }
    }

}
