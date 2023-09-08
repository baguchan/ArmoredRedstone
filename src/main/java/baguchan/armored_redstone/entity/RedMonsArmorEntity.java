package baguchan.armored_redstone.entity;

import baguchan.armored_redstone.ArmoredRedstone;
import baguchan.armored_redstone.message.ArmorAttackMessage;
import baguchan.armored_redstone.register.ModItems;
import baguchan.armored_redstone.register.ModKeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;

public class RedMonsArmorEntity extends BaseArmorEntity {
	private static final EntityDataAccessor<Integer> DATA_ATTACK = SynchedEntityData.defineId(RedMonsArmorEntity.class, EntityDataSerializers.INT);


	public RedMonsArmorEntity(EntityType<? extends RedMonsArmorEntity> p_20966_, Level p_20967_) {
		super(p_20966_, p_20967_);
	}

	protected void updateControlFlags() {
		boolean flag = !(this.getControllingPassenger() instanceof Mob) || this.getControllingPassenger().getType().is(EntityTypeTags.RAIDERS);
		boolean flag1 = !(this.getVehicle() instanceof Boat);
		this.goalSelector.setControlFlag(Goal.Flag.MOVE, flag);
		this.goalSelector.setControlFlag(Goal.Flag.JUMP, flag && flag1);
		this.goalSelector.setControlFlag(Goal.Flag.LOOK, flag);
		this.goalSelector.setControlFlag(Goal.Flag.TARGET, flag);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0F, true) {
			@Override
			public boolean canUse() {
				return getFirstPassenger() != null && super.canUse();
			}

			@Override
			public boolean canContinueToUse() {
				return getFirstPassenger() != null && super.canContinueToUse();
			}

			@Override
			protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
				double d0 = this.getAttackReachSqr(p_25557_);
				if (p_25558_ <= d0) {
					attack();
				}

			}

			protected double getAttackReachSqr(LivingEntity p_25556_) {
				return (double) (this.mob.getBbWidth() * 2.5F * this.mob.getBbWidth() * 2.5F + p_25556_.getBbWidth());
			}
		});
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ATTACK, 0);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
		super.onSyncedDataUpdated(p_21104_);
		if (DATA_ATTACK.equals(p_21104_)) {
			if (this.getAttackTick() == 31) {
				this.attackAnimationState.start(this.tickCount);
			}

			if (this.getAttackTick() == 0) {
				this.attackAnimationState.stop();
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (this.getAttackTick() > 0) {
			this.setAttackTick(this.getAttackTick() - 1);
		}

		if (this.getFirstPassenger() != null && this.getFirstPassenger() instanceof Mob) {
			if (((Mob) this.getFirstPassenger()).getTarget() != null) {
				this.setTarget(((Mob) this.getFirstPassenger()).getTarget());
			}
		} else if (this.getFirstPassenger() == null && this.getTarget() != null) {
			this.setTarget(null);
		}
	}

	public boolean healItem(ItemStack itemstack) {
		return itemstack.is(Tags.Items.COBBLESTONE);
	}

	@Override
	public void attack() {
		if (this.getAttackTick() == 0) {
			for (Entity entity : this.pickEntitys(4, this.getEyePosition())) {
				if (entity != this && (this.getFirstPassenger() == null || this.getFirstPassenger() != null && entity != this.getFirstPassenger()) && !this.isAlliedTo(entity) && (entity.isAttackable())) {
					this.doHurtTarget(entity);
					entity.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 2.0F, 1.0F);
				}
			}
			this.setAttackTick(32);
		}
	}

	@Override
	public void secondAttack() {

	}

	@OnlyIn(Dist.CLIENT)
	protected void updateClientControls() {
		super.updateClientControls();
		Minecraft mc = Minecraft.getInstance();

		if (mc.player != null && this.hasPassenger(mc.player)) {

			if (ModKeyMappings.keyFire.isDown()) {
				if (this.getAttackTick() == 0) {
					attackingStart();
				}
			}
		}
	}


	public void setAttackTick(int attackTick) {
		this.entityData.set(DATA_ATTACK, attackTick);
	}

	public int getAttackTick() {
		return this.entityData.get(DATA_ATTACK);
	}

	private void attackingStart() {
		ArmoredRedstone.CHANNEL.sendToServer(new ArmorAttackMessage(this));
	}

	protected boolean isAttacked() {
		return getAttackTick() > 0;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 200.0D).add(ForgeMod.ENTITY_GRAVITY.get(), 0.14F).add(Attributes.ARMOR, 16.0F).add(Attributes.MOVEMENT_SPEED, 0.24D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).add(Attributes.ATTACK_DAMAGE, 16.0D).add(Attributes.ATTACK_KNOCKBACK, 3D);
	}

	@Override
	public boolean canSprint() {
		return true;
	}

	public boolean hasSprintUnique() {
		return false;
	}

	public ItemStack getPickItem() {
		return new ItemStack(ModItems.REDMONS_ARMOR.get());
	}
}
