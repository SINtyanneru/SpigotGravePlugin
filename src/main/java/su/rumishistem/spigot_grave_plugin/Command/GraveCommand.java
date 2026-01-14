package su.rumishistem.spigot_grave_plugin.Command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import su.rumishistem.spigot_grave_plugin.Main;
import su.rumishistem.spigot_grave_plugin.UUIDToXZ;

public class GraveCommand implements CommandExecutor{
	private Main ctx;

	public GraveCommand(Main ctx) {
		this.ctx = ctx;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Player only.");
			return true;
		}

		Player player = (Player)sender;
		World grave_world = ctx.graveyard;

		if (grave_world == null) {
			player.sendMessage("墓場が見つかりませんでした。");
			return true;
		}

		//UUIDから座標を生成
		UUIDToXZ xz = new UUIDToXZ(player.getUniqueId());

		//チェストを取得
		Location main_chest_location = new Location(ctx.graveyard, xz.get_x(), xz.get_y(), xz.get_z());
		Location sub_chest_location = new Location(ctx.graveyard, xz.get_x(), xz.get_y() + 1, xz.get_z());
		Chest main_chest = (Chest)main_chest_location.getBlock().getState();
		Chest sub_chest = (Chest)sub_chest_location.getBlock().getState();
		Inventory main_inventory = main_chest.getInventory();
		Inventory sub_inventory = sub_chest.getInventory();
		ItemStack[] main_contents = main_inventory.getContents();
		ItemStack[] sub_contents = sub_inventory.getContents();

		//インベントリ作成
		Inventory grave_inventory = Bukkit.createInventory(null, 54, "貴様の墓");

		//装備
		grave_inventory.setItem(0, sub_contents[9]);
		grave_inventory.setItem(1, sub_contents[10]);
		grave_inventory.setItem(2, sub_contents[11]);
		grave_inventory.setItem(3, sub_contents[12]);

		//オフハンド
		grave_inventory.setItem(5, sub_contents[13]);

		//メイン
		for (int i = 0; i < main_contents.length; i++) {
			grave_inventory.setItem(i + 18, main_contents[i]);
		}

		//ホットバー
		for (int i = 0; i < 9; i++) {
			if (sub_contents[i] == null) continue;
			grave_inventory.setItem(i + 45, sub_contents[i]);
		}

		//インベントリをクリア
		main_inventory.clear();
		sub_inventory.clear();

		//開く
		player.openInventory(grave_inventory);

		return true;
	}
}
