package bs.untitled13;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class newListener implements Listener {
    public static List<Location>locations = new ArrayList<>();
    private static Map<UUID, Integer>logExperience = new HashMap<>();

    public static void main(){
        World world = Bukkit.getWorlds().get(0);
        for (int x = 0; x <= 2; x++) {
            for (int z = 0; z <= 2; z++) {
                Location location = new Location(world, x, 0, z);
                locations.add(location);
            }
        }
    }


    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e){
        Player player = e.getPlayer();
        UUID playerId = player.getUniqueId();
        ItemStack tool = player.getInventory().getItemInMainHand();
        ItemMeta toolMeta = tool.getItemMeta();
        Location blockLocate = e.getBlock().getLocation();

        if(toolMeta.getDisplayName().equals("興趣系統-伐木")) {
            player.sendMessage("hello");
            for(Location canBreak : locations){
                if(blockLocate.equals(canBreak)){
                    player.sendMessage("world");
                    int logEx = logExperience.getOrDefault(playerId, 0);
                    logExperience.put(playerId, logEx + 1);
                    player.sendMessage("伐木興趣經驗+1");
                    player.sendMessage("當前伐木興趣經驗" + (logEx + 1));
                    return;
                }
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
