package baguchan.armored_redstone.message;

import baguchan.armored_redstone.entity.FireArmorEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FireArmorStopAttackMessage {
	private int entityId;

	public FireArmorStopAttackMessage(Entity entity) {
		this.entityId = entity.getId();
	}

	public FireArmorStopAttackMessage(int id) {
		this.entityId = id;
	}

	public void serialize(FriendlyByteBuf buffer) {
		buffer.writeInt(this.entityId);
	}

	public static FireArmorStopAttackMessage deserialize(FriendlyByteBuf buffer) {
		int entityId = buffer.readInt();

		return new FireArmorStopAttackMessage(entityId);
	}

	public static boolean handle(FireArmorStopAttackMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();

		if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {
			context.enqueueWork(() -> {
				Entity entity = context.getSender().level().getEntity(message.entityId);
				if (entity instanceof FireArmorEntity) {
					((FireArmorEntity) entity).setFireAttack(false);
				}
			});
		}

		return true;
	}
}