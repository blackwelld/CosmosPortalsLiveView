package com.blackwell.cosmosportalsliveview;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import com.blackwell.cosmosportalsliveview.config.PortalLiveViewConfig;
import com.blackwell.cosmosportalsliveview.client.PortalLiveViewClientSetup;

@Mod(CosmosPortalsLiveView.MOD_ID)
public class CosmosPortalsLiveView {
    
    public static final String MOD_ID = "cosmosportals_liveview";
    
    public CosmosPortalsLiveView(ModContainer container, IEventBus bus) {
        container.registerConfig(ModConfig.Type.CLIENT, PortalLiveViewConfig.SPEC, "cosmosportals-liveview-client.toml");
        
        bus.addListener(this::onFMLCommonSetup);
        bus.addListener(this::onFMLClientSetup);
    }
    
    private void onFMLCommonSetup(final FMLCommonSetupEvent event) {
    }
    
    private void onFMLClientSetup(final FMLClientSetupEvent event) {
        PortalLiveViewClientSetup.setupClient();
    }
}
