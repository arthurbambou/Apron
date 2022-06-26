package io.github.betterthanupdates.forge.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface ForgeItem {
	float getStrVsBlock(ItemStack stack, Block block, int meta);

	boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, PlayerEntity player);
}
