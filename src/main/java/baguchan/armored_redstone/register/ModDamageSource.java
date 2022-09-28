package baguchan.armored_redstone.register;

import baguchan.armored_redstone.entity.BaseArmorEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class ModDamageSource {
	public static DamageSource fire(BaseArmorEntity p_19350_, @Nullable Entity p_19351_) {
		return p_19351_ == null ? (new IndirectEntityDamageSource("armored_redstone.onFire", p_19350_, p_19350_)).setIsFire() : (new IndirectEntityDamageSource("armored_redstone.onFireShot", p_19350_, p_19351_)).setIsFire();
	}
}
