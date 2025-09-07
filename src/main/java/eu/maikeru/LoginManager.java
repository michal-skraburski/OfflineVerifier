package eu.maikeru;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

public class LoginManager {
    private static Gson gson = new Gson();
    private static ComponentLogger logger = ExamplePlugin.getLog();
    private File loginsFile;

    public LoginManager() {
        Path pluginDir = FileSystems.getDefault().getPath("/plugins","OfflineVerifier");
        if (!Files.exists(pluginDir)){
            try {
               Files.createDirectory(pluginDir);
               logger.info("Created a plugin folder!");
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
        File loginsFile = new File(pluginDir.toFile(), "logins.json");
        if (!loginsFile.exists()) {
            try {
                loginsFile.createNewFile();
                logger.info("Created a logins file!");
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        this.loginsFile = loginsFile;
    }
}
