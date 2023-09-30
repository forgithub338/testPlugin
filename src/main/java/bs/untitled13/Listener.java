package bs.untitled13;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Listener implements org.bukkit.event.Listener {
    private static Map<UUID, Integer>logExperience = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockDropItemEvent e){
        Player player = e.getPlayer();
        UUID playerId = player.getUniqueId();
        if(e.getItems().size() >1){
            return;
        }
        ItemStack drop = e.getItems().get(0).getItemStack();
        ItemMeta dropMeta = drop.getItemMeta();


        ItemStack tool = player.getInventory().getItemInMainHand();
        ItemMeta toolMeta = tool.getItemMeta();

        if(toolMeta.getDisplayName().equals("興趣系統-伐木")){
            if(drop.getType().equals(Material.OAK_LOG)){
                player.sendMessage("hello");

                if(dropMeta.getDisplayName().equals("興趣系統-橡木")){
                    int logEx = logExperience.getOrDefault(playerId, 0);
                    logExperience.put(playerId, logEx+1);
                    player.sendMessage("伐木興趣經驗+1");
                    player.sendMessage("當前伐木興趣經驗" + (logEx+1));
                    return;
                }
                else{
                    player.sendMessage("此木頭不可砍伐");
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        ItemStack log = new ItemStack(Material.OAK_LOG, 10);
        ItemMeta logMeta = log.getItemMeta();
        logMeta.setDisplayName("興趣系統-橡木");
        log.setItemMeta(logMeta);

        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta axeMeta = axe.getItemMeta();
        axeMeta.setDisplayName("興趣系統-伐木");
        axe.setItemMeta(axeMeta);

        player.getInventory().addItem(log);
        player.getInventory().addItem(axe);
        player.getInventory().addItem(new ItemStack(Material.OAK_LOG, 10));

    }
}
