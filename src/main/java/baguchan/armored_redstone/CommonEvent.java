package baguchan.armored_redstone;

import baguchan.armored_redstone.entity.BaseArmorEntity;
import baguchan.armored_redstone.entity.RedMonsArmorEntity;
import baguchan.armored_redstone.register.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArmoredRedstone.MODID)
public class CommonEvent {
	@SubscribeEvent
	public static void onEntityHurt(LivingHurtEvent event) {
		LivingEntity entity = event.getEntity();

		if (entity.getRootVehicle() instanceof BaseArmorEntity) {
			event.setAmount(((BaseArmorEntity) entity.getRootVehicle()).hurtRider(event.getSource(), event.getAmount()));
		}
	}

	@SubscribeEvent
	public static void onSpawnEntity(LivingSpawnEvent.SpecialSpawn event) {
		if (event.getEntity() instanceof Pillager) {
			LevelAccessor level = event.getLevel();

			if (level instanceof Level && ((Pillager) event.getEntity()).hasActiveRaid() && event.getLevel().getRandom().nextFloat() < 0.05F * ((Pillager) event.getEntity()).getCurrentRaid().getBadOmenLevel() + ((Pillager) event.getEntity()).getCurrentRaid().getEnchantOdds()) {
				Pillager livingEntity = (Pillager) event.getEntity();

				RedMonsArmorEntity armor = ModEntities.RED_MONS_ARMOR.get().create((Level) level);
				armor.moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.getYRot(), 0.0F);
				armor.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(event.getEntity().blockPosition()), MobSpawnType.JOCKEY, (SpawnGroupData) null, (CompoundTag) null);
				livingEntity.startRiding(armor);
				level.addFreshEntity(armor);
			}
		}
	}
}
