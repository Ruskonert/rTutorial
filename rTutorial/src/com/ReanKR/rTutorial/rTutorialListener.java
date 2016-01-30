package com.ReanKR.rTutorial;

import com.ReanKR.rTutorial.Util.FileSection;
import com.ReanKR.rTutorial.Util.SoundCreative;
import java.io.File;
import java.io.IOException;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class rTutorialListener implements Listener
{
	  private Plugin plugin;
	
	  public rTutorialListener(rTutorial Main)
	  {
	    this.plugin = Main;
	  }

	  @EventHandler
	  public void PlayerInteract(PlayerInteractEvent e)
	  {
		  
	  }
	  
	  @EventHandler
	  public void PlayerCommand(PlayerCommandPreprocessEvent e)
	  {
	  }
	  
	  @EventHandler
	  public void PlayerQuit(PlayerQuitEvent e)
	  {
		  if(rTutorial.ProgressingTutorial.containsKey(e.getPlayer()))
		  {
			  if(rTutorial.ProgressingTutorial.get(e.getPlayer()))
			  {
				  
			  }
		  }
	  }
	
	  @EventHandler
	  public void PlayerJoin(PlayerJoinEvent e)
	  {
	    File file = new File("plugins/rTutorial/Player.yml");
	    if (!file.exists()) {
	      try
	      {
	        file.createNewFile();
	      } catch (IOException e1) {
	        e1.printStackTrace();
	      }
	    }
	    YamlConfiguration PlayerYaml = YamlConfiguration.loadConfiguration(file);
	    FileSection fs = new FileSection();
	
	    Player player = e.getPlayer();
	    String UUID = e.getPlayer().getUniqueId().toString();
	    ConfigurationSection CS = PlayerYaml;
	    if (!CS.isConfigurationSection(UUID))
	    {
	      CS.createSection(UUID);
	      ConfigurationSection PlayerInfo = fs.PlusSelect(CS, UUID);
	      PlayerInfo.set("PlayerName", player.getName());
	      PlayerInfo.set("Completed", Boolean.valueOf(false));
	      PlayerInfo.set("Status", "None");
	      try {
	        PlayerYaml.save(file);
	      }
	      catch (IOException error) {
	        error.printStackTrace();
	      }
	    }
	    ConfigurationSection PlayerInfo = fs.PlusSelect(CS, UUID);
	    rTutorial.TutorialComplete.put(UUID, Boolean.valueOf(PlayerInfo.getBoolean("Completed")));
	  }
	  
		@EventHandler
	  	public void CreatingLocation(AsyncPlayerChatEvent e)
	  	{
	  		SoundCreative SC = new SoundCreative();
	  		Player player = e.getPlayer();
	  		if(rTutorial.IsCreateNewLocation.containsKey(player) && player.isOp())
	  		{
	  			if(rTutorial.IsCreateNewLocation.get(player).booleanValue() && rTutorial.CreatingNewLocation.containsKey(player))
	  			{
	  				int Value = rTutorial.CreatingNewLocation.get(player).intValue();
	  				switch(Value)
	  				{
		  				case 0:
		  				{
		  					rTutorial.MainMessage.put(player, e.getMessage());
		  					SC.PlayerSound(e.getPlayer(), Sound.ITEM_PICKUP, 1.2F, 2.0F);
		  					player.sendMessage(rTutorial.Prefix + "");
		  					player.sendMessage(rTutorial.Prefix + "보조 메세지를 채팅을 이용해 입력하세요.");
		  					player.sendMessage(rTutorial.Prefix + "(TitleAPI 사용시 해당, 없으면 없음 또는 None이라고 입력)");
		  					break;
		  				}
		  				case 1:
		  				{
		  					rTutorial.SubMessage.put(player, e.getMessage());
		  					SC.PlayerSound(e.getPlayer(), Sound.ITEM_PICKUP, 1.2F, 2.0F);
		  					player.sendMessage(rTutorial.Prefix + "");
		  					player.sendMessage(rTutorial.Prefix + "메인 메세지 : " + rTutorial.MainMessage.get(player));
		  					player.sendMessage(rTutorial.Prefix + "보조 메세지 : " + rTutorial.SubMessage.get(player));
		  					player.sendMessage(rTutorial.Prefix + "설정이 모두 완료되었습니다. /rt set complete를 입력해주세요.");
		  					player.sendMessage(rTutorial.Prefix + "만약 설정이 잘못되었다면, /rt set cancel를 입력해주세요.");
		  				}
	  				}
	  				if(Value + 1 >= 2)
	  				{
	  					rTutorial.SavedNewLocation.put(player, true);
	  					rTutorial.CreatingNewLocation.remove(player);
	  					rTutorial.IsCreateNewLocation.put(player, false);
	  				}
	  				else
	  				{
	  					rTutorial.CreatingNewLocation.put(player, Value + 1);
	  				}
	  				e.setCancelled(true);
	  			}
	  		}
	  	}
}
