package baguchan.armored_redstone.entity;

import baguchan.armored_redstone.register.ModDamageSource;
import baguchan.armored_redstone.register.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

public class SoulFireArmorEntity extends FireArmorEntity {
	public SoulFireArmorEntity(EntityType<? extends FireArmorEntity> p_20966_, Level p_20967_) {
		super(p_20966_, p_20967_);
	}

	@Override
	public boolean canJumpOnLava() {
		return true;
	}

	public void fireAttack() {
		for (Entity entity : this.level.getEntitiesOfClass(Entity.class, this.getFireBoundingBox())) {
			if (entity != this && (this.getControllingPassenger() == null || this.getControllingPassenger() != null && entity != this.getControllingPassenger()) && !this.isAlliedTo(entity) && (entity.isAttackable() && this.distanceTo(entity) < 26.0D)) {
				entity.hurt(ModDamageSource.soul(this, this.getControllingPassenger()), 4.0F);
				entity.hurt(ModDamageSource.fire(this, this.getControllingPassenger()), 9.0F);
				entity.setSecondsOnFire(8);
				if (entity instanceof LivingEntity) {
					if (this.getControllingPassenger() instanceof Player) {
						((LivingEntity) entity).setLastHurtByPlayer((Player) this.getControllingPassenger());
					}
				}
			}
		}
		playSound(SoundEvents.FIRECHARGE_USE, this.getRandom().nextFloat() * 0.5F, this.getRandom().nextFloat() * 0.5F);
	}

	@Override
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

				if (this.random.nextFloat() < 0.25F) {
					this.getLevel().addParticle(ParticleTypes.SOUL, px, py, pz, dx, dy, dz);
				} else {
					this.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, px, py, pz, dx, dy, dz);
				}
			}
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 120.0D).add(ForgeMod.ENTITY_GRAVITY.get(), 0.10F).add(Attributes.ARMOR, 16.0F).add(Attributes.ARMOR_TOUGHNESS, 4.0F).add(Attributes.MOVEMENT_SPEED, 0.1D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).add(Attributes.ATTACK_DAMAGE, 10.0D).add(Attributes.ATTACK_KNOCKBACK, 2.0D);
	}

	public ItemStack getPickResult() {
		return new ItemStack(ModItems.SOUL_FIRE_ARMOR.get());
	}
}
