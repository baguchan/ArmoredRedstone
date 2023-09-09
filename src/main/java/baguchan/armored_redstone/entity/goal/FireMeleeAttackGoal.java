package baguchan.armored_redstone.entity.goal;

import baguchan.armored_redstone.entity.BaseArmorEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class FireMeleeAttackGoal extends Goal {
    public final BaseArmorEntity baseArmorEntity;

    public FireMeleeAttackGoal(BaseArmorEntity mob) {
        this.baseArmorEntity = mob;
    }

    @Override
    public boolean canUse() {
        if (this.baseArmorEntity.getFirstPassenger() instanceof Mob passenger) {
            return passenger.isAlive() && passenger.getTarget() != null && passenger.getTarget().isAlive();
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.baseArmorEntity.getFirstPassenger() instanceof Mob passenger) {
            return passenger.isAlive() && passenger.getTarget() != null && passenger.getTarget().isAlive();
        } else {
            return false;
        }
    }

    public void tick() {
        Entity entity = this.baseArmorEntity.getFirstPassenger();
        if (entity instanceof Mob passenger) {
            LivingEntity livingentity = passenger.getTarget();
            if (livingentity != null) {
                double d0 = this.baseArmorEntity.getPerceivedTargetDistanceSquareForMeleeAttack(livingentity);
                this.checkAndPerformAttack(livingentity, d0);
            }
        }
    }

    protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
        double d0 = this.getAttackReachSqr(p_25557_);
        if (p_25558_ <= d0) {
            this.baseArmorEntity.attack();
        }

    }

    protected double getAttackReachSqr(LivingEntity p_25556_) {
        return (double) 64;
    }
}
