package com.mineverse.main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {

	public static main pl;
	
	// Custom .yml files
    public File dataConfigFile;
    private FileConfiguration dataConfig;
	
	public int olddate;
	public List<String> already_claimed = null;
	public String timeleft;
	
	@Override
    public void onEnable() {
		
		pl = this;
		Bukkit.getPluginManager().registerEvents(this, pl);
		
		saveDefaultConfig();
		loadDataConfig();
		
		checkIfNewDate();
		
	}
	
	@Override
	public void onDisable() {
		pl = null;
	}
	
	
	
	public void checkIfNewDate() {
		
		olddate = pl.getDataConfig().getInt("date", 0);
		int currentDate = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis())));
		
		// time left
		int minutes = (int) ((86400000-(System.currentTimeMillis()%86400000))/60000) + 1;
		timeleft = "";
		if (minutes >= 60) timeleft += minutes/60+"h";
		timeleft += minutes%60+"m";
		
		if (olddate != currentDate) {
			
			ArrayList<String> noone = new ArrayList<String>();
			noone.add("");
			
			pl.getDataConfig().set("date", currentDate);
			pl.getDataConfig().set("list_of_people_on_cooldown", noone);
			pl.saveDataConfig();
			Bukkit.getConsoleSender().sendMessage("[" + pl.getName() + "] NEW DAY! (removed all cooldowns)");
		
		}
	}
	
	
	
	public static String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg.replace("%prefix%", pl.getConfig().getString("prefix", "&5[&dMineverse&5]")));
	}
	
	
	
	@EventHandler
	public void colorSign(SignChangeEvent e) {
	    	
		Player p = e.getPlayer();
		 
		// Colour signs
		if (p.hasPermission("signs.color")) {
			for (int i = 0; i < 4; i++) {
				String line = e.getLines()[i];
				line = color(line);
				e.setLine(i, line);
			}
		}
		
		// Create parkour sign
		if (!p.hasPermission("signs.parkour")) {
			String parkourline1 = color(pl.getConfig().getString("signs.claim_sign.1").replace("{reward}", String.valueOf(pl.getConfig().getInt("parkour.reward", 15))));
			if (color(e.getLines()[0]).equals(parkourline1)) e.setCancelled(true);
		}
	}
	
	
	
	@EventHandler
    public void signInteract(PlayerInteractEvent e) {
    	
		Block block = e.getClickedBlock();
		Player p = e.getPlayer();
		Action action = e.getAction();
    
		if (action != Action.RIGHT_CLICK_BLOCK || block == null || block.getType() == Material.AIR) return;

		if (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
			
			checkIfNewDate();
                      
			Sign sign = (Sign) block.getState();
			
    		int parkour_reward = pl.getConfig().getInt("reward.reward", 0);
    		String parkour_rewardCMD = pl.getConfig().getString("reward.command").replace("{user}", p.getName()).replace("{reward}", String.valueOf(parkour_reward));
    		if (already_claimed == null) already_claimed = pl.getDataConfig().getStringList("list_of_people_on_cooldown");
    		
    		String parkour_reward_yes = pl.getConfig().getString("messages.success").replace("{user}", p.getName()).replace("{reward}", String.valueOf(parkour_reward)).replace("{timeleft}", timeleft);
    		String parkour_reward_no = pl.getConfig().getString("messages.fail").replace("{user}", p.getName()).replace("{reward}", String.valueOf(parkour_reward)).replace("{timeleft}", timeleft);
    		
    		String parkourline1 = color(pl.getConfig().getString("signs.claim_sign.1").replace("{reward}", String.valueOf(parkour_reward)));
    		String parkourline2 = color(pl.getConfig().getString("signs.claim_sign.2").replace("{reward}", String.valueOf(parkour_reward)));
    		String parkourline3 = color(pl.getConfig().getString("signs.claim_sign.3").replace("{reward}", String.valueOf(parkour_reward)));
    		String parkourline4 = color(pl.getConfig().getString("signs.claim_sign.4").replace("{reward}", String.valueOf(parkour_reward)));
    
    		String claimedline1 = color(pl.getConfig().getString("signs.already_claimed_sign.1").replace("{user}", p.getName()).replace("{reward}", String.valueOf(parkour_reward)).replace("{timeleft}", timeleft));
            String claimedline2 = color(pl.getConfig().getString("signs.already_claimed_sign.2").replace("{user}", p.getName()).replace("{reward}", String.valueOf(parkour_reward)).replace("{timeleft}", timeleft));
            String claimedline3 = color(pl.getConfig().getString("signs.already_claimed_sign.3").replace("{user}", p.getName()).replace("{reward}", String.valueOf(parkour_reward)).replace("{timeleft}", timeleft));
            String claimedline4 = color(pl.getConfig().getString("signs.already_claimed_sign.4").replace("{user}", p.getName()).replace("{reward}", String.valueOf(parkour_reward)).replace("{timeleft}", timeleft));
    		
    		String sign1 = sign.getLine(0);
            String sign2 = sign.getLine(1);
            String sign3 = sign.getLine(2);
            String sign4 = sign.getLine(3);
		
            if (sign1.contains(parkourline1) && sign2.contains(parkourline2) && sign3.contains(parkourline3) && sign4.contains(parkourline4)) {
          	  
            	if (already_claimed != null && !already_claimed.contains(p.getUniqueId().toString())) {
    				
    				already_claimed.add(p.getUniqueId().toString());
    				pl.getDataConfig().set("list_of_people_on_cooldown", already_claimed);
    				pl.saveDataConfig();
            		
    				
    				p.playSound(p.getLocation(), Gsound.BLOCK_NOTE_BLOCK_PLING.parseSound(), 1.0F, 1.0F);
    					
            		
        			p.sendMessage(color(parkour_reward_yes));
        			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), parkour_rewardCMD);
        			
        			Bukkit.getConsoleSender().sendMessage("[Parkour Reward] "+p.getName()+" claimed "+String.valueOf(parkour_reward)+"!");
            	
            	} else {
            	
            		p.playSound(p.getLocation(), Gsound.BLOCK_NOTE_BLOCK_BASS.parseSound(), 1.0F, 1.0F);
        			p.sendMessage(color(parkour_reward_no));
        			String[] newlines = {claimedline1, claimedline2, claimedline3, claimedline4};
        			p.sendSignChange(sign.getLocation(), newlines);
            	
            	}
            }  
		} 
	}
	
	
	
	public FileConfiguration getDataConfig() {
        return this.dataConfig;
    }
	
	
	
	public void saveDataConfig() {
		dataConfigFile = new File(getDataFolder(), "data.yml");
		try {
			getDataConfig().save(dataConfigFile);
		} catch (IOException ex) {
        	Bukkit.getConsoleSender().sendMessage("[" + this.getName() + "] Failed to save data.yml!");
			ex.printStackTrace();
			return;
		}
	}
	
	
	
	public void loadDataConfig() {
        dataConfigFile = new File(getDataFolder(), "data.yml");
        if (!dataConfigFile.exists()) {
            dataConfigFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
            Bukkit.getConsoleSender().sendMessage("[" + this.getName() + "] Created the data.yml!");
        }
 
        dataConfig = new YamlConfiguration();
        try {
        	dataConfig.load(dataConfigFile);
        } catch (IOException | InvalidConfigurationException ex) {
        	Bukkit.getConsoleSender().sendMessage("[" + this.getName() + "] Failed to load data.yml!");
            ex.printStackTrace();
			return;
        }
    }
	
	
	
}
