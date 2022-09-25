package baguchan.armored_redstone.message;

import baguchan.armored_redstone.entity.BaseArmorEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ArmorAttackMessage {
	private int entityId;

	public ArmorAttackMessage(Entity entity) {
		this.entityId = entity.getId();
	}

	public ArmorAttackMessage(int id) {
		this.entityId = id;
	}

	public void serialize(FriendlyByteBuf buffer) {
		buffer.writeInt(this.entityId);
	}

	public static ArmorAttackMessage deserialize(FriendlyByteBuf buffer) {
		int entityId = buffer.readInt();

		return new ArmorAttackMessage(entityId);
	}

	public static boolean handle(ArmorAttackMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();

		if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {
			context.enqueueWork(() -> {
				Entity entity = context.getSender().level.getEntity(message.entityId);
				if (entity != null && entity instanceof BaseArmorEntity) {

					((BaseArmorEntity) entity).attack();
				}
			});
		}

		return true;
	}
}