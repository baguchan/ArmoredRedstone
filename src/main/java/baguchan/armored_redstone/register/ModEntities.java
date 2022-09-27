package baguchan.armored_redstone.register;

import baguchan.armored_redstone.ArmoredRedstone;
import baguchan.armored_redstone.entity.PistonArmorEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ArmoredRedstone.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES_REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ArmoredRedstone.MODID);

	public static final RegistryObject<EntityType<PistonArmorEntity>> PISTON_ARMOR = ENTITIES_REGISTRY.register("piston_armor", () -> EntityType.Builder.of(PistonArmorEntity::new, MobCategory.MISC).sized(1.3F, 2.0F).build(prefix("piston_armor")));

	private static String prefix(String path) {
		return ArmoredRedstone.MODID + "." + path;
	}

	@SubscribeEvent
	public static void registerEntity(EntityAttributeCreationEvent event) {
		event.put(PISTON_ARMOR.get(), PistonArmorEntity.createAttributes().build());
	}
}