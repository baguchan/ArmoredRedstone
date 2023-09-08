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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;

public class PistonArmorEntity extends BaseArmorEntity {
	private static final EntityDataAccessor<Integer> DATA_ATTACK = SynchedEntityData.defineId(PistonArmorEntity.class, EntityDataSerializers.INT);


	public PistonArmorEntity(EntityType<? extends PistonArmorEntity> p_20966_, Level p_20967_) {
		super(p_20966_, p_20967_);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ATTACK, 0);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
		super.onSyncedDataUpdated(p_21104_);
		if (DATA_ATTACK.equals(p_21104_)) {
			if (this.getAttackTick() == 24) {
				this.attackAnimationState.start(this.tickCount);
				this.attackFinishedAnimationState.stop();
			}

			if (this.getAttackTick() == 1) {
				this.attackFinishedAnimationState.start(this.tickCount);
				this.attackAnimationState.stop();
				this.playSound(SoundEvents.PISTON_CONTRACT, 1.1F, 1.15F);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		if(this.getAttackTick() > 0) {
			this.setAttackTick(this.getAttackTick() - 1);
		}
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
			this.setAttackTick(24);
			this.playSound(SoundEvents.PISTON_EXTEND, 1.0F, 1.15F);
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
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 100.0D).add(ForgeMod.ENTITY_GRAVITY.get(), 0.14F).add(Attributes.ARMOR, 16.0F).add(Attributes.MOVEMENT_SPEED, 0.1D).add(Attributes.KNOCKBACK_RESISTANCE, 0.75D).add(Attributes.ATTACK_DAMAGE, 10.0D).add(Attributes.ATTACK_KNOCKBACK, 2.0D);
	}

	public ItemStack getPickItem() {
		return new ItemStack(ModItems.PISTON_ARMOR.get());
	}
}
