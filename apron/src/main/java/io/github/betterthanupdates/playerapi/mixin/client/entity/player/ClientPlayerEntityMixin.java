package io.github.betterthanupdates.playerapi.mixin.client.entity.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.catcore.cursedmixinextensions.annotations.ShadowSuper;
import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import playerapi.PlayerAPI;
import playerapi.PlayerBase;

import net.minecraft.block.Block;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.Session;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.SleepAttemptResult;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import io.github.betterthanupdates.playerapi.client.entity.player.PlayerAPIClientPlayerEntity;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity implements PlayerAPIClientPlayerEntity {
	@Shadow
	public Minecraft minecraft;

	public ClientPlayerEntityMixin(World arg) {
		super(arg);
	}

	@Unique
	public List<PlayerBase> playerBases = new ArrayList<>();

	@Inject(method = "<init>", at = @At("RETURN"))
	private void papi$init(Minecraft arg, World arg2, Session i, int par4, CallbackInfo ci) {
		this.playerBases = PlayerAPI.playerInit((ClientPlayerEntity) (Object) this);
	}

	@Override
	public boolean damage(Entity entity, int i) {
		return !PlayerAPI.attackEntityFrom((ClientPlayerEntity) (Object) this, entity, i) && super.damage(entity, i);
	}

	@Override
	public void onKilledBy(Entity entity) {
		if (!PlayerAPI.onDeath((ClientPlayerEntity) (Object) this, entity)) {
			super.onKilledBy(entity);
		}
	}

	@Inject(method = "method_910", at = @At("HEAD"), cancellable = true)
	private void papi$tickHandSwing(CallbackInfo ci) {
		if (PlayerAPI.updatePlayerActionState((ClientPlayerEntity) (Object) this)) ci.cancel();
	}

	@Override
	public void superUpdatePlayerActionState() {
		super.tickLiving();
	}

	@Inject(method = "method_937", at = @At("HEAD"), cancellable = true)
	private void papi$updateDespawnCounter(CallbackInfo ci) {
		if (PlayerAPI.onLivingUpdate((ClientPlayerEntity) (Object) this)) ci.cancel();
	}

	@Override
	public void superOnLivingUpdate() {
		super.tickMovement();
	}

	@Override
	public void superOnUpdate() {
		super.tick();
	}

	@Override
	public void moveNonSolid(float f, float f1, float f2) {
		if (!PlayerAPI.moveFlying((ClientPlayerEntity) (Object) this, f, f1, f2)) {
			super.moveNonSolid(f, f1, f2);
		}
	}

	@Override
	protected boolean bypassesSteppingEffects() {
		return PlayerAPI.canTriggerWalking((ClientPlayerEntity) (Object) this, true);
	}

	@Inject(method = "method_136", at = @At("HEAD"), cancellable = true)
	private void papi$method_136(int i, boolean flag, CallbackInfo ci) {
		if (PlayerAPI.handleKeyPress((ClientPlayerEntity) (Object) this, i, flag)) ci.cancel();
	}

	@Inject(method = "writeNbt", at = @At("HEAD"), cancellable = true)
	private void papi$writeAdditional(NbtCompound par1, CallbackInfo ci) {
		if (PlayerAPI.writeEntityToNBT((ClientPlayerEntity) (Object) this, par1)) ci.cancel();
	}

	@Inject(method = "readNbt", at = @At("HEAD"), cancellable = true)
	private void papi$readAdditional(NbtCompound par1, CallbackInfo ci) {
		if (PlayerAPI.readEntityFromNBT((ClientPlayerEntity) (Object) this, par1)) ci.cancel();
	}

	@Inject(method = "closeScreen", at = @At("HEAD"), cancellable = true)
	private void papi$closeContainer(CallbackInfo ci) {
		if (PlayerAPI.onExitGUI((ClientPlayerEntity) (Object) this)) ci.cancel();
	}

	@Inject(method = "method_489", at = @At("HEAD"), cancellable = true)
	private void papi$openSignScreen(SignBlockEntity par1, CallbackInfo ci) {
		if (PlayerAPI.displayGUIEditSign((ClientPlayerEntity) (Object) this, par1)) ci.cancel();
	}

	@Inject(method = "method_486", at = @At("HEAD"), cancellable = true)
	private void papi$openChestScreen(Inventory par1, CallbackInfo ci) {
		if (PlayerAPI.displayGUIChest((ClientPlayerEntity) (Object) this, par1)) ci.cancel();
	}

	@Inject(method = "method_484", at = @At("HEAD"), cancellable = true)
	private void papi$openCraftingScreen(int i, int j, int k, CallbackInfo ci) {
		if (PlayerAPI.displayWorkbenchGUI((ClientPlayerEntity) (Object) this, i, j, k)) ci.cancel();
	}

	@Inject(method = "method_487", at = @At("HEAD"), cancellable = true)
	private void papi$openFurnaceScreen(FurnaceBlockEntity par1, CallbackInfo ci) {
		if (PlayerAPI.displayGUIFurnace((ClientPlayerEntity) (Object) this, par1)) ci.cancel();
	}

	@Inject(method = "method_485", at = @At("HEAD"), cancellable = true)
	private void papi$openDispenserScreen(DispenserBlockEntity par1, CallbackInfo ci) {
		if (PlayerAPI.displayGUIDispenser((ClientPlayerEntity) (Object) this, par1)) ci.cancel();
	}

	@Inject(method = "method_141", at = @At("RETURN"), cancellable = true)
	private void papi$getArmorValue(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(PlayerAPI.getPlayerArmorValue((ClientPlayerEntity) (Object) this, cir.getReturnValue()));
	}

	@Override
	public void markDead() {
		if (!PlayerAPI.setEntityDead((ClientPlayerEntity) (Object) this)) {
			super.markDead();
		}
	}

	@Override
	public double getSquaredDistance(double d, double d1, double d2) {
		return PlayerAPI.getDistanceSq((ClientPlayerEntity) (Object) this, d, d1, d2, super.getSquaredDistance(d, d1, d2));
	}

	@Override
	public boolean isSubmergedInWater() {
		return PlayerAPI.isInWater((ClientPlayerEntity) (Object) this, this.submergedInWater);
	}

	@Inject(method = "method_1373", at = @At("RETURN"), cancellable = true)
	private void papi$isSneaking(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(PlayerAPI.isSneaking((ClientPlayerEntity) (Object) this, cir.getReturnValue()));
	}

	@Override
	public float getBlockBreakingSpeed(Block block) {
		float f = this.inventory.getStrengthOnBlock(block);

		if (this.isInFluid(Material.WATER)) {
			f /= 5.0F;
		}

		if (!this.onGround) {
			f /= 5.0F;
		}

		return PlayerAPI.getCurrentPlayerStrVsBlock((ClientPlayerEntity) (Object) this, block, f);
	}

	@Override
	public void heal(int i) {
		if (!PlayerAPI.heal((ClientPlayerEntity) (Object) this, i)) {
			super.heal(i);
		}
	}

	@Inject(method = "respawn", at = @At("HEAD"), cancellable = true)
	private void papi$respawn(CallbackInfo ci) {
		if (PlayerAPI.respawn((ClientPlayerEntity) (Object) this)) ci.cancel();
	}

	@Inject(method = "pushOutOfBlock", at = @At("HEAD"), cancellable = true)
	private void papi$method_1372(double d, double d1, double d2, CallbackInfoReturnable<Boolean> cir) {
		if (PlayerAPI.pushOutOfBlocks((ClientPlayerEntity) (Object) this, d, d1, d2)) cir.setReturnValue(false);
	}

	@Override
	public SleepAttemptResult superSleepInBedAt(int i, int j, int k) {
		return super.trySleep(i, j, k);
	}

	@Override
	public Minecraft getMc() {
		return this.minecraft;
	}

	@Override
	public void superMoveEntity(double d, double d1, double d2) {
		super.move(d, d1, d2);
	}

	@Override
	public void setMoveForward(float f) {
		this.forwardSpeed = f;
	}

	@Override
	public void setMoveStrafing(float f) {
		this.sidewaysSpeed = f;
	}

	@Override
	public void setIsJumping(boolean flag) {
		this.jumping = flag;
	}

	@Override
	public float getBrightnessAtEyes(float f) {
		return PlayerAPI.getEntityBrightness((ClientPlayerEntity) (Object) this, f, super.getBrightnessAtEyes(f));
	}

	@Override
	public void tick() {
		PlayerAPI.beforeUpdate((ClientPlayerEntity) (Object) this);

		if (!PlayerAPI.onUpdate((ClientPlayerEntity) (Object) this)) {
			super.tick();
		}

		PlayerAPI.afterUpdate((ClientPlayerEntity) (Object) this);
	}

	@Override
	public void superMoveFlying(float f, float f1, float f2) {
		super.moveNonSolid(f, f1, f2);
	}

	@Inject(method = "move", at = @At("HEAD"))
	private void papi$beforeMoveEntity(double d, double d1, double d2, CallbackInfo ci) {
		PlayerAPI.beforeMoveEntity((ClientPlayerEntity) (Object) this, d, d1, d2);
	}

	@Inject(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;move(DDD)V"), cancellable = true)
	private void papi$moveEntity(double d, double d1, double d2, CallbackInfo ci) {
		if (PlayerAPI.moveEntity((ClientPlayerEntity) (Object) this, d, d1, d2)) {
			ci.cancel();
		}
	}

	@Inject(method = "move", at = @At("RETURN"))
	private void papi$afterMoveEntity(double d, double d1, double d2, CallbackInfo ci) {
		PlayerAPI.afterMoveEntity((ClientPlayerEntity) (Object) this, d, d1, d2);
	}

	@Override
	public SleepAttemptResult trySleep(int i, int j, int k) {
		PlayerAPI.beforeSleepInBedAt((ClientPlayerEntity) (Object) this, i, j, k);
		SleepAttemptResult sleepStatus = PlayerAPI.sleepInBedAt((ClientPlayerEntity) (Object) this, i, j, k);
		return sleepStatus == null ? super.trySleep(i, j, k) : sleepStatus;
	}

	@Override
	public void doFall(float fallDist) {
		super.onLanding(fallDist);
	}

	@Override
	public float getFallDistance() {
		return this.fallDistance;
	}

	@Override
	public boolean getSleeping() {
		return this.sleeping;
	}

	@Override
	public boolean getJumping() {
		return this.jumping;
	}

	@Override
	public void doJump() {
		this.jump();
	}

	@Override
	public Random getRandom() {
		return this.random;
	}

	@Override
	public void setFallDistance(float f) {
		this.fallDistance = f;
	}

	@Override
	public void setYSize(float f) {
		this.cameraOffset = f;
	}

	@Override
	public void travel(float f, float f1) {
		if (!PlayerAPI.moveEntityWithHeading((ClientPlayerEntity) (Object) this, f, f1)) {
			super.travel(f, f1);
		}
	}

	@Override
	public boolean isOnLadder() {
		return PlayerAPI.isOnLadder((ClientPlayerEntity) (Object) this, super.isOnLadder());
	}

	@Override
	public void setActionState(float newMoveStrafing, float newMoveForward, boolean newIsJumping) {
		this.sidewaysSpeed = newMoveStrafing;
		this.forwardSpeed = newMoveForward;
		this.jumping = newIsJumping;
	}

	@Override
	public boolean isInFluid(Material material) {
		return PlayerAPI.isInsideOfMaterial((ClientPlayerEntity) (Object) this, material, super.isInFluid(material));
	}

	@Override
	public void dropSelectedItem() {
		if (!PlayerAPI.dropCurrentItem((ClientPlayerEntity) (Object) this)) {
			super.dropSelectedItem();
		}
	}

	@Override
	public void dropItem(ItemStack itemstack) {
		if (!PlayerAPI.dropPlayerItem((ClientPlayerEntity) (Object) this, itemstack)) {
			super.dropItem(itemstack);
		}
	}

	@Override
	public boolean superIsInsideOfMaterial(Material material) {
		return super.isInFluid(material);
	}

	@Override
	public float superGetEntityBrightness(float f) {
		return super.getBrightnessAtEyes(f);
	}

	@Inject(method = "sendChatMessage", at = @At("RETURN"))
	private void papi$sendChatMessage(String s, CallbackInfo ci) {
		PlayerAPI.sendChatMessage((ClientPlayerEntity) (Object) this, s);
	}

	@Override
	protected String getHurtSound() {
		String result = PlayerAPI.getHurtSound((ClientPlayerEntity) (Object) this);
		return result != null ? result : super.getHurtSound();
	}

	@Override
	public String superGetHurtSound() {
		return super.getHurtSound();
	}

	@Override
	public float superGetCurrentPlayerStrVsBlock(Block block) {
		return super.getBlockBreakingSpeed(block);
	}

	@Override
	public boolean canHarvest(Block block) {
		Boolean result = PlayerAPI.canHarvestBlock((ClientPlayerEntity) (Object) this, block);
		return result != null ? result : super.canHarvest(block);
	}

	@Override
	public boolean superCanHarvestBlock(Block block) {
		return super.canHarvest(block);
	}

	@Override
	protected void onLanding(float f) {
		if (!PlayerAPI.fall((ClientPlayerEntity) (Object) this, f)) {
			super.onLanding(f);
		}
	}

	@Override
	public void superFall(float f) {
		super.onLanding(f);
	}

	@Override
	protected void jump() {
		if (!PlayerAPI.jump((ClientPlayerEntity) (Object) this)) {
			super.jump();
		}
	}

	@Override
	public void superJump() {
		super.jump();
	}

	@Override
	protected void applyDamage(int i) {
		if (!PlayerAPI.damageEntity((ClientPlayerEntity) (Object) this, i)) {
			super.applyDamage(i);
		}
	}

	@ShadowSuper("superDamageEntity")
	public abstract void superSuperDamageEntity(int i);

	@Override
	public void superDamageEntity(int i) {
		if (FabricLoader.getInstance().isModLoaded("station-player-api-v0")) {
			this.superSuperDamageEntity(i);
		} else {
			super.applyDamage(i);
		}
	}

	@Override
	public double getSquaredDistance(Entity entity) {
		Double result = PlayerAPI.getDistanceSqToEntity((ClientPlayerEntity) (Object) this, entity);
		return result != null ? result : super.getSquaredDistance(entity);
	}

	@Override
	public double superGetDistanceSqToEntity(Entity entity) {
		return super.getSquaredDistance(entity);
	}

	@Override
	public void attack(Entity entity) {
		if (!PlayerAPI.attackTargetEntityWithCurrentItem((ClientPlayerEntity) (Object) this, entity)) {
			super.attack(entity);
		}
	}

	@Override
	public void superAttackTargetEntityWithCurrentItem(Entity entity) {
		super.attack(entity);
	}

	@Override
	public boolean checkWaterCollisions() {
		Boolean result = PlayerAPI.handleWaterMovement((ClientPlayerEntity) (Object) this);
		return result != null ? result : super.checkWaterCollisions();
	}

	@Override
	public boolean superHandleWaterMovement() {
		return super.checkWaterCollisions();
	}

	@Override
	public boolean isTouchingLava() {
		Boolean result = PlayerAPI.handleLavaMovement((ClientPlayerEntity) (Object) this);
		return result != null ? result : super.isTouchingLava();
	}

	@Override
	public boolean superHandleLavaMovement() {
		return super.isTouchingLava();
	}

	@Override
	public void dropItem(ItemStack itemstack, boolean flag) {
		if (!PlayerAPI.dropPlayerItemWithRandomChoice((ClientPlayerEntity) (Object) this, itemstack, flag)) {
			super.dropItem(itemstack, flag);
		}
	}

	@Override
	public void superDropPlayerItemWithRandomChoice(ItemStack itemstack, boolean flag) {
		super.dropItem(itemstack, flag);
	}

	@Override
	public List<PlayerBase> getPlayerBases() {
		return this.playerBases;
	}
}
