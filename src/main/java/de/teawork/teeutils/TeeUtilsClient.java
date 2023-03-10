package de.teawork.teeutils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import de.teawork.teeutils.tools.ToolDimensionalVolume;
import de.teawork.teeutils.tools.ToolRepair;
import de.teawork.teeutils.util.ToolManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.CommandRegistryAccess;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class TeeUtilsClient implements ClientModInitializer {

    private ToolManager manager = ToolManager.INSTANCE;

    public static Path configDir;
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(TeeUtilsClient::registerCommands);

        configDir = FabricLoader.getInstance().getConfigDir().resolve("teeutils");
        try {
            Files.createDirectories(configDir);
        } catch (IOException e) {
            LOGGER.error("Failed to create config dir", e);
        }

        manager.registerTools();
        manager.loadConfig();
    }

    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher,
                                        CommandRegistryAccess registryAccess) {
        ToolRepair.registerCommand(dispatcher, registryAccess);
        ToolDimensionalVolume.registerCommand(dispatcher, registryAccess);
    }

}
