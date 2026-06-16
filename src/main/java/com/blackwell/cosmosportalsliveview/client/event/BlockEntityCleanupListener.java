package com.blackwell.cosmosportalsliveview.client.event;

import com.tcn.cosmosportals.core.blockentity.BlockEntityPortal;
import com.blackwell.cosmosportalsliveview.client.renderer.PortalLiveViewManager;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

@Mod.EventBusSubscriber(modid = "cosmosportals_liveview", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class BlockEntityCleanupListener {
    
    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if (event.isWorldGenerated() && event.getLevel().isClientSide) {
            var chunk = event.getChunk();
            chunk.getBlockEntities().forEach((pos, entity) -> {
                if (entity instanceof BlockEntityPortal) {
                    PortalLiveViewManager.removePortal(pos);
                }
            });
        }
    }
    
    @SubscribeEvent
    public static void onWorldUnload(LevelEvent.Unload event) {
        if (event.getLevel().isClientSide) {
            PortalLiveViewManager.cleanup();
        }
    }
}
