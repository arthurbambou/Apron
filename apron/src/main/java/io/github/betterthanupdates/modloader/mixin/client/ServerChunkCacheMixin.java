package io.github.betterthanupdates.modloader.mixin.client;

import modloader.ModLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkCache;
import net.minecraft.world.chunk.ChunkSource;

@Mixin(ChunkCache.class)
public abstract class ServerChunkCacheMixin implements ChunkSource {
	@Shadow
	private ChunkSource field_1227;

	@Shadow
	private World field_1231;

	@Inject(method = "method_1803", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;method_885()V"))
	private void modloader$decorate(ChunkSource i, int j, int par3, CallbackInfo ci) {
		ModLoader.PopulateChunk(this.field_1227, j, par3, this.field_1231);
	}
}
