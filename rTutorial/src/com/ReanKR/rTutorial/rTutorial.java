package com.ReanKR.rTutorial;

import com.ReanKR.rTutorial.File.FileLoader;
import com.ReanKR.rTutorial.Function.ProgressTutorial;
import com.ReanKR.rTutorial.Function.RegisterLocation;
import com.ReanKR.rTutorial.Util.ConfigManager;
import com.ReanKR.rTutorial.Util.ErrorReporter;
import com.ReanKR.rTutorial.Util.FileSection;
import com.ReanKR.rTutorial.Util.SoundCreative;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class rTutorial extends JavaPlugin implements Listener
{
	  public static int ConfigVersion;
	  public static boolean BlockMovement = false;
	  public static boolean BlockAllCommands = false;
	  public static boolean CompleteBroadcast = false;
	  public static boolean RunFirstJoinPlayer = true;
	  public static boolean EditComplete = false;
	  public static int DefaultDelaySeconds = 5;
	  public static int DefaultCooldownSeconds = 5;
	  public static boolean[] CompatiblePlugins;
	  public static boolean SoundDisabled = true;
	  public static List<String> ResultCommands;
	  public static List<ItemStack> ResultItems;
	  public static List<String> ErrorReporting;
	  public static List<String> LocationMethod;
	  public static List<String> MessageMethod;
	  public static int MethodAmount = 0;
	  public static Map<String, String> MainMessages;
	  public static HashMap<String, Boolean> TutorialComplete;
	  public static HashMap<Player, Boolean> ProgressingTutorial;
	  public static HashMap<Player, Boolean> IsCreateNewLocation;
	  public static HashMap<Player, Boolean> SavedNewLocation;
	  public static HashMap<Player, Integer> CreatingNewLocation;
	  public static HashMap<Player, String> MainMessage;
	  public static HashMap<Player, String> SubMessage;
	  public static HashMap<Player, String> TutorialStatus;
	  public static rTutorial RTutorial;
	  public static String Prefix = "§e[§9r§aT§futorial§e]§f ";
	  public static Plugin plugin;
	  private FileSection FS = new FileSection();
	  private FileLoader FL = new FileLoader(this);
	  private ConfigManager CM = new ConfigManager();
	  private ErrorReporter ER = new ErrorReporter();
	  private SoundCreative SC = new SoundCreative();
	  private RegisterLocation RL = new RegisterLocation();
	  private ConsoleCommandSender Server = Bukkit.getConsoleSender();
	  private ProgressTutorial PT = new ProgressTutorial(this);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onEnable()
	{
		RTutorial = this;
	    plugin = this;
	    CompatiblePlugins = new boolean[4];
	    ErrorReporting = new ArrayList();
	    ResultCommands = new ArrayList();
	    ResultItems = new ArrayList();
	    LocationMethod = new ArrayList();
	    MessageMethod = new ArrayList();
	    ProgressingTutorial = new HashMap();
	    TutorialComplete = new HashMap();
	    TutorialStatus = new HashMap();
	    IsCreateNewLocation = new HashMap();
	    CreatingNewLocation = new HashMap();
	    SavedNewLocation = new HashMap();
	    MainMessage = new HashMap();
	    SubMessage = new HashMap();
	    Bukkit.getPluginManager().registerEvents(new rTutorialListener(this), this);
	    this.FS.LoadFile("config");
	    this.FS.LoadFile("message");
	    this.FL.LoadCfg();
	    this.FL.LocationCfg();
	    this.CM.ScanPlugin();
	    this.FL.LoadMessage();
	    this.ER.ResultErrorReport();
	    if (CompatiblePlugins[1] == true)
	    {
	      this.Server.sendMessage(Prefix + ChatColor.YELLOW + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " now Enabled.");
	      this.Server.sendMessage(Prefix + "§bM§fade §bb§fy Rean KR,§9 whitehack97@gmail.com");
	      this.Server.sendMessage(Prefix + "§bD§fevoloper §bW§febsite §e: §fhttp://cafe.naver.com/suserver24");
	    }
	    else
	    {
	      this.Server.sendMessage(Prefix + "§cVault Required. rTutorial cannot running.");
	      Bukkit.getPluginManager().disablePlugin(this);
	    }
	  }
	
	  public void onDisable()
	  {
	    this.Server.sendMessage(Prefix + ChatColor.RED + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " now Disabled.");
	    this.Server.sendMessage(Prefix + "§bM§fade §bb§fy Rean KR,§9 whitehack97@gmail.com");
	    this.Server.sendMessage(Prefix + "§bD§fevoloper §bW§febsite §e: §fhttp://cafe.naver.com/suserver24§f");
	  }
	
	  public boolean onCommand(CommandSender Sender, Command command, String label, String[] args)
	  {
		  ConsoleCommandSender Console = Bukkit.getConsoleSender();
			String cmd = command.getName();
			if(cmd.equalsIgnoreCase("tutorial"))
			{
				Player player = (Player)Sender;
				if(! rTutorial.EditComplete)
				{
					/*
					for(Player players: Bukkit.getOnlinePlayers())
					{
					players.hidePlayer(player);
					}
					*/
					PT.TutorialCooldown(player);
					return true;
				}
				else
				{
					PT.TutorialCooldown(player);
					return true;
				}
				
			}
			if(cmd.equalsIgnoreCase("rtutorial.main"))
			{
					if(args.length < 1)
					{
						Player player = (Player)Sender;
						player.sendMessage(rTutorial.Prefix + "==============================================");
						player.sendMessage(rTutorial.Prefix + "§9r§aT§futorial v" + plugin.getDescription().getVersion());
						player.sendMessage(rTutorial.Prefix + "");
						player.sendMessage(rTutorial.Prefix + "/tutorial");
						player.sendMessage(rTutorial.Prefix + "Start tutorial.");
						player.sendMessage(rTutorial.Prefix + "");
						player.sendMessage(rTutorial.Prefix + "/rt | rtutorial set [complete | cancel]");
						player.sendMessage(rTutorial.Prefix + "");
						player.sendMessage(rTutorial.Prefix + "/rt | rtutorial delete <index>");
						player.sendMessage(rTutorial.Prefix + "Delete location information <index>.");
						player.sendMessage(rTutorial.Prefix + "");
						player.sendMessage(rTutorial.Prefix + "/rt | rtutorial reload");
						player.sendMessage(rTutorial.Prefix + "");
						player.sendMessage(rTutorial.Prefix + "==============================================");
						return true;
					}
					if(args[0].equalsIgnoreCase("reload"))
					{
						saveConfig();
					    this.FL.LoadCfg();
					    this.FL.LocationCfg();
					    this.CM.ScanPlugin();
					    this.FL.LoadMessage();
					    this.ER.ResultErrorReport();
						if(!(Sender instanceof Player)) Console.sendMessage(rTutorial.Prefix + "rTutorial reload complete");
						else Sender.sendMessage(rTutorial.Prefix + "rTutorial reload complete");
					}
					if(args[0].equalsIgnoreCase("set"))
					{
						Player player = (Player)Sender;
						if(player.isOp())
						{
							if(args.length < 2)
							{
								SC.PlayerSound(player, Sound.ITEM_PICKUP, 1.2F, 1.7F);
								if(rTutorial.MainMessage.containsKey(player)) rTutorial.MainMessage.remove(player);
								if(rTutorial.SubMessage.containsKey(player)) rTutorial.SubMessage.remove(player);
								if(rTutorial.CreatingNewLocation.containsKey(player)) rTutorial.CreatingNewLocation.remove(player);
								if(rTutorial.SavedNewLocation.containsKey(player)) rTutorial.SavedNewLocation.remove(player);
								player.sendMessage(rTutorial.Prefix + "메인 메세지를 채팅를 이용하여 적어주십시오.");
								rTutorial.IsCreateNewLocation.put(player, true);
								rTutorial.CreatingNewLocation.put(player, 0);
							}
							if(args.length >= 2)
							{
								if(args[1].equalsIgnoreCase("complete"))
								{
									if(rTutorial.SavedNewLocation.containsKey(player))
									{
										if(rTutorial.SavedNewLocation.get(player).booleanValue() && rTutorial.MainMessage.containsKey(player))
										{
											List<String> Messages = new ArrayList();
											Messages.add(rTutorial.MainMessage.get(player));
											Messages.add(rTutorial.SubMessage.get(player));
											RL.LocationRegister(player.getLocation(), Messages, 0);
											SC.PlayerSound(player, Sound.LEVEL_UP, 1.2F, 2.1F);
											player.sendMessage(rTutorial.Prefix + "§a위치가 정상적으로 저장되었습니다.");
											rTutorial.SavedNewLocation.remove(player);
											rTutorial.MainMessage.remove(player);
											rTutorial.SubMessage.remove(player);
											return true;
										}
										else if(! rTutorial.MainMessage.containsKey(player))
										{
											player.sendMessage(rTutorial.Prefix + "§c임시 저장된 위치 정보가 없습니다. /rt set로 다시 설정하여 주십시오.");
											return false;
										}
									}
									else
									{
											SC.PlayerSound(player, Sound.ANVIL_LAND, 1.2F, 1.7F);
											player.sendMessage(rTutorial.Prefix + "§cAt first, Use command /rt set");
											return false;
									}
								}
								if(args[1].equalsIgnoreCase("cancel"))
								{
									if(rTutorial.SavedNewLocation.containsKey(player))
									{
										if(rTutorial.SavedNewLocation.get(player).booleanValue() && rTutorial.MainMessage.containsKey(player))
										{
											rTutorial.MainMessage.remove(player);
											rTutorial.SubMessage.remove(player);
											rTutorial.SavedNewLocation.remove(player);
											SC.PlayerSound(player, Sound.ANVIL_LAND, 1.2F, 3.0F);
											player.sendMessage(rTutorial.Prefix + "§d설정이 취소되었습니다.");
											return true;
										}
										else
										{
											player.sendMessage(rTutorial.Prefix + "§c오류가 발생해 임시 저장된 위치 정보가 삭제되었습니다. /rt set로 다시 설정하여 주십시오.");
											return false;
										}
									}
									else if(rTutorial.IsCreateNewLocation.get(player).booleanValue())
									{
										rTutorial.IsCreateNewLocation.put(player, false);
										rTutorial.MainMessage.remove(player);
										rTutorial.SubMessage.remove(player);
										rTutorial.SavedNewLocation.remove(player);
										SC.PlayerSound(player, Sound.ANVIL_LAND, 1.2F, 3.0F);
										player.sendMessage(rTutorial.Prefix + "§d설정을 중간에 취소하였습니다.");
										return true;
									}
									else
									{
										SC.PlayerSound(player, Sound.ANVIL_LAND, 1.2F, 1.7F);
										player.sendMessage(rTutorial.Prefix + "취소할 사항이 없습니다.");
										return false;
									}
								}
							}
						}
						else
						{
							SC.PlayerSound(player, Sound.ANVIL_LAND, 1.2F, 1.7F);
							player.sendMessage(rTutorial.Prefix + "§c오피만 사용 가능한 명령어입니다.");
							return false;
						}
					}
			}
			return false;
		}
}