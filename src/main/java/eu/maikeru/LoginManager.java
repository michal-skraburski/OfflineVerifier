package eu.maikeru;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

public final class LoginManager {
    private static Gson gson = new Gson();
    private static ComponentLogger logger = JavaPlugin.getPlugin(ExamplePlugin.class).getLog();
    private File loginsFile;
    private List<Login> logins = new ArrayList<>(); // init empty

    public LoginManager() {
        Path pluginDir = Path.of(System.getProperty("user.dir"), "/plugins/OfflineVerifier");
        logger.info("Path:" + pluginDir.toAbsolutePath().toString());
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
        if (loginsFile.length() == 0) {
            this.writeLogins(loginsFile);
            logger.info("Written empty into file!");
        }
        
        this.loginsFile = loginsFile;
        this.logins = this.loadLogins();
    }
    public void addLogin(String name, String password) {
        logins.add(new Login(name, password));
        writeLogins();
    }

    public void writeLogins(File file) {
        try (Writer writer = new FileWriter(file)){
            gson.toJson(logins, writer);
        } catch (JsonIOException e) {
            logger.error("Failed to write login JSON: " + e.getMessage());
        }catch (IOException e) {
            logger.error("Failed to : " + e.getMessage());
        }
    }
    
    public void writeLogins() {
        try (Writer writer = new FileWriter(loginsFile)){
            gson.toJson(logins, writer);
        } catch (JsonIOException e) {
            logger.error("Failed to write login JSON: " + e.getMessage());
        }catch (IOException e) {
            logger.error("Failed to : " + e.getMessage());
        }
    }

    public List<Login> loadLogins() {
        try (Reader reader = new FileReader(loginsFile)){
            logins = gson.fromJson(reader, new TypeToken<ArrayList<Login>>() {}.getType());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        
        return getLogins();
    }
    
    public List<Login> getLogins() {
        return logins;
    }
}
