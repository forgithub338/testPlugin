package bs.untitled13;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Event implements Listener {
    private final Map<UUID, Boolean> playerSneak = new HashMap<>();
    private final Map<UUID, Long> playerCold = new HashMap<>();
    private final JavaPlugin plugin;

    public Event(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void sneaking(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        UUID playerId = player.getUniqueId();
        if (e.isSneaking()) {
            playerSneak.put(playerId, true);
        } else {
            playerSneak.put(playerId, false);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        UUID playerId = player.getUniqueId();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(playerSneak.getOrDefault(playerId, false)){
                if(isPlayerCold(playerId)){
                    player.sendMessage(ChatColor.GREEN + "技能發動");
                    coldTime(playerId, 20);
                    continueTime(playerId, 5);
                }
                else{
                    player.sendMessage(ChatColor.RED + "尚在冷卻時間");
                }
            }
        }
    }

    public void coldTime(UUID playerId, Integer coldTime){
        long currentTime = System.currentTimeMillis();
        playerCold.put(playerId, currentTime+(coldTime*1000));

        new BukkitRunnable() {
            @Override
            public void run() {
                Player player = Bukkit.getPlayer(playerId);
                if (player != null) {
                    player.sendMessage(ChatColor.GREEN + "技能冷卻時間結束");
                }
            }
        }.runTaskLater(plugin, coldTime * 20);
    }

    public void continueTime(UUID playerId, Integer continueTime){
        Player player = Bukkit.getPlayer(playerId);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(!(player == null)){
                    player.sendMessage(ChatColor.YELLOW + "技能持續時間結束");
                }

            }
        }.runTaskLater(plugin, continueTime * 20);
    }

    public boolean isPlayerCold(UUID playerId){
        long currentTime = System.currentTimeMillis();
        if(currentTime >= playerCold.getOrDefault(playerId, (long)1)){
            return true;
        }
        else{
            return false;
        }
    }
}