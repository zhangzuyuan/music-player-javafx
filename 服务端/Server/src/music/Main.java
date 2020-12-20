package music;

import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
		
		LocateRegistry.createRegistry(12334);
		// System.setProperty("java.rmi.server.hostname","127.0.0.1");;  
		Naming.rebind("rmi://localhost:12334/RHello", new Comment());
		
        launch(args);
    }
	public void start(Stage primaryStage) throws Exception{
		Label bn=new Label("服务器");
		bn.setFont(new Font("KaiTi",40));
		
		AnchorPane root =new AnchorPane(bn);
		//添加场景（scene）并设置布局
		Scene scene =new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
		primaryStage.setOnCloseRequest(event->{System.exit(0);});
	}

}
