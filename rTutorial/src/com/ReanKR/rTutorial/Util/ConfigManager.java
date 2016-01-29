package com.ReanKR.rTutorial.Util;

import com.ReanKR.rTutorial.API.EconomyAPI;
import com.ReanKR.rTutorial.rTutorial;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager
{
  public void ScanPlugin()
  {
    Server server = rTutorial.plugin.getServer();
    if (!server.getPluginManager().isPluginEnabled("TitleAPI"))
    {
      if (rTutorial.CompatiblePlugins[0] == true)
      {
        rTutorial.ErrorReporting.add("Compatible,TitleAPI_NOT_FOUND");
        SwitchCompatiblePlugin("TitleAPI");
      }

    }
    else
    {
        if (rTutorial.CompatiblePlugins[0] == true)
        {
        	server.getConsoleSender().sendMessage(rTutorial.Prefix + "¡×3TitleAPI Hooked");
        }
    }

    if (server.getPluginManager().isPluginEnabled("Vault"))
    {
      if (rTutorial.CompatiblePlugins[1] == false)
      {
        Bukkit.getConsoleSender().sendMessage(rTutorial.Prefix + "¡×dVault Hooked");
        SwitchCompatiblePlugin("Vault");
      }
      else
      {
        Bukkit.getConsoleSender().sendMessage(rTutorial.Prefix + "¡×dVault Hooked");
      }
    }

    if (!server.getPluginManager().isPluginEnabled("Vault"))
    {
      if (rTutorial.CompatiblePlugins[1] == true)
      {
        rTutorial.ErrorReporting.add("Compatible,Vault_NOT_FOUND");
        SwitchCompatiblePlugin("Vault");
      }
    }

    if (!server.getPluginManager().isPluginEnabled("Vault"))
    {
      if (rTutorial.CompatiblePlugins[2] == true)
      {
        rTutorial.ErrorReporting.add("Compatible,Economy_CANNOT_FOUND");
        SwitchCompatiblePlugin("Economy");
      }

    }
   
    if (rTutorial.CompatiblePlugins[2] == true)
    {
      try
      {
        Economy echo = EconomyAPI.getEconomy();
        if (!echo.isEnabled())
        {
          rTutorial.ErrorReporting.add("Compatible,Economy_NOT_FOUND");
          SwitchCompatiblePlugin("Economy");
        }
        else
        {
          server.getConsoleSender().sendMessage(rTutorial.Prefix + ChatColor.GREEN + echo.getName() + " Hooked");
        }

      }
      catch (NullPointerException e)
      {
        rTutorial.ErrorReporting.add("Compatible,Economy_NOT_FOUND");
        SwitchCompatiblePlugin("Economy");
      }
    }
  }

  public void SwitchCompatiblePlugin(String PluginName)
  {
    FileConfiguration Config = rTutorial.plugin.getConfig();
    ConfigurationSection MainNode = Config.getConfigurationSection("Compatibles");
    ConfigurationSection CS = Config.getConfigurationSection("Compatibles");
    CS.set(PluginName, Boolean.valueOf(!CS.getBoolean(PluginName)));
    rTutorial.plugin.saveConfig();
    rTutorial.CompatiblePlugins[0] = MainNode.getBoolean("TitleAPI");
    rTutorial.CompatiblePlugins[1] = MainNode.getBoolean("Vault");
    rTutorial.CompatiblePlugins[2] = MainNode.getBoolean("Economy");
  }
}