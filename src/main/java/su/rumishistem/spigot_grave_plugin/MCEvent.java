package su.rumishistem.spigot_grave_plugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MCEvent implements Listener{
	private Main ctx;
	public MCEvent(Main ctx) {
		this.ctx = ctx;
	}

	@EventHandler
	private void onDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();
		PlayerInventory inventory = player.getInventory();

		//メインインベントリ(0〜8：ホットバー 9〜35：インベントリ)
		ItemStack[] contents = inventory.getContents();

		//装備(3がヘルメットなので逆順)
		ItemStack[] armor = inventory.getArmorContents();

		//手
		ItemStack off_hand = inventory.getItemInOffHand();

		//UUIDから座標を生成
		UUIDToXZ xz = new UUIDToXZ(player.getUniqueId());

		//チェストを用意(メインチェストはインベントリの中身を、サブに装備とオフハンドを)
		Location main_chest_location = new Location(ctx.graveyard, xz.get_x(), xz.get_y(), xz.get_z());
		Location sub_chest_location = new Location(ctx.graveyard, xz.get_x(), xz.get_y() + 1, xz.get_z());
		main_chest_location.getBlock().setType(Material.CHEST);
		sub_chest_location.getBlock().setType(Material.CHEST);

		//チェストを設置
		Chest main_chest = (Chest)main_chest_location.getBlock().getState();
		Chest sub_chest = (Chest)sub_chest_location.getBlock().getState();
		Inventory main_inventory = main_chest.getInventory();
		Inventory sub_inventory = sub_chest.getInventory();

		//メインインベントリを突っ込む
		for (int i = 9; i < 36; i++) {
			main_inventory.setItem(i - 9, contents[i]);
		}

		//ホットバーを入れる
		for (int i = 0; i < 9; i++) {
			sub_inventory.setItem(i, contents[i]);
		}

		//装備とオフハンドを入れる
		sub_inventory.setItem(9, armor[3]);
		sub_inventory.setItem(10, armor[2]);
		sub_inventory.setItem(11, armor[1]);
		sub_inventory.setItem(12, armor[0]);
		sub_inventory.setItem(13, off_hand);

		//ドロップしたアイテムを削除
		e.getDrops().clear();
		inventory.clear();
		inventory.setArmorContents(null);
		inventory.setItemInOffHand(null);

		player.sendMessage("§4あなたは死んでしまいましたが、狐さんが慈悲をかけてくれました。");
		player.sendMessage("§4あなたの落としたアイテムは墓にぶち込まれています。");
		player.sendMessage("§4/graveコマンドを使用して、墓を覗きましょう。");
	}
}
