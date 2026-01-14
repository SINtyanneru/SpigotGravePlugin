package su.rumishistem.spigot_grave_plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

import su.rumishistem.spigot_grave_plugin.Command.GraveCommand;
import su.rumishistem.spigot_grave_plugin.Command.GraveXYZCommand;
import su.rumishistem.spigot_grave_plugin.Type.XYZ;
import su.rumishistem.spigot_grave_plugin.Type.XYZDBType;

public class Main extends JavaPlugin{
	public World graveyard;

	@Override
	public void onEnable() {
		getLogger().info("Spigot Grave Plugin");
		getLogger().info("By RImi");
		getLogger().info("Website https://rumi-room.net/");

		getServer().getPluginManager().registerEvents(new MCEvent(this), this);

		getCommand("grave").setExecutor(new GraveCommand(this));
		getCommand("grave_xyz").setExecutor(new GraveXYZCommand());

		WorldCreator wc = new WorldCreator("graveyard");
		wc.environment(World.Environment.NORMAL);
		wc.type(WorldType.FLAT);
		graveyard = Bukkit.createWorld(wc);

		//ロード
		File dir = getDataFolder();
		if (!dir.exists()) {
			dir.mkdir();
		}

		File xyz_db_file = new File(dir, "xyz.json");
		try {
			if (xyz_db_file.exists()) {
				List<XYZDBType> list = new ObjectMapper().readValue(xyz_db_file, new TypeReference<List<XYZDBType>>() {});
				for (XYZDBType row:list) {
					XYZTable.regist(UUID.fromString(row.uuid), row.x, row.y, row.z);
				}
			} else {
				xyz_db_file.createNewFile();
				Files.write("[]".getBytes(), xyz_db_file);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		graveyard.save();

		//保存
		try {
			List<XYZDBType> list = new ArrayList<>();
			for (Entry<UUID, XYZ> entry:XYZTable.get_list()) {
				XYZDBType t = new XYZDBType();
				t.uuid = entry.getKey().toString();
				t.x = entry.getValue().get_x();
				t.y = entry.getValue().get_y();
				t.z = entry.getValue().get_z();
				list.add(t);
			}

			String json = new ObjectMapper().writeValueAsString(list);
			File xyz_db_file = new File(getDataFolder(), "xyz.json");
			Files.write(json.getBytes(), xyz_db_file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
