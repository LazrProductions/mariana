package com.lazrproductions.mariana;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lazrproductions.mariana.config.ModCommonConfigs;
import com.lazrproductions.mariana.event.ModClientEvents;
import com.lazrproductions.mariana.event.ModServerEvents;
import com.lazrproductions.mariana.init.ModBlocks;
import com.lazrproductions.mariana.init.ModCreativeTabs;
import com.lazrproductions.mariana.init.ModEntityTypes;
import com.lazrproductions.mariana.init.ModItems;
import com.lazrproductions.mariana.init.ModSounds;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MarianaMod.MODID)
public class MarianaMod {
    public static final Logger LOGGER = LogManager.getLogger(MarianaMod.MODID);
    public static final String MODID = "mariana";


    // #region Registers

    // #endregion

    // #region Creative Mode Tabs

    // #endregion

    // #region Sounds

    // #endregion

    // #region Attirbutes
    public static final AttributeModifier HANDCUFFED_ATTIRBUTE = new AttributeModifier("handcuffed", -1,
            AttributeModifier.Operation.MULTIPLY_BASE);
    // #endregion

    // #region Configs

    //#endregion

    public MarianaMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModEntityTypes.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerSounds);

        ModLoadingContext.get().registerConfig(Type.COMMON, ModCommonConfigs.SPEC, "mariana-common.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Running commmon setup for Cuffed");
        MinecraftForge.EVENT_BUS.register(new ModServerEvents());
    }

    private void registerSounds(RegisterEvent event) {
        LOGGER.info("Registering sound for Mariana mod");
        ModSounds.register(event);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Running server setup for Mariana");

        // In case you want to add a command
        // Register Commands
        // event.getServer().getCommands().getDispatcher()
        //         .register(
        //                 Commands.literal("handcuff").requires(x -> x.hasPermission(2))
        //                         .then(Commands.literal("get")
        //                                 .then(Commands.argument("players", EntityArgument.players()).executes(x -> {
        //                                     Collection<ServerPlayer> players = EntityArgument.getPlayers(x, "players");
        //                                     for (ServerPlayer player : players)
        //                                         if (CuffedServer.getHandcuffed(player).isGettingOrCurrentlyCuffed()) {
        //                                             if (!CuffedServer.getHandcuffed(player).isSoftCuffed()) {
        //                                                 player.sendSystemMessage(Component.literal(
        //                                                         "" + player.getName().getString()
        //                                                                 + " is handcuffed."));
        //                                             } else {
        //                                                 player.sendSystemMessage(Component.literal(
        //                                                         "" + player.getName().getString()
        //                                                                 + " is soft-cuffed."));
        //                                             }
        //                                         } else {
        //                                             player.sendSystemMessage(Component.literal(
        //                                                     "" + player.getName().getString()
        //                                                             + " is not in handcuffs."));
        //                                         }
        //                                     return 0;
        //                                 }))));
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Running client setup for Cuffed");

            MinecraftForge.EVENT_BUS.register(new ModClientEvents());

            //EntityRenderers.register(ModEntityTypes.PADLOCK.get(), PadlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            //event.registerLayerDefinition(PadlockEntityModel.LAYER_LOCATION, PadlockEntityModel::getModelData);
        }
    }
}