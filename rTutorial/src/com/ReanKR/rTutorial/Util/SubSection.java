package com.ReanKR.rTutorial.Util;

import org.bukkit.entity.Player;

public class SubSection
{
	public static String Sub(String str, Player p)
	{
		String replace = str.replaceAll("%player%", p.getName());
		return replace;
	}
	
	public static String VariableSub(String str, Object object)
	{
		String replace = str.replaceAll("%var%", String.valueOf(object));
		return replace;
	}
}
