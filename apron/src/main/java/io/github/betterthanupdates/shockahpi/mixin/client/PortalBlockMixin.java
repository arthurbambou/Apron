package io.github.betterthanupdates.shockahpi.mixin.client;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.PortalBlock;

import io.github.betterthanupdates.shockahpi.block.ShockAhPIPortalBlock;

@Mixin(PortalBlock.class)
public abstract class PortalBlockMixin implements ShockAhPIPortalBlock {
	@Override
	public int getDimNumber() {
		return -1;
	}
}
