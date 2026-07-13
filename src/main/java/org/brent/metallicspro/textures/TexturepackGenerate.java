package org.brent.metallicspro.textures;

import org.brent.metallicspro.MetallicsPro;
import org.brent.metallicspro.items.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TexturepackGenerate {

    private static int ITEMS_HANDLED = 0;

    public static void start() {
        Bukkit.getLogger().info("[MetallicsPro] Started Pack Development");

        File dataFolder = MetallicsPro.getPlugin().getDataFolder();
        File zipFile = new File(dataFolder, "texturepack.zip");

        copyResourceToDisk("texturepack.zip", zipFile);

        unzip(zipFile);
        zipFile.delete();

        File packFolder = new File(dataFolder, "texturepack");
        workOnAllItems(packFolder);

        Bukkit.getLogger().info("[MetallicsPro] Pack Development Finished");
    }

    private static void workOnAllItems(File packFolder) {

        for (CustomItem item : MetallicsPro.getItemRegistry().getCustomItems().values()) {
            if (!item.shouldHandleTextures()) continue;

            NamespacedKey key = item.getModelKey();
            if (key == null) continue;

            File modelTemplate = CustomItem.getModelJson(packFolder);

            File modelOut = new File(
                    packFolder,
                    "assets/" + key.getNamespace()
                            + "/models/"
                            + key.getKey() + ".json"
            );

            copyTemplate(modelTemplate, modelOut);

            applyModelReplacement(modelOut, key);

            File itemTemplate = CustomItem.getItemJson(packFolder);

            File itemOut = new File(
                    packFolder,
                    "assets/" + key.getNamespace()
                            + "/items/"
                            + key.getKey() + ".json"
            );

            copyTemplate(itemTemplate, itemOut);

            applyItemReplacement(itemOut, key);

            ITEMS_HANDLED++;
        }

        Bukkit.getLogger().info("[MetallicsPro] " + ITEMS_HANDLED + " item texture jsons handled");
    }

    private static void applyItemReplacement(File file, NamespacedKey key) {
        try {
            String json = Files.readString(file.toPath());

            String replacement =
                    key.getNamespace() + ":" + key.getKey();

            json = json.replace("%EXAMPLE%", replacement);

            Files.writeString(file.toPath(), json);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void applyModelReplacement(File file, NamespacedKey key) {
        try {
            String json = Files.readString(file.toPath());

            String replacement = key.getNamespace() + ":item/" + key.getKey();

            json = json.replace("%EXAMPLE%", replacement);

            Files.writeString(file.toPath(), json);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyTemplate(File source, File target) {
        try {
            target.getParentFile().mkdirs();

            Files.copy(
                    source.toPath(),
                    target.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void unzip(File zipFile) {
        try {
            Path zipPath = zipFile.toPath();
            Path outputDir = zipPath.getParent();

            if (outputDir == null) {
                throw new IOException("Could not determine output directory.");
            }

            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {

                ZipEntry entry;

                while ((entry = zis.getNextEntry()) != null) {

                    Path newPath = outputDir.resolve(entry.getName()).normalize();
                    if (!newPath.startsWith(outputDir)) {
                        throw new IOException("Bad zip entry: " + entry.getName());
                    }

                    if (entry.isDirectory()) {

                        Files.createDirectories(newPath);

                    } else {
                        Files.createDirectories(newPath.getParent());

                        try (OutputStream os = Files.newOutputStream(newPath)) {

                            byte[] buffer = new byte[4096];
                            int len;

                            while ((len = zis.read(buffer)) > 0) {
                                os.write(buffer, 0, len);
                            }
                        }
                    }

                    zis.closeEntry();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean copyResourceToDisk(String resourcePath, File targetLocation) {
        try (InputStream in = TexturepackGenerate.class.getClassLoader().getResourceAsStream(resourcePath)) {

            if (in == null) {
                System.err.println("Error: Resource not found inside JAR: " + resourcePath);
                return false;
            }

            File parentDir = targetLocation.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            Path targetPath = targetLocation.toPath();

            Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);

            return true;

        } catch (IOException e) {
            System.err.println("Failed to copy resource: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
