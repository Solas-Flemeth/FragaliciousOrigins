package org.originsreborn.fragaliciousorigins.abilities;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class InvisibileUtil {
    public void makePlayerTranslucent(Player player) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(com.comphenix.protocol.PacketType.Play.Server.ENTITY_METADATA);

        packet.getIntegers().write(0, player.getEntityId());

        // Create a DataWatcher to manipulate the metadata
        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(player);

        // 0x20 is the invisibility flag in Minecraft, same as NMS
        byte translucentFlag = 0x20;
        WrappedDataWatcher.WrappedDataWatcherObject invisibleObject = new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class));

        // Apply the translucent flag to the DataWatcher
        dataWatcher.setObject(invisibleObject, translucentFlag);

        packet.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        // Send the packet to all online players
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            protocolManager.sendServerPacket(onlinePlayer, packet);
        }
    }
}
