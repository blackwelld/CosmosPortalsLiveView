package com.blackwell.cosmosportalsliveview.client.renderer;

import com.blackwell.cosmosportalsliveview.config.PortalLiveViewConfig;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ClientLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LocalizedChunkCapture {
    
    public static void captureLocalizedPortalView(PortalViewData portalData, ClientLevel level) {
        if (portalData == null || level == null) return;
        
        BlockPos destPos = portalData.destPos;
        ResourceLocation destDimension = portalData.destDimension;
        
        if (!canAccessDimension(destDimension, level)) return;
        if (destPos == null || destPos == BlockPos.ZERO) return;
        
        int captureRadius = PortalLiveViewConfig.CAPTURE_RADIUS_CHUNKS.get();
        int resolution = PortalLiveViewConfig.CAPTURE_RESOLUTION.get();
        
        try {
            DynamicTexture texture = createPortalViewTexture(level, destPos, captureRadius, resolution);
            if (texture != null) {
                portalData.setTexture(texture);
            }
        } catch (Exception e) {
        }
    }
    
    private static DynamicTexture createPortalViewTexture(ClientLevel level, BlockPos center, int radiusChunks, int resolution) {
        NativeImage image = new NativeImage(NativeImage.Format.RGBA, resolution, resolution, false);
        
        int halfRadius = radiusChunks * 16;
        int minX = center.getX() - halfRadius;
        int minZ = center.getZ() - halfRadius;
        int maxX = center.getX() + halfRadius;
        int maxZ = center.getZ() + halfRadius;
        
        int width = maxX - minX;
        int depth = maxZ - minZ;
        
        for (int texY = 0; texY < resolution; texY++) {
            for (int texX = 0; texX < resolution; texX++) {
                int worldX = minX + (int) ((texX / (float) resolution) * width);
                int worldZ = minZ + (int) ((texY / (float) resolution) * depth);
                int worldY = center.getY();
                
                BlockPos samplePos = new BlockPos(worldX, worldY, worldZ);
                BlockState blockState = level.getBlockState(samplePos);
                
                int color = getBlockColor(blockState);
                image.setPixelRGBA(texX, texY, color);
            }
        }
        
        return new DynamicTexture(image);
    }
    
    private static int getBlockColor(BlockState blockState) {
        if (blockState.getMaterial().isReplaceable()) {
            return 0x00000000;
        }
        
        int r = 100, g = 100, b = 100, a = 255;
        
        if (blockState.getBlock() == Blocks.GRASS_BLOCK || blockState.getBlock() == Blocks.DIRT) {
            r = 139; g = 101; b = 68;
        } else if (blockState.getBlock() == Blocks.STONE || blockState.getBlock() == Blocks.COBBLESTONE) {
            r = 128; g = 128; b = 128;
        } else if (blockState.getBlock() == Blocks.OAK_LOG || blockState.getBlock() == Blocks.OAK_LEAVES) {
            r = 139; g = 69; b = 19;
        } else if (blockState.getBlock() == Blocks.WATER) {
            r = 0; g = 100; b = 200;
        } else if (blockState.getBlock() == Blocks.SAND) {
            r = 238; g = 203; b = 139;
        } else if (blockState.getBlock() == Blocks.SNOW) {
            r = 255; g = 255; b = 255;
        }
        
        return (r << 16) | (g << 8) | b | (a << 24);
    }
    
    private static boolean canAccessDimension(ResourceLocation dimension, ClientLevel currentLevel) {
        if (currentLevel == null) return false;
        return currentLevel.dimension().location().equals(dimension);
    }
}
