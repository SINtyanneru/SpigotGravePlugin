package su.rumishistem.spigot_grave_plugin;

import java.util.UUID;

public class UUIDToXZ {
	private static final int RANGE = 8000;
	private int x = 0;
	private int y = 0;
	private int z = 0;

	public UUIDToXZ(UUID uuid) {
		long h = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
		long v = h & Long.MAX_VALUE;

		x = (int)(v % RANGE) - (RANGE / 2);
		z = (int)((v / RANGE) % RANGE) - (RANGE / 2);
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
