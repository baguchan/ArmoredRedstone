package baguchan.armored_redstone.register;

import baguchan.armored_redstone.ArmoredRedstone;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public interface ModDamageSource {
    ResourceKey<DamageType> FIRE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArmoredRedstone.MODID, "fire"));
    ResourceKey<DamageType> SOUL_FIRE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArmoredRedstone.MODID, "soul_fire"));
}
