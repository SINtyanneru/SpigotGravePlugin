package su.rumishistem.spigot_grave_plugin.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

public class XYZDBType {
	@JsonProperty("UUID")
	public String uuid;

	@JsonProperty("X")
	public int x;

	@JsonProperty("Y")
	public int y;

	@JsonProperty("Z")
	public int z;
}
