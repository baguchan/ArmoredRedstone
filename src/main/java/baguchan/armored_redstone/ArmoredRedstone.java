package baguchan.armored_redstone;

import baguchan.armored_redstone.message.ArmorAttackMessage;
import baguchan.armored_redstone.message.FinishDushAttackMessage;
import baguchan.armored_redstone.message.StartDushAttackMessage;
import baguchan.armored_redstone.register.ModEntities;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArmoredRedstone.MODID)
public class ArmoredRedstone
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "armored_redstone";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String NETWORK_PROTOCOL = "2";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "net"))
            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

    public ArmoredRedstone()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        this.setupMessages();
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModEntities.ENTITIES_REGISTRY.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setupMessages() {
        CHANNEL.messageBuilder(ArmorAttackMessage.class, 0)
                .encoder(ArmorAttackMessage::serialize).decoder(ArmorAttackMessage::deserialize)
                .consumerMainThread(ArmorAttackMessage::handle)
                .add();
        CHANNEL.messageBuilder(StartDushAttackMessage.class, 1)
                .encoder(StartDushAttackMessage::serialize).decoder(StartDushAttackMessage::deserialize)
                .consumerMainThread(StartDushAttackMessage::handle)
                .add();
        CHANNEL.messageBuilder(FinishDushAttackMessage.class, 2)
                .encoder(FinishDushAttackMessage::serialize).decoder(FinishDushAttackMessage::deserialize)
                .consumerMainThread(FinishDushAttackMessage::handle)
                .add();
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }
}
