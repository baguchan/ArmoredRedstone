package baguchan.armored_redstone.entity;

import baguchan.armored_redstone.register.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
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
        if (this.hasSprintUnique()) {
            AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attributeinstance.getModifier(SPEED_MODIFIER_EXTRA_SPRINTING_UUID) != null) {
                attributeinstance.removeModifier(SPEED_MODIFIER_EXTRA_SPRINTING);
            }

            if (p_21284_) {
                attributeinstance.addTransientModifier(SPEED_MODIFIER_EXTRA_SPRINTING);
            }
        }

    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
        super.onSyncedDataUpdated(p_21104_);
        if (DATA_SHARED_FLAGS_ID.equals(p_21104_)) {
            if (this.hasSprintUnique()) {
                if (this.isSprinting()) {
                    this.playSound(SoundEvents.REDSTONE_TORCH_BURNOUT, 1.5F, 1.2F);
                } else {
                    this.playSound(SoundEvents.REDSTONE_TORCH_BURNOUT, 1.5F, 1.2F);
                }
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
        if (this.level().isClientSide()) {
            this.updateClientControls();
        }

        if (this.getFirstPassenger() != null && this.hasSprintUnique()) {
            if (this.getFirstPassenger().isSprinting() && !this.isSprinting() && this.isMoving() && this.onGround()) {
                this.setSprinting(true);
            } else if (this.isSprinting() && (!this.isMoving())) {
                this.setSprinting(false);
            }
        } else {
            if (this.isSprinting() && this.getControllingPassenger() == null) {
                this.setSprinting(false);
            }
        }

        if (this.isSprinting() && this.hasSprintUnique()) {
            this.dash();
        }
        if (this.canStepUp()) {
            if (this.isSprinting()) {
                this.setMaxUpStep(0.5F);
            } else {
                this.setMaxUpStep(1.0F);
            }
        }
    }

    private boolean isMoving() {
        return (!canDashWithWall() && !this.horizontalCollision || canDashWithWall()) && this.getFirstPassenger() instanceof LivingEntity livingEntity && livingEntity.zza > 0;
    }

    protected boolean canDashWithWall() {
        return false;
    }

    protected boolean canStepUp() {
        return true;
    }


    @OnlyIn(Dist.CLIENT)
    protected void updateClientControls() {
        Minecraft mc = Minecraft.getInstance();


    }

    @Override
    protected void spawnSprintParticle() {
        super.spawnSprintParticle();
        if (this.hasSprintUnique()) {
            EntityDimensions dimensions = this.getDimensions(this.getPose());
            Vec3 vec3 = this.getDeltaMovement();
            this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (this.random.nextDouble() - 0.5D) * (double) dimensions.width, this.getY() + 0.1D, this.getZ() + (this.random.nextDouble() - 0.5D) * (double) dimensions.width, vec3.x * -0.25D, 0.25D, vec3.z * -0.25D);
        }
    }

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        super.playStepSound(p_20135_, p_20136_);
        if (!this.isSprinting() || this.isSprinting() && !this.hasSprintUnique()) {

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

    public List<Entity> pickEntitys(double range, Vec3 offset) {
        return pickEntitys(range, offset, this.getBbWidth());
    }

    public List<Entity> pickEntitys(double range, Vec3 offset, float width) {
        Vec3 vec3 = offset;
        Vec3 vec31 = this.getLookAngle();
        List<Entity> entities = Lists.newArrayList();
        Vec3 vec32 = vec3.add(vec31.x * range, vec31.y * range, vec31.z * range);
        AABB aabb = this.getBoundingBox().move(vec31.x() * (range * 0.5F), vec31.y() * (range * 0.5F), vec31.z() * (range * 0.5F)).inflate(range);

        for (Entity entity : this.level().getEntitiesOfClass(Entity.class, aabb)) {
            if (entity.isAttackable() && entity != this && entity != this.getFirstPassenger()) {
                float borderSize = entity.getPickRadius() + width;
                AABB collisionBB = entity.getBoundingBox().inflate(borderSize, borderSize, borderSize);
                Optional<Vec3> interceptPos = collisionBB.clip(vec3, vec32);
                if (collisionBB.contains(vec3)) {
                    entities.add(entity);
                } else if (interceptPos.isPresent()) {
                    entities.add(entity);
                }
            }

        }
        return entities;
    }

    private void dash() {
        for (Entity entity : this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(0.75D, 0.0D, 0.75D))) {
            if (entity != this && (this.getFirstPassenger() == null || this.getFirstPassenger() != null && entity != this.getFirstPassenger()) && !this.isAlliedTo(entity) && (entity.isAttackable() && this.distanceTo(entity) < 26.0D)) {
                if (entity.hurt(this.damageSources().mobAttack(this.getFirstPassenger() instanceof LivingEntity ? (LivingEntity) this.getFirstPassenger() : this), 7.0F)) {
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

    protected void tickRidden(Player p_278233_, Vec3 p_275693_) {
        super.tickRidden(p_278233_, p_275693_);
        Vec2 vec2 = this.getRiddenRotation(p_278233_);
        this.setRot(vec2.y, vec2.x);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
        if (this.isControlledByLocalInstance()) {

            if (this.onGround()) {
                this.setIsJumping(false);
                if (this.playerJumpPendingScale > 0.0F && !this.isJumping()) {
                    this.executeRidersJump(this.playerJumpPendingScale, p_275693_);
                }

                this.playerJumpPendingScale = 0.0F;
            }
        }

    }

    protected Vec2 getRiddenRotation(LivingEntity p_275502_) {
        return new Vec2(p_275502_.getXRot() * 0.5F, p_275502_.getYRot());
    }

    protected Vec3 getRiddenInput(Player p_278278_, Vec3 p_275506_) {
        if (this.onGround() && this.playerJumpPendingScale == 0.0F && !this.allowStandSliding) {
            return Vec3.ZERO;
        } else {
            float f = p_278278_.xxa * 0.5F;
            float f1 = p_278278_.zza;
            if (f1 <= 0.0F) {
                f1 *= 0.25F;
            }

            return new Vec3((double) f, 0.0D, (double) f1);
        }
    }

    protected float getRiddenSpeed(Player p_278336_) {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    public void onPlayerJump(int p_30591_) {
        if (p_30591_ < 0) {
            p_30591_ = 0;
        } else {
            this.allowStandSliding = true;
        }

        if (p_30591_ >= 90) {
            this.playerJumpPendingScale = 1.0F;
        } else {
            this.playerJumpPendingScale = 0.4F + 0.4F * (float) p_30591_ / 90.0F;
        }
    }

    public void handleStartJump(int p_30574_) {
        this.allowStandSliding = true;
        this.stand();
        this.playSound(SoundEvents.REDSTONE_TORCH_BURNOUT, 1.5F, 1.2F);

    }

    protected void executeRidersJump(float p_248808_, Vec3 p_275435_) {
        double d0 = 1.1F * (double) p_248808_ * (double) this.getBlockJumpFactor();
        double d1 = d0 + (double) this.getJumpBoostPower();
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, d1, vec3.z);
        this.setIsJumping(true);
        this.hasImpulse = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(this);
        if (p_275435_.z > 0.0D) {
            float f = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
            float f1 = Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
            this.setDeltaMovement(this.getDeltaMovement().add((double) (-0.4F * f * p_248808_), 0.0D, (double) (0.4F * f1 * p_248808_)));
        }

    }


    protected float getFlyingSpeed() {
        return this.getControllingPassenger() != null ? this.getSpeed() * 0.1F : 0.02F;
    }

    public boolean canJumpOnLava() {
        return false;
    }

    public boolean hasSprintUnique() {
        return true;
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        if (entity instanceof LivingEntity) {
            return (LivingEntity) entity;
        }
        return null;
    }

    public InteractionResult mobInteract(Player p_30713_, InteractionHand p_30714_) {
        ItemStack itemstack = p_30713_.getItemInHand(p_30714_);
        if (itemstack.is(ModItems.WRENCH.get())) {
            return super.mobInteract(p_30713_, p_30714_);
        } else {
            if (this.getHealth() < this.getMaxHealth() && this.healItem(itemstack)) {
                this.heal(20);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

            this.doPlayerRide(p_30713_);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
    }

    public boolean healItem(ItemStack itemstack) {
        return itemstack.is(Items.IRON_INGOT);
    }


    protected void doPlayerRide(Player p_30634_) {
        if (!this.level().isClientSide) {
            if (!this.hasControllingPassenger()) {
                p_30634_.setYRot(this.getYRot());
                p_30634_.setXRot(this.getXRot());
                p_30634_.startRiding(this);
            }
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
    public void handleStopJump() {
    }

    @Override
    public boolean dismountsUnderwater() {
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

        if (damageSource.is(DamageTypeTags.IS_EXPLOSION)) {
            return damage * 0.95F;
        }

        return damage;
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

    public abstract ItemStack getPickItem();

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return getPickItem();
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    protected boolean canAddPassenger(Entity p_248594_) {
        return this.getPassengers().size() <= 1;
    }

}
