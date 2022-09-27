package baguchan.armored_redstone.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StartDushAttackMessage {
	private int entityId;

	public StartDushAttackMessage(Entity entity) {
		this.entityId = entity.getId();
	}

	public StartDushAttackMessage(int id) {
		this.entityId = id;
	}

	public void serialize(FriendlyByteBuf buffer) {
		buffer.writeInt(this.entityId);
	}

	public static StartDushAttackMessage deserialize(FriendlyByteBuf buffer) {
		int entityId = buffer.readInt();

		return new StartDushAttackMessage(entityId);
	}

	public static boolean handle(StartDushAttackMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();

		if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {
			context.enqueueWork(() -> {
				Entity entity = context.getSender().level.getEntity(message.entityId);
				if (entity != null) {
					entity.setSprinting(true);
				}
			});
		}

		return true;
	}
}