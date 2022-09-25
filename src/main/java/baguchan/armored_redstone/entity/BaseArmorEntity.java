package baguchan.armored_redstone.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;

public abstract class BaseArmorEntity extends Mob implements PlayerRideableJumping {
	protected boolean isJumping;
	protected float playerJumpPendingScale;
	private boolean allowStandSliding;
	protected BaseArmorEntity(EntityType<? extends BaseArmorEntity> p_20966_, Level p_20967_) {
		super(p_20966_, p_20967_);
	}

	@Override
	protected int calculateFallDamage(float p_21237_, float p_21238_) {
		return super.calculateFallDamage(p_21237_, p_21238_) - 4;
	}

	@Override
	public void tick() {
		super.tick();
		if(this.level.isClientSide()){
			this.updateClientControls();
		}
	}

	@Override
	protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
		super.playStepSound(p_20135_, p_20136_);
		if(stepSounds() != null) {
			this.playSound(stepSounds(), 1.0F, 1.0F);
		}
	}

	@Nullable
	public SoundEvent stepSounds(){
		return SoundEvents.IRON_GOLEM_STEP;
	}

	@Override
	public double getPassengersRidingOffset() {
		return (double)this.getDimensions(this.getPose()).height * 0.85D;
	}

	public abstract void attack();

	public abstract void secondAttack();

	@OnlyIn(Dist.CLIENT)
	protected abstract void updateClientControls();

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
					double d0 = 1.0D * (double)this.playerJumpPendingScale * (double)this.getBlockJumpFactor();
					double d1 = d0 + this.getJumpBoostPower();
					Vec3 vec3 = this.getDeltaMovement();
					this.setDeltaMovement(vec3.x, d1, vec3.z);
					this.setIsJumping(true);
					this.hasImpulse = true;
					net.minecraftforge.common.ForgeHooks.onLivingJump(this);
					if (f1 > 0.0F) {
						float f2 = Mth.sin(this.getYRot() * ((float)Math.PI / 180F));
						float f3 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F));
						this.setDeltaMovement(this.getDeltaMovement().add((double)(-0.4F * f2 * this.playerJumpPendingScale), 0.0D, (double)(0.4F * f3 * this.playerJumpPendingScale)));
					}

					this.playerJumpPendingScale = 0.0F;
				}

				this.flyingSpeed = this.getSpeed() * 0.1F;
				if (this.isControlledByLocalInstance()) {
					this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
					super.travel(new Vec3((double)f, p_30633_.y, (double)f1));
				} else if (livingentity instanceof Player) {
					this.setDeltaMovement(Vec3.ZERO);
				}

				if (this.onGround) {
					this.playerJumpPendingScale = 0.0F;
					this.setIsJumping(false);
				}

				this.calculateEntityAnimation(this, false);
				this.tryCheckInsideBlocks();
			} else {
				this.flyingSpeed = 0.02F;
				super.travel(p_30633_);
			}
		}
	}

	@Nullable
	public LivingEntity getControllingPassenger() {
		Entity entity = this.getFirstPassenger();
		if (entity instanceof LivingEntity) {
			return (LivingEntity)entity;
		}
		return null;
	}

	public InteractionResult mobInteract(Player p_30713_, InteractionHand p_30714_) {
		ItemStack itemstack = p_30713_.getItemInHand(p_30714_);
		if (this.isBaby()) {
			return super.mobInteract(p_30713_, p_30714_);
		} else {
			this.doPlayerRide(p_30713_);
			return InteractionResult.sidedSuccess(this.level.isClientSide);
		}
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
			this.playerJumpPendingScale = 0.4F + 0.4F * (float)p_30591_ / 90.0F;
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

	public float hurtRider(DamageSource damageSource, float damage){
		if(damageSource.isFall()){
			return damage * 0.25F;
		}

		if(damageSource.isExplosion()){
			return damage * 0.95F;
		}

		return damage;
	}
}
