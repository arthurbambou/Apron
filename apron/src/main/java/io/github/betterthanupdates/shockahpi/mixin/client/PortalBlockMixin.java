package io.github.betterthanupdates.shockahpi.mixin.client;

import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import io.github.betterthanupdates.shockahpi.block.ShockAhPIPortalBlock;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import playerapi.PlayerAPI;
import shockahpi.PlayerBaseSAPI;

@Mixin(NetherPortalBlock.class)
public abstract class PortalBlockMixin implements ShockAhPIPortalBlock {
	@Override
	public int getDimNumber() {
		return -1;
	}
}
