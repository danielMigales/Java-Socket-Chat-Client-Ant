package controlador;

import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.ConexionBD;
import modelo.User;

/**
 * FXML Controller class
 *
 * @author daniel migales puertas
 *
 */
public class StartController implements Initializable {

    //variables para el movimiento del raton
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
    private Label labelLoginOk;

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
    private Label labelRegisterOK;

    @FXML
    private Tab tabAyuda;

    @FXML
    private TextArea textAreaHelpStart;

    @FXML
    private ChoiceBox<?> ChoiBoxHelpStart;

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
        assert labelLoginOk != null : "fx:id=\"labelLoginOk\" was not injected: check your FXML file 'Start.fxml'.";
        assert tabRegister != null : "fx:id=\"tabRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert buttonRegister != null : "fx:id=\"buttonRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert textAreaEmailRegister != null : "fx:id=\"textAreaEmailRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert textAreaUsernameRegister != null : "fx:id=\"textAreaUsernameRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert textAreaPasswordRegister != null : "fx:id=\"textAreaPasswordRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert imageviewIconEmailRegister != null : "fx:id=\"imageviewIconEmailRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert imageviewIconUserRegister != null : "fx:id=\"imageviewIconUserRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert imageviewIconPasswordRegister != null : "fx:id=\"imageviewIconPasswordRegister\" was not injected: check your FXML file 'Start.fxml'.";
        assert labelRegisterOK != null : "fx:id=\"labelRegisterOK\" was not injected: check your FXML file 'Start.fxml'.";
        assert tabAyuda != null : "fx:id=\"tabAyuda\" was not injected: check your FXML file 'Start.fxml'.";
        assert textAreaHelpStart != null : "fx:id=\"textAreaHelpStart\" was not injected: check your FXML file 'Start.fxml'.";
        assert ChoiBoxHelpStart != null : "fx:id=\"ChoiBoxHelpStart\" was not injected: check your FXML file 'Start.fxml'.";
        assert imageViewLogo != null : "fx:id=\"imageViewLogo\" was not injected: check your FXML file 'Start.fxml'.";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void login(ActionEvent event) throws IOException {

        //obtencion de los textos
        String username = textAreaUsernameLogin.getText();
        String password = textFieldPasswordLogin.getText();

        //llamada a la bd para la comprobacion de los datos
        ConexionBD conexionBD = new ConexionBD();
        User user = conexionBD.login(username, password);

        //validadcion del login
        if (textAreaUsernameLogin.getText().equals(user.getUsername())
                && textFieldPasswordLogin.getText().equals(user.getPassword())) {

            //se cierra la ventana de login
            //se llama a la siguiente ventana
            newStage();

        } else {
            labelLoginOk.setText("Sus credenciales no son correctas");
        }

    }

    @FXML
    void register(ActionEvent event) {

        String email = textAreaEmailRegister.getText();
        String username = textAreaUsernameRegister.getText();
        String password = textAreaPasswordRegister.getText();

        ConexionBD conexionBD = new ConexionBD();
        conexionBD.register(email, username, password);

        System.out.println(email + username + password);

        textAreaEmailRegister.clear();
        textAreaUsernameRegister.clear();
        textAreaPasswordRegister.clear();
        labelRegisterOK.setText("Registro completo");

    }

    public void newStage() throws IOException {

        //se inicia la ventana de chat
        Stage secondStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/vista/Chat.fxml"));
        Scene scene = new Scene(root);
        secondStage.setScene(scene);
        secondStage.initStyle(StageStyle.UNIFIED); //aparece el boton minimizar y cerrar en el marco
        secondStage.setTitle("Java Socket Chat");
        secondStage.show();

        //arrastrar con el raton la ventana
        root.setOnMousePressed((MouseEvent event1) -> {
            xOffset = event1.getSceneX();
            yOffset = event1.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event1) -> {
            secondStage.setX(event1.getScreenX() - xOffset);
            secondStage.setY(event1.getScreenY() - yOffset);
        });

    }
}
