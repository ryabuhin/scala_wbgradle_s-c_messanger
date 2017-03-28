package ua.ryabuhin_valentine.presenter

import ua.ryabuhin_valentine.view.View;
import ua.ryabuhin_valentine.model.NetworkModel;
import java.io._;

class Presenter(private val view: View) {
	private var model: NetworkModel = null;
	private var dins: DataInputStream = null;
	private var douts: DataOutputStream = null;
	private var clientAddr: String = null;
	
	def initModel(isServer:Boolean, port: Integer, ip:String = ""): Unit = {
		model = new NetworkModel(isServer, port, ip);
		clientAddr = model.applySocket.getInetAddress.toString;
		dins = new DataInputStream(model.applySocket.getInputStream);
		douts = new DataOutputStream(model.applySocket.getOutputStream);
	}
	
	def sendMessage(message:String): Unit = douts.writeUTF(message);
	
	def receiveMessage: Any = {
		try {
			var receiveMessage = dins.readUTF;
			s"( $clientAddr ): " + receiveMessage;
		} catch {
			case e: java.net.SocketException => { e.printStackTrace(); -1; }
			case f: java.io.EOFException => { f.printStackTrace(); -1; }
		}
	}
	
	def closeConnection() {
		if(model.isServer && model.serverSocket != null) model.applySocket.close;
		if(!model.isServer && model.applySocket != null) model.applySocket.close;
	}
	
}