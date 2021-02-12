package me.stefan923.supercore.configuration;

import me.stefan923.supercore.SuperCore;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class FileConfiguration extends CommentConfiguration {

    private final SuperCore plugin;
    private final String fileName;

    private File file;

    public FileConfiguration(SuperCore plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName.endsWith(".yml") ? fileName : fileName + ".yml";

        loadFile();
        createData();

        try {
            loadConfig();
        }
        catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() throws IOException, InvalidConfigurationException {
        load(file);
    }

    public File loadFile() {
        file = new File(this.plugin.getDataFolder(), this.fileName);
        return file;
    }

    public void saveData() {
        this.file = new File(plugin.getDataFolder(), fileName);
        try {
            save(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Attempting to fix error...");
            createData();
            saveData();
        }
    }

    @Override
    public void save(File file) throws IOException {
        super.save(file);
    }

    public void createData() {
        if (!file.exists()) {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }

            // If file isn't a resource, create from scratch
            if (plugin.getResource(fileName) == null) {
                try {
                    file.createNewFile();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            } else {
                plugin.saveResource(fileName, false);
            }
        }
    }

    public void delete() {
        if (file.exists()) {
            file.delete();
        }
    }

}
