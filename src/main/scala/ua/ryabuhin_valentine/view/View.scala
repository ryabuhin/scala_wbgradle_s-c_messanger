package ua.ryabuhin_valentine.view;

import ua.ryabuhin_valentine.presenter.Presenter;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout._;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.scene.input.KeyEvent;

object View {
	def main(args: Array[String]) = Application.launch(classOf[View], args: _*);
}

class View extends Application {
	private var _presenter: Presenter = null;
	private var _primaryStage: Stage = null;
	private var tfPort: TextField = null;
	private var tfIp: TextField = null;
	private var textArea: TextArea = null;
	private var textField: TextField = null;
	
	override def start(primaryStage: Stage): Unit = {
		_primaryStage = primaryStage;
		primaryStage.setScene(initMainMenu);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Scala custom messanger v1.0");
		primaryStage.show;
	}
	
	override def stop: Unit = {
		_presenter.closeConnection;
	}
	
	def initMainMenu: Scene = {
		var pane = new BorderPane;
		pane.setPrefSize(600, 250);
		var bServer:Button = initButton("Server");
		bServer.setOnMouseClicked(new EventHandler[Event] {
			override def handle(e: Event): Unit = _primaryStage.setScene(infoMenu(true));
		});
		var bClient = initButton("Client");
		bClient.setOnMouseClicked(new EventHandler[Event] {
			override def handle(e: Event): Unit = _primaryStage.setScene(infoMenu(false));
		});
		var vboxMainMenu = new VBox(10);
		vboxMainMenu.setAlignment(javafx.geometry.Pos.CENTER);
		vboxMainMenu.getChildren.addAll(bServer, bClient);
		pane.setCenter(vboxMainMenu);
		new Scene(pane);
	}
	
	def initChatMenu(isServer: Boolean): Scene = {
		communication(isServer, Integer.valueOf(tfPort.getText), if(tfIp != null) tfIp.getText else null);
		var pane = new Pane;
		var vbox = new VBox(20);
		vbox.setPrefSize(600, 500);
		vbox.setTranslateX(20);
		vbox.setTranslateY(20);
		var bSend = initButton("Send message");
		bSend.setOnMouseClicked(new EventHandler[Event] {
			override def handle(event: Event): Unit = sendMessage;
		});
		textField = new TextField;
		textField.setPrefSize(340, 50);
		var hbox = new HBox(20);
		hbox.getChildren.addAll(textField, bSend);
		var paneArea = new Pane;
		textArea = new TextArea;
		textArea.setPrefSize(560, 400);
		textArea.setWrapText(true);
		textArea.setEditable(false);
		paneArea.setPrefSize(460, 400);
		paneArea.getChildren.add(textArea);
		vbox.getChildren.addAll(paneArea, hbox);
		pane.getChildren.add(vbox);
		var scene = new Scene(pane);
		scene.setOnKeyPressed(new EventHandler[KeyEvent] {
			override def handle(event: KeyEvent): Unit = if(event.getCode.getName == "Enter") sendMessage;
		});
		scene;
	}
	
	def infoMenu(isServer: Boolean): Scene = {
		var pane = new BorderPane;
		pane.setPrefSize(600, 500);
		var gpane = new GridPane;
		gpane.setHgap(20);
		gpane.setVgap(20);
		gpane.setAlignment(javafx.geometry.Pos.CENTER);
		var bContinue = new Button("Continue ->");
		bContinue.setPrefSize(170, 30);
		bContinue.setOnMouseClicked(new EventHandler[Event] {
			override def handle(event: Event): Unit = _primaryStage.setScene(initChatMenu(isServer));
		});
		var bBack = new Button("<- Back");
		bBack.setPrefSize(170, 30);
		bBack.setOnMouseClicked(new EventHandler[Event] {
			override def handle(event: Event): Unit = _primaryStage.setScene(initMainMenu);
		});
		var lInf = new Label("Fill in required fields");
		lInf.setFont(javafx.scene.text.Font.font(27d));
		var shift: Int = 0;
		if(!isServer) {
			tfIp = new TextField;
			gpane.add(tfIp, 1, 2);
			gpane.add(new Label("IPv4: "), 0, 2);
		} else shift += 1;
		gpane.add(lInf, 0, 0, 2, 1);
		tfPort = new TextField;
		gpane.add(new Label("Free port: "), 0, 3 - shift);
		gpane.add(tfPort, 1, 3 - shift);
		gpane.add(bBack, 0, 4 - shift);
		gpane.add(bContinue, 1, 4 - shift);
		pane.setCenter(gpane);
		new Scene(pane);
	}
	
	def initButton(bName: String): Button = {
		var button:Button = new Button(bName);
		button.setPrefSize(200, 50);
		button;
	}
	
	def communication(isServer: Boolean, port: Integer, ip: String = ""): Unit = {
		_presenter = new Presenter(View.this);
		_presenter.initModel(isServer, port, ip);
		new Thread {
			override def run: Unit = {
				while(true) {
					var receiveMessage = _presenter.receiveMessage;
					if(receiveMessage.isInstanceOf[Int] && receiveMessage == -1) {
						textArea.appendText("\n\n\n[WARNING] Sorry, connection problems ..." +
								"\n[WARNING] Program will be closed !")
						Thread.sleep(5000);
						System.exit(0);
					}
					else
						textArea.appendText(s"\n$receiveMessage");
				}
			}
		}.start;
	}
	
	def sendMessage: Unit = {
		var message = textField.getText;
		_presenter.sendMessage(message);
		textArea.appendText("\n( You ): " + message);
		textField.setText("");
	}
	
}
	
	