package ml.voltiac.bukkit.messenger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

public class Bot extends PircBot {
	private String channelJOIN = "#Voltiac_BukkitMessenger";

	Main m = null;
	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	public Bot(Main main) {
		m = main;
		this.setName("BukkitManagerAPI");
		this.setName(m.getConfig().getString("ServerDetails.Name"));
		this.setAutoNickChange(true);
		this.setVerbose(false);
		try {
			this.connect("wilhelm.freenode.net");
		} catch (IOException | IrcException e) {
			System.out.print(
					"Can't Connect to messageing servers. Contact the Develoepr (DiamondDeveloper) for more help!");
			return;
		}
		this.joinChannel(channelJOIN);
		System.out.print("Connected to messaging servers!");
	}

	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		String[] words = message.split("\\s+");
		String command = words[0];
		String[] args = {};
		List<String> list = new ArrayList<>();
		Collections.addAll(list, words);
		list.removeAll(Arrays.asList(command));
		args = list.toArray(EMPTY_STRING_ARRAY);
		command = command.replaceFirst("!", "");
		if (message.startsWith("!")) {
			onCommand(sender, command, args);
		}
	}

	private void onCommand(String sender, String command, String[] args) {
		if (command.equalsIgnoreCase("PLAYERMSG")) {
			// PLAYERMSG <PLAYER> <MESSAGE>
			if (args.length >= 2) {
				String msg = "";
				for (int i = 2; i < args.length; i++) {
					msg = msg + args[i] + " ";
				}
				msg = msg.trim();
				for (Player p : m.getServer().getOnlinePlayers()) {
					if (args[0].equalsIgnoreCase(p.getName()) || args[0].equals(p.getUniqueId())) {
						String format = Lang.PLAYER_MESSAGE_RECIVE.toString();
						format = format.replaceAll("%m", msg);
						format = format.replaceAll("%p", args[1]);
						format = format.trim();
						p.sendMessage(format);
						return;
					}
				}
				return;
			}
		} else if (command.equalsIgnoreCase("FINDPLAYER")) {
			// FINDPLAYER <PLAYER>
			for (Player p : m.getServer().getOnlinePlayers()) {
				if (args[0].equalsIgnoreCase(p.getName()) || args[0].equals(p.getUniqueId())) {
					Action.sendCommand.GOTPLAYER(args[0]);
					return;
				}
			}
		}
	}

	@Override
	protected void onPrivateMessage(String sender, String login, String hostname, String message) {
		String[] words = message.split("\\s+");
		String command = words[0];
		String[] args = {};
		List<String> list = new ArrayList<>();
		Collections.addAll(list, words);
		list.removeAll(Arrays.asList(command));
		args = list.toArray(EMPTY_STRING_ARRAY);
		
		if(!(command.length() > 0)){
			return;
		} else if(args.length == 0){
			return;
		}
		
		if(command.equalsIgnoreCase("GOTPLAYER")){
			
		}
	}

	@Override
	protected void onDisconnect() {
	}

}
