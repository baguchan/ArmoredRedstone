package baguchan.armored_redstone.item;

import baguchan.armored_redstone.client.render.item.ArmorBWLR;
import baguchan.armored_redstone.entity.BaseArmorEntity;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ArmorRedstoneItem extends Item {
	private final Supplier<? extends EntityType<? extends Mob>> defaultType;

	public ArmorRedstoneItem(Supplier<? extends EntityType<? extends Mob>> p_43207_, Item.Properties p_43210_) {
		super(p_43210_);
		this.defaultType = p_43207_;
	}

	public InteractionResult useOn(UseOnContext p_43223_) {
		Level level = p_43223_.getLevel();
		if (!(level instanceof ServerLevel)) {
			return InteractionResult.SUCCESS;
		} else {
			ItemStack itemstack = p_43223_.getItemInHand();
			BlockPos blockpos = p_43223_.getClickedPos();
			Direction direction = p_43223_.getClickedFace();
			BlockState blockstate = level.getBlockState(blockpos);

			BlockPos blockpos1;
			if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
				blockpos1 = blockpos;
			} else {
				blockpos1 = blockpos.relative(direction);
			}

			EntityType<?> entitytype = this.getType(itemstack.getTag());
			Entity entity = entitytype.spawn((ServerLevel) level, itemstack, p_43223_.getPlayer(), blockpos1, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);
			if (entity != null) {
				if (itemstack.getTag() != null) {
					if (entity instanceof BaseArmorEntity baseArmorEntity) {
						baseArmorEntity.readAdditionalSaveData(itemstack.getTag().getCompound("ArmorData"));
						baseArmorEntity.setCustomName(Component.literal(itemstack.getTag().getCompound("ArmorData").getString("CustomName")));
					}
				}
				itemstack.shrink(1);
				level.gameEvent(p_43223_.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
			}

			return InteractionResult.CONSUME;
		}
	}

	public InteractionResultHolder<ItemStack> use(Level p_43225_, Player p_43226_, InteractionHand p_43227_) {
		ItemStack itemstack = p_43226_.getItemInHand(p_43227_);
		HitResult hitresult = getPlayerPOVHitResult(p_43225_, p_43226_, ClipContext.Fluid.SOURCE_ONLY);
		if (hitresult.getType() != HitResult.Type.BLOCK) {
			return InteractionResultHolder.pass(itemstack);
		} else if (!(p_43225_ instanceof ServerLevel)) {
			return InteractionResultHolder.success(itemstack);
		} else {
			BlockHitResult blockhitresult = (BlockHitResult) hitresult;
			BlockPos blockpos = blockhitresult.getBlockPos();
			if (!(p_43225_.getBlockState(blockpos).getBlock() instanceof LiquidBlock)) {
				return InteractionResultHolder.pass(itemstack);
			} else if (p_43225_.mayInteract(p_43226_, blockpos) && p_43226_.mayUseItemAt(blockpos, blockhitresult.getDirection(), itemstack)) {
				EntityType<?> entitytype = this.getType(itemstack.getTag());
				Entity entity = entitytype.spawn((ServerLevel) p_43225_, itemstack, p_43226_, blockpos, MobSpawnType.SPAWN_EGG, false, false);
				if (itemstack.getTag() != null) {
					if (entity instanceof BaseArmorEntity baseArmorEntity) {
						baseArmorEntity.readAdditionalSaveData(itemstack.getTag().getCompound("ArmorData"));
						baseArmorEntity.setCustomName(Component.literal(itemstack.getTag().getCompound("ArmorData").getString("CustomName")));
					}
				}
				if (entity == null) {
					return InteractionResultHolder.pass(itemstack);
				} else {
					if (!p_43226_.getAbilities().instabuild) {
						itemstack.shrink(1);
					}

					p_43226_.awardStat(Stats.ITEM_USED.get(this));
					p_43225_.gameEvent(p_43226_, GameEvent.ENTITY_PLACE, entity.position());
					return InteractionResultHolder.consume(itemstack);
				}

			} else {
				return InteractionResultHolder.fail(itemstack);
			}
		}
	}

	public boolean spawnsEntity(@Nullable CompoundTag p_43231_, EntityType<?> p_43232_) {
		return Objects.equals(this.getType(p_43231_), p_43232_);
	}

	public EntityType<?> getType(@Nullable CompoundTag p_43229_) {
		if (p_43229_ != null && p_43229_.contains("EntityTag", 10)) {
			CompoundTag compoundtag = p_43229_.getCompound("EntityTag");
			if (compoundtag.contains("id", 8)) {
				return EntityType.byString(compoundtag.getString("id")).orElse(this.defaultType.get());
			}
		}

		return this.defaultType.get();
	}

	public Optional<Mob> spawnOffspringFromSpawnEgg(Player p_43216_, Mob p_43217_, EntityType<? extends Mob> p_43218_, ServerLevel p_43219_, Vec3 p_43220_, ItemStack p_43221_) {
		if (!this.spawnsEntity(p_43221_.getTag(), p_43218_)) {
			return Optional.empty();
		} else {
			Mob mob;
			if (p_43217_ instanceof AgeableMob) {
				mob = ((AgeableMob) p_43217_).getBreedOffspring(p_43219_, (AgeableMob) p_43217_);
			} else {
				mob = p_43218_.create(p_43219_);
			}

			if (mob == null) {
				return Optional.empty();
			} else {
				mob.setBaby(true);
				if (!mob.isBaby()) {
					return Optional.empty();
				} else {
					mob.moveTo(p_43220_.x(), p_43220_.y(), p_43220_.z(), 0.0F, 0.0F);
					p_43219_.addFreshEntityWithPassengers(mob);
					if (p_43221_.hasCustomHoverName()) {
						mob.setCustomName(p_43221_.getHoverName());
					}

					if (!p_43216_.getAbilities().instabuild) {
						p_43221_.shrink(1);
					}

					return Optional.of(mob);
				}
			}
		}
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return new ArmorBWLR();
			}
		});
	}

	@Override
	public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
		super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
		if (p_41421_.getTag() != null) {
			if (p_41421_.getTag().getCompound("ArmorData").contains("CustomName")) {
				p_41423_.add(Component.literal("Custom Name:").append(" ").append(p_41421_.getTag().getCompound("ArmorData").getString("CustomName")));
			}
		}
	}
}
