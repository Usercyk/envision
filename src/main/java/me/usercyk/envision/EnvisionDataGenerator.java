package me.usercyk.envision;

import me.usercyk.envision.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class EnvisionDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        Envision.LOGGER.info("Initializing data generator for " + Envision.MOD_ID);

        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(EnvisionChineseProvider::new);
        pack.addProvider(EnvisionEnglishProvider::new);
        pack.addProvider(EnvisionSimpleJsonProvider::new);
        pack.addProvider(EnvisionModelProvider::new);
        pack.addProvider(EnvisionRecipeProvider::new);
        pack.addProvider(EnvisionRummageLootProvider::new);
        pack.addProvider(EnvisionTagProvider::new);
    }
}
