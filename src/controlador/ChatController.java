package controlador;

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
import modelo.DataPaquete;
import modelo.Hash;
import static modelo.Hash.hash;
import modelo.User;

/**
 * FXML Controller class
 *
 * @author daniel
 */
public class ChatController implements Initializable {

    //variables 
    private final String IP_SERVIDOR = "192.168.1.49";
    private final int PUERTO_SERVIDOR = 40000;
    private final int PUERTO_CLIENTE = 50000;
    
    StartController controller2;

    //variables de Scenebuilder
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane AnchorPane;

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
    void initialize() {
        assert AnchorPane != null : "fx:id=\"AnchorPane\" was not injected: check your FXML file 'Chat.fxml'.";
        assert buttonSendChat != null : "fx:id=\"buttonSendChat\" was not injected: check your FXML file 'Chat.fxml'.";
        assert textAreaWatchMessages != null : "fx:id=\"textAreaWatchMessages\" was not injected: check your FXML file 'Chat.fxml'.";
        assert textFieldChatUsername != null : "fx:id=\"textFieldChatUsername\" was not injected: check your FXML file 'Chat.fxml'.";
        assert textFieldChatIPAddress != null : "fx:id=\"textFieldChatIPAddress\" was not injected: check your FXML file 'Chat.fxml'.";
        assert textFieldWriteArea != null : "fx:id=\"textFieldWriteArea\" was not injected: check your FXML file 'Chat.fxml'.";
        assert textAreaHostNameChat != null : "fx:id=\"textAreaHostNameChat\" was not injected: check your FXML file 'Chat.fxml'.";
        assert buttonLogOutChat != null : "fx:id=\"buttonLogOutChat\" was not injected: check your FXML file 'Chat.fxml'.";

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
        hiloSocket();

    }

    public void getParams(StartController controller, String username) {

        textFieldChatUsername.setText(username);
        controller2 = controller;

    }

    @FXML
    void sendMessage(ActionEvent event) {

        //obtener el mensaje del los campos de texto de la interfaz
        var nombreUsuario = textFieldChatUsername.getText();
        var direccionIP = textFieldChatIPAddress.getText();
        var mensaje = textFieldWriteArea.getText();

        //encriptarlo con hash
        byte[] messageByte = hash(mensaje);
        final String messageHash = Hash.byteToHex(messageByte);
        System.out.println("El mensaje ha sido encriptado antes del envio: " + messageHash);

        //se envia encriptado
        var dataSalida = new DataPaquete(nombreUsuario, direccionIP, messageHash);
        //System.out.println(mensaje);

        try {

            //flujo de informacion y se asocia al socket
            try ( //crear el socket (conector con el servidor)
                    Socket socket = new Socket(IP_SERVIDOR, PUERTO_SERVIDOR); //flujo de informacion y se asocia al socket
                    java.io.ObjectOutputStream flujoSalida = new ObjectOutputStream(socket.getOutputStream())) {
                //enviar al dato
                flujoSalida.writeObject(dataSalida);
            }

            //el mensaje sin encriptar se muestra en el area de chat
            textAreaWatchMessages.appendText(mensaje + "\n");
            //se limpia el area de envio de mensajes
            textFieldWriteArea.clear();

        } catch (IOException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se pudo crear el socket para conectar con el servidor en la direccion " + IP_SERVIDOR);
        }

    }

    public void getUserIP() {

        //obtener los datos para colocarlos en el las casillas del chat (datos usuario)
        try {
            User user;
            //obtener el nombre de usuario

            //obtener direccion IP y el nombre de host y colocarlos
            InetAddress address = InetAddress.getLocalHost();
            String hostName = address.getHostName();
            textAreaHostNameChat.setText(hostName);
            String localIpAddress = address.getHostAddress();
            textFieldChatIPAddress.setText(localIpAddress);

        } catch (UnknownHostException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void hiloSocket() {

        //lanzar el hilo del socket
        var t = new Thread() {
            @Override
            public void run() {

                try {
                    var serverCliente = new ServerSocket(PUERTO_CLIENTE);

                    while (true) {
                        Socket socket = serverCliente.accept();

                        //crear el flujo de entrada asociado al socket
                        var flujoEntrada = new ObjectInputStream(socket.getInputStream());

                        //extraer el mensaje
                        DataPaquete dataEntrada = (DataPaquete) flujoEntrada.readObject();

                        var nombreUsuario = dataEntrada.getNombreUsuario();
                        var direccionIP = dataEntrada.getDireccionIP();
                        var mensaje = dataEntrada.getMensaje();

                        //visualizar los datos en la interfaz
                        var mensajeConcatenado = nombreUsuario + "/" + direccionIP + " dice:\t" + mensaje + "\n";
                        System.out.println(mensajeConcatenado);
                        textAreaWatchMessages.appendText(mensajeConcatenado);
                    }

                } catch (IOException | ClassNotFoundException ex) {

                    System.out.println("No se ha podido crear el servidor de escucha en el puerto " + PUERTO_CLIENTE
                            + " en la aplicacion cliente");
                    System.out.println("No se ha encontrado la clase DataPaquete");
                    System.out.println(ex.getMessage());
                }
            }
        };
        //iniciar el hilo
        t.start();
    }

    @FXML
    void logOut(ActionEvent event) {

    }

}
