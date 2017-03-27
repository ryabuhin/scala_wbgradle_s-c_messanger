package ua.ryabuhin_valentine.main;

import java.util._;
import ua.ryabuhin_valentine.side.server.MyScalaServer;
import ua.ryabuhin_valentine.side.client.MyScalaClient;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout._;
import javafx.event.EventHandler;
import javafx.event.Event;

object Main {
	def main(args: Array[String]) {
		Application.launch(classOf[Main], args: _*);
	}
	var primaryStage: Stage = null;
}

class Main extends Application {
	override def start(primaryStage: Stage): Unit = {
		Main.primaryStage = primaryStage;
		var pane = new BorderPane;
		pane.setPrefSize(600, 500);
		pane.setCenter(initMainMenu);
		primaryStage.setScene(new Scene(pane));
		primaryStage.setResizable(false);
		primaryStage.setTitle("Scala custom chat v1.0");
		primaryStage.show;
	}
	def initMainMenu: VBox = {
		var bServer:Button = initButton("Server");
		bServer.setOnMouseClicked(new EventHandler[Event] {
			override def handle(e: Event): Unit = {
				Main.primaryStage.setScene(initChatMenu);
				//new MyScalaServer(8987).start;
			}
		});
		var bClient = initButton("Client");
		bClient.setOnMouseClicked(new EventHandler[Event] {
			override def handle(e: Event): Unit = {
				Main.primaryStage.setScene(initChatMenu);
				//new MyScalaClient("localhost", 8987).start;
			}
		});
		var vboxMainMenu = new VBox(10);
		vboxMainMenu.setAlignment(javafx.geometry.Pos.CENTER);
		vboxMainMenu.getChildren.addAll(bServer, bClient);
		vboxMainMenu;
	}
	def initChatMenu: Scene = {
		var pane = new Pane;
		var vbox = new VBox(20);
		vbox.setPrefSize(600, 500);
		vbox.setTranslateX(20);
		vbox.setTranslateY(20);
		var bSend = initButton("Send message");
		var textField = new TextField();
		textField.setPrefSize(340, 50);
		var hbox = new HBox(20);
		hbox.getChildren.addAll(textField, bSend);
		var paneArea = new Pane;
		var textArea = new TextArea();
		textArea.setPrefSize(560, 400);
		textArea.setWrapText(true);
		textArea.setEditable(false);
		paneArea.setPrefSize(460, 400);
		paneArea.getChildren.add(textArea);
		vbox.getChildren.addAll(paneArea, hbox);
		pane.getChildren.add(vbox);
		new Scene(pane);
	}
	def initButton(bName: String):Button = {
		var button:Button = new Button(bName);
		button.setPrefSize(200, 50);
		button;
	}
}

