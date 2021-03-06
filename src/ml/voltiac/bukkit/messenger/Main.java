package ml.voltiac.bukkit.messenger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	Bot bot = null;
	public static YamlConfiguration LANG;
	public static File LANG_FILE;
	Logger log = getServer().getLogger();

	@Override
	public void onLoad() {
		bot = new Bot(this);
	}

	@Override
	public void onEnable() {
		if (getConfig().getString("ServerDetails.Name").equalsIgnoreCase("NOT_SET")
				|| getConfig().getString("ServerDetails.IP").equalsIgnoreCase("NOT_SET")) {
			bot.disconnect();
			bot = null;
		}
		bot.setName(getConfig().getString("ServerDetails.Name"));
		Action.sendCommand.checkIn(getConfig().getString("ServerDetails.Name"), getConfig().getString("ServerDetails.IP"));
		loadConfig();
		loadLang();
		getCommand("BukkitMessenger").setExecutor(new Commands(this));
	}

	@Override
	public void onDisable() {
		bot.disconnect();
	}
	
	public static Main gi(){
		return (Main) Bukkit.getPluginManager().getPlugin("BukkitMessenger");
	}

	private void loadConfig() {
		getConfig().getDefaults();
		saveDefaultConfig();
		reloadConfig();
	}

	public YamlConfiguration getLang() {
		return LANG;
	}

	public File getLangFile() {
		return LANG_FILE;
	}

	public void loadLang() {
		File lang = new File(getDataFolder(), "lang.yml");
		if (!lang.exists()) {
			try {
				getDataFolder().mkdir();
				lang.createNewFile();
				InputStream defConfigStream = this.getResource("lang.yml");
				if (defConfigStream != null) {
					YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(LANG_FILE);
					defConfig.save(lang);
					Lang.setFile(defConfig);
					return;
				}
			} catch (IOException e) {
				e.printStackTrace(); // So they notice
				log.severe("[PluginName] Couldn't create language file.");
				log.severe("[PluginName] This is a fatal error. Now disabling");
				this.setEnabled(false); // Without it loaded, we can't send them
										// messages
			}
		}
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
		for (Lang item : Lang.values()) {
			if (conf.getString(item.getPath()) == null) {
				conf.set(item.getPath(), item.getDefault());
			}
		}
		Lang.setFile(conf);
		Main.LANG = conf;
		Main.LANG_FILE = lang;
		try {
			conf.save(getLangFile());
		} catch (IOException e) {
			log.log(Level.WARNING, "PluginName: Failed to save lang.yml.");
			log.log(Level.WARNING, "PluginName: Report this stack trace to <your name>.");
			e.printStackTrace();
		}
	}
}
