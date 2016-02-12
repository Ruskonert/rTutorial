package com.ReanKR.rTutorial.Util;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.ReanKR.rTutorial.rTutorial;
import com.ReanKR.rTutorial.Util.FileSection;
import com.ReanKR.rTutorial.Function.ProgressTutorial;

public class PlayerBackup
{
	private ProgressTutorial PT = new ProgressTutorial(rTutorial.RTutorial);
	@SuppressWarnings("deprecation")
	public static void StatusBackup(Player player)
	{
		
		if(! rTutorial.RTutorial.getServer().getOfflinePlayer(player.getName()).isOnline())
		{
			if(rTutorial.ProgressingTutorial.containsKey(player))
			{
				Bukkit.getConsoleSender().sendMessage(rTutorial.Prefix + "¡×cPlayer name : " + player.getName() + "does not playing in the game. Saving temp data");
			}
		}
		else
		{
			return;
		}
		FileSection FS = new FileSection();
		File file = new File("plugins/rTutorial/Backup.yml");
		ProgressTutorial PT = new ProgressTutorial(rTutorial.RTutorial);
		if(! file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration Yaml = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection CS = Yaml;
		if(! CS.contains(player.getName())) CS.createSection(player.getName());
		CS = FS.PlusSelect(CS, player.getName());
		CS.set("FlySpeed", PT.PlayerFlySpeed.get(player.getName()));
		CS.set("WalkSpeed", PT.PlayerSpeed.get(player.getName()));
		CS.set("Gamemode", PT.PlayerGameMode.get(player.getName()));
		try {
			Yaml.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
