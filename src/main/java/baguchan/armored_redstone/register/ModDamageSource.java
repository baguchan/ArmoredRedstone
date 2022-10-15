package baguchan.armored_redstone.register;

import baguchan.armored_redstone.entity.BaseArmorEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

public class ModDamageSource {
	public static DamageSource soul(BaseArmorEntity p_19350_, Entity controllingPassenger) {
		return controllingPassenger != null ? (new IndirectEntityDamageSource("armored_redstone.onSoul", p_19350_, controllingPassenger)).bypassArmor() : (new IndirectEntityDamageSource("armored_redstone.onSoul.player", p_19350_, controllingPassenger)).bypassArmor();
	}

	public static DamageSource fire(BaseArmorEntity p_19350_, Entity controllingPassenger) {
		return controllingPassenger != null ? (new IndirectEntityDamageSource("armored_redstone.onFire", p_19350_, controllingPassenger)).setIsFire() : (new IndirectEntityDamageSource("armored_redstone.onFire.player", p_19350_, controllingPassenger)).setIsFire();
	}
}
