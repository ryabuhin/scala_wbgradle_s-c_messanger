package ua.ryabuhin_valentine.presenter

import ua.ryabuhin_valentine.view.View;
import ua.ryabuhin_valentine.model.NetworkModel;
import java.io._;

class Presenter(private val view: View) {
	var model: NetworkModel = null;
	var dins: DataInputStream = null;
	var douts: DataOutputStream = null;
	
	def initModel(isServer:Boolean, port: Integer, ip:String = ""): Unit = {
		model = new NetworkModel(isServer, port, ip);
		dins = new DataInputStream(model.applySocket.getInputStream);
		douts = new DataOutputStream(model.applySocket.getOutputStream);
	}
	
	def sentMessage(message:String): Unit = douts.writeUTF(message);
	
	def receiveMessage(): String = {
		val clientAddr = model.applySocket.getInetAddress;
		s"( $clientAddr ): " + dins.readUTF;
	}
	
}