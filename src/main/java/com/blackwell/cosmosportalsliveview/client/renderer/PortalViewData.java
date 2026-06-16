package com.blackwell.cosmosportalsliveview.client.renderer;

import java.util.HashSet;
import java.util.Set;

import com.tcn.cosmosportals.core.blockentity.BlockEntityPortal;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PortalViewData {
    public final BlockPos portalPos;
    public final ResourceLocation destDimension;
    public final BlockPos destPos;
    
    private DynamicTexture liveViewTexture;
    private long lastCaptureTime;
    
    private final Set<ChunkPos> cachedChunks = new HashSet<>();
    private boolean needsUpdate = true;
    
    public PortalViewData(BlockEntityPortal entity) {
        this.portalPos = entity.getBlockPos();
        this.destDimension = entity.destDimension;
        this.destPos = entity.getDestPos();
        this.lastCaptureTime = 0;
    }
    
    public boolean shouldUpdateCapture(long currentTime, long captureInterval) {
        long timeSinceCapture = currentTime - lastCaptureTime;
        return timeSinceCapture >= captureInterval || needsUpdate;
    }
    
    public DynamicTexture getTexture() {
        return liveViewTexture;
    }
    
    public void setTexture(DynamicTexture texture) {
        this.liveViewTexture = texture;
        this.lastCaptureTime = System.currentTimeMillis();
        this.needsUpdate = false;
    }
    
    public void cleanup() {
        if (liveViewTexture != null) {
            liveViewTexture.close();
            liveViewTexture = null;
        }
        cachedChunks.clear();
    }
    
    public void markForUpdate() {
        this.needsUpdate = true;
    }
}
