package su.rumishistem.spigot_grave_plugin;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import su.rumishistem.spigot_grave_plugin.Type.XYZ;

public class UUIDToXZ {
	private int x = 0;
	private int y = 0;
	private int z = 0;

	public UUIDToXZ(UUID uuid) {
		XYZ xyz = XYZTable.get(uuid);
		if (xyz == null) {
			while (true) {
				int a = ThreadLocalRandom.current().nextInt(-8000, 8001);
				int b = ThreadLocalRandom.current().nextInt(-8000, 8001);

				if (!XYZTable.exists(a, b)) {
					x = a;
					y = 0;
					z = b;
					XYZTable.regist(uuid, x, y, z);
					return;
				}
			}
		} else {
			x = xyz.get_x();
			y = xyz.get_y();
			z = xyz.get_z();
		}
	}

	public int get_x() {
		return x;
	}

	public int get_y() {
		return y;
	}

	public int get_z() {
		return z;
	}
}
