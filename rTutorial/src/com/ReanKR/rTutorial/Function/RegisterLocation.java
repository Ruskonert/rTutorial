package com.ReanKR.rTutorial.Function;

import com.ReanKR.rTutorial.File.FileLoader;
import com.ReanKR.rTutorial.Util.FileSection;
import com.ReanKR.rTutorial.rTutorial;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class RegisterLocation
{
  private rTutorial Plugin = (rTutorial)rTutorial.plugin;
  private FileSection FS = new FileSection();
  private FileLoader FL = new FileLoader(this.Plugin);

  public void LocationRegister(Location Loc, List<String> Msg, int index)
  {
    File file = new File("plugins/rTutorial/Location.yml");
    YamlConfiguration LocationYaml = YamlConfiguration.loadConfiguration(file);
    ConfigurationSection CS = LocationYaml.getConfigurationSection("Locations");
    CS.createSection(String.valueOf(rTutorial.MethodAmount));
    World world = Loc.getWorld();
    double x = Double.valueOf(Loc.getX());
    double y = Double.valueOf(Loc.getY());
    double z = Double.valueOf(Loc.getZ());
    float pitch = Loc.getPitch();
    float yaw = Loc.getYaw();
    ConfigurationSection CS2 = CS.getConfigurationSection(String.valueOf(rTutorial.MethodAmount));
    CS2.createSection("Location");
    CS2.createSection("Message");
    ConfigurationSection CS3 = this.FS.PlusSelect(CS2, "Location");
    CS3.set("World", world.getName());
    CS3.set("Coordinate", x + "," + y + "," + z);
    CS3.set("Angle", String.valueOf(yaw + "," + pitch));
    ConfigurationSection CS4 = this.FS.PlusSelect(CS2, "Message");
    CS4.set("Main", Msg.get(0));
    if(! (Msg.get(1).equalsIgnoreCase("¾øÀ½") || Msg.get(1).equalsIgnoreCase("None")))
    {
     CS4.set("Sub", Msg.get(1));
    }
    try
    {
		LocationYaml.save(file);
	}
    catch (IOException e)
    {
		e.printStackTrace();
	}
    FL.LocationCfg();
    return;
  }
}