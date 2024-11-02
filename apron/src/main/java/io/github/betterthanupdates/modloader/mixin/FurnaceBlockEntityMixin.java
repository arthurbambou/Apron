package io.github.betterthanupdates.modloader.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import modloader.ModLoader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.BlockEntity;
import net.minecraft.entity.block.FurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

@Mixin(FurnaceBlockEntity.class)
public abstract class FurnaceBlockEntityMixin extends BlockEntity implements Inventory {
	@Shadow
	private ItemStack[] inventory;

	@WrapWithCondition(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/item/ItemStack;count:I", opcode = Opcodes.PUTFIELD))
	private boolean modloader$tick(ItemStack instance, int value) {
		if (this.inventory[1].getItem().hasContainerItemType()) {
			this.inventory[1] = new ItemStack(this.inventory[1].getItem().getContainerItemType());
			return false;
		}

		return true;
	}

	@Inject(method = "getFuelTime", cancellable = true, at = @At("RETURN"))
	private void modloader$getFuelTime(ItemStack par1, CallbackInfoReturnable<Integer> cir) {
		if (par1 != null && cir.getReturnValue() == 0) {
			int j = par1.getItem().id;
			cir.setReturnValue(ModLoader.AddAllFuel(j));
		}
	}
}
