package ua.ryabuhin_valentine.main;

import java.util._;
import ua.ryabuhin_valentine.side.server.MyScalaServer;
import ua.ryabuhin_valentine.side.client.MyScalaClient;

object Main {
	def main(args: Array[String]) {
		
		print("1. Server\n2. Client\n\nYour choice: ");
		
		var choice = new Scanner(System.in).nextInt;
		
		val side = choice match {
			case 1 => new MyScalaServer(8987);
			case 2 => new MyScalaClient("localhost", 8987);
		}
		side.start;
	}
}