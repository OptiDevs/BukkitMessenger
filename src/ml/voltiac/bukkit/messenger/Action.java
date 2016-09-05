package ml.voltiac.bukkit.messenger;

public class Action {

	private static Bot bot = Main.gi().bot;


	public static class sendMessage {
		public static void toPlayer(String to, String from, String message) {
			String msg = message;
			msg = msg.replaceAll("%m", message);
			msg = msg.replaceAll("%p", from);
			msg = msg.trim();
			sendCommand.PLAYERMSG(to, from, msg);
		}

		public static void toServer(String to, String from, String format, String message) {
			String msg = format;
			msg = msg.replaceAll("%m", message);
			msg = msg.replaceAll("%p", from);
			msg = msg.trim();
			bot.sendMessage("#Voltiac_BukkitMessenger", msg);
		}

		public static void toIRC(String from, String format, String message) {
			String msg = format;
			msg = msg.replaceAll("%m", message);
			msg = msg.replaceAll("%s", from);
			msg = msg.trim();
			bot.sendMessage("#Voltiac_BukkitMessenger", msg);

		}

		public static void toBMHost(String from, String format, String message) {
			String msg = format;
			msg = msg.replaceAll("%m", message);
			msg = msg.replaceAll("%p", from);
			msg = msg.trim();
			sendCommand.PLAYERMSG("", from, msg);

		}
		
	}

	public static class sendCommand {
		public static void PLAYERMSG(String to, String from, String message) {
			bot.sendMessage("#Voltiac_BukkitMessenger", "!PLAYERMSG " + from + " " + to + " " + message);
		}

		public static void FINDPLAYER(String player) {
			bot.sendMessage("#Voltiac_BukkitMessenger", "!FINDPLAYER " + player);
		}

		public static void FINDSERVER(String server) {
			bot.sendMessage("#Voltiac_BukkitMessenger", "!FINDSERVER " + server);
		}

		public static void GOTPLAYER(String player) {
			bot.sendMessage("#Voltiac_BukkitMessenger", "!GOTPLAYER " + player);
		}

		public static void SERVER(String server) {
			bot.sendMessage("#Voltiac_BukkitMessenger", "!SERVER " + server);
		}
		
		public static void checkIn(String name, String ip){
			bot.sendMessage("#Voltiac_BukkitMessenger", "!CHECKIN " + name + " " + ip);
		}
	}
	
	public static void findPlayer(String player){
		sendCommand.FINDPLAYER(player);
	}
	
	public static void findServer(String server){
		sendCommand.FINDSERVER(server);
	}
}
