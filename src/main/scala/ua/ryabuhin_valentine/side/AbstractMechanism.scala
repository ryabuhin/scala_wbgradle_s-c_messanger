package ua.ryabuhin_valentine.side

import java.util._
import java.net._
import java.io._;

trait AbstractMechanism {
	val scanner = new Scanner(System.in);
	
	def communication(socket: Socket): Unit = {
		val ins = new DataInputStream(socket.getInputStream);
		val outs = new DataOutputStream(socket.getOutputStream);
		val clientAddr = socket.getInetAddress;
		// read messages from the socket stream
		new Thread {
			override def run {
				while(true)
					println(s"Client message($clientAddr): " + ins.readUTF);
			}
		}.start;
		// sendMessage
		var ymessage = scanner.next;
		outs.writeUTF(ymessage);
		println(s"Your message: $ymessage");
	}
	
}