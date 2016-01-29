package com.ReanKR.rTutorial.Util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundCreative
{
	@SuppressWarnings("deprecation")
	public void PlayerSound(Player player, Sound sound, float volume, float pitch)
	{
    	if(Bukkit.getOfflinePlayer(player.getName()).isOnline()) player.playSound(player.getLocation(), sound ,volume, pitch);
	}
}
