package io.github.betterthanupdates.modloader.mixin.server;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.command.ServerCommandHandler;
import net.minecraft.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.SERVER)
@Mixin(ServerCommandHandler.class)
public abstract class CommandManagerMixin {
	@Redirect(method = "method_1411", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_73;method_204(J)V"))
	private void makeClientCompatibleIGuess(ServerWorld instance, long l) {
		instance.setTime(l);
	}
}
