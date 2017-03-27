package ua.ryabuhin_valentine.side.server;

import java.net._;
import java.io._;
import ua.ryabuhin_valentine.side.AbstractMechanism;

class MyScalaServer(port: Int) extends Thread with AbstractMechanism {
	val aplySocket = new ServerSocket(port).accept;
	
	override def run: Unit = {
		println("\nConnection with client has successful\n\n");
		communication(aplySocket);
	}
	
}