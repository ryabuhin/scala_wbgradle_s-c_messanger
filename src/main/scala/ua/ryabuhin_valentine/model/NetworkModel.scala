package ua.ryabuhin_valentine.model;

import java.net._;

class NetworkModel(private val _isServer: Boolean, private val _port: Int, private val _ip: String = "") {
	private var _serverSocket = if (_isServer) new ServerSocket(_port) else null;
	private var _applySocket = if(_isServer) _serverSocket.accept() else new Socket(_ip, _port);
	
	def isServer: Boolean = _isServer;
	def applySocket: Socket = _applySocket;
	def serverSocket: ServerSocket = _serverSocket;
}