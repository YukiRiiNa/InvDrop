package ltd.teaparty.invdrop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class InvDrop extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("====================================");
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("死亡随机掉落，启动！");
        getLogger().info("====================================");
        getLogger().info("加载完成");
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent e){
        Player p = e.getPlayer();
        p.sendMessage("nizai:"+ p.getWorld().getName());

        if(Objects.equals(p.getWorld().getName(), "world") | Objects.equals(p.getWorld().getName(), "DIM1") | Objects.equals(p.getWorld().getName(), "DIM-1")){
        //Random random = new Random();
        //int randomIntBound = random.nextInt(41);
        //int shouldDeleteSlot = randomIntBound+1;
        List<Integer> Invlist = new ArrayList<>();
        for(int i=1;i<=41;i++){
            Invlist.add(i);
        }
        Collections.shuffle(Invlist);
        int randomItem = Invlist.get(0); //获取物品栏的某一格
        boolean bedelete = true;

        p.sendMessage(String.valueOf(p.getInventory().getSize()));

        while(bedelete){ //循环
            //读取lore

            //读取mat

            if (p.getInventory().getItem(randomItem) != null){
                Material Mat = p.getInventory().getItem(randomItem).getType();
                List<String> lorelist = p.getInventory().getItem(randomItem).getLore();
                if (lorelist != null && lorelist.contains("&6该物品死亡不会掉落")) {
                    Invlist.remove(0);
                    Collections.shuffle(Invlist);
                    randomItem = Invlist.get(0);
                    p.sendMessage(randomItem+"是无法掉落的");
                    continue;
                } else {
                    if(Mat == Material.DIRT) {
                        Invlist.remove(0);
                        Collections.shuffle(Invlist);
                        randomItem = Invlist.get(0);
                        p.sendMessage(randomItem + "是泥土");
                        continue;
                    } else {
                        int count = p.getInventory().getItem(randomItem).getAmount();
                        String itemname = p.getInventory().getItem(randomItem).getItemMeta().getDisplayName();
                        p.getInventory().getItem(randomItem).setAmount(count-1);
                        p.sendMessage(randomItem+"掉落了，是"+itemname);
                        break;
                    }
                }
            }else{
                Invlist.remove(0);
                Collections.shuffle(Invlist);
                randomItem = Invlist.get(0);
                p.sendMessage(randomItem+"是空气");
                continue;
            }
        }
    }
    }
}
