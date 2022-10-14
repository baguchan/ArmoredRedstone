package baguchan.armored_redstone.register;

import baguchan.armored_redstone.ArmoredRedstone;
import baguchan.armored_redstone.entity.FireArmorEntity;
import baguchan.armored_redstone.entity.PistonArmorEntity;
import baguchan.armored_redstone.entity.RedMonsArmorEntity;
import baguchan.armored_redstone.entity.SoulFireArmorEntity;
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

	public static final RegistryObject<EntityType<PistonArmorEntity>> PISTON_ARMOR = ENTITIES_REGISTRY.register("piston_armor", () -> EntityType.Builder.of(PistonArmorEntity::new, MobCategory.MISC).sized(1.3F, 2.5F).build(prefix("piston_armor")));
	public static final RegistryObject<EntityType<FireArmorEntity>> FIRE_ARMOR = ENTITIES_REGISTRY.register("fire_armor", () -> EntityType.Builder.of(FireArmorEntity::new, MobCategory.MISC).sized(1.3F, 2.5F).fireImmune().build(prefix("fire_armor")));
	public static final RegistryObject<EntityType<SoulFireArmorEntity>> SOUL_FIRE_ARMOR = ENTITIES_REGISTRY.register("soul_fire_armor", () -> EntityType.Builder.of(SoulFireArmorEntity::new, MobCategory.MISC).sized(1.3F, 2.5F).fireImmune().build(prefix("soul_fire_armor")));
	public static final RegistryObject<EntityType<RedMonsArmorEntity>> RED_MONS_ARMOR = ENTITIES_REGISTRY.register("redmons_armor", () -> EntityType.Builder.of(RedMonsArmorEntity::new, MobCategory.MISC).sized(1.3F, 2.5F).build(prefix("redmons_armor")));


	private static String prefix(String path) {
		return ArmoredRedstone.MODID + "." + path;
	}

	@SubscribeEvent
	public static void registerEntity(EntityAttributeCreationEvent event) {
		event.put(PISTON_ARMOR.get(), PistonArmorEntity.createAttributes().build());
		event.put(FIRE_ARMOR.get(), FireArmorEntity.createAttributes().build());
		event.put(SOUL_FIRE_ARMOR.get(), SoulFireArmorEntity.createAttributes().build());
		event.put(RED_MONS_ARMOR.get(), RedMonsArmorEntity.createAttributes().build());
	}
}