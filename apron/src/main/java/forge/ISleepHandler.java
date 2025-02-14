/**
 * This software is provided under the terms of the Minecraft Forge Public
 * License v1.1.
 */

package forge;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.SleepAttemptResult;
import io.github.betterthanupdates.Legacy;

@Legacy
public interface ISleepHandler {
	SleepAttemptResult sleepInBedAt(PlayerEntity player, int x, int y, int z);
}
