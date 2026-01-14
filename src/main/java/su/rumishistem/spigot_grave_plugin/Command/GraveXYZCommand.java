package su.rumishistem.spigot_grave_plugin.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import su.rumishistem.spigot_grave_plugin.XYZTable;
import su.rumishistem.spigot_grave_plugin.Type.XYZ;

public class GraveXYZCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Player only.");
			return true;
		}

		Player player = (Player)sender;

		//UUIDから座標を生成
		XYZ xyz = XYZTable.get(player.getUniqueId());
		if (xyz == null) {
			player.sendMessage("まだあなたの墓はありません");
			return true;
		}

		player.sendMessage("あなたの墓場は、墓地ディメンションのここにあります。");
		player.sendMessage("メインチェスト：" + xyz.get_x() + " / " + xyz.get_y() + " / " + xyz.get_z());
		player.sendMessage("サブチェスト：" + xyz.get_x() + " / " + (xyz.get_y() + 1) + " / " + xyz.get_z());
		return true;
	}
}
