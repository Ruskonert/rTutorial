package com.ReanKR.rTutorial.Util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.ReanKR.rTutorial.rTutorial;
import com.ReanKR.rTutorial.Util.FileSection;
import com.ReanKR.rTutorial.Function.ProgressTutorial;

public class PlayerBackup
{
	public void StatusBackup(Player player)
	{
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
		CS.createSection(player.getName());
		CS = FS.PlusSelect(CS, player.getName());
	}

}
