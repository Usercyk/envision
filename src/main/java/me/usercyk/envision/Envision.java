package me.usercyk.envision;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.usercyk.envision.block.EnvisionBlocks;
import me.usercyk.envision.item.EnvisionItemGroup;
import me.usercyk.envision.item.EnvisionItems;
import me.usercyk.envision.screen.EnvisionScreenHandlerTypes;
import me.usercyk.envision.worldgen.EnvisionWorlds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Envision implements ModInitializer {

    public static final String MOD_ID = "envision";
    public static final Logger LOGGER = LoggerFactory.getLogger("Envision");

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing common side for " + MOD_ID);

        EnvisionWorlds.register();
        EnvisionScreenHandlerTypes.register();
        registerItemsAndBlocks();
    }

    private void registerItemsAndBlocks() {
        EnvisionItems.register();
        EnvisionBlocks.register();
        EnvisionItemGroup.register();
    }
}
