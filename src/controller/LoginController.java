package controller;

import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DataBaseConnection;
import model.User;

/**
 * FXML Controller class
 *
 * @author daniel migales puertas
 *
 */
public class LoginController implements Initializable {

    //variables para el movimiento del raton
    double xOffset = 0;
    double yOffset = 0;

    LoginController controller;

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
    private ImageView imageViewLogo;

    @FXML
    void initialize() {
        assert tabPaneLoginRegister != null : "fx:id=\"tabPaneLoginRegister\" was not injected: check your FXML file 'Login.fxml'.";
        assert tabLogin != null : "fx:id=\"tabLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert buttonLogin != null : "fx:id=\"buttonLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert textAreaUsernameLogin != null : "fx:id=\"textAreaUsernameLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert textFieldPasswordLogin != null : "fx:id=\"textFieldPasswordLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert imageviewIconUserLogin != null : "fx:id=\"imageviewIconUserLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert imageviewIconPasswordLogin != null : "fx:id=\"imageviewIconPasswordLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert labelLoginOk != null : "fx:id=\"labelLoginOk\" was not injected: check your FXML file 'Login.fxml'.";
        assert tabRegister != null : "fx:id=\"tabRegister\" was not injected: check your FXML file 'Login.fxml'.";
        assert buttonRegister != null : "fx:id=\"buttonRegister\" was not injected: check your FXML file 'Login.fxml'.";
        assert textAreaEmailRegister != null : "fx:id=\"textAreaEmailRegister\" was not injected: check your FXML file 'Login.fxml'.";
        assert textAreaUsernameRegister != null : "fx:id=\"textAreaUsernameRegister\" was not injected: check your FXML file 'Login.fxml'.";
        assert textAreaPasswordRegister != null : "fx:id=\"textAreaPasswordRegister\" was not injected: check your FXML file 'Login.fxml'.";
        assert imageviewIconEmailRegister != null : "fx:id=\"imageviewIconEmailRegister\" was not injected: check your FXML file 'Login.fxml'.";
        assert imageviewIconUserRegister != null : "fx:id=\"imageviewIconUserRegister\" was not injected: check your FXML file 'Login.fxml'.";
        assert imageviewIconPasswordRegister != null : "fx:id=\"imageviewIconPasswordRegister\" was not injected: check your FXML file 'Login.fxml'.";
        assert labelRegisterOK != null : "fx:id=\"labelRegisterOK\" was not injected: check your FXML file 'Login.fxml'.";
        assert imageViewLogo != null : "fx:id=\"imageViewLogo\" was not injected: check your FXML file 'Login.fxml'.";

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        controller = this;
    }

    @FXML
    void login(ActionEvent event) throws IOException {

        //obtencion de los textos
        String username = textAreaUsernameLogin.getText();
        String password = textFieldPasswordLogin.getText();

        //llamada a la bd para la comprobacion de los datos
        DataBaseConnection connectionBD = new DataBaseConnection();
        User user = connectionBD.login(username, password);

        //validadcion del login
        if (textAreaUsernameLogin.getText().equals(user.getUsername())
                && textFieldPasswordLogin.getText().equals(user.getPassword())) {

            //se cierra la ventana de login
            closeStage(event);
            //se llama a la siguiente ventana
            newStage(username);

        } else {
            labelLoginOk.setText("Sus credenciales no son correctas");
        }

    }

    @FXML
    void register(ActionEvent event) {

        //obtener los datos de los textarea 
        String email = textAreaEmailRegister.getText();
        String username = textAreaUsernameRegister.getText();
        String password = textAreaPasswordRegister.getText();

        //conectar a la Bd para insertar los datos
        DataBaseConnection connectionBD = new DataBaseConnection();
        connectionBD.register(email, username, password);
        //System.out.println(email + "," + username + "," + password);

        //limpiar las casillas tras pulsar el boton para dar el efecto de que se ha enviado
        textAreaEmailRegister.clear();
        textAreaUsernameRegister.clear();
        textAreaPasswordRegister.clear();
        //etiqueta de confirmacion
        labelRegisterOK.setText("Registro completo");

    }

    public void newStage(String username) throws IOException {

        //se inicia la ventana de chat instanciando la clase Stage y FXMLLoader
        Stage secondStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        //se le indica la ruta del fxml
        Parent root = loader.load(getClass().getResource("/view/Chat.fxml").openStream());
        //ruta del css
        root.getStylesheets().add(getClass().getResource("/css/chatStyleCss.css").toExternalForm());
        //se instancia el controlador para poder pasar un parametro a la siguiente ventana
        ChatController instancia = loader.getController();
        instancia.getParams(controller, username);
        //se instancia nueva Scene
        Scene scene = new Scene(root);
        secondStage.setScene(scene);
        //se añaden los parametros de aspecto a la nueva ventana
        
        secondStage.setTitle("Java Socket Chat");
        //secondStage.setAlwaysOnTop(true); //siempre encima
        secondStage.setResizable(false); //no modificable de tamaño
        secondStage.initModality(Modality.APPLICATION_MODAL);
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

    private void closeStage(ActionEvent event) {

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
