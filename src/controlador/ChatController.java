package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author daniel
 */
public class ChatController implements Initializable {

      @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane chatStage;

    @FXML
    private Button buttonSendChat;

    @FXML
    private TextArea textAreaWatchMessages;

    @FXML
    private TextField textFieldChatUsername;

    @FXML
    private TextField textFieldChatIPAddress;

    @FXML
    private TextField textFieldWriteArea;

    @FXML
    void sendMessage(ActionEvent event) {
        
        Stage stage = (Stage) this.chatStage.getScene().getWindow();
        stage.close();

    }

    @FXML
    void initialize() {
        assert chatStage != null : "fx:id=\"chatStage\" was not injected: check your FXML file 'U.I.Chat.fxml'.";
        assert buttonSendChat != null : "fx:id=\"buttonSendChat\" was not injected: check your FXML file 'U.I.Chat.fxml'.";
        assert textAreaWatchMessages != null : "fx:id=\"textAreaWatchMessages\" was not injected: check your FXML file 'U.I.Chat.fxml'.";
        assert textFieldChatUsername != null : "fx:id=\"textFieldChatUsername\" was not injected: check your FXML file 'U.I.Chat.fxml'.";
        assert textFieldChatIPAddress != null : "fx:id=\"textFieldChatIPAddress\" was not injected: check your FXML file 'U.I.Chat.fxml'.";
        assert textFieldWriteArea != null : "fx:id=\"textFieldWriteArea\" was not injected: check your FXML file 'U.I.Chat.fxml'.";

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
