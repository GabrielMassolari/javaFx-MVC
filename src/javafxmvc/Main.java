package javafxmvc;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("view/FXMLVboxMain.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sistema de Vendas (JavaFx MVC)");
        stage.setResizable(false);
        stage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
