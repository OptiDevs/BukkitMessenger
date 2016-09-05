package ml.voltiac.bukkit.messenger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

	Main m = null;

	public Commands(Main main) {
		m = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("BukkitMessenger")) {
			if (args.length == 0) {
				sender.sendMessage(Lang.INVALID_ARGS.toString());
				return true;
			} else if (args[0].equalsIgnoreCase("SET")) {
				if (sender.hasPermission("BukkitMessenger.msg") || sender.isOp()) {
					// BM SET <NAME> <IP>
					if (args.length == 3) {
						m.getConfig().set("ServerDetails.Name", args[1]);
						m.getConfig().set("ServerDetails.IP", args[2]);
						return true;
					} else {
						sender.sendMessage(Lang.SERVERDETAILS_ARGS.toString());
						return true;
					}
				} else {
					sender.sendMessage(Lang.NO_PERMS.toString());
					return true;
				}
			}
		}

		if (m == null) {
			sender.sendMessage("");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("BukkitMessenger")) {
			if (args.length == 0) {
				sender.sendMessage(Lang.INVALID_ARGS.toString());
			} else if (args[0].equalsIgnoreCase("MSG") || args[0].equalsIgnoreCase("MESSAGE")) {
				Main.gi();
				if (sender.isOp() || sender.hasPermission("BukkitMessenger.msg")) {
					// BM MSG <PLAYER> <MESSAGE>
					if (args.length >= 3) {
						String msg = "";
						for (int i = 2; i < args.length; i++) {
							msg = msg + args[i] + " ";
						}
						msg = msg.trim();
						String snd = "";
						snd = Lang.PLAYER_MESSAGE_SEND.toString();
						snd = snd.replaceAll("%p", sender.getName());
						snd = snd.replaceAll("%m", msg);
						sender.sendMessage(snd);
						Action.sendMessage.toPlayer(args[1], sender.getName(), msg);
						return true;
					} else {
						sender.sendMessage(Lang.INVALID_ARGS.toString());
						return true;
					}
				} else {
					sender.sendMessage(Lang.NO_PERMS.toString());
					return true;
				}
			} else if (args[0].equalsIgnoreCase("FIND")) {
				if (sender.isOp() || sender.hasPermission("BukkitMessenger.find.player")) {
					// BM FIND <PLAYER>
					if (args.length == 2) {
						Action.findPlayer(args[1]);
						return true;
					} else {
						sender.sendMessage(Lang.INVALID_ARGS.toString());
						return true;
					}
				} else {
					sender.sendMessage(Lang.NO_PERMS.toString());
					return true;
				}
			} else if (args[0].equalsIgnoreCase("SERVER")) {
				if (sender.isOp() || sender.hasPermission("BukkitMessenger.find.server")) {
					// BM FIND <PLAYER>
					if (args.length == 2) {
						Action.findServer(args[1]);
						return true;
					} else {
						sender.sendMessage(Lang.INVALID_ARGS.toString());
						return true;
					}
				} else {
					sender.sendMessage(Lang.NO_PERMS.toString());
					return true;
				}
			} else if (args[0].equalsIgnoreCase("RELOAD")) {
				if (sender.isOp() || sender.hasPermission("BukkitMessenger.admin")) {
					// BM RELOAD
					if (args.length == 1) {
						m.reloadConfig();
						return true;
					} else {
						sender.sendMessage(Lang.INVALID_ARGS.toString());
						return true;
					}
				} else {
					sender.sendMessage(Lang.NO_PERMS.toString());
					return true;
				}
			}
		} else {
			sender.sendMessage(Lang.INVALID_ARGS.toString());
			return true;
		}
		return false;
	}
}
