package baguchan.armored_redstone;

import baguchan.armored_redstone.entity.BaseArmorEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArmoredRedstone.MODID)
public class CommonEvent {
	@SubscribeEvent
	public static void onEntityHurt(LivingHurtEvent event) {
		LivingEntity entity = event.getEntity();

		if(entity.getRootVehicle() instanceof BaseArmorEntity){
			event.setAmount(((BaseArmorEntity) entity.getRootVehicle()).hurtRider(event.getSource(), event.getAmount()));
		}
	}
}
