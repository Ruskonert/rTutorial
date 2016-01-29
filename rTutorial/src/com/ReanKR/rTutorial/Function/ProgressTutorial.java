package com.ReanKR.rTutorial.Function;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ReanKR.rTutorial.rTutorial;
import com.ReanKR.rTutorial.API.EconomyAPI;
import com.ReanKR.rTutorial.File.FileLoader;
import com.ReanKR.rTutorial.Util.SoundCreative;
import com.ReanKR.rTutorial.Util.SubSection;
import com.connorlinfoot.titleapi.TitleAPI;

import net.milkbowl.vault.economy.Economy;

public class ProgressTutorial
{
	public Map<String, Integer> taskID = new HashMap<String, Integer>();
	public Map<String, Integer> Progress = new HashMap<String, Integer>();
	public Map<String, GameMode> PlayerGameMode = new HashMap<String, GameMode>();
	public Map<String, Float> PlayerSpeed = new HashMap<String, Float>();
	public Map<String, Float> PlayerFlySpeed = new HashMap<String, Float>();

	rTutorial plugin;

	public ProgressTutorial(rTutorial plugin)
	{
		this.plugin = plugin;
	}

	public void TutorialCooldown(final Player p)
	{
		SoundCreative SC = new SoundCreative();
		final int tid = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
		{
			@Override
			public void run()
			{
				if(Progress.get(p.getName()) <= 0)
				{
					endTask(p, false);
					Working(p);
				}
				else
				{
					SC.PlayerSound(p, Sound.NOTE_PLING, 10.0F, 1.0F);
					if(rTutorial.CompatiblePlugins[0])
					{
						if(Progress.get(p.getName()) >= 2) TitleAPI.sendTitle(p, 0, 0, 30, SubSection.VariableSub(FileLoader.SubMsg("LeftSecondsMessage", p, true, false),
								Progress.get(p.getName())), "rTutorial " + plugin.getDescription().getVersion());
						else TitleAPI.sendTitle(p, 0, 0, 30, SubSection.VariableSub(FileLoader.SubMsg("LeftSecondsMessage", p, true, false),
								Progress.get(p.getName())), "Let's go tutorial");
					}
					else
					{
						p.sendMessage(rTutorial.Prefix + SubSection.VariableSub(FileLoader.SubMsg("LeftSecondsMessage", p, true, false), Progress.get(p.getName())));
					}
					Progress.put(p.getName(), Progress.get(p.getName()) - 1);
				}
			}

		},0L, 20L);
		taskID.put(p.getName(), tid);
		Progress.put(p.getName(), rTutorial.DefaultCooldownSeconds);
		PlayerGameMode.put(p.getName(), p.getGameMode());
		PlayerSpeed.put(p.getName(), p.getWalkSpeed());
		PlayerFlySpeed.put(p.getName(), p.getFlySpeed());
	}

	public void Working(final Player p)
	{
		SoundCreative SC = new SoundCreative();
		final int tid = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
		{
			@Override
			public void run()
			{
				if(Progress.get(p.getName()) >= rTutorial.MethodAmount)
				{
					endTask(p, true);
					Result(p);
				}
				else
				{
					String[] LocationInfo = rTutorial.LocationMethod.get(Progress.get(p.getName())).split(",");
					Location Loc = new Location(plugin.getServer().getWorld(LocationInfo[0]), Double.parseDouble(LocationInfo[1]), 
							Double.parseDouble(LocationInfo[2]), Double.parseDouble(LocationInfo[3]), Float.parseFloat(LocationInfo[4]), Float.parseFloat(LocationInfo[5]));
					String[] Cutter = rTutorial.MessageMethod.get(Progress.get(p.getName())).split(",");
					p.teleport(Loc);
					SC.PlayerSound(p, Sound.LEVEL_UP, 8.0F, 8.0F);
					if(rTutorial.CompatiblePlugins[0])
					{
						TitleAPI.sendTitle(p, 20, 20, rTutorial.DefaultDelaySeconds*20, ChatColor.translateAlternateColorCodes('&', Cutter[0]), ChatColor.translateAlternateColorCodes('&', Cutter[1]));
					}
					else
					{
						p.sendMessage(rTutorial.Prefix + ChatColor.translateAlternateColorCodes('&', Cutter[0]));
					}
					Progress.put(p.getName(), Progress.get(p.getName()) + 1);
				}
			}
		}, 0L, rTutorial.DefaultDelaySeconds*20L);
		taskID.put(p.getName(), tid);
		Progress.put(p.getName(), 0);
		p.setGameMode(GameMode.SPECTATOR);
		p.setFlySpeed(0.0F);
		p.setWalkSpeed(0.0F);
	}
	
	
	public void Result(Player p)
	{
		for(String Commands : rTutorial.ResultCommands)
		{
			String[] Cutter = Commands.split(": ");
			if(Commands.contains("Console"))
			{
				plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), SubSection.Sub(Cutter[1], p));
			}
			
			else if(Commands.contains("Money"))
			{
				Economy Echo = EconomyAPI.getEconomy();
				Echo.depositPlayer(p, Double.parseDouble(Cutter[1]));
			}
			else
			{
				plugin.getServer().dispatchCommand(p, SubSection.Sub(Commands, p));
			}
		}
		for(ItemStack Items : rTutorial.ResultItems)
		{
			p.getInventory().addItem(Items);
		}
	}
	
	public void endTask(Player p, boolean CompleteTutorial)
	{
		if(taskID.containsKey(p.getName()))
		{
			int tid = taskID.get(p.getName());
			plugin.getServer().getScheduler().cancelTask(tid);
			taskID.remove(p.getName());
			Progress.remove(p.getName());
		}
		if(CompleteTutorial)
		{
			p.setGameMode(PlayerGameMode.get(p.getName()));
			p.setWalkSpeed(PlayerSpeed.get(p.getName()));
			p.setFlySpeed(PlayerFlySpeed.get(p.getName()));
		}
	}
}
