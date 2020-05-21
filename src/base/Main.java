package base;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author daniel migales puertas
 *
 */
public class Main extends Application {

    //variables del evento del mouse para mover la ventana por la pantalla
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/vista/Start.fxml"));
        primaryStage.setTitle("Inicio de sesion / Registro");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNIFIED); //ocultar el marco
        primaryStage.show(); //mostrar la ventana
        primaryStage.setAlwaysOnTop(true); //siempre encima
        primaryStage.setResizable(false); //no modificable de tamaño
        

        //esto sirve para que la ventana pueda ser arrastrada por la pantalla con el raton, ya que sin marco se queda clavada
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
