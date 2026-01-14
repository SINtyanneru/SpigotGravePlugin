package su.rumishistem.spigot_grave_plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import su.rumishistem.spigot_grave_plugin.Type.XYZ;

public class XYZTable {
	private static Map<UUID, XYZ> xyz_table = new HashMap<>();

	public static void regist(UUID uuid, int x, int y, int z) {
		xyz_table.put(uuid, new XYZ(x, y, z));
	}

	public static XYZ get(UUID uuid) {
		return xyz_table.get(uuid);
	}

	public static boolean exists(int x, int z) {
		for (XYZ xyz:xyz_table.values()) {
			if (xyz.get_x() == x && xyz.get_z() == z) {
				return true;
			}
		}

		return false;
	}

	public static Set<Entry<UUID, XYZ>> get_list() {
		return xyz_table.entrySet();
	}
}
