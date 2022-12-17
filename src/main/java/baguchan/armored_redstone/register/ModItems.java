package baguchan.armored_redstone.register;

import baguchan.armored_redstone.ArmoredRedstone;
import baguchan.armored_redstone.item.ArmorRedstoneItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ArmoredRedstone.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
	public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ArmoredRedstone.MODID);

	public static final RegistryObject<Item> CONVEYOR = ITEM_REGISTRY.register("conveyor", () -> new Item((new Item.Properties()).stacksTo(8)));
	public static final RegistryObject<Item> GOLD_CONVEYOR = ITEM_REGISTRY.register("gold_conveyor", () -> new Item((new Item.Properties()).stacksTo(8)));
	public static final RegistryObject<Item> NETHERITE_CONVEYOR = ITEM_REGISTRY.register("netherite_conveyor", () -> new Item((new Item.Properties()).stacksTo(8)));

	public static final RegistryObject<Item> PISTON_ARMOR = ITEM_REGISTRY.register("piston_armor", () -> new ArmorRedstoneItem(ModEntities.PISTON_ARMOR, (new Item.Properties()).stacksTo(1)));
	public static final RegistryObject<Item> FIRE_ARMOR = ITEM_REGISTRY.register("fire_armor", () -> new ArmorRedstoneItem(ModEntities.FIRE_ARMOR, (new Item.Properties()).stacksTo(1)));
	public static final RegistryObject<Item> SOUL_FIRE_ARMOR = ITEM_REGISTRY.register("soul_fire_armor", () -> new ArmorRedstoneItem(ModEntities.SOUL_FIRE_ARMOR, (new Item.Properties()).stacksTo(1)));
	public static final RegistryObject<Item> REDMONS_ARMOR = ITEM_REGISTRY.register("redmons_armor", () -> new ArmorRedstoneItem(ModEntities.RED_MONS_ARMOR, (new Item.Properties()).stacksTo(1)));

	@SubscribeEvent
	public static void registerCreativeModeTabs(CreativeModeTabEvent.BuildContents event) {
		if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.accept(PISTON_ARMOR);
			event.accept(FIRE_ARMOR);
			event.accept(SOUL_FIRE_ARMOR);
			event.accept(REDMONS_ARMOR);
		}
		if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
			event.accept(CONVEYOR);
			event.accept(GOLD_CONVEYOR);
			event.accept(NETHERITE_CONVEYOR);
		}
	}
}
