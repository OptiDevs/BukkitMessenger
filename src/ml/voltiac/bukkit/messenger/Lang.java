package ml.voltiac.bukkit.messenger;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
 
/**
* An enum for requesting strings from the language file.
* @author gomeow
*/
public enum Lang {
	// Plugin
	PREFIX("prefix", "&8[&6BM&8]&r"),
	CONFIG_RELOAD("config-reload", "&6Config reloaded!"),
	CONFIG_SAVE("config-reload", "&6Config saved!"),
	SERVERDETAILS_NOTSET("server-details-not-set", "&cServer details not set! Set these with &b/bm set!&c"),
	SERVERDETAILS_ARGS("server-details-args", "&cUSAGE: /bm set <NAME> <IP>"),
	// Commands
    INVALID_ARGS("invalid-args", "&cInvalid args!"),
    // Permissions
    NO_PERMS("no-permissions", "&cYou don''t have permission for that!"),
    // Messaging
    PLAYER_MESSAGE_SEND("player-message-send", "&eYou&0 > &e%p&0 :&f %m"),
    PLAYER_MESSAGE_RECIVE("player-message-recive", "&e%p&0 > &eYou&0 :&f %m"),
    SERVER_MESSAGE_SEND("server-message-send", "&eServer&0 > &e%s&0 :&f %m"),
    SERVER_MESSAGE_RECIVE("server-message-recive", "&e%p&0 > &eServer&0 :&f %m"),
    // TODO: Sort these commands
    ;
 
    private String path;
    private String def;
    private static YamlConfiguration LANG;
 
    /**
    * Lang enum constructor.
    * @param path The string path.
    * @param start The default string.
    */
    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }
 
    /**
    * Set the {@code YamlConfiguration} to use.
    * @param config The config to set.
    */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }
 
    @Override
    public String toString() {
        if (this == PREFIX)
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }
 
    /**
    * Get the default value of the path.
    * @return The default value of the path.
    */
    public String getDefault() {
        return this.def;
    }
 
    /**
    * Get the path to the string.
    * @return The path to the string.
    */
    public String getPath() {
        return this.path;
    }
}
