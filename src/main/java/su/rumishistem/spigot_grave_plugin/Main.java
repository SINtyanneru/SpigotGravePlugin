package su.rumishistem.spigot_grave_plugin;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;

import su.rumishistem.spigot_grave_plugin.Command.GraveCommand;

public class Main extends JavaPlugin{
	public World graveyard;

	@Override
	public void onEnable() {
		getLogger().info("Spigot Grave Plugin");
		getLogger().info("By RImi");
		getLogger().info("Website https://rumi-room.net/");

		getServer().getPluginManager().registerEvents(new MCEvent(this), this);

		getCommand("grave").setExecutor(new GraveCommand(this));

		WorldCreator wc = new WorldCreator("graveyard");
		wc.environment(World.Environment.NORMAL);
		wc.type(WorldType.FLAT);
		graveyard = Bukkit.createWorld(wc);
	}

	@Override
	public void onDisable() {
		graveyard.save();
	}
}
