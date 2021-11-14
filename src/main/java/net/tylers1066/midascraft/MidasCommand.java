package net.tylers1066.midascraft;

import net.countercraft.movecraft.MovecraftLocation;
import net.countercraft.movecraft.craft.CraftManager;
import net.countercraft.movecraft.craft.PlayerCraft;
import net.countercraft.movecraft.localisation.I18nSupport;
import net.countercraft.movecraft.util.hitboxes.HitBox;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.countercraft.movecraft.util.ChatUtils.MOVECRAFT_COMMAND_PREFIX;

public class MidasCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!command.getName().equalsIgnoreCase("midas"))
            return false;

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(MOVECRAFT_COMMAND_PREFIX + I18nSupport.getInternationalisedString("Command - Must Be Player"));
            return true;
        }
        Player player = (Player) commandSender;

        if(!player.hasPermission("midas.use")) {
            player.sendMessage(MOVECRAFT_COMMAND_PREFIX + I18nSupport.getInternationalisedString("Insufficient Permissions"));
            return true;
        }

        PlayerCraft craft = CraftManager.getInstance().getCraftByPlayer(player);
        if(craft == null) {
            player.sendMessage(MOVECRAFT_COMMAND_PREFIX + I18nSupport.getInternationalisedString("Player- Error - You do not have a craft to release!"));
            return true;
        }

        HitBox hitbox = craft.getHitBox();
        HitBox collapsed = craft.getCollapsedHitBox();
        World w = craft.getWorld();

        for(MovecraftLocation loc : hitbox.difference(collapsed)) {
            w.getBlockAt(loc.toBukkit(w)).setType(Material.GOLD_BLOCK);
        }
        for(MovecraftLocation loc : collapsed.difference(hitbox)) {
            w.getBlockAt(loc.toBukkit(w)).setType(Material.EMERALD_BLOCK);
        }
        for(MovecraftLocation loc : hitbox.intersection(collapsed)) {
            w.getBlockAt(loc.toBukkit(w)).setType(Material.DIAMOND_BLOCK);
        }
        return true;
    }
}
