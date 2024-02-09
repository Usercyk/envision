package me.usercyk.envision.datagen.custom;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import static me.usercyk.envision.Envision.GSON;

public abstract class SimpleJsonProvider implements DataProvider {
    protected final FabricDataOutput dataOutput;

    public SimpleJsonProvider(FabricDataOutput dataOutput) {
        this.dataOutput = dataOutput;
    }

    public abstract void generateSimpleJsons(SimpleJsonBuilder simpleJsonBuilder);

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        ArrayList<CompletableFuture<?>> futures = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();
        generateSimpleJsons(((jsonObject, directoryName, file, isData) -> {
            Path path = getPath(directoryName, file, isData);
            if (paths.contains(path)) {
                throw new RuntimeException("Existing path found - " + path.toString() + " - Duplicate will be ignored.");
            } else {
                paths.add(path);
                futures.add(DataProvider.writeToPath(writer, jsonObject, path));
            }
        }));
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}));
    }

    @Override
    public String getName() {
        return "Envision Simple Json Provider";
    }

    private Path getPath(String directoryName, Identifier file, boolean isData) {
        DataOutput.OutputType type = isData ? DataOutput.OutputType.DATA_PACK : DataOutput.OutputType.RESOURCE_PACK;
        return dataOutput.getResolver(type, directoryName).resolveJson(file);
    }

    @FunctionalInterface
    public interface SimpleJsonBuilder {
        void add(JsonObject jsonObject, String directoryName, Identifier file, boolean isData);

        default void add(String json, String directoryName, Identifier file, boolean isData) {
            JsonObject object = GSON.fromJson(json, JsonObject.class);
            add(object, directoryName, file, isData);
        }
    }
}
