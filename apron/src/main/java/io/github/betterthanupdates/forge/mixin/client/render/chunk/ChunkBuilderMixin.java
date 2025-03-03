package io.github.betterthanupdates.forge.mixin.client.render.chunk;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import forge.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.chunk.ChunkBuilder;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;

@Environment(EnvType.CLIENT)
@Mixin(ChunkBuilder.class)
public abstract class ChunkBuilderMixin {

	@Inject(method = "rebuild", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Tessellator;startQuads()V", shift = At.Shift.BEFORE))
	public void method_296$beforeRenderPass(CallbackInfo ci, @Local(index = 11) int i2) {
		ForgeHooksClient.beforeRenderPass(i2);
	}

	@Inject(method = "rebuild", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Tessellator;draw()V", shift = At.Shift.BEFORE))
	public void method_296$afterRenderPass(CallbackInfo ci, @Local(index = 11) int i2) {
		ForgeHooksClient.afterRenderPass(i2);
	}

	// @Coerce should be used for flag and flag1 as they are actually booleans, but I couldn't get it to work
	@Inject(method = "rebuild", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/block/Block;getRenderLayer()I"))
	public void method_296(CallbackInfo ci, @Local BlockRenderManager renderblocks, @Local(index = 11) int i2, @Local(index = 12) LocalIntRef flag, @Local(index = 13) LocalIntRef flag1, @Local(index = 15) int j2, @Local(index = 16) int k2, @Local(index = 17) int l2, @Local Block block, @Local(index = 20) int j3) {
		if (j3 > i2) {
			flag.set(1);
		}

		if (ForgeHooksClient.canRenderInPass(block, i2)) {
			ForgeHooksClient.beforeBlockRender(block, renderblocks);
			flag1.set((flag1.get() == 1 | renderblocks.render(block, l2, j2, k2)) ? 1 : 0);
			ForgeHooksClient.afterBlockRender(block, renderblocks);
		}
	}

	// Set j3 to i2 so the if statement never succeeds
	@ModifyVariable(method = "rebuild", index = 20, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getRenderLayer()I", shift = At.Shift.BY, by = 2))
	public int method_296$modifyJ3(int j3, @Local(index = 11) int i2) {
		return i2;
	}

	// Set flag1 to itself so it doesn't change
	@Redirect(method = "rebuild", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/BlockRenderManager;render(Lnet/minecraft/block/Block;III)Z"))
	public boolean method_296$redirectRender(BlockRenderManager instance, Block block, int j, int k, int i) {
		return false;
	}
}
