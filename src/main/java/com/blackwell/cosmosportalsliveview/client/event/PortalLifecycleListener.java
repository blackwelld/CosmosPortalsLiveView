package com.blackwell.cosmosportalsliveview.client.event;

import com.blackwell.cosmosportalsliveview.client.renderer.PortalLiveViewManager;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.BlockEvent;

@Mod.EventBusSubscriber(modid = "cosmosportals_liveview", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class PortalLifecycleListener {
    
    @SubscribeEvent
    public static void onBlockChange(BlockEvent.DestroyEvent event) {
        Block block = event.getState().getBlock();
        
        if (isCosmosPortalBlock(block)) {
            PortalLiveViewManager.removePortal(event.getPos());
        }
    }
    
    private static boolean isCosmosPortalBlock(Block block) {
        try {
            ResourceLocation key = ForgeRegistries.BLOCKS.getKey(block);
            if (key != null) {
                String modId = key.getNamespace();
                String blockName = key.getPath();
                return modId.equals("cosmosportals") && blockName.contains("portal");
            }
        } catch (Exception e) {
        }
        return false;
    }
}
