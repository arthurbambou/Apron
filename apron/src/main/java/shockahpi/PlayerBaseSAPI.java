package shockahpi;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ClientPlayerEntity;
import playerapi.PlayerBase;
import net.minecraft.achievement.Achievements;
import net.minecraft.client.Minecraft;
import io.github.betterthanupdates.Legacy;
import io.github.betterthanupdates.playerapi.client.entity.player.PlayerAPIClientPlayerEntity;

@Legacy
public class PlayerBaseSAPI extends PlayerBase {
	public int portal;

	public PlayerBaseSAPI(ClientPlayerEntity p) {
		super(p);
	}

	public boolean onLivingUpdate() {
		if (FabricLoader.getInstance().isModLoaded("station-dimensions-v0")) return false;

		Minecraft mc = this.player.minecraft;
		if (!mc.stats.hasAchievement(Achievements.OPEN_INVENTORY)) {
			mc.toast.setTutorial(Achievements.OPEN_INVENTORY);
		}

		this.player.lastScreenDistortion = this.player.screenDistortion;
		if (this.portal != 0) {
			DimensionBase dimensionbase = DimensionBase.getDimByNumber(this.portal);
			ClientPlayerEntity var10000;
			if (dimensionbase != null && this.player.inTeleportationState) {
				if (!this.player.world.isRemote && this.player.vehicle != null) {
					this.player.setVehicle((Entity) null);
				}

				if (mc.currentScreen != null) {
					mc.setScreen((Screen) null);
				}

				if (this.player.screenDistortion == 0.0F) {
					mc.soundManager.playSound(dimensionbase.soundTrigger, 1.0F, this.player.random.nextFloat() * 0.4F + 0.8F);
				}

				var10000 = this.player;
				var10000.screenDistortion += 0.0125F;
				if (this.player.screenDistortion >= 1.0F) {
					this.player.screenDistortion = 1.0F;
					if (!this.player.world.isRemote) {
						this.player.portalCooldown = 10;
						mc.soundManager.playSound(dimensionbase.soundTravel, 1.0F, this.player.random.nextFloat() * 0.4F + 0.8F);
						DimensionBase.usePortal(this.portal);
					}
				}

				this.player.inTeleportationState = false;
			} else {
				if (this.player.screenDistortion > 0.0F) {
					var10000 = this.player;
					var10000.screenDistortion -= 0.05F;
				}

				if (this.player.screenDistortion < 0.0F) {
					this.player.screenDistortion = 0.0F;
				}
			}
		}

		if (this.player.portalCooldown > 0) {
			--this.player.portalCooldown;
		}

		this.player.input.update(this.player);
		if (this.player.input.sneaking && this.player.cameraOffset < 0.2F) {
			this.player.cameraOffset = 0.2F;
		}

		this.player.pushOutOfBlock(this.player.x - (double)this.player.width * 0.35, this.player.boundingBox.minY + 0.5, this.player.z + (double)this.player.width * 0.35);
		this.player.pushOutOfBlock(this.player.x - (double)this.player.width * 0.35, this.player.boundingBox.minY + 0.5, this.player.z - (double)this.player.width * 0.35);
		this.player.pushOutOfBlock(this.player.x + (double)this.player.width * 0.35, this.player.boundingBox.minY + 0.5, this.player.z - (double)this.player.width * 0.35);
		this.player.pushOutOfBlock(this.player.x + (double)this.player.width * 0.35, this.player.boundingBox.minY + 0.5, this.player.z + (double)this.player.width * 0.35);
		((PlayerAPIClientPlayerEntity) this.player).superOnLivingUpdate();
		return true;
	}

	public boolean respawn() {
		if (FabricLoader.getInstance().isModLoaded("station-dimensions-v0")) return false;

		DimensionBase.respawn(false, 0);
		return true;
	}
}
