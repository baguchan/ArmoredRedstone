package baguchan.armored_redstone.entity;

import baguchan.armored_redstone.ArmoredRedstone;
import baguchan.armored_redstone.message.ArmorAttackMessage;
import baguchan.armored_redstone.message.FireArmorStopAttackMessage;
import baguchan.armored_redstone.register.ModDamageSource;
import baguchan.armored_redstone.register.ModKeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;

public class FireArmorEntity extends BaseArmorEntity {
	private static final EntityDataAccessor<Boolean> DATA_FIRE_ATTACK = SynchedEntityData.defineId(FireArmorEntity.class, EntityDataSerializers.BOOLEAN);

	public FireArmorEntity(EntityType<? extends FireArmorEntity> p_20966_, Level p_20967_) {
		super(p_20966_, p_20967_);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_FIRE_ATTACK, false);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
		super.onSyncedDataUpdated(p_21104_);
		if (DATA_FIRE_ATTACK.equals(p_21104_)) {
			if (this.isFireAttack()) {
				this.attackFinishedAnimationState.start(this.tickCount);
				this.attackAnimationState.stop();
			} else {
				this.attackAnimationState.start(this.tickCount);
				this.attackFinishedAnimationState.stop();
			}
		}
	}

	@Override
	public void attack() {
		setFireAttack(true);
	}


	@Override
	public void secondAttack() {

	}

	@Override
	public void aiStep() {
		super.aiStep();

		// when breathing fire, spew particles
		if (this.isFireAttack()) {
			this.addFireParticle();
			this.fireAttack();

			this.gameEvent(GameEvent.PROJECTILE_SHOOT);
		}
	}

	public void fireAttack() {
		for (Entity entity : this.level.getEntitiesOfClass(Entity.class, this.getFireBoundingBox())) {
			if (entity != this && (this.getControllingPassenger() == null || this.getControllingPassenger() != null && entity != this.getControllingPassenger()) && !this.isAlliedTo(entity) && (entity.isAttackable() && this.distanceTo(entity) < 26.0D)) {
				entity.hurt(ModDamageSource.fire(this, this.getControllingPassenger()), 8.0F);
				entity.setSecondsOnFire(8);
			}
		}
		playSound(SoundEvents.FIRECHARGE_USE, this.getRandom().nextFloat() * 0.5F, this.getRandom().nextFloat() * 0.5F);
	}

	public AABB getFireBoundingBox() {
		Vec3 vec3d = this.getViewVector(1.0F);
		return this.getBoundingBox().contract(0, -(this.getBbHeight() - this.getBbWidth()), 0).expandTowards(vec3d.x * 4.0D, vec3d.y * 4.0D, vec3d.z * 4.0D).move(vec3d.x * 1.6D, vec3d.y * 1.6D, vec3d.z * 1.6D);
	}

	public void addFireParticle() {

		double dist = 1.5;
		for (int i2 = 0; i2 < 2; i2++) {
			Vec3 look = this.getLookAngle();

			float f14 = this.getYRot() * ((float) Math.PI / 180F);
			float f2 = Mth.sin(f14);
			float f15 = Mth.cos(f14);
			float direct = i2 == 0 ? -1.0F : 1.0F;

			double px = this.getX() + f15 * 1.4F * direct + look.x() * dist;
			double py = this.getY() + this.getEyeHeight() + look.y() * dist;
			double pz = this.getZ() + f2 * 1.4F * direct + look.z() * dist;

			for (int i = 0; i < 2; i++) {
				double dx = look.x();
				double dy = look.y();
				double dz = look.z();

				double spread = 5 + this.getRandom().nextDouble() * 2.5;
				double velocity = 0.2 + this.getRandom().nextDouble() * 0.15;

				dx += this.getRandom().nextGaussian() * 0.0075D * spread;
				dy += this.getRandom().nextGaussian() * 0.0075D * spread;
				dz += this.getRandom().nextGaussian() * 0.0075D * spread;
				dx *= velocity;
				dy *= velocity;
				dz *= velocity;

				this.getLevel().addParticle(ParticleTypes.FLAME, px, py, pz, dx, dy, dz);
			}
		}
	}


	@OnlyIn(Dist.CLIENT)
	protected void updateClientControls() {
		super.updateClientControls();
		Minecraft mc = Minecraft.getInstance();

		if (mc.player != null && this.hasPassenger(mc.player)) {

			if (ModKeyMappings.keyFire.isDown()) {
				attackingStart();
			} else if (isFireAttack()) {
				ArmoredRedstone.CHANNEL.sendToServer(new FireArmorStopAttackMessage(this));
			}
		} else if (isFireAttack()) {
			ArmoredRedstone.CHANNEL.sendToServer(new FireArmorStopAttackMessage(this));
		}
	}

	@Override
	public boolean isInLava() {
		return super.isInLava();
	}

	public void setFireAttack(boolean attack) {
		this.entityData.set(DATA_FIRE_ATTACK, attack);
	}

	public boolean isFireAttack() {
		return this.entityData.get(DATA_FIRE_ATTACK);
	}

	private void attackingStart() {
		ArmoredRedstone.CHANNEL.sendToServer(new ArmorAttackMessage(this));
	}


	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(ForgeMod.ENTITY_GRAVITY.get(), 0.10F).add(Attributes.ARMOR, 8.0F).add(Attributes.MOVEMENT_SPEED, 0.1D).add(Attributes.KNOCKBACK_RESISTANCE, 0.75D).add(Attributes.ATTACK_DAMAGE, 10.0D).add(Attributes.ATTACK_KNOCKBACK, 2.0D);
	}
}
