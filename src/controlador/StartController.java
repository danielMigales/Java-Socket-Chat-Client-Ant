package controlador;

import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author daniel migales puertas
 *
 */
public class StartController implements Initializable {

    double xOffset = 0;
    double yOffset = 0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TabPane tabPaneLoginRegister;

    @FXML
    private Tab tabLogin;

    @FXML
    private Button buttonLogin;

    @FXML
    private TextField textAreaUsernameLogin;

    @FXML
    private PasswordField textFieldPasswordLogin;

    @FXML
    private ImageView imageviewIconUserLogin;

    @FXML
    private ImageView imageviewIconPasswordLogin;

    @FXML
    private Tab tabRegister;

    @FXML
    private Button buttonRegister;

    @FXML
    private TextField textAreaEmailRegister;

    @FXML
    private TextField textAreaUsernameRegister;

    @FXML
    private PasswordField textAreaPasswordRegister;

    @FXML
    private ImageView imageviewIconEmailRegister;

    @FXML
    private ImageView imageviewIconUserRegister;

    @FXML
    private ImageView imageviewIconPasswordRegister;

    @FXML
    private MenuBar MenuBarMenuLogin;

    @FXML
    private MenuItem TabMenuCerrar;

    @FXML
    private ImageView imageViewLogo;

    @FXML
    void initialize() {
        assert tabPaneLoginRegister != null : "fx:id=\"tabPaneLoginRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert tabLogin != null : "fx:id=\"tabLogin\" was not injected: check your FXML file 'Start.fxml'.";
        assert buttonLogin != null : "fx:id=\"buttonLogin\" was not injected: check your FXML file 'Start.fxml'.";
        assert textAreaUsernameLogin != null : "fx:id=\"textAreaUsernameLogin\" was not injected: check your FXML file 'Start.fxml'.";
        assert textFieldPasswordLogin != null : "fx:id=\"textFieldPasswordLogin\" was not injected: check your FXML file 'Start.fxml'.";
        assert imageviewIconUserLogin != null : "fx:id=\"imageviewIconUserLogin\" was not injected: check your FXML file 'Start.fxml'.";
        assert imageviewIconPasswordLogin != null : "fx:id=\"imageviewIconPasswordLogin\" was not injected: check your FXML file 'Start.fxml'.";
        assert tabRegister != null : "fx:id=\"tabRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert buttonRegister != null : "fx:id=\"buttonRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert textAreaEmailRegister != null : "fx:id=\"textAreaEmailRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert textAreaUsernameRegister != null : "fx:id=\"textAreaUsernameRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert textAreaPasswordRegister != null : "fx:id=\"textAreaPasswordRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert imageviewIconEmailRegister != null : "fx:id=\"imageviewIconEmailRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert imageviewIconUserRegister != null : "fx:id=\"imageviewIconUserRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert imageviewIconPasswordRegister != null : "fx:id=\"imageviewIconPasswordRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert MenuBarMenuLogin != null : "fx:id=\"MenuBarMenuLogin\" was not injected: check your FXML file 'Start.fxml'.";
        assert TabMenuCerrar != null : "fx:id=\"TabMenuCerrar\" was not injected: check your FXML file 'Start.fxml'.";
        assert imageViewLogo != null : "fx:id=\"imageViewLogo\" was not injected: check your FXML file 'Start.fxml'.";

    }

    @FXML
    void cerrarAplicacion(ActionEvent event) {

        //cierra la aplicacion desde el menu cerrar
        Stage stage = (Stage) this.MenuBarMenuLogin.getScene().getWindow();
        stage.close();
    }

    @FXML
    void login(ActionEvent event) throws IOException {

        if (textAreaUsernameLogin.getText().equals("Daniel") && textFieldPasswordLogin.getText().equals("password")) {

            //se cierra la ventana de login
            Stage stage = (Stage) this.MenuBarMenuLogin.getScene().getWindow();
            stage.close();

            //se inicia la ventana de chat
            Stage secondStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/vista/Chat.fxml"));
            Scene scene = new Scene(root);
            secondStage.setScene(scene);
            secondStage.initStyle(StageStyle.UNDECORATED); //ocultar el marco
            secondStage.show(); //mostrar la ventana
            secondStage.setAlwaysOnTop(true); //siempre encima
            secondStage.setResizable(false); //no modificable de tamaño
            secondStage.show();

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event1) {
                    xOffset = event1.getSceneX();
                    yOffset = event1.getSceneY();
                }
            });
            root.setOnMouseDragged((MouseEvent event1) -> {
                secondStage.setX(event1.getScreenX() - xOffset);
                secondStage.setY(event1.getScreenY() - yOffset);
            });

        } else {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();

        }

    }

    @FXML
    void register(ActionEvent event) {

        System.out.println("gracias por registrarte");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
