package io.github.betterthanupdates.shockahpi.mixin.client.nostation;

import java.io.File;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shockahpi.DimensionBase;
import net.minecraft.world.chunk.storage.ChunkStorage;
import net.minecraft.world.chunk.storage.RegionChunkStorage;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.storage.AlphaWorldStorage;
import net.minecraft.world.storage.RegionWorldStorage;

@Mixin(RegionWorldStorage.class)
public abstract class McRegionDimensionFileMixin extends AlphaWorldStorage {
	public McRegionDimensionFileMixin(File file, String string, boolean bl) {
		super(file, string, bl);
	}

	/**
	 * @author SAPI
	 * @reason
	 */
	@Inject(method = "method_1734", at = @At("HEAD"), cancellable = true)
	public void getChunkIO(Dimension paramxa, CallbackInfoReturnable<ChunkStorage> cir) {
		File localFile1 = this.getDirectory();
		DimensionBase localDimensionBase = DimensionBase.getDimByProvider(paramxa.getClass());

		if (localDimensionBase != null && localDimensionBase.number != 0) {
			File localFile2 = new File(localFile1, "DIM" + localDimensionBase.number);
			localFile2.mkdirs();
			cir.setReturnValue(new RegionChunkStorage(localFile2));
		} else {
			cir.setReturnValue(new RegionChunkStorage(localFile1));
		}
	}
}
