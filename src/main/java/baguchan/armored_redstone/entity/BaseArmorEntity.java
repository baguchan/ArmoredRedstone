package baguchan.armored_redstone.entity;

import baguchan.armored_redstone.ArmoredRedstone;
import baguchan.armored_redstone.message.FinishDushAttackMessage;
import baguchan.armored_redstone.message.StartDushAttackMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class BaseArmorEntity extends PathfinderMob implements PlayerRideableJumping {
	private static final UUID SPEED_MODIFIER_EXTRA_SPRINTING_UUID = UUID.fromString("d4c7a47d-709e-9722-a10c-91cc76449c88");
	private static final AttributeModifier SPEED_MODIFIER_EXTRA_SPRINTING = new AttributeModifier(SPEED_MODIFIER_EXTRA_SPRINTING_UUID, "Extra Sprinting speed boost", (double) 1.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);

	protected boolean isJumping;
	protected float playerJumpPendingScale;
	private boolean allowStandSliding;

	public final AnimationState attackAnimationState = new AnimationState();
	public final AnimationState attackFinishedAnimationState = new AnimationState();

	protected BaseArmorEntity(EntityType<? extends BaseArmorEntity> p_20966_, Level p_20967_) {
		super(p_20966_, p_20967_);
	}

	public void setSprinting(boolean p_21284_) {
		super.setSprinting(p_21284_);
		AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (attributeinstance.getModifier(SPEED_MODIFIER_EXTRA_SPRINTING_UUID) != null) {
			attributeinstance.removeModifier(SPEED_MODIFIER_EXTRA_SPRINTING);
		}

		if (p_21284_) {
			attributeinstance.addTransientModifier(SPEED_MODIFIER_EXTRA_SPRINTING);
		}

	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
		super.onSyncedDataUpdated(p_21104_);
		if (DATA_SHARED_FLAGS_ID.equals(p_21104_)) {
			if (this.isSprinting()) {
				this.playSound(SoundEvents.REDSTONE_TORCH_BURNOUT, 1.5F, 1.2F);
			} else {
				this.playSound(SoundEvents.REDSTONE_TORCH_BURNOUT, 1.5F, 1.2F);
			}
		}
	}

	public boolean causeFallDamage(float p_149499_, float p_149500_, DamageSource p_149501_) {

		int i = this.calculateFallDamage(p_149499_, p_149500_);
		if (i <= 0) {
			return false;
		} else {
			this.hurt(p_149501_, (float) i);
			if (this.isVehicle()) {
				for (Entity entity : this.getIndirectPassengers()) {
					entity.hurt(p_149501_, (float) i);
				}
			}

			this.playBlockFallSound();
			return true;
		}
	}

	@Override
	protected int calculateFallDamage(float p_21237_, float p_21238_) {
		return super.calculateFallDamage(p_21237_, p_21238_) - 10;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level.isClientSide()) {
			this.updateClientControls();
		}

		if (this.isSprinting()) {
			this.dash();
		}
		if (this.canStepUp()) {
			if (this.isSprinting()) {
				this.maxUpStep = 0.5F;
			} else {
				this.maxUpStep = 1.0F;
			}
		}
	}

	private boolean isMoving() {
		return canDashWithWall() || this.getDeltaMovement().horizontalDistanceSqr() > 0D;
	}

	protected boolean canDashWithWall() {
		return false;
	}

	protected boolean canStepUp() {
		return true;
	}

	protected boolean canDush() {
		return true;
	}


	@OnlyIn(Dist.CLIENT)
	protected void updateClientControls() {
		Minecraft mc = Minecraft.getInstance();

		if (mc.player != null && this.hasPassenger(mc.player)) {
			boolean flag6 = this.horizontalCollision;


			if (!this.isSprinting() && this.canDush() && mc.options.keySprint.isDown() && mc.options.keyUp.isDown() && this.isMoving() && this.isOnGround()) {
				dushStart();
			} else if (this.isSprinting() && (!this.isMoving()) && !mc.options.keySprint.isDown()) {
				dushFinish();
			}
		} else {
			if (this.isSprinting() && this.getControllingPassenger() == null) {
				dushFinish();
			}
		}
	}

	@Override
	protected void spawnSprintParticle() {
		super.spawnSprintParticle();
		EntityDimensions dimensions = this.getDimensions(this.getPose());
		Vec3 vec3 = this.getDeltaMovement();
		this.level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (this.random.nextDouble() - 0.5D) * (double) dimensions.width, this.getY() + 0.1D, this.getZ() + (this.random.nextDouble() - 0.5D) * (double) dimensions.width, vec3.x * -0.25D, 0.25D, vec3.z * -0.25D);
	}

	private void dushFinish() {
		ArmoredRedstone.CHANNEL.sendToServer(new FinishDushAttackMessage(this));
	}

	private void dushStart() {
		ArmoredRedstone.CHANNEL.sendToServer(new StartDushAttackMessage(this));
	}

	@Override
	protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
		super.playStepSound(p_20135_, p_20136_);
		if (!this.isSprinting()) {

			if (stepSounds() != null) {
				this.playSound(stepSounds(), 1.0F, 1.0F);
			}
		}
	}

	@Nullable
	public SoundEvent stepSounds() {
		return SoundEvents.IRON_GOLEM_STEP;
	}

	@Override
	public double getPassengersRidingOffset() {
		return (double) this.getDimensions(this.getPose()).height * 0.75D;
	}

	public abstract void attack();

	public abstract void secondAttack();

	public AABB getAttackBoundingBox() {
		Vec3 vec3d = this.getViewVector(1.0F);
		return this.getBoundingBox().contract(0, -(this.getBbHeight() - this.getBbWidth()), 0).inflate(1.0D, 1.0D, 1.0D).move(vec3d.x * 1.65D, vec3d.y * 1.65D, vec3d.z * 1.65D);
	}

	private void dash() {
		for (Entity entity : this.level.getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(0.75D, 0.0D, 0.75D))) {
			if (entity != this && (this.getFirstPassenger() == null || this.getFirstPassenger() != null && entity != this.getFirstPassenger()) && !this.isAlliedTo(entity) && (entity.isAttackable() && this.distanceTo(entity) < 26.0D)) {
				if (entity.hurt(DamageSource.mobAttack(this), 7.0F)) {
					entity.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 2.0F, 1.0F);
					if (entity instanceof LivingEntity) {
						double d1 = entity.getX() - this.getX();

						double d0;
						for (d0 = entity.getZ() - this.getZ(); d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
							d1 = (Math.random() - Math.random()) * 0.01D;
						}

						((LivingEntity) entity).knockback(1.0, d1, d0);
						if (this.getFirstPassenger() instanceof Player) {
							((LivingEntity) entity).setLastHurtByPlayer((Player) this.getFirstPassenger());
						}
					}
				}
			}
		}
	}

	public void travel(Vec3 p_30633_) {
		if (this.isAlive()) {
			LivingEntity livingentity = this.getControllingPassenger();
			if (this.isVehicle() && livingentity != null) {
				this.setYRot(livingentity.getYRot());
				this.yRotO = this.getYRot();
				this.setXRot(livingentity.getXRot() * 0.5F);
				this.setRot(this.getYRot(), this.getXRot());
				this.yBodyRot = this.getYRot();
				this.yHeadRot = this.yBodyRot;
				float f = livingentity.xxa * 0.5F;
				float f1 = livingentity.zza;
				if (f1 <= 0.0F) {
					f1 *= 0.35F;
				}

				if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround) {
					double d0 = 1.1D * (double) this.playerJumpPendingScale * (double) this.getBlockJumpFactor();
					double d1 = d0 + this.getJumpBoostPower();
					Vec3 vec3 = this.getDeltaMovement();
					this.setDeltaMovement(vec3.x, d1, vec3.z);
					this.setIsJumping(true);
					this.hasImpulse = true;
					net.minecraftforge.common.ForgeHooks.onLivingJump(this);
					if (f1 > 0.0F) {
						float f2 = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
						float f3 = Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
						this.setDeltaMovement(this.getDeltaMovement().add((double) (-0.4F * f2 * this.playerJumpPendingScale), 0.0D, (double) (0.4F * f3 * this.playerJumpPendingScale)));
					}

					this.playerJumpPendingScale = 0.0F;
				}

				this.flyingSpeed = this.getSpeed() * 0.1F;
				if (this.isControlledByLocalInstance()) {
					this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
					super.travel(new Vec3((double) f, p_30633_.y, (double) f1));
				} else if (livingentity instanceof Player) {
					this.setDeltaMovement(Vec3.ZERO);
				}

				if (this.onGround || this.fireImmune() && this.canJumpOnLava() && this.isInFluidType(ForgeMod.LAVA_TYPE.get())) {
					this.playerJumpPendingScale = 0.0F;
					this.setIsJumping(false);
				}

				//this.calculateEntityAnimation(this, false);
				this.tryCheckInsideBlocks();
			} else {
				this.flyingSpeed = 0.02F;
				super.travel(p_30633_);
			}
		}
	}

	public boolean canJumpOnLava() {
		return false;
	}

	@Nullable
	public LivingEntity getControllingPassenger() {
		Entity entity = this.getFirstPassenger();
		if (entity instanceof Player) {
			return (LivingEntity) entity;
		}
		return null;
	}

	public InteractionResult mobInteract(Player p_30713_, InteractionHand p_30714_) {
		ItemStack itemstack = p_30713_.getItemInHand(p_30714_);
		if (this.isBaby()) {
			return super.mobInteract(p_30713_, p_30714_);
		} else {
			if (this.getHealth() < this.getMaxHealth() && this.healItem(itemstack)) {
				this.heal(20);
				return InteractionResult.sidedSuccess(this.level.isClientSide);
			}

			this.doPlayerRide(p_30713_);
			return InteractionResult.sidedSuccess(this.level.isClientSide);
		}
	}

	public boolean healItem(ItemStack itemstack) {
		return itemstack.is(Items.IRON_INGOT);
	}


	protected void doPlayerRide(Player p_30634_) {
		if (!this.level.isClientSide) {
			p_30634_.setYRot(this.getYRot());
			p_30634_.setXRot(this.getXRot());
			p_30634_.startRiding(this);
		}

	}

	@Override
	public void onPlayerJump(int p_30591_) {

		if (p_30591_ < 0) {
			p_30591_ = 0;
		} else {
			this.allowStandSliding = true;
			this.stand();
		}

		if (p_30591_ >= 90) {
			this.playerJumpPendingScale = 1.0F;
		} else {
			this.playerJumpPendingScale = 0.4F + 0.4F * (float) p_30591_ / 90.0F;
		}
	}

	private void stand() {
	}

	public boolean isJumping() {
		return this.isJumping;
	}

	public void setIsJumping(boolean p_30656_) {
		this.isJumping = p_30656_;
	}

	@Override
	public boolean canJump() {
		return true;
	}

	@Override
	public void handleStartJump(int p_21695_) {
		this.allowStandSliding = true;
		this.stand();
		this.playSound(SoundEvents.REDSTONE_TORCH_BURNOUT, 1.5F, 1.2F);
	}

	@Override
	public void handleStopJump() {

	}

	public boolean rideableUnderWater() {
		return true;
	}

	protected float getWaterSlowDown() {
		return 0.9F;
	}

	@Override
	public boolean canDrownInFluidType(FluidType type) {
		return false;
	}

	@Override
	public boolean canFreeze() {
		return false;
	}

	@Override
	public boolean isAffectedByPotions() {
		return false;
	}

	public boolean canBeAffected(MobEffectInstance p_34192_) {
		return p_34192_.getEffect() == MobEffects.WITHER || p_34192_.getEffect() == MobEffects.POISON ? false : super.canBeAffected(p_34192_);
	}

	public float hurtRider(DamageSource damageSource, float damage) {

		if (damageSource.isExplosion()) {
			return damage * 0.95F;
		}

		return damage;
	}

	@Override
	public boolean doHurtTarget(Entity p_21372_) {
		if (p_21372_ instanceof LivingEntity) {
			if (this.getFirstPassenger() instanceof Player) {
				((LivingEntity) p_21372_).setLastHurtByPlayer((Player) this.getFirstPassenger());
			}
		}

		return super.doHurtTarget(p_21372_);
	}

	@Override
	public boolean canBeLeashed(Player p_21418_) {
		return false;
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return false;
	}

	@Override
	public boolean removeWhenFarAway(double p_21542_) {
		return false;
	}
}
