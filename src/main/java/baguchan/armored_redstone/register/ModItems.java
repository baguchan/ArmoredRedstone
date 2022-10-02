package baguchan.armored_redstone.register;

import baguchan.armored_redstone.ArmoredRedstone;
import baguchan.armored_redstone.item.ArmorRedstoneItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ArmoredRedstone.MODID);

	public static final RegistryObject<Item> CONVEYOR = ITEM_REGISTRY.register("conveyor", () -> new Item((new Item.Properties()).stacksTo(8).tab(CreativeModeTab.TAB_REDSTONE)));
	public static final RegistryObject<Item> GOLD_CONVEYOR = ITEM_REGISTRY.register("gold_conveyor", () -> new Item((new Item.Properties()).stacksTo(8).tab(CreativeModeTab.TAB_REDSTONE)));
	public static final RegistryObject<Item> NETHERITE_CONVEYOR = ITEM_REGISTRY.register("netherite_conveyor", () -> new Item((new Item.Properties()).stacksTo(8).tab(CreativeModeTab.TAB_REDSTONE)));

	public static final RegistryObject<Item> PISTON_ARMOR = ITEM_REGISTRY.register("piston_armor", () -> new ArmorRedstoneItem(ModEntities.PISTON_ARMOR.get(), (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_REDSTONE)));
	public static final RegistryObject<Item> FIRE_ARMOR = ITEM_REGISTRY.register("fire_armor", () -> new ArmorRedstoneItem(ModEntities.FIRE_ARMOR.get(), (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_REDSTONE)));
	public static final RegistryObject<Item> SOUL_FIRE_ARMOR = ITEM_REGISTRY.register("soul_fire_armor", () -> new ArmorRedstoneItem(ModEntities.SOUL_FIRE_ARMOR.get(), (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_REDSTONE)));

}
