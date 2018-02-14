package moe.plushie.dakimakuramod.proxies;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import moe.plushie.dakimakuramod.DakimakuraMod;
import moe.plushie.dakimakuramod.client.handler.PlacementPreviewHandler;
import moe.plushie.dakimakuramod.client.model.ModelDakimakura;
import moe.plushie.dakimakuramod.client.render.item.RenderItemDakimakura;
import moe.plushie.dakimakuramod.client.render.tileentity.RenderBlockDakimakura;
import moe.plushie.dakimakuramod.client.texture.DakiTextureManagerClient;
import moe.plushie.dakimakuramod.common.block.ModBlocks;
import moe.plushie.dakimakuramod.common.tileentities.TileEntityDakimakura;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.MinecraftForgeClient;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    
    private DakiTextureManagerClient dakiTextureManager;
    private int maxGpuTextureSize;
    
    @Override
    public void initRenderers() {
        dakiTextureManager = new DakiTextureManagerClient();
        ModelDakimakura modelDakimakura = new ModelDakimakura(dakiTextureManager);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.blockDakimakura), new RenderItemDakimakura(modelDakimakura));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDakimakura.class, new RenderBlockDakimakura(modelDakimakura));
        maxGpuTextureSize = GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE);
        DakimakuraMod.getLogger().info(String.format("Max GPU texture size: %d.", maxGpuTextureSize));
        new PlacementPreviewHandler(modelDakimakura);
    }
    
    @Override
    public MinecraftServer getServer() {
        if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
            return Minecraft.getMinecraft().getIntegratedServer();
        }
        return super.getServer();
    }
    
    public DakiTextureManagerClient getDakiTextureManager() {
        return dakiTextureManager;
    }
    
    public int getMaxGpuTextureSize() {
        return maxGpuTextureSize;
    }
}
