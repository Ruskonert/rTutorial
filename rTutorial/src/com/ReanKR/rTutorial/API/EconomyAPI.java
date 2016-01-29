package com.ReanKR.rTutorial.API;

import com.ReanKR.rTutorial.rTutorial;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyAPI
{
  public static Economy getEconomy()
  {
    Economy ecoHook = null;

    RegisteredServiceProvider<Economy> economyProvider = rTutorial.plugin.getServer().getServicesManager().getRegistration(Economy.class);
    if (economyProvider != null)
    {
      ecoHook = (Economy)economyProvider.getProvider();
    }
    return ecoHook;
  }
}