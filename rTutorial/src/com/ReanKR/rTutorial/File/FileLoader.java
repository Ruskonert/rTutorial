package com.ReanKR.rTutorial.File;

import com.ReanKR.rTutorial.Util.FileSection;
import com.ReanKR.rTutorial.rTutorial;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class FileLoader
{
  private FileSection FS = new FileSection();
  public static rTutorial plugin;

  public FileLoader(rTutorial Main)
  {
    plugin = Main;
  }

public void LoadCfg()
  {
    FileConfiguration ConfigFile = FS.LoadFile("config");
    Set<String> Keyword = ConfigFile.getKeys(false);
    try
    {
      for (String Str : Keyword)
      {
        ConfigurationSection MainNode = ConfigFile.getConfigurationSection(Str);
        try
        {
          boolean ProblemNotFound = true;
          if (Str.equalsIgnoreCase("Main"))
          {
            if (!MainNode.isSet("Config-Version"))
            {
              rTutorial.ErrorReporting.add(Str + ",MISSING_CONFIG_VERSION");
              ProblemNotFound = false;
            }

            if (!MainNode.isSet("Run-First-Join-Player"))
            {
              rTutorial.ErrorReporting.add(Str + ",MISSING_VALUE_RUN_FIRST_JOIN_PLAYER");
              ProblemNotFound = false;
            }

            if (MainNode.isSet("Block-Movement"))
            {
              rTutorial.BlockMovement = MainNode.getBoolean("Block-Movement");
            }

            if (MainNode.isSet("Block-All-Commands"))
            {
              rTutorial.BlockAllCommands = MainNode.getBoolean("Block-All-Commands");
            }

            if (MainNode.isSet("Broadcast-Complete-Tutorial"))
            {
              rTutorial.CompleteBroadcast = MainNode.getBoolean("Broadcast-Complete-Tutorial");
            }

            if (!MainNode.isSet("Edit-Complete"))
            {
              rTutorial.ErrorReporting.add(Str + ",MISSING_VALUE_EDIT_COMPLETE");
              ProblemNotFound = false;
            }
            rTutorial.ConfigVersion = MainNode.getInt("Config-Version");
            rTutorial.RunFirstJoinPlayer = MainNode.getBoolean("Run-First-Join-Player");
            rTutorial.EditComplete = MainNode.getBoolean("Edit-Complete");
            rTutorial.DefaultDelaySeconds = MainNode.getInt("Default-Delay-Seconds");
            rTutorial.DefaultCooldownSeconds = MainNode.getInt("Default-Cooldown-Seconds");
            rTutorial.SoundDisabled = MainNode.getBoolean("Sound-Disabled");
          }
          if (ProblemNotFound)
          {
            if (Str.equalsIgnoreCase("Compatibles"))
            {
              rTutorial.CompatiblePlugins[0] = MainNode.getBoolean("TitleAPI");
              rTutorial.CompatiblePlugins[1] = MainNode.getBoolean("Vault");
              rTutorial.CompatiblePlugins[2] = MainNode.getBoolean("Economy");
            }

            if (!Str.equalsIgnoreCase("Result"))
              continue;
            rTutorial.ResultCommands.addAll(MainNode.getStringList("Commands"));

            ConfigurationSection ResultNode = PlusSelect(MainNode, "Items");
            Set<String> Results = ResultNode.getKeys(false);
            for (String ResultKeyword : Results)
            {
              if (isNumber(ResultKeyword))
              {
                int ID = 0;
                int Amounts = 0;
                byte Data = 0;
                ConfigurationSection ResultNode2 = PlusSelect(ResultNode, ResultKeyword);
                try
                {
                  ID = Integer.valueOf(ResultNode2.getInt("ID")).intValue();
                  Data = Byte.parseByte(ResultNode2.getString("DATA-VALUE"));
                  Amounts = Integer.parseInt(ResultNode2.getString("Amounts"));
                }
                catch (NullPointerException e)
                {
                  rTutorial.ErrorReporting.add(Str + "," + ResultKeyword + ",LOAD_FAILED_ITEM_INFO");
                  continue;
                }
                @SuppressWarnings("deprecation")
				ItemStack item = new MaterialData(ID, Data).toItemStack(Amounts);
                rTutorial.ResultItems.add(item);
              }
              else
              {
                rTutorial.ErrorReporting.add(Str + "," + ResultKeyword + ",MUST_INTEGER_METHOD");
              }
            }

          }
          else
          {
            rTutorial.ErrorReporting.add(Str + ",MISSING_REQUIED_CONFIG_VALUE");
            return;
          }
        }
        catch (NullPointerException e)
        {
          e.printStackTrace();
          rTutorial.ErrorReporting.add(Str + ",MISSING_CONFIG_VALUE");
        }
      }

    }
    catch (NullPointerException e)
    {
      rTutorial.ErrorReporting.add("ConfigFile,MISSING_KEY_VALUE");
    }
  }

  public void LocationCfg()
  {
    int i = 0;
    FileConfiguration LocationYaml = this.FS.LoadFile("Location");
    ConfigurationSection CS = LocationYaml.getConfigurationSection("Locations");
    Set<String> Information = CS.getKeys(false);
    try
    {
      for (String Loc : Information)
      {
        String Coordinate = null; String Angle = null; String Msg = null; String SubMsg = "NONE"; String WorldName = null;
        boolean LocationFound = false; boolean MessageFound = false;
        ConfigurationSection LocAmounts = PlusSelect(CS, Loc);
        Set<String> LocMethod = LocAmounts.getKeys(false);
        for (String Method : LocMethod)
        {
          boolean AF;
          if (Method.equalsIgnoreCase("Location"))
          {
            ConfigurationSection LocInformation = PlusSelect(LocAmounts, Method);
            Set<String> LocDetailed = LocInformation.getKeys(false);
            boolean CF = false; AF = false;
            for (String Detailed : LocDetailed)
            {
              try
              {
                if (Detailed.equalsIgnoreCase("World"))
                {
                  WorldName = LocInformation.getString("World");
                }
                if (Detailed.equalsIgnoreCase("Coordinate"))
                {
                  Coordinate = LocInformation.getString("Coordinate");
                  CF = true;
                }
                else if (Detailed.equalsIgnoreCase("Angle"))
                {
                  Angle = LocInformation.getString("Angle");
                  AF = true;
                }
              }
              catch (NullPointerException e)
              {
                rTutorial.ErrorReporting.add("LocationFile," + Loc + "," + Detailed + ",VAULE_NOT_EXIST");
                continue;
              }
              if (CF && AF) LocationFound = true;
            }
          }
          if (Method.equalsIgnoreCase("Message"))
          {
	          ConfigurationSection LocInformation = PlusSelect(LocAmounts, Method);
	          Set<String> MsgDetailed = LocInformation.getKeys(false);
	          for (String Detailed : MsgDetailed)
	          {
	            if (Detailed.equalsIgnoreCase("Main"))
	            {
	              Msg = LocInformation.getString(Detailed);
	              if (Msg == null)
	              {
	                rTutorial.ErrorReporting.add("LocationFile," + Loc + "," + Detailed + ",NOT_EXIST_VALUE");
	              }
	              else
	              {
	                MessageFound = true;
	              }
	            }
	
	            if (!Detailed.equalsIgnoreCase("Sub"))
	              continue;
	            if (rTutorial.CompatiblePlugins[0] == true)
	            {
	              SubMsg = LocInformation.getString(Detailed);
	            }
	            else
	            {
	              rTutorial.ErrorReporting.add("LocationFile," + Loc + "," + Detailed + ",NOT_USE_TITLEAPI");
	            }
	          }
          }
        }

        if ((LocationFound) && (MessageFound))
        {
          try
          {
            int j = 0;
            String[] Chack = (Coordinate + "," + Angle).split(",");
            while (Chack[j] != null)
            {
              if ((isNumber(Chack[j])) || (j == 0))
              {
                j++;
              }
              else
              {
                rTutorial.ErrorReporting.add("LocationFile," + Loc + "," + "INCORRECT_LOCATION_VALUE");
                return;
              }
            }
          }
          catch (NullPointerException e)
          {
            rTutorial.ErrorReporting.add("LocationFile," + Loc + "," + "MISSING_LOCATION_VALUE");
            return;
          }
          catch (ArrayIndexOutOfBoundsException e2)
          {
          }

          rTutorial.LocationMethod.add(WorldName + "," + Coordinate + "," + Angle);
          rTutorial.MessageMethod.add(Msg + "," + SubMsg);
          i++;
        }
      }
    }
    catch (NullPointerException e)
    {
    	Bukkit.getConsoleSender().sendMessage(rTutorial.Prefix + "Location.yml is empty, maybe tutorial system doesn't working.");
    	rTutorial.ErrorReporting.add("LocationFile,FILE_INVAILED");
    }
    rTutorial.MethodAmount = i;
  }

  public static boolean isNumber(String Str)
  {
    try
    {
      Double.parseDouble(Str);
    }
    catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void LoadMessage()
	  {
	    rTutorial.MainMessages = new HashMap();
	    FileConfiguration LangFile = this.FS.LoadFile("message.yml");
	    ConfigurationSection LangNode = LangFile.getConfigurationSection("Messages");
	    Set<String> MessageMethod = LangNode.getKeys(true);
	    for (String MethodName : MessageMethod)
	    {
	      rTutorial.MainMessages.put(MethodName, LangNode.getString(MethodName));
	    }
	  }
	
	  public static String SubMsg(String MessageMethod, Player player, Boolean Return, Boolean AddPrefix)
	  {
	    String Message = (String)rTutorial.MainMessages.get(MessageMethod);
	    String Replacement = Message.replaceAll("%player%", player.getName());
	    String Replacements = Replacement.replaceAll("%command%", "/tutorial");
	    if(Return)
	    {
	    	if(AddPrefix) return ChatColor.translateAlternateColorCodes('&', rTutorial.Prefix + Replacements);
	    	else return ChatColor.translateAlternateColorCodes('&', Replacements);
	    }
	    else
	    {
	    	if(AddPrefix) player.sendMessage(ChatColor.translateAlternateColorCodes('&', rTutorial.Prefix + Replacements));
	    	else player.sendMessage(ChatColor.translateAlternateColorCodes('&', Replacements));
	    }
	    return null;
	  }

	public static ConfigurationSection PlusSelect(ConfigurationSection CS, String Name)
	  {
	    return CS.getConfigurationSection(Name);
	  }
}