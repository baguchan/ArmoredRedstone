package baguchan.armored_redstone.client;

import baguchan.armored_redstone.ArmoredRedstone;
import baguchan.armored_redstone.client.render.PistonArmorRenderer;
import baguchan.armored_redstone.register.ModEntities;
import net.minecraft.client.renderer.entity.RavagerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = ArmoredRedstone.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.PISTON_ARMOR.get(), PistonArmorRenderer::new);
	}

}