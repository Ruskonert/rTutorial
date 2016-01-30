package com.ReanKR.rTutorial.Util;

import com.ReanKR.rTutorial.rTutorial;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileSection
{
  public YamlConfiguration LoadFile(String Path)
  {
    if (!Path.endsWith(".yml"))
    {
      Path = Path + ".yml";
    }
    File file = new File("plugins/rTutorial/" + Path);
    if (!file.exists())
    {
      try
      {
        rTutorial.plugin.saveResource(Path, true);
        Bukkit.getConsoleSender().sendMessage(rTutorial.Prefix + "Create New File " + file.getAbsolutePath());
      }
      catch (NullPointerException e)
      {
        e.printStackTrace();
        Bukkit.getConsoleSender().sendMessage(rTutorial.Prefix + "Cannot save " + Path);
        return null;
      }
    }
    YamlConfiguration Config = YamlConfiguration.loadConfiguration(file);
    return Config;
  }

  public ConfigurationSection PlusSelect(ConfigurationSection CS, String Name)
  {
    return CS.getConfigurationSection(Name);
  }
  
  public static void SetKey(File file, ConfigurationSection CS, String Key, Object object)
  {
	  YamlConfiguration Yaml = YamlConfiguration.loadConfiguration(file);
	  CS.set(Key, object);
	  try
	  {
		 Yaml.save(file);
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
  }
}