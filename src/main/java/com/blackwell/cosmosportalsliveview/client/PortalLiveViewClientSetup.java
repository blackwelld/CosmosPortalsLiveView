package com.blackwell.cosmosportalsliveview.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForge;

import com.blackwell.cosmosportalsliveview.client.event.PortalRenderEventHandler;
import com.blackwell.cosmosportalsliveview.client.event.PortalLifecycleListener;
import com.blackwell.cosmosportalsliveview.client.event.BlockEntityCleanupListener;

@OnlyIn(Dist.CLIENT)
public class PortalLiveViewClientSetup {
    
    public static void setupClient() {
        NeoForge.EVENT_BUS.register(PortalRenderEventHandler.class);
        NeoForge.EVENT_BUS.register(PortalLifecycleListener.class);
        NeoForge.EVENT_BUS.register(BlockEntityCleanupListener.class);
    }
}
