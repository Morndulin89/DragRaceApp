
package dragapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Pulu
 */
public class DragApp extends Application {
    
    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DragFXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("The Unofficial Drag Race App!");
        scene.getStylesheets().add("dragapp/DragCSS.css");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
