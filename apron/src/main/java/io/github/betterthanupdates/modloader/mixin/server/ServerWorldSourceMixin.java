package io.github.betterthanupdates.modloader.mixin.server;

import modloader.ModLoader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.world.chunk.ServerChunkCache;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.chunk.ChunkSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.SERVER)
@Mixin(ServerChunkCache.class)
public abstract class ServerWorldSourceMixin implements ChunkSource {
	@Shadow
	private ChunkSource field_936;

	@Shadow
	private ServerWorld field_940;

	@Inject(method = "method_1803", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;method_885()V"))
	private void modloader$decorate(ChunkSource ichunkprovider, int i, int j, CallbackInfo ci) {
		ModLoader.PopulateChunk(this.field_936, i << 4, j << 4, this.field_940);
	}
}
