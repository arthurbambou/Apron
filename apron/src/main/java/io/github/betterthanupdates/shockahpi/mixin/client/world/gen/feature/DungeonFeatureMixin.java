package io.github.betterthanupdates.shockahpi.mixin.client.world.gen.feature;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shockahpi.SAPI;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.DungeonFeature;
import net.minecraft.world.gen.feature.Feature;
import com.llamalad7.mixinextras.sugar.Local;

@Mixin(DungeonFeature.class)
public abstract class DungeonFeatureMixin extends Feature {

	/**
	 * @author SAPI
	 * @reason
	 */
	@Inject(method = "generate", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/World;method_1777(III)Lnet/minecraft/block/entity/BlockEntity;", ordinal = 0, shift = At.Shift.BY, by = 2))
	public void generate(World world, Random random, int i, int j, int k, CallbackInfoReturnable<Boolean> cir, @Local ChestBlockEntity chestBlockEntity) {
		for (int i6 = 0; i6 < Math.min(19, SAPI.dungeonGetAmountOfGuaranteed()); ++i6) {
			ItemStack stack = SAPI.dungeonGetGuaranteed(i6).getStack();

			if (stack != null) {
				chestBlockEntity.setStack(random.nextInt(chestBlockEntity.size()), stack);
			}
		}
	}

	/**
	 * @author SAPI
	 * @reason
	 */
	@Inject(method = "getRandomChestItem", at = @At("HEAD"), cancellable = true)
	private void getRandomChestItem(Random paramRandom, CallbackInfoReturnable<ItemStack> cir) {
		cir.setReturnValue(SAPI.dungeonGetRandomItem());
	}

	/**
	 * @author SAPI
	 * @reason
	 */
	@Inject(method = "getRandomEntity", at = @At("HEAD"), cancellable = true)
	private void getRandomEntity(Random paramRandom, CallbackInfoReturnable<String> cir) {
		cir.setReturnValue(SAPI.dungeonGetRandomMob());
	}
}
