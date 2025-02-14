package io.github.betterthanupdates.forge.mixin.server.nostation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.PrintStream;
import net.minecraft.server.ChunkMap;

@Mixin(ChunkMap.TrackedChunk.class)
public class ServerPlayerView_class_514Mixin {
	@Redirect(method = "method_1752", at = @At(value = "INVOKE", target = "Ljava/io/PrintStream;println(Ljava/lang/String;)V", remap = false))
	private void preventFromSpammingConsole(PrintStream instance, String x) {
		// Cancelled
	}
}
