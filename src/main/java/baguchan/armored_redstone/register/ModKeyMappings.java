package baguchan.armored_redstone.register;

import baguchan.armored_redstone.ArmoredRedstone;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArmoredRedstone.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModKeyMappings {
	public static final KeyMapping keyFire = new KeyMapping("key.armored_redstone.fire", InputConstants.KEY_G, "key.categories.movement");

	@SubscribeEvent
	public static void init(RegisterKeyMappingsEvent event) {
		event.register(keyFire);
	}
}
