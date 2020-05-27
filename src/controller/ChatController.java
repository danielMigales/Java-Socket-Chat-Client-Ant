package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import dataPaquete.DataPaquete;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Cryptography;

/**
 * FXML Controller class
 *
 * @author daniel migales puertas
 *
 */
public class ChatController implements Initializable {

    private String SERVER_IP;
    private final int SERVER_PORT = 40000;
    private final int CLIENT_PORT = 50000;

    //instancia del controlador de la primera pantalla (para obtener el username desde alli)
    StartController controller2;

    //variables de Scenebuilder
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane AnchorPaneChat;

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
    private TextField textAreaHostNameChat;

    @FXML
    private Button buttonLogOutChat;

    @FXML
    private TextField textFieldIP_Server;

    @FXML
    private TextField textFieldIPdestino;

    @FXML
    void initialize() {
        assert AnchorPaneChat != null : "fx:id=\"AnchorPaneChat\" was not injected: check your FXML file 'Chat.fxml'.";
        assert buttonSendChat != null : "fx:id=\"buttonSendChat\" was not injected: check your FXML file 'Chat.fxml'.";
        assert textAreaWatchMessages != null : "fx:id=\"textAreaWatchMessages\" was not injected: check your FXML file 'Chat.fxml'.";
        assert textFieldChatUsername != null : "fx:id=\"textFieldChatUsername\" was not injected: check your FXML file 'Chat.fxml'.";
        assert textFieldChatIPAddress != null : "fx:id=\"textFieldChatIPAddress\" was not injected: check your FXML file 'Chat.fxml'.";
        assert textFieldWriteArea != null : "fx:id=\"textFieldWriteArea\" was not injected: check your FXML file 'Chat.fxml'.";
        assert textAreaHostNameChat != null : "fx:id=\"textAreaHostNameChat\" was not injected: check your FXML file 'Chat.fxml'.";
        assert buttonLogOutChat != null : "fx:id=\"buttonLogOutChat\" was not injected: check your FXML file 'Chat.fxml'.";
        assert textFieldIP_Server != null : "fx:id=\"textFieldIP_Server\" was not injected: check your FXML file 'Chat.fxml'.";
        assert textFieldIPdestino != null : "fx:id=\"textFieldIPdestino\" was not injected: check your FXML file 'Chat.fxml'.";
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Al iniciar la escena se realiza lo siguiente:
        getUserIP();
        socketThread();
    }

    public void getParams(StartController controller, String username) {

        textFieldChatUsername.setText(username);
        controller2 = controller;

    }

    @FXML
    void sendMessage(ActionEvent event) {

        //obtener el mensaje del los campos de texto de la interfaz
        var userName = textFieldChatUsername.getText();
        var IPAddress = textFieldChatIPAddress.getText();
        var message = textFieldWriteArea.getText();
        var destinatario = textFieldIPdestino.getText();

        //encriptar mensaje
        Cryptography encryption = new Cryptography();
        String cryptoMessage = "";
        try {
            cryptoMessage = encryption.encrypt(message);
        } catch (Exception ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("El mensaje ha sido encriptado antes del envio: " + cryptoMessage);

        //se envia encriptado
        DataPaquete outputData = new DataPaquete(userName, IPAddress, cryptoMessage, destinatario);
        //System.out.println(mensaje);

        try {

            SERVER_IP = textFieldIP_Server.getText();

            //flujo de informacion y se asocia al socket
            try ( //crear el socket (conector con el servidor)
                    Socket socket = new Socket(SERVER_IP, SERVER_PORT); //flujo de informacion y se asocia al socket
                    java.io.ObjectOutputStream outFlow = new ObjectOutputStream(socket.getOutputStream())) {
                //enviar al dato
                outFlow.writeObject(outputData);
            }

            //el mensaje sin encriptar se muestra en el area de chat
            //textAreaWatchMessages.appendText(message + "\n");
            //se limpia el area de envio de mensajes
            textFieldWriteArea.clear();

        } catch (IOException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se pudo crear el socket para conectar con el servidor en la direccion " + SERVER_IP);
        }

    }

    public void getUserIP() {

        //obtener los datos para colocarlos en el las casillas del chat (datos usuario)
        try {

            //obtener direccion IP y el nombre de host
            InetAddress address = InetAddress.getLocalHost();
            String hostName = address.getHostName(); //nombre de la maquina
            byte[] IPAddress = address.getAddress(); //direccion ip
            String sIPAddress = "";

            for (int x = 0; x < IPAddress.length; x++) {
                if (x > 0) {
                    sIPAddress += ".";
                }
                sIPAddress += IPAddress[x] & 255;
            }
            String localIpAddress = address.getHostAddress(); //direccion ip localhost

            //colocarlos en los textfield
            textAreaHostNameChat.setText(hostName);
            textFieldChatIPAddress.setText(sIPAddress);

        } catch (UnknownHostException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void socketThread() {

        //lanzar el hilo del socket
        var thread = new Thread() {
            @Override
            public void run() {

                try {
                    DataPaquete inputData;
                    var serverClient = new ServerSocket(CLIENT_PORT);
                    Socket socket;

                    while (true) {
                        socket = serverClient.accept();

                        //crear el flujo de entrada asociado al socket
                        var inputFlow = new ObjectInputStream(socket.getInputStream());

                        //extraer el mensaje
                        inputData = (DataPaquete) inputFlow.readObject();

                        var userName = inputData.getNombreUsuario();
                        var ipAddress = inputData.getDireccionIP();
                        var message = inputData.getMensaje();

                        Cryptography decryption = new Cryptography();

                        String messageDecrypted = decryption.decrypt(message);

                        var concatenatedMessage = userName + "/" + ipAddress + " dice:\t\t" + messageDecrypted + "\n";
                        System.out.println(concatenatedMessage);

                        //visualizar los datos en la interfaz
                        Platform.runLater(() -> {
                            textAreaWatchMessages.appendText(concatenatedMessage);
                        });
                    }
                } catch (IOException | ClassNotFoundException ex) {

                    System.out.println("No se ha podido crear el servidor de escucha en el puerto " + CLIENT_PORT
                            + " en la aplicacion cliente");
                    System.out.println("No se ha encontrado la clase DataPaquete");
                    System.out.println(ex.getMessage());
                } catch (Exception ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        //iniciar el hilo
        thread.start();
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        stage = new Stage();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/Start.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
