package ua.ryabuhin_valentine.side.client;

import java.net._;
import java.io._;
import ua.ryabuhin_valentine.side.AbstractMechanism;

class MyScalaClient(ip: String, port: Int) extends Thread with AbstractMechanism {
	val connectionSocket = new Socket(ip, port);
	
	override def run: Unit = {
		println("\nConnection with server has successful\n\n");
		communication(connectionSocket);
	}
}
