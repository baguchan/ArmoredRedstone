package baguchan.armored_redstone.item;

import baguchan.armored_redstone.entity.BaseArmorEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WrenchItem extends Item {
    public WrenchItem(Properties p_43210_) {
        super(p_43210_);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack p_41398_, Player p_41399_, LivingEntity p_41400_, InteractionHand p_41401_) {
        if (!p_41399_.level().isClientSide()) {
            if (p_41400_ instanceof BaseArmorEntity baseArmorEntity) {
                BehaviorUtils.throwItem(p_41400_, baseArmorEntity.getPickItem(), p_41400_.position());
                p_41400_.discard();
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
