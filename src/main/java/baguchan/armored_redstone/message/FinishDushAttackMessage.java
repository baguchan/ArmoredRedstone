package baguchan.armored_redstone.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FinishDushAttackMessage {
	private int entityId;

	public FinishDushAttackMessage(Entity entity) {
		this.entityId = entity.getId();
	}

	public FinishDushAttackMessage(int id) {
		this.entityId = id;
	}

	public void serialize(FriendlyByteBuf buffer) {
		buffer.writeInt(this.entityId);
	}

	public static FinishDushAttackMessage deserialize(FriendlyByteBuf buffer) {
		int entityId = buffer.readInt();

		return new FinishDushAttackMessage(entityId);
	}

	public static boolean handle(FinishDushAttackMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();

		if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {
			context.enqueueWork(() -> {
				Entity entity = context.getSender().level.getEntity(message.entityId);
				if (entity != null) {
					entity.setSprinting(false);
				}
			});
		}

		return true;
	}
}