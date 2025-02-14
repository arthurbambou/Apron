package io.github.betterthanupdates.forge.mixin.client;

import forge.ForgeHooksClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.PistonBlockEntityRenderer;

@Environment(EnvType.CLIENT)
@Mixin(PistonBlockEntityRenderer.class)
public abstract class PistonRendererMixin extends BlockEntityRenderer {
	@Shadow
	private BlockRenderManager field_1131;

	/**
	 * @author Eloraam
	 * @reason implement Forge hooks
	 */
	@Inject(method = "render(Lnet/minecraft/class_283;DDDF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Tessellator;startQuads()V"))
	private void forge$beforeBlockRender(PistonBlockEntity tileentitypiston, double d, double d1, double d2, float f, CallbackInfo ci) {
		Block block = Block.BLOCKS[tileentitypiston.getPushedBlockId()];

		if (block != null && tileentitypiston.getProgress(f) < 1.0F) {
			ForgeHooksClient.beforeBlockRender(block, this.field_1131);
		}
	}

	/**
	 * @author Eloraam
	 * @reason implement Forge hooks
	 */
	@Inject(method = "render(Lnet/minecraft/class_283;DDDF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_583;method_1930()V"))
	private void forge$afterBlockRender(PistonBlockEntity tileentitypiston, double d, double d1, double d2, float f, CallbackInfo ci) {
		Block block = Block.BLOCKS[tileentitypiston.getPushedBlockId()];
		ForgeHooksClient.afterBlockRender(block, this.field_1131);
	}
}
