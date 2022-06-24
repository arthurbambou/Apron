package io.github.betterthanupdates.forge.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.client.ClientInteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.SingleplayerInteractionManager;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import io.github.betterthanupdates.forge.block.ForgeBlock;
import io.github.betterthanupdates.forge.item.ForgeItem;

@Environment(EnvType.CLIENT)
@Mixin(SingleplayerInteractionManager.class)
public class SinglePlayerInteractionManagerMixin extends ClientInteractionManager {
	public SinglePlayerInteractionManagerMixin(Minecraft client) {
		super(client);
	}

	@Unique
	int cachedMeta = 0;

	@Inject(method = "method_1716", at = @At("HEAD"), cancellable = true)
	private void forge$method_1716(int i, int j, int k, int l, CallbackInfoReturnable<Boolean> cir) {
		this.cachedMeta = this.client.world.getBlockMeta(i, j, k);
		ItemStack itemstack = this.client.player.getHeldItem();

		if (itemstack != null && ((ForgeItem) itemstack.getItem()).onBlockStartBreak(itemstack, i, j, k, this.client.player)) {
			cir.setReturnValue(false);
		}
	}

	@Redirect(method = "method_1716", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/entity/player/AbstractClientPlayerEntity;canRemoveBlock(Lnet/minecraft/block/Block;)Z"))
	private boolean forge$method_1716(AbstractClientPlayerEntity instance, Block block) {
		return ((ForgeBlock) block).canHarvestBlock(instance, this.cachedMeta);
	}

	int cachedI, cachedJ, cachedK;

	@Inject(method = "method_1707", at = @At("HEAD"))
	private void reforged$method_1707(int i, int j, int k, int l, CallbackInfo ci) {
		this.cachedI = i;
		this.cachedJ = j;
		this.cachedK = k;
	}

	@Redirect(method = "method_1707", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getHardness(Lnet/minecraft/entity/player/PlayerEntity;)F"))
	private float reforged$method_1707(Block instance, PlayerEntity playerEntity) {
		return ((ForgeBlock) instance).blockStrength(this.client.world, playerEntity, this.cachedI, this.cachedJ, this.cachedK);
	}

	int cachedI2, cachedJ2, cachedK2;

	@Inject(method = "method_1721", at = @At("HEAD"))
	private void reforged$method_1721(int i, int j, int k, int l, CallbackInfo ci) {
		this.cachedI2 = i;
		this.cachedJ2 = j;
		this.cachedK2 = k;
	}

	@Redirect(method = "method_1721", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getHardness(Lnet/minecraft/entity/player/PlayerEntity;)F"))
	private float reforged$method_1721(Block instance, PlayerEntity playerEntity) {
		return ((ForgeBlock) instance).blockStrength(this.client.world, playerEntity, this.cachedI2, this.cachedJ2, this.cachedK2);
	}
}
